package node

import chisel3._
import chisel3.util._
import org.scalacheck.Prop.False

import config._
import interfaces._
import utility._
import Constants._
import utility.UniformPrintfs


/**
  * @note
  * There are three types of handshaking:
  * 1)   There is no ordering -> (No PredOp/ No SuccOp)
  * it has only vectorized output
  * @note HandshakingIONPS
  * @todo Put special case for singl output vs two outputs
  *
  *       2)  There is ordering    -> (PredOp/ SuccOp)
  *       vectorized output/succ/pred
  * @note HandshakingIOPS
  *
  *       3)  There is vectorized output + vectorized input
  *       No ordering
  * @todo needs to be implimented
  * @note HandshakingIO
  *
  *       4)  Control handshaking -> The only input is enable signal
  * @note HandshakingCtrl
  *
  *       5) Control handshaking (PHI) -> There is mask and enable signal
  * @note HandshakingCtrlPhi
  *
  */


/**
  * @note Type1
  *       Handshaking IO with no ordering.
  * @note IO Bundle for Handshaking
  * @param NumOuts Number of outputs
  *
  */
class HandShakingIONPS(val NumOuts: Int)
                      (implicit p: Parameters)
  extends CoreBundle()(p) {
  // Predicate enable
  val enable = Flipped(Decoupled(Bool()))
  // Output IO
  val Out = Vec(NumOuts, Decoupled(DataBundle.default))
}


/**
  * @note Type2
  *       Handshaking IO.
  * @note IO Bundle for Handshaking
  *       PredOp: Vector of RvAckIOs
  *       SuccOp: Vector of RvAckIOs
  *       Out      : Vector of Outputs
  * @param NumPredOps Number of parents
  * @param NumSuccOps Number of successors
  * @param NumOuts    Number of outputs
  *
  *
  */
class HandShakingIOPS(val NumPredOps: Int,
                      val NumSuccOps: Int,
                      val NumOuts: Int)
                     (implicit p: Parameters)
  extends CoreBundle()(p) {
  // Predicate enable
  val enable = Flipped(Decoupled(Bool()))
  // Predecessor Ordering
  val PredOp = Vec(NumPredOps, Flipped(Decoupled(new AckBundle)))
  // Successor Ordering
  val SuccOp = Vec(NumSuccOps, Decoupled(new AckBundle()))
  // Output IO
  val Out = Vec(NumOuts, Decoupled(DataBundle.default))
}


/**
  * @note Type4
  *       Handshaking IO with no ordering for control nodes
  * @note IO Bundle for Handshaking
  * @param NumInputs Number of input basicBlocks
  * @param NumOuts   Number of outputs (Num Inst.)
  *
  */
class HandShakingCtrlMaskIO(val NumInputs: Int,
                        val NumOuts: Int,
                        val NumPhi: Int)
                       (implicit p: Parameters)
  extends CoreBundle()(p) {
  // Output IO
  val MaskBB = Vec(NumPhi, Decoupled(UInt(NumInputs.W)))
  val Out = Vec(NumOuts, Decoupled(Bool()))
}


/**
  * @note Type5
  *       Handshaking IO with no ordering for control nodes
  * @note IO Bundle for Handshaking
  * @param NumInputs Number of input basicBlocks
  * @param NumOuts   Number of outputs (Num Inst.)
  *
  */
class HandShakingCtrlNoMaskIO(val NumInputs: Int,
                        val NumOuts: Int)
                       (implicit p: Parameters)
  extends CoreBundle()(p) {
  // Output IO
  val Out = Vec(NumOuts, Decoupled(Bool()))
}


/**
  * @brief Handshaking between data nodes with no ordering.
  * @details Sets up base registers and hand shaking registers
  * @param NumOuts Number of outputs
  * @param ID      Node id
  * @return Module
  */

