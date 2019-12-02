package dandelion.generator

import chisel3._
import chisel3.util._
import chisel3.Module._
import chisel3.testers._
import chisel3.iotesters._
import dandelion.accel._
import dandelion.arbiters._
import dandelion.config._
import dandelion.control._
import dandelion.fpu._
import dandelion.interfaces._
import dandelion.junctions._
import dandelion.loop._
import dandelion.memory._
import dandelion.memory.stack._
import dandelion.node._
import muxes._
import org.scalatest._
import regfile._
import util._


  /* ================================================================== *
   *                   PRINTING PORTS DEFINITION                        *
   * ================================================================== */

abstract class extracted_function_reductionDFIO(implicit val p: Parameters) extends Module with CoreParams {
  val io = IO(new Bundle {
    val in = Flipped(Decoupled(new Call(List(32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32))))
    val MemResp = Flipped(Valid(new MemResp))
    val MemReq = Decoupled(new MemReq)
    val out = Decoupled(new Call(List()))
  })
}

class extracted_function_reductionDF(implicit p: Parameters) extends extracted_function_reductionDFIO()(p) {


  /* ================================================================== *
   *                   PRINTING MEMORY MODULES                          *
   * ================================================================== */

  val MemCtrl = Module(new UnifiedController(ID = 0, Size = 32, NReads = 2, NWrites = 1)
  (WControl = new WriteMemoryController(NumOps = 1, BaseSize = 2, NumEntries = 2))
  (RControl = new ReadMemoryController(NumOps = 2, BaseSize = 2, NumEntries = 2))
  (RWArbiter = new ReadWriteArbiter()))

  io.MemReq <> MemCtrl.io.MemReq
  MemCtrl.io.MemResp <> io.MemResp

  val InputSplitter = Module(new SplitCallNew(List(1, 1, 1, 1, 3, 2, 3, 2, 3, 1, 1)))
  InputSplitter.io.In <> io.in



  /* ================================================================== *
   *                   PRINTING LOOP HEADERS                            *
   * ================================================================== */

  val Loop_0 = Module(new LoopBlockNode(NumIns = List(1, 1, 1), NumOuts = List(1), NumCarry = List(1, 1), NumExits = 1, ID = 1))

  val Loop_1 = Module(new LoopBlockNode(NumIns = List(1, 1, 1, 1), NumOuts = List(1), NumCarry = List(1, 1), NumExits = 1, ID = 2))

  val Loop_2 = Module(new LoopBlockNode(NumIns = List(1, 2, 1, 1, 1, 1), NumOuts = List(), NumCarry = List(1), NumExits = 1, ID = 3))

  val Loop_3 = Module(new LoopBlockNode(NumIns = List(1, 1, 1, 1, 1, 1, 1, 1, 2), NumOuts = List(), NumCarry = List(1), NumExits = 1, ID = 4))

  val Loop_4 = Module(new LoopBlockNode(NumIns = List(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1), NumOuts = List(), NumCarry = List(1), NumExits = 1, ID = 5))

  val Loop_5 = Module(new LoopBlockNode(NumIns = List(2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), NumOuts = List(), NumCarry = List(1), NumExits = 1, ID = 6))



  /* ================================================================== *
   *                   PRINTING BASICBLOCK NODES                        *
   * ================================================================== */

