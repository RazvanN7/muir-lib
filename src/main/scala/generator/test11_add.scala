package dataflow

import chisel3._
import chisel3.util._
import chisel3.Module
import chisel3.testers._
import chisel3.iotesters._
import org.scalatest.{FlatSpec, Matchers}
import muxes._
import config._
import control._
import util._
import interfaces._
import regfile._
import memory._
import stack._
import arbiters._
import loop._
import accel._
import node._


/**
  * This Object should be initialized at the first step
  * It contains all the transformation from indices to their module's name
  */

object Data_test11_add_FlowParam{

  val bb_entry_pred = Map(
    "active" -> 0
  )


  val bb_entry_activate = Map(
    "add0" -> 0,
    "ret1" -> 1
  )


  //  %add = add i32 %a, %b, !UID !8, !ScalaLabel !9
  val add0_in = Map(
    "field0" -> 0,
    "field1" -> 0
  )


  //  ret i32 %add, !UID !10, !BB_UID !11, !ScalaLabel !12
  val ret1_in = Map(
    "add0" -> 0
  )


}




  /* ================================================================== *
   *                   PRINTING PORTS DEFINITION                        *
   * ================================================================== */


abstract class test11_addDFIO(implicit val p: Parameters) extends Module with CoreParams {
  val io = IO(new Bundle {
    val in = Flipped(new CallDecoupled(List(32,32)))
    val CacheResp = Flipped(Valid(new CacheRespT))
    val CacheReq = Decoupled(new CacheReq)
    val out = new CallDecoupled(List(32))
  })
}




  /* ================================================================== *
   *                   PRINTING MODULE DEFINITION                       *
   * ================================================================== */


class test11_addDF(implicit p: Parameters) extends test11_addDFIO()(p) {



  /* ================================================================== *
   *                   PRINTING MEMORY SYSTEM                           *
   * ================================================================== */


	val StackPointer = Module(new Stack(NumOps = 1))

	val RegisterFile = Module(new TypeStackFile(ID=0,Size=32,NReads=2,NWrites=2)
		            (WControl=new WriteMemoryController(NumOps=2,BaseSize=2,NumEntries=2))
		            (RControl=new ReadMemoryController(NumOps=2,BaseSize=2,NumEntries=2)))

	val CacheMem = Module(new UnifiedController(ID=0,Size=32,NReads=2,NWrites=2)
		            (WControl=new WriteMemoryController(NumOps=2,BaseSize=2,NumEntries=2))
		            (RControl=new ReadMemoryController(NumOps=2,BaseSize=2,NumEntries=2))
		            (RWArbiter=new ReadWriteArbiter()))

  io.CacheReq <> CacheMem.io.CacheReq
  CacheMem.io.CacheResp <> io.CacheResp



  /* ================================================================== *
   *                   PRINTING LOOP HEADERS                            *
   * ================================================================== */


  //Function doesn't have any loop


  /* ================================================================== *
   *                   PRINTING BASICBLOCKS                             *
   * ================================================================== */


  //Initializing BasicBlocks: 

  val bb_entry = Module(new BasicBlockNoMaskNode(NumInputs = 1, NumOuts = 2, BID = 0)(p))






  /* ================================================================== *
   *                   PRINTING INSTRUCTIONS                            *
   * ================================================================== */


  //Initializing Instructions: 

  // [BasicBlock]  entry:

  //  %add = add i32 %a, %b, !UID !8, !ScalaLabel !9
  val add0 = Module (new ComputeNode(NumOuts = 1, ID = 0, opCode = "add")(sign=false)(p))


  //  ret i32 %add, !UID !10, !BB_UID !11, !ScalaLabel !12
  val ret1 = Module(new RetNode(NumOuts=1, ID=1))







  /* ================================================================== *
   *                   INITIALIZING PARAM                               *
   * ================================================================== */


  /**
    * Instantiating parameters
    */
  val param = Data_test11_add_FlowParam



  /* ================================================================== *
   *                   CONNECTING BASIC BLOCKS TO PREDICATE INSTRUCTIONS*
   * ================================================================== */


  /**
     * Connecting basic blocks to predicate instructions
     */


  bb_entry.io.predicateIn(0) <> io.in.enable

  /**
    * Connecting basic blocks to predicate instructions
    */


  // There is no branch instruction



  // There is no detach instruction




  /* ================================================================== *
   *                   CONNECTING BASIC BLOCKS TO INSTRUCTIONS          *
   * ================================================================== */


  /**
    * Wiring enable signals to the instructions
    */
  //Wiring enable signals

  add0.io.enable <> bb_entry.io.Out(param.bb_entry_activate("add0"))

  ret1.io.enable <> bb_entry.io.Out(param.bb_entry_activate("ret1"))





  /* ================================================================== *
   *                   DUMPING PHI NODES                                *
   * ================================================================== */


  /**
    * Connecting PHI Masks
    */
  //Connect PHI node

  /**
    * Connecting PHI Masks
    */
  //Connect PHI node

  /**
    * Connecting PHI Masks
    */
  //Connect PHI node
  // There is no PHI node


  /* ================================================================== *
   *                   CONNECTING LOOPHEADERS                           *
   * ================================================================== */


  //Function doesn't have any for loop


  /* ================================================================== *
   *                   DUMPING DATAFLOW                                 *
   * ================================================================== */


  /**
    * Connecting Dataflow signals
    */

  // Wiring Binary instruction to the function argument
  add0.io.LeftIO <> io.in.data("field0")

  // Wiring Binary instruction to the function argument
  add0.io.RightIO <> io.in.data("field1")

  // Wiring return instructions
  ret1.io.InputIO <> add0.io.Out(param.ret1_in("add0"))
  io.out.data("field0") <> ret1.io.Out(0)

  io.out.enable.valid := ret1.io.Out(0).valid
  io.out.enable.bits.control := true.B


}

import java.io.{File, FileWriter}
object test11_addMain extends App {
  val dir = new File("RTL/test11_add") ; dir.mkdirs
  implicit val p = config.Parameters.root((new MiniConfig).toInstance)
  val chirrtl = firrtl.Parser.parse(chisel3.Driver.emit(() => new test11_addDF()))

  val verilogFile = new File(dir, s"${chirrtl.main}.v")
  val verilogWriter = new FileWriter(verilogFile)
  val compileResult = (new firrtl.VerilogCompiler).compileAndEmit(firrtl.CircuitState(chirrtl, firrtl.ChirrtlForm))
  val compiledStuff = compileResult.getEmittedCircuit
  verilogWriter.write(compiledStuff.value)
  verilogWriter.close()
}