class HandShakingNPS(val NumOuts: Int,
                     val ID: Int)
                    (implicit val p: Parameters)
  extends Module with CoreParams with UniformPrintfs {

  lazy val io = IO(new HandShakingIONPS(NumOuts))

  /*=================================
  =            Registers            =
  =================================*/
  // Extra information
  val token = RegInit(0.U)
  val nodeID_R = RegInit(ID.U)

  // Enable
  val enable_R = RegInit(true.B)
  val enable_valid_R = RegInit(false.B)

  // Output Handshaking
  val out_ready_R = RegInit(Vec(Seq.fill(NumOuts)(false.B)))
  val out_valid_R = RegInit(Vec(Seq.fill(NumOuts)(false.B)))


  /*============================*
   *           Wiring           *
   *============================*/

  // Wire up OUT READYs and VALIDs
  for (i <- 0 until NumOuts) {
    io.Out(i).valid := out_valid_R(i)
    when(io.Out(i).fire()) {
      // Detecting when to reset
      out_ready_R(i) := io.Out(i).ready
      // Propagating output
      out_valid_R(i) := false.B
    }
  }

  // Wire up enable READY and VALIDs
  io.enable.ready := ~enable_valid_R
  when(io.enable.fire()) {
    enable_valid_R := io.enable.valid
    enable_R := io.enable.bits
  }

  /*===================================*
   *            Helper Checks          *
   *===================================*/
  def IsEnable(): Bool = {
    enable_R
  }

  def IsEnableValid(): Bool = {
    enable_valid_R
  }

  def ResetEnable(): Unit = {
    enable_valid_R := false.B
  }

  // OUTs
  def IsOutReady(): Bool = {
    out_ready_R.asUInt.andR
  }

  def IsOutValid(): Bool = {
    out_valid_R.asUInt.andR
  }

  def ValidOut(): Unit = {
    out_valid_R := Vec(Seq.fill(NumOuts) {
      true.B
    })
  }

  def InvalidOut(): Unit = {
    out_valid_R := Vec(Seq.fill(NumOuts) {
      false.B
    })
  }

  def Reset(): Unit = {
    out_ready_R := Vec(Seq.fill(NumOuts) {
      false.B
    })
    enable_valid_R := false.B
  }
}


/**
  * @brief Handshaking between data nodes.
  * @details Sets up base registers and hand shaking registers
  * @param NumPredOps Number of parents
  * @param NumSuccOps Number of successors
  * @param NumOuts    Number of outputs
  * @param ID         Node id
  * @return Module
  */