  val bb_entry0 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 3, BID = 0))

  val bb_for_body_lr_ph1 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 5, BID = 1))

  val bb_for_cond_cleanup_loopexit2 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 1, BID = 2))

  val bb_for_cond_cleanup3 = Module(new BasicBlockNoMaskFastNode(NumInputs = 2, NumOuts = 1, BID = 3))

  val bb_for_body4 = Module(new BasicBlockNode(NumInputs = 2, NumOuts = 4, NumPhi = 1, BID = 4))

  val bb_for_body7_preheader5 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 1, BID = 5))

  val bb_for_cond_cleanup6_loopexit6 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 1, BID = 6))

  val bb_for_cond_cleanup67 = Module(new BasicBlockNoMaskFastNode(NumInputs = 2, NumOuts = 4, BID = 7))

  val bb_for_body78 = Module(new BasicBlockNode(NumInputs = 2, NumOuts = 2, NumPhi = 1, BID = 8))

  val bb_for_body15_lr_ph9 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 4, BID = 9))

  val bb_for_cond_cleanup14_loopexit10 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 1, BID = 10))

  val bb_for_cond_cleanup1411 = Module(new BasicBlockNoMaskFastNode(NumInputs = 2, NumOuts = 4, BID = 11))

  val bb_for_body1512 = Module(new BasicBlockNode(NumInputs = 2, NumOuts = 6, NumPhi = 1, BID = 12))

  val bb_for_cond_cleanup2013 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 4, BID = 13))

  val bb_for_body2114 = Module(new BasicBlockNode(NumInputs = 2, NumOuts = 5, NumPhi = 1, BID = 14))

  val bb_for_cond_cleanup2615 = Module(new BasicBlockNode(NumInputs = 1, NumOuts = 12, NumPhi = 1, BID = 15))

  val bb_for_body2716 = Module(new BasicBlockNode(NumInputs = 2, NumOuts = 8, NumPhi = 2, BID = 16))

  val bb_for_cond_cleanup3317 = Module(new BasicBlockNode(NumInputs = 1, NumOuts = 6, NumPhi = 1, BID = 17))

  val bb_for_body3418 = Module(new BasicBlockNode(NumInputs = 2, NumOuts = 13, NumPhi = 2, BID = 18))



  /* ================================================================== *
   *                   PRINTING INSTRUCTION NODES                       *
   * ================================================================== */

  //  %add = add i32 %_34, %_33, !dbg !1193
  val binaryOp_add0 = Module(new ComputeNode(NumOuts = 2, ID = 0, opCode = "add")(sign = false))

  //  %cmp142 = icmp ugt i32 %add, %_33, !dbg !1194
  val icmp_cmp1421 = Module(new ComputeNode(NumOuts = 1, ID = 1, opCode = "ugt")(sign = false))

  //  br i1 %cmp142, label %for.body.lr.ph, label %for.cond.cleanup, !dbg !1195, !BB_UID !1196
  val br_2 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 2))

  //  %add4 = add i32 %_31, %_30
  val binaryOp_add43 = Module(new ComputeNode(NumOuts = 2, ID = 3, opCode = "add")(sign = false))

  //  %cmp5139 = icmp ugt i32 %add4, %_30
  val icmp_cmp51394 = Module(new ComputeNode(NumOuts = 1, ID = 4, opCode = "ugt")(sign = false))

  //  %add12 = add i32 %_28, %_27
  val binaryOp_add125 = Module(new ComputeNode(NumOuts = 2, ID = 5, opCode = "add")(sign = false))

  //  %cmp13137 = icmp ugt i32 %add12, %_27
  val icmp_cmp131376 = Module(new ComputeNode(NumOuts = 1, ID = 6, opCode = "ugt")(sign = false))

  //  br label %for.body, !dbg !1195, !BB_UID !1197
  val br_7 = Module(new UBranchNode(ID = 7))

  //  br label %for.cond.cleanup, !dbg !1198
  val br_8 = Module(new UBranchNode(ID = 8))

  //  ret void, !dbg !1198, !BB_UID !1199
  val ret_9 = Module(new RetNode2(retTypes = List(), ID = 9))

  //  %_pw_conv_reduction_s1_k.0143 = phi i32 [ %_33, %for.body.lr.ph ], [ %inc64, %for.cond.cleanup6 ]
  val phi_pw_conv_reduction_s1_k_014310 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 2, ID = 10, Res = true))

  //  %sub = sub nuw i32 %_pw_conv_reduction_s1_k.0143, %_33, !dbg !1201
  val binaryOp_sub11 = Module(new ComputeNode(NumOuts = 1, ID = 11, opCode = "sub")(sign = false))

  //  %mul1 = mul i32 %sub, %_31, !dbg !1204
  val binaryOp_mul112 = Module(new ComputeNode(NumOuts = 1, ID = 12, opCode = "mul")(sign = false))

  //  br i1 %cmp5139, label %for.body7.preheader, label %for.cond.cleanup6, !dbg !1209, !BB_UID !1210
  val br_13 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 13))

  //  br label %for.body7, !dbg !1211, !BB_UID !1212
  val br_14 = Module(new UBranchNode(ID = 14))

  //  br label %for.cond.cleanup6, !dbg !1213
  val br_15 = Module(new UBranchNode(ID = 15))

  //  %inc64 = add nuw nsw i32 %_pw_conv_reduction_s1_k.0143, 1, !dbg !1213
  val binaryOp_inc6416 = Module(new ComputeNode(NumOuts = 2, ID = 16, opCode = "add")(sign = false))

  //  %exitcond148 = icmp eq i32 %inc64, %add, !dbg !1194
  val icmp_exitcond14817 = Module(new ComputeNode(NumOuts = 1, ID = 17, opCode = "eq")(sign = false))

  //  br i1 %exitcond148, label %for.cond.cleanup.loopexit, label %for.body, !dbg !1195, !llvm.loop !1215, !BB_UID !1217
  val br_18 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 18))

  //  %_pw_conv_reduction_s1_y.0140 = phi i32 [ %inc61, %for.cond.cleanup14 ], [ %_30, %for.body7.preheader ]
  val phi_pw_conv_reduction_s1_y_014019 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 3, ID = 19, Res = false))

  //  br i1 %cmp13137, label %for.body15.lr.ph, label %for.cond.cleanup14, !dbg !1211, !BB_UID !1225
  val br_20 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 20))

  //  %sub8 = sub nuw i32 %_pw_conv_reduction_s1_y.0140, %_30, !dbg !1226
  val binaryOp_sub821 = Module(new ComputeNode(NumOuts = 1, ID = 21, opCode = "sub")(sign = false))

  //  %reass.add = add i32 %sub8, %mul1
  val binaryOp_reass_add22 = Module(new ComputeNode(NumOuts = 1, ID = 22, opCode = "add")(sign = false))

  //  %reass.mul = mul i32 %reass.add, %_28
  val binaryOp_reass_mul23 = Module(new ComputeNode(NumOuts = 1, ID = 23, opCode = "mul")(sign = false))

  //  br label %for.body15, !dbg !1211, !BB_UID !1227
  val br_24 = Module(new UBranchNode(ID = 24))

  //  br label %for.cond.cleanup14, !dbg !1228
  val br_25 = Module(new UBranchNode(ID = 25))

  //  %inc61 = add nuw nsw i32 %_pw_conv_reduction_s1_y.0140, 1, !dbg !1228
  val binaryOp_inc6126 = Module(new ComputeNode(NumOuts = 2, ID = 26, opCode = "add")(sign = false))

  //  %exitcond147 = icmp eq i32 %inc61, %add4, !dbg !1230
  val icmp_exitcond14727 = Module(new ComputeNode(NumOuts = 1, ID = 27, opCode = "eq")(sign = false))

  //  br i1 %exitcond147, label %for.cond.cleanup6.loopexit, label %for.body7, !dbg !1209, !llvm.loop !1231, !BB_UID !1233
  val br_28 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 28))

  //  %_pw_conv_reduction_s1_x.0138 = phi i32 [ %_27, %for.body15.lr.ph ], [ %inc58, %for.cond.cleanup20 ]
  val phi_pw_conv_reduction_s1_x_013829 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 3, ID = 29, Res = true))

  //  %sub16 = sub i32 %_pw_conv_reduction_s1_x.0138, %_305, !dbg !1235
  val binaryOp_sub1630 = Module(new ComputeNode(NumOuts = 1, ID = 30, opCode = "sub")(sign = false))

  //  %add10 = sub nuw i32 %_pw_conv_reduction_s1_x.0138, %_27, !dbg !1238
  val binaryOp_add1031 = Module(new ComputeNode(NumOuts = 1, ID = 31, opCode = "sub")(sign = false))

  //  %add17 = add i32 %add10, %reass.mul, !dbg !1239
  val binaryOp_add1732 = Module(new ComputeNode(NumOuts = 1, ID = 32, opCode = "add")(sign = false))

  //  %arrayidx50 = getelementptr inbounds i32, i32* %_pw_conv_reduction, i32 %add17
  val Gep_arrayidx5033 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 33)(ElementSize = 4, ArraySize = List()))

  //  br label %for.body21, !dbg !1244, !BB_UID !1245
  val br_34 = Module(new UBranchNode(ID = 34))

  //  %inc58 = add nuw nsw i32 %_pw_conv_reduction_s1_x.0138, 1, !dbg !1246
  val binaryOp_inc5835 = Module(new ComputeNode(NumOuts = 2, ID = 35, opCode = "add")(sign = false))

  //  %exitcond146 = icmp eq i32 %inc58, %add12, !dbg !1248
  val icmp_exitcond14636 = Module(new ComputeNode(NumOuts = 1, ID = 36, opCode = "eq")(sign = false))

  //  br i1 %exitcond146, label %for.cond.cleanup14.loopexit, label %for.body15, !dbg !1211, !llvm.loop !1249, !BB_UID !1251
  val br_37 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 37))

  //  %_pw_conv_reduction_s1_r_pw__x.0136 = phi i32 [ 0, %for.body15 ], [ %inc55, %for.cond.cleanup26 ]
  val phi_pw_conv_reduction_s1_r_pw__x_013638 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 2, ID = 38, Res = true))

  //  %mul22 = mul i32 %_pw_conv_reduction_s1_r_pw__x.0136, %_21, !dbg !1261
  val binaryOp_mul2239 = Module(new ComputeNode(NumOuts = 1, ID = 39, opCode = "mul")(sign = false))

  //  %add23 = add nsw i32 %sub16, %mul22, !dbg !1264
  val binaryOp_add2340 = Module(new ComputeNode(NumOuts = 1, ID = 40, opCode = "add")(sign = false))

  //  br label %for.body27, !dbg !1270, !BB_UID !1271
  val br_41 = Module(new UBranchNode(ID = 41))

  //  %add38.lcssa.lcssa = phi i16 [ %add38.lcssa, %for.cond.cleanup33 ]
  val phiadd38_lcssa_lcssa42 = Module(new PhiFastNode(NumInputs = 1, NumOutputs = 1, ID = 42, Res = false))

  //  %mul44 = mul i16 %add38.lcssa.lcssa, 3, !dbg !1276
  val binaryOp_mul4443 = Module(new ComputeNode(NumOuts = 1, ID = 43, opCode = "mul")(sign = false))

  //  %conv47 = sext i16 %mul44 to i32, !dbg !1279
  val sextconv4744 = Module(new SextNode(NumOuts = 1))

  //  %0 = load i32, i32* %arrayidx50, align 4, !dbg !1283, !tbaa !1284
  val ld_45 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 45, RouteID = 0))

  //  %add52 = add nsw i32 %0, %conv47, !dbg !1294
  val binaryOp_add5246 = Module(new ComputeNode(NumOuts = 1, ID = 46, opCode = "add")(sign = false))

  //  store i32 %add52, i32* %arrayidx50, align 4, !dbg !1297, !tbaa !1284
  val st_47 = Module(new UnTypStore(NumPredOps = 0, NumSuccOps = 0, ID = 47, RouteID = 0))

  //  %inc55 = add nuw nsw i32 %_pw_conv_reduction_s1_r_pw__x.0136, 1, !dbg !1298
  val binaryOp_inc5548 = Module(new ComputeNode(NumOuts = 2, ID = 48, opCode = "add")(sign = false))

  //  %exitcond145 = icmp eq i32 %inc55, 4, !dbg !1300
  val icmp_exitcond14549 = Module(new ComputeNode(NumOuts = 1, ID = 49, opCode = "eq")(sign = false))

  //  br i1 %exitcond145, label %for.cond.cleanup20, label %for.body21, !dbg !1244, !llvm.loop !1301, !BB_UID !1303
  val br_50 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 50))

  //  %_dw_conv.0135 = phi i16 [ 0, %for.body21 ], [ %add38.lcssa, %for.cond.cleanup33 ]
  val phi_dw_conv_013551 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 1, ID = 51, Res = true))

  //  %_dw_conv_s1_r_dw__y.0134 = phi i32 [ 0, %for.body21 ], [ %inc41, %for.cond.cleanup33 ]
  val phi_dw_conv_s1_r_dw__y_013452 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 2, ID = 52, Res = true))

  //  %add28 = add nsw i32 %_dw_conv_s1_r_dw__y.0134, %_pw_conv_reduction_s1_y.0140, !dbg !1306
  val binaryOp_add2853 = Module(new ComputeNode(NumOuts = 1, ID = 53, opCode = "add")(sign = false))

  //  %mul29 = mul i32 %add28, %_18, !dbg !1309
  val binaryOp_mul2954 = Module(new ComputeNode(NumOuts = 1, ID = 54, opCode = "mul")(sign = false))

  //  %add30 = add nsw i32 %add23, %mul29, !dbg !1312
  val binaryOp_add3055 = Module(new ComputeNode(NumOuts = 1, ID = 55, opCode = "add")(sign = false))

  //  br label %for.body34, !dbg !1318, !BB_UID !1319
  val br_56 = Module(new UBranchNode(ID = 56))

  //  %add38.lcssa = phi i16 [ %add38, %for.body34 ]
  val phiadd38_lcssa57 = Module(new PhiFastNode(NumInputs = 1, NumOutputs = 2, ID = 57, Res = false))

  //  %inc41 = add nuw nsw i32 %_dw_conv_s1_r_dw__y.0134, 1, !dbg !1320
  val binaryOp_inc4158 = Module(new ComputeNode(NumOuts = 2, ID = 58, opCode = "add")(sign = false))

  //  %exitcond144 = icmp eq i32 %inc41, 3, !dbg !1323
  val icmp_exitcond14459 = Module(new ComputeNode(NumOuts = 1, ID = 59, opCode = "eq")(sign = false))

  //  br i1 %exitcond144, label %for.cond.cleanup26, label %for.body27, !dbg !1270, !llvm.loop !1324, !BB_UID !1326
  val br_60 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 60))

  //  %_dw_conv.1133 = phi i16 [ %_dw_conv.0135, %for.body27 ], [ %add38, %for.body34 ]
  val phi_dw_conv_113361 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 1, ID = 61, Res = true))

  //  %_dw_conv_s1_r_dw__x.0132 = phi i32 [ 0, %for.body27 ], [ %inc, %for.body34 ]
  val phi_dw_conv_s1_r_dw__x_013262 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 2, ID = 62, Res = true))

  //  %add35 = add nsw i32 %add30, %_dw_conv_s1_r_dw__x.0132, !dbg !1331
  val binaryOp_add3563 = Module(new ComputeNode(NumOuts = 1, ID = 63, opCode = "add")(sign = false))

  //  %arrayidx = getelementptr inbounds i8, i8* %_input, i32 %add35, !dbg !1334
  val Gep_arrayidx64 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 64)(ElementSize = 1, ArraySize = List()))

  //  %1 = load i8, i8* %arrayidx, align 1, !dbg !1334, !tbaa !1335
  val ld_65 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 65, RouteID = 1))

  //  %conv37 = sext i8 %1 to i16, !dbg !1338
  val sextconv3766 = Module(new SextNode(NumOuts = 1))

  //  %add38 = add i16 %_dw_conv.1133, %conv37, !dbg !1339
  val binaryOp_add3867 = Module(new ComputeNode(NumOuts = 2, ID = 67, opCode = "add")(sign = false))

  //  %inc = add nuw nsw i32 %_dw_conv_s1_r_dw__x.0132, 1, !dbg !1342
  val binaryOp_inc68 = Module(new ComputeNode(NumOuts = 2, ID = 68, opCode = "add")(sign = false))

  //  %exitcond = icmp eq i32 %inc, 3, !dbg !1345
  val icmp_exitcond69 = Module(new ComputeNode(NumOuts = 1, ID = 69, opCode = "eq")(sign = false))

  //  br i1 %exitcond, label %for.cond.cleanup33, label %for.body34, !dbg !1318, !llvm.loop !1346, !BB_UID !1348
  val br_70 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 70))



  /* ================================================================== *
   *                   PRINTING CONSTANTS NODES                         *
   * ================================================================== */

  //i32 1
  val const0 = Module(new ConstFastNode(value = 1, ID = 0))

  //i32 1
  val const1 = Module(new ConstFastNode(value = 1, ID = 1))

  //i32 1
  val const2 = Module(new ConstFastNode(value = 1, ID = 2))

  //i32 0
  val const3 = Module(new ConstFastNode(value = 0, ID = 3))

  //i16 3
  val const4 = Module(new ConstFastNode(value = 3, ID = 4))

  //i32 1
  val const5 = Module(new ConstFastNode(value = 1, ID = 5))

  //i32 4
  val const6 = Module(new ConstFastNode(value = 4, ID = 6))

  //i16 0
  val const7 = Module(new ConstFastNode(value = 0, ID = 7))

  //i32 0
  val const8 = Module(new ConstFastNode(value = 0, ID = 8))

  //i32 1
  val const9 = Module(new ConstFastNode(value = 1, ID = 9))

  //i32 3
  val const10 = Module(new ConstFastNode(value = 3, ID = 10))

  //i32 0
  val const11 = Module(new ConstFastNode(value = 0, ID = 11))

  //i32 1
  val const12 = Module(new ConstFastNode(value = 1, ID = 12))

  //i32 3
  val const13 = Module(new ConstFastNode(value = 3, ID = 13))



  /* ================================================================== *
   *                   BASICBLOCK -> PREDICATE INSTRUCTION              *
   * ================================================================== */

  bb_entry0.io.predicateIn(0) <> InputSplitter.io.Out.enable

  bb_for_body_lr_ph1.io.predicateIn(0) <> br_2.io.TrueOutput(0)

  bb_for_cond_cleanup3.io.predicateIn(1) <> br_2.io.FalseOutput(0)

  bb_for_cond_cleanup3.io.predicateIn(0) <> br_8.io.Out(0)

  bb_for_body7_preheader5.io.predicateIn(0) <> br_13.io.TrueOutput(0)

  bb_for_cond_cleanup67.io.predicateIn(1) <> br_13.io.FalseOutput(0)

  bb_for_cond_cleanup67.io.predicateIn(0) <> br_15.io.Out(0)

  bb_for_body15_lr_ph9.io.predicateIn(0) <> br_20.io.TrueOutput(0)

  bb_for_cond_cleanup1411.io.predicateIn(1) <> br_20.io.FalseOutput(0)

  bb_for_cond_cleanup1411.io.predicateIn(0) <> br_25.io.Out(0)



  /* ================================================================== *
   *                   BASICBLOCK -> PREDICATE LOOP                     *
   * ================================================================== */

  bb_for_cond_cleanup_loopexit2.io.predicateIn(0) <> Loop_5.io.loopExit(0)

  bb_for_body4.io.predicateIn(1) <> Loop_5.io.activate_loop_start

  bb_for_body4.io.predicateIn(0) <> Loop_5.io.activate_loop_back

  bb_for_cond_cleanup6_loopexit6.io.predicateIn(0) <> Loop_4.io.loopExit(0)

  bb_for_body78.io.predicateIn(1) <> Loop_4.io.activate_loop_start

  bb_for_body78.io.predicateIn(0) <> Loop_4.io.activate_loop_back

  bb_for_cond_cleanup14_loopexit10.io.predicateIn(0) <> Loop_3.io.loopExit(0)

  bb_for_body1512.io.predicateIn(1) <> Loop_3.io.activate_loop_start

  bb_for_body1512.io.predicateIn(0) <> Loop_3.io.activate_loop_back

  bb_for_cond_cleanup2013.io.predicateIn(0) <> Loop_2.io.loopExit(0)

  bb_for_body2114.io.predicateIn(1) <> Loop_2.io.activate_loop_start

  bb_for_body2114.io.predicateIn(0) <> Loop_2.io.activate_loop_back

  bb_for_cond_cleanup2615.io.predicateIn(0) <> Loop_1.io.loopExit(0)

  bb_for_body2716.io.predicateIn(1) <> Loop_1.io.activate_loop_start

  bb_for_body2716.io.predicateIn(0) <> Loop_1.io.activate_loop_back

  bb_for_cond_cleanup3317.io.predicateIn(0) <> Loop_0.io.loopExit(0)

  bb_for_body3418.io.predicateIn(1) <> Loop_0.io.activate_loop_start

  bb_for_body3418.io.predicateIn(0) <> Loop_0.io.activate_loop_back



  /* ================================================================== *
   *                   PRINTING PARALLEL CONNECTIONS                    *
   * ================================================================== */



  /* ================================================================== *
   *                   LOOP -> PREDICATE INSTRUCTION                    *
   * ================================================================== */

  Loop_0.io.enable <> br_56.io.Out(0)

  Loop_0.io.loopBack(0) <> br_70.io.FalseOutput(0)

  Loop_0.io.loopFinish(0) <> br_70.io.TrueOutput(0)

  Loop_1.io.enable <> br_41.io.Out(0)

  Loop_1.io.loopBack(0) <> br_60.io.FalseOutput(0)

  Loop_1.io.loopFinish(0) <> br_60.io.TrueOutput(0)

  Loop_2.io.enable <> br_34.io.Out(0)

  Loop_2.io.loopBack(0) <> br_50.io.FalseOutput(0)

  Loop_2.io.loopFinish(0) <> br_50.io.TrueOutput(0)

  Loop_3.io.enable <> br_24.io.Out(0)

  Loop_3.io.loopBack(0) <> br_37.io.FalseOutput(0)

  Loop_3.io.loopFinish(0) <> br_37.io.TrueOutput(0)

  Loop_4.io.enable <> br_14.io.Out(0)

  Loop_4.io.loopBack(0) <> br_28.io.FalseOutput(0)

  Loop_4.io.loopFinish(0) <> br_28.io.TrueOutput(0)

  Loop_5.io.enable <> br_7.io.Out(0)

  Loop_5.io.loopBack(0) <> br_18.io.FalseOutput(0)

  Loop_5.io.loopFinish(0) <> br_18.io.TrueOutput(0)



  /* ================================================================== *
   *                   ENDING INSTRUCTIONS                              *
   * ================================================================== */



  /* ================================================================== *
   *                   LOOP INPUT DATA DEPENDENCIES                     *
   * ================================================================== */

  Loop_0.io.InLiveIn(0) <> phi_dw_conv_013551.io.Out(0)

  Loop_0.io.InLiveIn(1) <> binaryOp_add3055.io.Out(0)

  Loop_0.io.InLiveIn(2) <> Loop_1.io.OutLiveIn.elements("field1")(0)

  Loop_1.io.InLiveIn(0) <> binaryOp_add2340.io.Out(0)

  Loop_1.io.InLiveIn(1) <> Loop_2.io.OutLiveIn.elements("field4")(0)

  Loop_1.io.InLiveIn(2) <> Loop_2.io.OutLiveIn.elements("field2")(0)

  Loop_1.io.InLiveIn(3) <> Loop_2.io.OutLiveIn.elements("field3")(0)

  Loop_2.io.InLiveIn(0) <> binaryOp_sub1630.io.Out(0)

  Loop_2.io.InLiveIn(1) <> Gep_arrayidx5033.io.Out(0)

  Loop_2.io.InLiveIn(2) <> Loop_3.io.OutLiveIn.elements("field2")(0)

  Loop_2.io.InLiveIn(3) <> Loop_3.io.OutLiveIn.elements("field1")(0)

  Loop_2.io.InLiveIn(4) <> Loop_3.io.OutLiveIn.elements("field4")(0)

  Loop_2.io.InLiveIn(5) <> Loop_3.io.OutLiveIn.elements("field3")(0)

  Loop_3.io.InLiveIn(0) <> binaryOp_reass_mul23.io.Out(0)

  Loop_3.io.InLiveIn(1) <> phi_pw_conv_reduction_s1_y_014019.io.Out(0)

  Loop_3.io.InLiveIn(2) <> Loop_4.io.OutLiveIn.elements("field1")(0)

  Loop_3.io.InLiveIn(3) <> Loop_4.io.OutLiveIn.elements("field2")(0)

  Loop_3.io.InLiveIn(4) <> Loop_4.io.OutLiveIn.elements("field4")(0)

  Loop_3.io.InLiveIn(5) <> Loop_4.io.OutLiveIn.elements("field3")(0)

  Loop_3.io.InLiveIn(6) <> Loop_4.io.OutLiveIn.elements("field5")(0)

  Loop_3.io.InLiveIn(7) <> Loop_4.io.OutLiveIn.elements("field6")(0)

  Loop_3.io.InLiveIn(8) <> Loop_4.io.OutLiveIn.elements("field7")(0)

  Loop_4.io.InLiveIn(0) <> binaryOp_mul112.io.Out(0)

  Loop_4.io.InLiveIn(1) <> Loop_5.io.OutLiveIn.elements("field10")(0)

  Loop_4.io.InLiveIn(2) <> Loop_5.io.OutLiveIn.elements("field9")(0)

  Loop_4.io.InLiveIn(3) <> Loop_5.io.OutLiveIn.elements("field8")(0)

  Loop_4.io.InLiveIn(4) <> Loop_5.io.OutLiveIn.elements("field11")(0)

  Loop_4.io.InLiveIn(5) <> Loop_5.io.OutLiveIn.elements("field12")(0)

  Loop_4.io.InLiveIn(6) <> Loop_5.io.OutLiveIn.elements("field7")(0)

  Loop_4.io.InLiveIn(7) <> Loop_5.io.OutLiveIn.elements("field6")(0)

  Loop_4.io.InLiveIn(8) <> Loop_5.io.OutLiveIn.elements("field4")(0)

  Loop_4.io.InLiveIn(9) <> Loop_5.io.OutLiveIn.elements("field13")(0)

  Loop_4.io.InLiveIn(10) <> Loop_5.io.OutLiveIn.elements("field3")(0)

  Loop_4.io.InLiveIn(11) <> Loop_5.io.OutLiveIn.elements("field5")(0)

  Loop_5.io.InLiveIn(0) <> InputSplitter.io.Out.data.elements("field8")(0)

  Loop_5.io.InLiveIn(1) <> InputSplitter.io.Out.data.elements("field7")(0)

  Loop_5.io.InLiveIn(2) <> icmp_cmp51394.io.Out(0)

  Loop_5.io.InLiveIn(3) <> InputSplitter.io.Out.data.elements("field6")(0)

  Loop_5.io.InLiveIn(4) <> icmp_cmp131376.io.Out(0)

  Loop_5.io.InLiveIn(5) <> InputSplitter.io.Out.data.elements("field5")(0)

  Loop_5.io.InLiveIn(6) <> InputSplitter.io.Out.data.elements("field4")(0)

  Loop_5.io.InLiveIn(7) <> InputSplitter.io.Out.data.elements("field10")(0)

  Loop_5.io.InLiveIn(8) <> InputSplitter.io.Out.data.elements("field0")(0)

  Loop_5.io.InLiveIn(9) <> InputSplitter.io.Out.data.elements("field3")(0)

  Loop_5.io.InLiveIn(10) <> InputSplitter.io.Out.data.elements("field2")(0)

  Loop_5.io.InLiveIn(11) <> InputSplitter.io.Out.data.elements("field1")(0)

  Loop_5.io.InLiveIn(12) <> binaryOp_add125.io.Out(0)

  Loop_5.io.InLiveIn(13) <> binaryOp_add43.io.Out(0)

  Loop_5.io.InLiveIn(14) <> binaryOp_add0.io.Out(0)



  /* ================================================================== *
   *                   LOOP DATA LIVE-IN DEPENDENCIES                   *
   * ================================================================== */

  phi_dw_conv_113361.io.InData(0) <> Loop_0.io.OutLiveIn.elements("field0")(0)

  binaryOp_add3563.io.LeftIO <> Loop_0.io.OutLiveIn.elements("field1")(0)

  Gep_arrayidx64.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field2")(0)

  binaryOp_add3055.io.LeftIO <> Loop_1.io.OutLiveIn.elements("field0")(0)

  binaryOp_mul2954.io.RightIO <> Loop_1.io.OutLiveIn.elements("field2")(0)

  binaryOp_add2853.io.RightIO <> Loop_1.io.OutLiveIn.elements("field3")(0)

  binaryOp_add2340.io.LeftIO <> Loop_2.io.OutLiveIn.elements("field0")(0)

  ld_45.io.GepAddr <> Loop_2.io.OutLiveIn.elements("field1")(0)

  st_47.io.GepAddr <> Loop_2.io.OutLiveIn.elements("field1")(1)

  binaryOp_mul2239.io.RightIO <> Loop_2.io.OutLiveIn.elements("field5")(0)

  binaryOp_add1732.io.RightIO <> Loop_3.io.OutLiveIn.elements("field0")(0)

  Gep_arrayidx5033.io.baseAddress <> Loop_3.io.OutLiveIn.elements("field5")(0)

  icmp_exitcond14636.io.RightIO <> Loop_3.io.OutLiveIn.elements("field6")(0)

  binaryOp_sub1630.io.RightIO <> Loop_3.io.OutLiveIn.elements("field7")(0)

  phi_pw_conv_reduction_s1_x_013829.io.InData(0) <> Loop_3.io.OutLiveIn.elements("field8")(0)

  binaryOp_add1031.io.RightIO <> Loop_3.io.OutLiveIn.elements("field8")(1)

  binaryOp_reass_add22.io.RightIO <> Loop_4.io.OutLiveIn.elements("field0")(0)

  br_20.io.CmpIO <> Loop_4.io.OutLiveIn.elements("field8")(0)

  icmp_exitcond14727.io.RightIO <> Loop_4.io.OutLiveIn.elements("field9")(0)

  phi_pw_conv_reduction_s1_y_014019.io.InData(1) <> Loop_4.io.OutLiveIn.elements("field10")(0)

  binaryOp_sub821.io.RightIO <> Loop_4.io.OutLiveIn.elements("field10")(1)

  binaryOp_reass_mul23.io.RightIO <> Loop_4.io.OutLiveIn.elements("field11")(0)

  phi_pw_conv_reduction_s1_k_014310.io.InData(0) <> Loop_5.io.OutLiveIn.elements("field0")(0)

  binaryOp_sub11.io.RightIO <> Loop_5.io.OutLiveIn.elements("field0")(1)

  binaryOp_mul112.io.RightIO <> Loop_5.io.OutLiveIn.elements("field1")(0)

  br_13.io.CmpIO <> Loop_5.io.OutLiveIn.elements("field2")(0)

  icmp_exitcond14817.io.RightIO <> Loop_5.io.OutLiveIn.elements("field14")(0)



  /* ================================================================== *
   *                   LOOP DATA LIVE-OUT DEPENDENCIES                  *
   * ================================================================== */

  Loop_0.io.InLiveOut(0) <> binaryOp_add3867.io.Out(0)

  Loop_1.io.InLiveOut(0) <> phiadd38_lcssa57.io.Out(0)



  /* ================================================================== *
   *                   LOOP LIVE OUT DEPENDENCIES                       *
   * ================================================================== */

  phiadd38_lcssa57.io.InData(0) <> Loop_0.io.OutLiveOut.elements("field0")(0)

  phiadd38_lcssa_lcssa42.io.InData(0) <> Loop_1.io.OutLiveOut.elements("field0")(0)



  /* ================================================================== *
   *                   LOOP CARRY DEPENDENCIES                          *
   * ================================================================== */

  Loop_0.io.CarryDepenIn(0) <> binaryOp_add3867.io.Out(1)

  Loop_0.io.CarryDepenIn(1) <> binaryOp_inc68.io.Out(0)

  Loop_1.io.CarryDepenIn(0) <> binaryOp_inc4158.io.Out(0)

  Loop_1.io.CarryDepenIn(1) <> phiadd38_lcssa57.io.Out(1)

  Loop_2.io.CarryDepenIn(0) <> binaryOp_inc5548.io.Out(0)

  Loop_3.io.CarryDepenIn(0) <> binaryOp_inc5835.io.Out(0)

  Loop_4.io.CarryDepenIn(0) <> binaryOp_inc6126.io.Out(0)

  Loop_5.io.CarryDepenIn(0) <> binaryOp_inc6416.io.Out(0)



  /* ================================================================== *
   *                   LOOP DATA CARRY DEPENDENCIES                     *
   * ================================================================== */

  phi_dw_conv_113361.io.InData(1) <> Loop_0.io.CarryDepenOut.elements("field0")(0)

  phi_dw_conv_s1_r_dw__x_013262.io.InData(1) <> Loop_0.io.CarryDepenOut.elements("field1")(0)

  phi_dw_conv_s1_r_dw__y_013452.io.InData(1) <> Loop_1.io.CarryDepenOut.elements("field0")(0)

  phi_dw_conv_013551.io.InData(1) <> Loop_1.io.CarryDepenOut.elements("field1")(0)

  phi_pw_conv_reduction_s1_r_pw__x_013638.io.InData(1) <> Loop_2.io.CarryDepenOut.elements("field0")(0)

  phi_pw_conv_reduction_s1_x_013829.io.InData(1) <> Loop_3.io.CarryDepenOut.elements("field0")(0)

  phi_pw_conv_reduction_s1_y_014019.io.InData(0) <> Loop_4.io.CarryDepenOut.elements("field0")(0)

  phi_pw_conv_reduction_s1_k_014310.io.InData(1) <> Loop_5.io.CarryDepenOut.elements("field0")(0)



  /* ================================================================== *
   *                   BASICBLOCK -> ENABLE INSTRUCTION                 *
   * ================================================================== */

  binaryOp_add0.io.enable <> bb_entry0.io.Out(0)


  icmp_cmp1421.io.enable <> bb_entry0.io.Out(1)


  br_2.io.enable <> bb_entry0.io.Out(2)


  binaryOp_add43.io.enable <> bb_for_body_lr_ph1.io.Out(0)


  icmp_cmp51394.io.enable <> bb_for_body_lr_ph1.io.Out(1)


  binaryOp_add125.io.enable <> bb_for_body_lr_ph1.io.Out(2)


  icmp_cmp131376.io.enable <> bb_for_body_lr_ph1.io.Out(3)


  br_7.io.enable <> bb_for_body_lr_ph1.io.Out(4)


  br_8.io.enable <> bb_for_cond_cleanup_loopexit2.io.Out(0)


  ret_9.io.In.enable <> bb_for_cond_cleanup3.io.Out(0)


  phi_pw_conv_reduction_s1_k_014310.io.enable <> bb_for_body4.io.Out(0)


  binaryOp_sub11.io.enable <> bb_for_body4.io.Out(1)


  binaryOp_mul112.io.enable <> bb_for_body4.io.Out(2)


  br_13.io.enable <> bb_for_body4.io.Out(3)


  br_14.io.enable <> bb_for_body7_preheader5.io.Out(0)


  br_15.io.enable <> bb_for_cond_cleanup6_loopexit6.io.Out(0)


  const0.io.enable <> bb_for_cond_cleanup67.io.Out(0)

  binaryOp_inc6416.io.enable <> bb_for_cond_cleanup67.io.Out(1)


  icmp_exitcond14817.io.enable <> bb_for_cond_cleanup67.io.Out(2)


  br_18.io.enable <> bb_for_cond_cleanup67.io.Out(3)


  phi_pw_conv_reduction_s1_y_014019.io.enable <> bb_for_body78.io.Out(0)


  br_20.io.enable <> bb_for_body78.io.Out(1)


  binaryOp_sub821.io.enable <> bb_for_body15_lr_ph9.io.Out(0)


  binaryOp_reass_add22.io.enable <> bb_for_body15_lr_ph9.io.Out(1)


  binaryOp_reass_mul23.io.enable <> bb_for_body15_lr_ph9.io.Out(2)


  br_24.io.enable <> bb_for_body15_lr_ph9.io.Out(3)


  br_25.io.enable <> bb_for_cond_cleanup14_loopexit10.io.Out(0)


  const1.io.enable <> bb_for_cond_cleanup1411.io.Out(0)

  binaryOp_inc6126.io.enable <> bb_for_cond_cleanup1411.io.Out(1)


  icmp_exitcond14727.io.enable <> bb_for_cond_cleanup1411.io.Out(2)


  br_28.io.enable <> bb_for_cond_cleanup1411.io.Out(3)


  phi_pw_conv_reduction_s1_x_013829.io.enable <> bb_for_body1512.io.Out(0)


  binaryOp_sub1630.io.enable <> bb_for_body1512.io.Out(1)


  binaryOp_add1031.io.enable <> bb_for_body1512.io.Out(2)


  binaryOp_add1732.io.enable <> bb_for_body1512.io.Out(3)


  Gep_arrayidx5033.io.enable <> bb_for_body1512.io.Out(4)


  br_34.io.enable <> bb_for_body1512.io.Out(5)


  const2.io.enable <> bb_for_cond_cleanup2013.io.Out(0)

  binaryOp_inc5835.io.enable <> bb_for_cond_cleanup2013.io.Out(1)


  icmp_exitcond14636.io.enable <> bb_for_cond_cleanup2013.io.Out(2)


  br_37.io.enable <> bb_for_cond_cleanup2013.io.Out(3)


  const3.io.enable <> bb_for_body2114.io.Out(0)

  phi_pw_conv_reduction_s1_r_pw__x_013638.io.enable <> bb_for_body2114.io.Out(1)


  binaryOp_mul2239.io.enable <> bb_for_body2114.io.Out(2)


  binaryOp_add2340.io.enable <> bb_for_body2114.io.Out(3)


  br_41.io.enable <> bb_for_body2114.io.Out(4)


  const4.io.enable <> bb_for_cond_cleanup2615.io.Out(0)

  const5.io.enable <> bb_for_cond_cleanup2615.io.Out(1)

  const6.io.enable <> bb_for_cond_cleanup2615.io.Out(2)

  phiadd38_lcssa_lcssa42.io.enable <> bb_for_cond_cleanup2615.io.Out(3)


  binaryOp_mul4443.io.enable <> bb_for_cond_cleanup2615.io.Out(4)


  sextconv4744.io.enable <> bb_for_cond_cleanup2615.io.Out(5)


  ld_45.io.enable <> bb_for_cond_cleanup2615.io.Out(6)


  binaryOp_add5246.io.enable <> bb_for_cond_cleanup2615.io.Out(7)


  st_47.io.enable <> bb_for_cond_cleanup2615.io.Out(8)


  binaryOp_inc5548.io.enable <> bb_for_cond_cleanup2615.io.Out(9)


  icmp_exitcond14549.io.enable <> bb_for_cond_cleanup2615.io.Out(10)


  br_50.io.enable <> bb_for_cond_cleanup2615.io.Out(11)


  const7.io.enable <> bb_for_body2716.io.Out(0)

  const8.io.enable <> bb_for_body2716.io.Out(1)

  phi_dw_conv_013551.io.enable <> bb_for_body2716.io.Out(2)


  phi_dw_conv_s1_r_dw__y_013452.io.enable <> bb_for_body2716.io.Out(3)


  binaryOp_add2853.io.enable <> bb_for_body2716.io.Out(4)


  binaryOp_mul2954.io.enable <> bb_for_body2716.io.Out(5)


  binaryOp_add3055.io.enable <> bb_for_body2716.io.Out(6)


  br_56.io.enable <> bb_for_body2716.io.Out(7)


  const9.io.enable <> bb_for_cond_cleanup3317.io.Out(0)

  const10.io.enable <> bb_for_cond_cleanup3317.io.Out(1)

  phiadd38_lcssa57.io.enable <> bb_for_cond_cleanup3317.io.Out(2)


  binaryOp_inc4158.io.enable <> bb_for_cond_cleanup3317.io.Out(3)


  icmp_exitcond14459.io.enable <> bb_for_cond_cleanup3317.io.Out(4)


  br_60.io.enable <> bb_for_cond_cleanup3317.io.Out(5)


  const11.io.enable <> bb_for_body3418.io.Out(0)

  const12.io.enable <> bb_for_body3418.io.Out(1)

  const13.io.enable <> bb_for_body3418.io.Out(2)

  phi_dw_conv_113361.io.enable <> bb_for_body3418.io.Out(3)


  phi_dw_conv_s1_r_dw__x_013262.io.enable <> bb_for_body3418.io.Out(4)


  binaryOp_add3563.io.enable <> bb_for_body3418.io.Out(5)


  Gep_arrayidx64.io.enable <> bb_for_body3418.io.Out(6)


  ld_65.io.enable <> bb_for_body3418.io.Out(7)


  sextconv3766.io.enable <> bb_for_body3418.io.Out(8)


  binaryOp_add3867.io.enable <> bb_for_body3418.io.Out(9)


  binaryOp_inc68.io.enable <> bb_for_body3418.io.Out(10)


  icmp_exitcond69.io.enable <> bb_for_body3418.io.Out(11)


  br_70.io.enable <> bb_for_body3418.io.Out(12)




  /* ================================================================== *
   *                   CONNECTING PHI NODES                             *
   * ================================================================== */

  phi_pw_conv_reduction_s1_k_014310.io.Mask <> bb_for_body4.io.MaskBB(0)

  phi_pw_conv_reduction_s1_y_014019.io.Mask <> bb_for_body78.io.MaskBB(0)

  phi_pw_conv_reduction_s1_x_013829.io.Mask <> bb_for_body1512.io.MaskBB(0)

  phi_pw_conv_reduction_s1_r_pw__x_013638.io.Mask <> bb_for_body2114.io.MaskBB(0)

  phiadd38_lcssa_lcssa42.io.Mask <> bb_for_cond_cleanup2615.io.MaskBB(0)

  phi_dw_conv_013551.io.Mask <> bb_for_body2716.io.MaskBB(0)

  phi_dw_conv_s1_r_dw__y_013452.io.Mask <> bb_for_body2716.io.MaskBB(1)

  phiadd38_lcssa57.io.Mask <> bb_for_cond_cleanup3317.io.MaskBB(0)

  phi_dw_conv_113361.io.Mask <> bb_for_body3418.io.MaskBB(0)

  phi_dw_conv_s1_r_dw__x_013262.io.Mask <> bb_for_body3418.io.MaskBB(1)



  /* ================================================================== *
   *                   PRINT ALLOCA OFFSET                              *
   * ================================================================== */



  /* ================================================================== *
   *                   CONNECTING MEMORY CONNECTIONS                    *
   * ================================================================== */

  MemCtrl.io.ReadIn(0) <> ld_45.io.memReq

  ld_45.io.memResp <> MemCtrl.io.ReadOut(0)

  MemCtrl.io.WriteIn(0) <> st_47.io.memReq

  st_47.io.memResp <> MemCtrl.io.WriteOut(0)

  MemCtrl.io.ReadIn(1) <> ld_65.io.memReq

  ld_65.io.memResp <> MemCtrl.io.ReadOut(1)



  /* ================================================================== *
   *                   PRINT SHARED CONNECTIONS                         *
   * ================================================================== */



  /* ================================================================== *
   *                   CONNECTING DATA DEPENDENCIES                     *
   * ================================================================== */

  binaryOp_inc6416.io.RightIO <> const0.io.Out

  binaryOp_inc6126.io.RightIO <> const1.io.Out

  binaryOp_inc5835.io.RightIO <> const2.io.Out

  phi_pw_conv_reduction_s1_r_pw__x_013638.io.InData(0) <> const3.io.Out

  binaryOp_mul4443.io.RightIO <> const4.io.Out

  binaryOp_inc5548.io.RightIO <> const5.io.Out

  icmp_exitcond14549.io.RightIO <> const6.io.Out

  phi_dw_conv_013551.io.InData(0) <> const7.io.Out

  phi_dw_conv_s1_r_dw__y_013452.io.InData(0) <> const8.io.Out

  binaryOp_inc4158.io.RightIO <> const9.io.Out

  icmp_exitcond14459.io.RightIO <> const10.io.Out

  phi_dw_conv_s1_r_dw__x_013262.io.InData(0) <> const11.io.Out

  binaryOp_inc68.io.RightIO <> const12.io.Out

  icmp_exitcond69.io.RightIO <> const13.io.Out

  icmp_cmp1421.io.LeftIO <> binaryOp_add0.io.Out(1)

  br_2.io.CmpIO <> icmp_cmp1421.io.Out(0)

  icmp_cmp51394.io.LeftIO <> binaryOp_add43.io.Out(1)

  icmp_cmp131376.io.LeftIO <> binaryOp_add125.io.Out(1)

  binaryOp_sub11.io.LeftIO <> phi_pw_conv_reduction_s1_k_014310.io.Out(0)

  binaryOp_inc6416.io.LeftIO <> phi_pw_conv_reduction_s1_k_014310.io.Out(1)

  binaryOp_mul112.io.LeftIO <> binaryOp_sub11.io.Out(0)

  icmp_exitcond14817.io.LeftIO <> binaryOp_inc6416.io.Out(1)

  br_18.io.CmpIO <> icmp_exitcond14817.io.Out(0)

  binaryOp_sub821.io.LeftIO <> phi_pw_conv_reduction_s1_y_014019.io.Out(1)

  binaryOp_inc6126.io.LeftIO <> phi_pw_conv_reduction_s1_y_014019.io.Out(2)

  binaryOp_reass_add22.io.LeftIO <> binaryOp_sub821.io.Out(0)

  binaryOp_reass_mul23.io.LeftIO <> binaryOp_reass_add22.io.Out(0)

  icmp_exitcond14727.io.LeftIO <> binaryOp_inc6126.io.Out(1)

  br_28.io.CmpIO <> icmp_exitcond14727.io.Out(0)

  binaryOp_sub1630.io.LeftIO <> phi_pw_conv_reduction_s1_x_013829.io.Out(0)

  binaryOp_add1031.io.LeftIO <> phi_pw_conv_reduction_s1_x_013829.io.Out(1)

  binaryOp_inc5835.io.LeftIO <> phi_pw_conv_reduction_s1_x_013829.io.Out(2)

  binaryOp_add1732.io.LeftIO <> binaryOp_add1031.io.Out(0)

  Gep_arrayidx5033.io.idx(0) <> binaryOp_add1732.io.Out(0)

  icmp_exitcond14636.io.LeftIO <> binaryOp_inc5835.io.Out(1)

  br_37.io.CmpIO <> icmp_exitcond14636.io.Out(0)

  binaryOp_mul2239.io.LeftIO <> phi_pw_conv_reduction_s1_r_pw__x_013638.io.Out(0)

  binaryOp_inc5548.io.LeftIO <> phi_pw_conv_reduction_s1_r_pw__x_013638.io.Out(1)

  binaryOp_add2340.io.RightIO <> binaryOp_mul2239.io.Out(0)

  binaryOp_mul4443.io.LeftIO <> phiadd38_lcssa_lcssa42.io.Out(0)

  sextconv4744.io.Input <> binaryOp_mul4443.io.Out(0)

  binaryOp_add5246.io.RightIO <> sextconv4744.io.Out(0)

  binaryOp_add5246.io.LeftIO <> ld_45.io.Out(0)

  st_47.io.inData <> binaryOp_add5246.io.Out(0)

  icmp_exitcond14549.io.LeftIO <> binaryOp_inc5548.io.Out(1)

  br_50.io.CmpIO <> icmp_exitcond14549.io.Out(0)

  binaryOp_add2853.io.LeftIO <> phi_dw_conv_s1_r_dw__y_013452.io.Out(0)

  binaryOp_inc4158.io.LeftIO <> phi_dw_conv_s1_r_dw__y_013452.io.Out(1)

  binaryOp_mul2954.io.LeftIO <> binaryOp_add2853.io.Out(0)

  binaryOp_add3055.io.RightIO <> binaryOp_mul2954.io.Out(0)

  icmp_exitcond14459.io.LeftIO <> binaryOp_inc4158.io.Out(1)

  br_60.io.CmpIO <> icmp_exitcond14459.io.Out(0)

  binaryOp_add3867.io.LeftIO <> phi_dw_conv_113361.io.Out(0)

  binaryOp_add3563.io.RightIO <> phi_dw_conv_s1_r_dw__x_013262.io.Out(0)

  binaryOp_inc68.io.LeftIO <> phi_dw_conv_s1_r_dw__x_013262.io.Out(1)

  Gep_arrayidx64.io.idx(0) <> binaryOp_add3563.io.Out(0)

  ld_65.io.GepAddr <> Gep_arrayidx64.io.Out(0)

  sextconv3766.io.Input <> ld_65.io.Out(0)

  binaryOp_add3867.io.RightIO <> sextconv3766.io.Out(0)

  icmp_exitcond69.io.LeftIO <> binaryOp_inc68.io.Out(1)

  br_70.io.CmpIO <> icmp_exitcond69.io.Out(0)

  binaryOp_add125.io.RightIO <> InputSplitter.io.Out.data.elements("field4")(1)

  icmp_cmp131376.io.RightIO <> InputSplitter.io.Out.data.elements("field4")(2)

  binaryOp_add125.io.LeftIO <> InputSplitter.io.Out.data.elements("field5")(1)

  binaryOp_add43.io.RightIO <> InputSplitter.io.Out.data.elements("field6")(1)

  icmp_cmp51394.io.RightIO <> InputSplitter.io.Out.data.elements("field6")(2)

  binaryOp_add43.io.LeftIO <> InputSplitter.io.Out.data.elements("field7")(1)

  binaryOp_add0.io.RightIO <> InputSplitter.io.Out.data.elements("field8")(1)

  icmp_cmp1421.io.RightIO <> InputSplitter.io.Out.data.elements("field8")(2)

  binaryOp_add0.io.LeftIO <> InputSplitter.io.Out.data.elements("field9")(0)

  st_47.io.Out(0).ready := true.B



  /* ================================================================== *
   *                   PRINTING OUTPUT INTERFACE                        *
   * ================================================================== */

  io.out <> ret_9.io.Out

}

import java.io.{File, FileWriter}

object extracted_function_reductionTop extends App {
  val dir = new File("RTL/extracted_function_reductionTop");
  dir.mkdirs
  implicit val p = Parameters.root((new MiniConfig).toInstance)
  val chirrtl = firrtl.Parser.parse(chisel3.Driver.emit(() => new extracted_function_reductionDF()))

  val verilogFile = new File(dir, s"${chirrtl.main}.v")
  val verilogWriter = new FileWriter(verilogFile)
  val compileResult = (new firrtl.VerilogCompiler).compileAndEmit(firrtl.CircuitState(chirrtl, firrtl.ChirrtlForm))
  val compiledStuff = compileResult.getEmittedCircuit
  verilogWriter.write(compiledStuff.value)
  verilogWriter.close()
}