class HandShaking(val NumPredOps: Int,
                  val NumSuccOps: Int,
                  val NumOuts: Int,
                  val ID: Int)
                 (implicit val p: Parameters)
  extends Module with CoreParams with UniformPrintfs {

  lazy val io = IO(new HandShakingIOPS(NumPredOps, NumSuccOps, NumOuts))

  /*=================================
  =            Registers            =
  =================================*/
  // Extra information
  val token = RegInit(0.U)
  val nodeID_R = RegInit(ID.U)

  // Enable
  val enable_R = RegInit(true.B)
  val enable_valid_R = RegInit(false.B)

  // Predecessor Handshaking
  val pred_valid_R  = Seq.fill(NumPredOps)(RegInit(false.B))
  val pred_bundle_R = Seq.fill(NumPredOps)(RegInit(AckBundle.default))

  // Successor Handshaking. Registers needed
  val succ_ready_R  = Seq.fill(NumSuccOps)(RegInit(false.B))
  val succ_valid_R  = Seq.fill(NumSuccOps)(RegInit(false.B))
  val succ_bundle_R = Seq.fill(NumSuccOps)(RegInit(AckBundle.default))

  // Output Handshaking
  val out_ready_R = RegInit(Vec(Seq.fill(NumOuts)(false.B)))
  val out_valid_R = RegInit(Vec(Seq.fill(NumOuts)(false.B)))


  /*==============================
  =            Wiring            =
  ==============================*/
  // Wire up Successors READYs and VALIDs
  for (i <- 0 until NumSuccOps) {
    io.SuccOp(i).valid := succ_valid_R(i)
    io.SuccOp(i).bits := succ_bundle_R(i)
    when(io.SuccOp(i).fire()) {
      succ_ready_R(i) := io.SuccOp(i).ready
      succ_valid_R(i) := false.B
    }
  }

  // Wire up OUT READYs and VALIDs
  for (i <- 0 until NumOuts) {
    io.Out(i).valid := out_valid_R(i)
    when(io.Out(i).fire()) {
      // Detecting when to reset
      out_ready_R(i) := io.Out(i).ready
      // Propagating output
      out_valid_R(i) := false.B
    }
  }
  // Wire up Predecessor READY and VALIDs
  for (i <- 0 until NumPredOps) {
    io.PredOp(i).ready := ~pred_valid_R(i)
    when(io.PredOp(i).fire()) {
      pred_valid_R(i) := io.PredOp(i).valid
      pred_bundle_R(i) := io.PredOp(i).bits
    }
  }

  //Enable is an input
  // Wire up enable READY and VALIDs
  io.enable.ready := ~enable_valid_R
  when(io.enable.fire()) {
    enable_valid_R := io.enable.valid
    enable_R := io.enable.bits
  }

  /*=====================================
  =            Helper Checks            =
  =====================================*/
  def IsEnable(): Bool = {
   return enable_R
 }

  def IsEnableValid(): Bool = {
    enable_valid_R
  }

  def ResetEnable(): Unit = {
    enable_valid_R := false.B
  }

  // Check if Predecssors have fired
  def IsPredValid(): Bool = {
   if (NumPredOps == 0) {
     return true.B
     } else {
      Vec(pred_valid_R).asUInt.andR
    }
  }

  // Fire Predecessors
  def ValidPred(): Unit = {
    pred_valid_R.map { _ := true.B }
    // pred_valid_R := Seq.fill(NumPredOps) {
    //   true.B
    // }
  }

  // Clear predessors
  def InvalidPred(): Unit = {
    pred_valid_R.foreach { _ := false.B }
    // pred_valid_R := Vec(Seq.fill(NumPredOps) {
    //   false.B
    // })
  }

  // Successors
  def IsSuccReady(): Bool = {
    if (NumSuccOps == 0) {
      return true.B
    } else {
      Vec(succ_ready_R).asUInt.andR
    }
  }

  def ValidSucc(): Unit = {
    succ_valid_R.foreach { _ := true.B }
  }

  def InvalidSucc(): Unit = {
    succ_valid_R.foreach { _ := false.B }
  }

  // OUTs
  def IsOutReady(): Bool = {
    out_ready_R.asUInt.andR
  }

  def ValidOut(): Unit = {
    out_valid_R := Vec(Seq.fill(NumOuts) {
      true.B
    })
  }

  def InvalidOut(): Unit = {
    out_valid_R := Vec(Seq.fill(NumOuts) {
      false.B
    })
  }

  def Reset(): Unit = {
    pred_valid_R.foreach { _ := false.B }

    succ_ready_R.foreach { _ := false.B }
   
    out_ready_R := Vec(Seq.fill(NumOuts) {
      false.B
    })
    enable_valid_R := false.B
  }
}

/**
  * @brief Handshaking between control nodes.
  * @details Sets up base registers and hand shaking registers
  * @param NumInputs Number of basick block inputs
  * @param NumOuts   Number of outputs
  * @param NumPhi    Number existing phi node
  * @param BID       Basic block id
  * @return Module
  */

class HandShakingCtrlMask(val NumInputs: Int,
                      val NumOuts: Int,
                      val NumPhi: Int,
                      val BID: Int)
                     (implicit val p: Parameters)
  extends Module with CoreParams with UniformPrintfs {

  lazy val io = IO(new HandShakingCtrlMaskIO(NumInputs, NumOuts, NumPhi))

  /*=================================
  =            Registers            =
  =================================*/
  // Extra information
  val token = RegInit(0.U)
  val nodeID_R = RegInit(BID.U)

  // Output Handshaking
  val out_ready_R = RegInit(Vec(Seq.fill(NumOuts)(false.B)))
  val out_valid_R = RegInit(Vec(Seq.fill(NumOuts)(false.B)))

  // Mask handshaking
  val mask_ready_R = RegInit(Vec(Seq.fill(NumPhi)(false.B)))
  val mask_valid_R = RegInit(Vec(Seq.fill(NumPhi)(false.B)))


  /*============================*
   *           Wiring           *
   *============================*/

  // Wire up OUT READYs and VALIDs
  for (i <- 0 until NumOuts) {
    io.Out(i).valid := out_valid_R(i)
    when(io.Out(i).fire()) {
      // Detecting when to reset
      out_ready_R(i) := io.Out(i).ready
      // Propagating output
      out_valid_R(i) := false.B
    }
  }

  // Wire up MASK Readys and Valids
  for (i <- 0 until NumPhi) {
    io.MaskBB(i).valid := mask_valid_R(i)
    when(io.MaskBB(i).fire()) {
      // Detecting when to reset
      mask_ready_R(i) := io.MaskBB(i).ready
      // Propagating mask
      out_valid_R(i) := false.B
    }

  }

  /*===================================*
   *            Helper Checks          *
   *===================================*/
  // OUTs
  def IsOutReady(): Bool = {
    out_ready_R.asUInt.andR
  }

  def IsMaskReady(): Bool = {
    mask_ready_R.asUInt.andR
  }

  def IsOutValid(): Bool = {
    out_valid_R.asUInt.andR
  }

  def IsMaskValid(): Bool = {
    mask_valid_R.asUInt.andR
  }

  def ValidOut(): Unit = {
    out_valid_R := Vec(Seq.fill(NumOuts) {
      true.B
    })

    mask_valid_R := Vec(Seq.fill(NumPhi) {
      true.B
    })
  }

  def InvalidOut(): Unit = {
    out_valid_R := Vec(Seq.fill(NumOuts) {
      false.B
    })

    mask_valid_R := Vec(Seq.fill(NumPhi) {
      false.B
    })
  }

  def Reset(): Unit = {
    out_ready_R := Vec(Seq.fill(NumOuts) {
      false.B
    })

    mask_ready_R := Vec(Seq.fill(NumPhi) {
      false.B
    })
  }
}


/**
  * @brief Handshaking between control nodes.
  * @details Sets up base registers and hand shaking registers
  * @param NumInputs Number of basick block inputs
  * @param NumOuts   Number of outputs
  * @param BID       Basic block id
  * @return Module
  */

class HandShakingCtrlNoMask(val NumInputs: Int,
                      val NumOuts: Int,
                      val BID: Int)
                     (implicit val p: Parameters)
  extends Module with CoreParams with UniformPrintfs {

  lazy val io = IO(new HandShakingCtrlNoMaskIO(NumInputs, NumOuts))

  /*=================================
  =            Registers            =
  =================================*/
  // Extra information
  val token = RegInit(0.U)
  val nodeID_R = RegInit(BID.U)

  // Output Handshaking
  val out_ready_R = RegInit(Vec(Seq.fill(NumOuts)(false.B)))
  val out_valid_R = RegInit(Vec(Seq.fill(NumOuts)(false.B)))

  /*============================*
   *           Wiring           *
   *============================*/

  // Wire up OUT READYs and VALIDs
  for (i <- 0 until NumOuts) {
    io.Out(i).valid := out_valid_R(i)
    when(io.Out(i).fire()) {
      // Detecting when to reset
      out_ready_R(i) := io.Out(i).ready
      // Propagating output
      out_valid_R(i) := false.B
    }
  }

  /*===================================*
   *            Helper Checks          *
   *===================================*/
  // OUTs
  def IsOutReady(): Bool = {
    out_ready_R.asUInt.andR
  }

  def IsOutValid(): Bool = {
    out_valid_R.asUInt.andR
  }

  def ValidOut(): Unit = {
    out_valid_R := Vec(Seq.fill(NumOuts) {
      true.B
    })
  }

  def InvalidOut(): Unit = {
    out_valid_R := Vec(Seq.fill(NumOuts) {
      false.B
    })

  }

  def Reset(): Unit = {
    out_ready_R := Vec(Seq.fill(NumOuts) {
      false.B
    })
  }

}
