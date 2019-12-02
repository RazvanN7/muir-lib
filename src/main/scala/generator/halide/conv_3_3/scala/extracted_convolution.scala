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

abstract class extracted_convolutionDFIO(implicit val p: Parameters) extends Module with CoreParams {
  val io = IO(new Bundle {
    val in = Flipped(Decoupled(new Call(List(32, 32, 32, 32, 32))))
    val MemResp = Flipped(Valid(new MemResp))
    val MemReq = Decoupled(new MemReq)
    val out = Decoupled(new Call(List()))
  })
}

class extracted_convolutionDF(implicit p: Parameters) extends extracted_convolutionDFIO()(p) {


  /* ================================================================== *
   *                   PRINTING MEMORY MODULES                          *
   * ================================================================== */

  val MemCtrl = Module(new UnifiedController(ID = 0, Size = 32, NReads = 3, NWrites = 1)
  (WControl = new WriteMemoryController(NumOps = 1, BaseSize = 2, NumEntries = 2))
  (RControl = new ReadMemoryController(NumOps = 3, BaseSize = 2, NumEntries = 2))
  (RWArbiter = new ReadWriteArbiter()))

  io.MemReq <> MemCtrl.io.MemReq
  MemCtrl.io.MemResp <> io.MemResp

  val InputSplitter = Module(new SplitCallNew(List(1, 1, 1, 1, 1)))
  InputSplitter.io.In <> io.in



  /* ================================================================== *
   *                   PRINTING LOOP HEADERS                            *
   * ================================================================== */

  val Loop_0 = Module(new LoopBlockNode(NumIns = List(1, 1, 2, 1, 1), NumOuts = List(), NumCarry = List(1), NumExits = 1, ID = 1))

  val Loop_1 = Module(new LoopBlockNode(NumIns = List(1, 1, 1, 1, 1, 1), NumOuts = List(), NumCarry = List(1), NumExits = 1, ID = 2))

  val Loop_2 = Module(new LoopBlockNode(NumIns = List(1, 1, 1, 1, 1, 1, 1), NumOuts = List(), NumCarry = List(1), NumExits = 1, ID = 3))

  val Loop_3 = Module(new LoopBlockNode(NumIns = List(1, 1, 1, 1, 1), NumOuts = List(), NumCarry = List(1), NumExits = 1, ID = 4))



  /* ================================================================== *
   *                   PRINTING BASICBLOCK NODES                        *
   * ================================================================== */

  val bb_entry0 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 1, BID = 0))

  val bb_for_cond_cleanup1 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 1, BID = 1))

  val bb_for_body2 = Module(new BasicBlockNode(NumInputs = 2, NumOuts = 5, NumPhi = 1, BID = 2))

  val bb_for_cond_cleanup33 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 5, BID = 3))

  val bb_for_body44 = Module(new BasicBlockNode(NumInputs = 2, NumOuts = 6, NumPhi = 1, BID = 4))

  val bb_for_cond_cleanup75 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 5, BID = 5))

  val bb_for_body86 = Module(new BasicBlockNode(NumInputs = 2, NumOuts = 8, NumPhi = 1, BID = 6))

  val bb_for_cond_cleanup157 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 5, BID = 7))

  val bb_for_body168 = Module(new BasicBlockNode(NumInputs = 2, NumOuts = 18, NumPhi = 1, BID = 8))



  /* ================================================================== *
   *                   PRINTING INSTRUCTION NODES                       *
   * ================================================================== */

  //  br label %for.body, !dbg !1142, !UID !1143, !BB_UID !1144
  val br_0 = Module(new UBranchNode(ID = 0))

  //  ret void, !dbg !1145, !UID !1146, !BB_UID !1147
  val ret_1 = Module(new RetNode2(retTypes = List(), ID = 1))

  //  %_conv_s1_y.070 = phi i32 [ 0, %entry ], [ %inc32, %for.cond.cleanup3 ], !UID !1148
  val phi_conv_s1_y_0702 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 3, ID = 2, Res = true))

  //  %mul = mul nuw nsw i32 %_conv_s1_y.070, 62, !UID !1150
  val binaryOp_mul3 = Module(new ComputeNode(NumOuts = 1, ID = 3, opCode = "mul")(sign = false))

  //  br label %for.body4, !dbg !1151, !UID !1152, !BB_UID !1153
  val br_4 = Module(new UBranchNode(ID = 4))

  //  %inc32 = add nuw nsw i32 %_conv_s1_y.070, 1, !dbg !1154, !UID !1155
  val binaryOp_inc325 = Module(new ComputeNode(NumOuts = 2, ID = 5, opCode = "add")(sign = false))

  //  %exitcond73 = icmp eq i32 %inc32, 62, !dbg !1156, !UID !1157
  val icmp_exitcond736 = Module(new ComputeNode(NumOuts = 1, ID = 6, opCode = "eq")(sign = false))

  //  br i1 %exitcond73, label %for.cond.cleanup, label %for.body, !dbg !1142, !llvm.loop !1158, !UID !1160, !BB_UID !1161
  val br_7 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 7))

  //  %_conv_s1_x.069 = phi i32 [ 0, %for.body ], [ %inc29, %for.cond.cleanup7 ], !UID !1162
  val phi_conv_s1_x_0698 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 3, ID = 8, Res = true))

  //  %sub = sub i32 %_conv_s1_x.069, %_222, !dbg !1163, !UID !1164
  val binaryOp_sub9 = Module(new ComputeNode(NumOuts = 1, ID = 9, opCode = "sub")(sign = false))

  //  %add = add nuw nsw i32 %_conv_s1_x.069, %mul, !dbg !1167, !UID !1168
  val binaryOp_add10 = Module(new ComputeNode(NumOuts = 1, ID = 10, opCode = "add")(sign = false))

  //  %arrayidx = getelementptr inbounds i32, i32* %_conv, i32 %add, !UID !1171
  val Gep_arrayidx11 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 11)(ElementSize = 4, ArraySize = List()))

  //  br label %for.body8, !dbg !1172, !UID !1173, !BB_UID !1174
  val br_12 = Module(new UBranchNode(ID = 12))

  //  %inc29 = add nuw nsw i32 %_conv_s1_x.069, 1, !dbg !1175, !UID !1176
  val binaryOp_inc2913 = Module(new ComputeNode(NumOuts = 2, ID = 13, opCode = "add")(sign = false))

  //  %exitcond72 = icmp eq i32 %inc29, 62, !dbg !1177, !UID !1178
  val icmp_exitcond7214 = Module(new ComputeNode(NumOuts = 1, ID = 14, opCode = "eq")(sign = false))

  //  br i1 %exitcond72, label %for.cond.cleanup3, label %for.body4, !dbg !1151, !llvm.loop !1179, !UID !1181, !BB_UID !1182
  val br_15 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 15))

  //  %_conv_s1_r__y.068 = phi i32 [ 0, %for.body4 ], [ %inc26, %for.cond.cleanup15 ], !UID !1183
  val phi_conv_s1_r__y_06816 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 3, ID = 16, Res = true))

  //  %mul9 = mul nuw nsw i32 %_conv_s1_r__y.068, 3, !dbg !1184, !UID !1185
  val binaryOp_mul917 = Module(new ComputeNode(NumOuts = 1, ID = 17, opCode = "mul")(sign = false))

  //  %add10 = add nuw nsw i32 %_conv_s1_r__y.068, %_conv_s1_y.070, !dbg !1187, !UID !1188
  val binaryOp_add1018 = Module(new ComputeNode(NumOuts = 1, ID = 18, opCode = "add")(sign = false))

  //  %mul11 = mul nsw i32 %add10, %_18, !dbg !1190, !UID !1191
  val binaryOp_mul1119 = Module(new ComputeNode(NumOuts = 1, ID = 19, opCode = "mul")(sign = false))

  //  %add12 = add nsw i32 %sub, %mul11, !dbg !1193, !UID !1194
  val binaryOp_add1220 = Module(new ComputeNode(NumOuts = 1, ID = 20, opCode = "add")(sign = false))

  //  br label %for.body16, !dbg !1197, !UID !1198, !BB_UID !1199
  val br_21 = Module(new UBranchNode(ID = 21))

  //  %inc26 = add nuw nsw i32 %_conv_s1_r__y.068, 1, !dbg !1200, !UID !1201
  val binaryOp_inc2622 = Module(new ComputeNode(NumOuts = 2, ID = 22, opCode = "add")(sign = false))

  //  %exitcond71 = icmp eq i32 %inc26, 3, !dbg !1202, !UID !1203
  val icmp_exitcond7123 = Module(new ComputeNode(NumOuts = 1, ID = 23, opCode = "eq")(sign = false))

  //  br i1 %exitcond71, label %for.cond.cleanup7, label %for.body8, !dbg !1172, !llvm.loop !1204, !UID !1206, !BB_UID !1207
  val br_24 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 24))

  //  %_conv_s1_r__x.067 = phi i32 [ 0, %for.body8 ], [ %inc, %for.body16 ], !UID !1208
  val phi_conv_s1_r__x_06725 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 3, ID = 25, Res = true))

  //  %0 = load i32, i32* %arrayidx, align 4, !dbg !1209, !tbaa !1210, !UID !1214
  val ld_26 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 26, RouteID = 0))

  //  %add17 = add nuw nsw i32 %_conv_s1_r__x.067, %mul9, !dbg !1216, !UID !1217
  val binaryOp_add1727 = Module(new ComputeNode(NumOuts = 1, ID = 27, opCode = "add")(sign = false))

  //  %arrayidx18 = getelementptr inbounds i32, i32* %_kernel, i32 %add17, !dbg !1219, !UID !1220
  val Gep_arrayidx1828 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 28)(ElementSize = 4, ArraySize = List()))

  //  %1 = load i32, i32* %arrayidx18, align 4, !dbg !1219, !tbaa !1210, !UID !1221
  val ld_29 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 29, RouteID = 1))

  //  %add19 = add nsw i32 %add12, %_conv_s1_r__x.067, !dbg !1223, !UID !1224
  val binaryOp_add1930 = Module(new ComputeNode(NumOuts = 1, ID = 30, opCode = "add")(sign = false))

  //  %arrayidx20 = getelementptr inbounds i8, i8* %_input, i32 %add19, !dbg !1226, !UID !1227
  val Gep_arrayidx2031 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 31)(ElementSize = 1, ArraySize = List()))

  //  %2 = load i8, i8* %arrayidx20, align 1, !dbg !1226, !tbaa !1228, !UID !1229
  val ld_32 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 32, RouteID = 2))

  //  %conv21 = zext i8 %2 to i32, !dbg !1231, !UID !1232
  val sextconv2133 = Module(new ZextNode(NumOuts = 1))

  //  %mul22 = mul nsw i32 %1, %conv21, !dbg !1234, !UID !1235
  val binaryOp_mul2234 = Module(new ComputeNode(NumOuts = 1, ID = 34, opCode = "mul")(sign = false))

  //  %add23 = add nsw i32 %mul22, %0, !dbg !1237, !UID !1238
  val binaryOp_add2335 = Module(new ComputeNode(NumOuts = 1, ID = 35, opCode = "add")(sign = false))

  //  store i32 %add23, i32* %arrayidx, align 4, !dbg !1240, !tbaa !1210, !UID !1241
  val st_36 = Module(new UnTypStore(NumPredOps = 0, NumSuccOps = 0, ID = 36, RouteID = 0))

  //  %inc = add nuw nsw i32 %_conv_s1_r__x.067, 1, !dbg !1242, !UID !1243
  val binaryOp_inc37 = Module(new ComputeNode(NumOuts = 2, ID = 37, opCode = "add")(sign = false))

  //  %exitcond = icmp eq i32 %inc, 3, !dbg !1244, !UID !1245
  val icmp_exitcond38 = Module(new ComputeNode(NumOuts = 1, ID = 38, opCode = "eq")(sign = false))

  //  br i1 %exitcond, label %for.cond.cleanup15, label %for.body16, !dbg !1197, !llvm.loop !1246, !UID !1248, !BB_UID !1249
  val br_39 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 39))



  /* ================================================================== *
   *                   PRINTING CONSTANTS NODES                         *
   * ================================================================== */

  //i32 0
  val const0 = Module(new ConstFastNode(value = 0, ID = 0))

  //i32 62
  val const1 = Module(new ConstFastNode(value = 62, ID = 1))

  //i32 1
  val const2 = Module(new ConstFastNode(value = 1, ID = 2))

  //i32 62
  val const3 = Module(new ConstFastNode(value = 62, ID = 3))

  //i32 0
  val const4 = Module(new ConstFastNode(value = 0, ID = 4))

  //i32 1
  val const5 = Module(new ConstFastNode(value = 1, ID = 5))

  //i32 62
  val const6 = Module(new ConstFastNode(value = 62, ID = 6))

  //i32 0
  val const7 = Module(new ConstFastNode(value = 0, ID = 7))

  //i32 3
  val const8 = Module(new ConstFastNode(value = 3, ID = 8))

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



  /* ================================================================== *
   *                   BASICBLOCK -> PREDICATE LOOP                     *
   * ================================================================== */

  bb_for_cond_cleanup1.io.predicateIn(0) <> Loop_3.io.loopExit(0)

  bb_for_body2.io.predicateIn(1) <> Loop_3.io.activate_loop_start

  bb_for_body2.io.predicateIn(0) <> Loop_3.io.activate_loop_back

  bb_for_cond_cleanup33.io.predicateIn(0) <> Loop_2.io.loopExit(0)

  bb_for_body44.io.predicateIn(1) <> Loop_2.io.activate_loop_start

  bb_for_body44.io.predicateIn(0) <> Loop_2.io.activate_loop_back

  bb_for_cond_cleanup75.io.predicateIn(0) <> Loop_1.io.loopExit(0)

  bb_for_body86.io.predicateIn(1) <> Loop_1.io.activate_loop_start

  bb_for_body86.io.predicateIn(0) <> Loop_1.io.activate_loop_back

  bb_for_cond_cleanup157.io.predicateIn(0) <> Loop_0.io.loopExit(0)

  bb_for_body168.io.predicateIn(1) <> Loop_0.io.activate_loop_start

  bb_for_body168.io.predicateIn(0) <> Loop_0.io.activate_loop_back



  /* ================================================================== *
   *                   PRINTING PARALLEL CONNECTIONS                    *
   * ================================================================== */



  /* ================================================================== *
   *                   LOOP -> PREDICATE INSTRUCTION                    *
   * ================================================================== */

  Loop_0.io.enable <> br_21.io.Out(0)

  Loop_0.io.loopBack(0) <> br_39.io.FalseOutput(0)

  Loop_0.io.loopFinish(0) <> br_39.io.TrueOutput(0)

  Loop_1.io.enable <> br_12.io.Out(0)

  Loop_1.io.loopBack(0) <> br_24.io.FalseOutput(0)

  Loop_1.io.loopFinish(0) <> br_24.io.TrueOutput(0)

  Loop_2.io.enable <> br_4.io.Out(0)

  Loop_2.io.loopBack(0) <> br_15.io.FalseOutput(0)

  Loop_2.io.loopFinish(0) <> br_15.io.TrueOutput(0)

  Loop_3.io.enable <> br_0.io.Out(0)

  Loop_3.io.loopBack(0) <> br_7.io.FalseOutput(0)

  Loop_3.io.loopFinish(0) <> br_7.io.TrueOutput(0)



  /* ================================================================== *
   *                   ENDING INSTRUCTIONS                              *
   * ================================================================== */



  /* ================================================================== *
   *                   LOOP INPUT DATA DEPENDENCIES                     *
   * ================================================================== */

  Loop_0.io.InLiveIn(0) <> binaryOp_mul917.io.Out(0)

  Loop_0.io.InLiveIn(1) <> binaryOp_add1220.io.Out(0)

  Loop_0.io.InLiveIn(2) <> Loop_1.io.OutLiveIn.elements("field1")(0)

  Loop_0.io.InLiveIn(3) <> Loop_1.io.OutLiveIn.elements("field2")(0)

  Loop_0.io.InLiveIn(4) <> Loop_1.io.OutLiveIn.elements("field3")(0)

  Loop_1.io.InLiveIn(0) <> binaryOp_sub9.io.Out(0)

  Loop_1.io.InLiveIn(1) <> Gep_arrayidx11.io.Out(0)

  Loop_1.io.InLiveIn(2) <> Loop_2.io.OutLiveIn.elements("field3")(0)

  Loop_1.io.InLiveIn(3) <> Loop_2.io.OutLiveIn.elements("field4")(0)

  Loop_1.io.InLiveIn(4) <> Loop_2.io.OutLiveIn.elements("field1")(0)

  Loop_1.io.InLiveIn(5) <> Loop_2.io.OutLiveIn.elements("field2")(0)

  Loop_2.io.InLiveIn(0) <> binaryOp_mul3.io.Out(0)

  Loop_2.io.InLiveIn(1) <> phi_conv_s1_y_0702.io.Out(0)

  Loop_2.io.InLiveIn(2) <> Loop_3.io.OutLiveIn.elements("field2")(0)

  Loop_2.io.InLiveIn(3) <> Loop_3.io.OutLiveIn.elements("field3")(0)

  Loop_2.io.InLiveIn(4) <> Loop_3.io.OutLiveIn.elements("field4")(0)

  Loop_2.io.InLiveIn(5) <> Loop_3.io.OutLiveIn.elements("field1")(0)

  Loop_2.io.InLiveIn(6) <> Loop_3.io.OutLiveIn.elements("field0")(0)

  Loop_3.io.InLiveIn(0) <> InputSplitter.io.Out.data.elements("field4")(0)

  Loop_3.io.InLiveIn(1) <> InputSplitter.io.Out.data.elements("field2")(0)

  Loop_3.io.InLiveIn(2) <> InputSplitter.io.Out.data.elements("field3")(0)

  Loop_3.io.InLiveIn(3) <> InputSplitter.io.Out.data.elements("field0")(0)

  Loop_3.io.InLiveIn(4) <> InputSplitter.io.Out.data.elements("field1")(0)



  /* ================================================================== *
   *                   LOOP DATA LIVE-IN DEPENDENCIES                   *
   * ================================================================== */

  binaryOp_add1727.io.RightIO <> Loop_0.io.OutLiveIn.elements("field0")(0)

  binaryOp_add1930.io.LeftIO <> Loop_0.io.OutLiveIn.elements("field1")(0)

  ld_26.io.GepAddr <> Loop_0.io.OutLiveIn.elements("field2")(0)

  st_36.io.GepAddr <> Loop_0.io.OutLiveIn.elements("field2")(1)

  Gep_arrayidx1828.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field3")(0)

  Gep_arrayidx2031.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field4")(0)

  binaryOp_add1220.io.LeftIO <> Loop_1.io.OutLiveIn.elements("field0")(0)

  binaryOp_add1018.io.RightIO <> Loop_1.io.OutLiveIn.elements("field4")(0)

  binaryOp_mul1119.io.RightIO <> Loop_1.io.OutLiveIn.elements("field5")(0)

  binaryOp_add10.io.RightIO <> Loop_2.io.OutLiveIn.elements("field0")(0)

  Gep_arrayidx11.io.baseAddress <> Loop_2.io.OutLiveIn.elements("field5")(0)

  binaryOp_sub9.io.RightIO <> Loop_2.io.OutLiveIn.elements("field6")(0)



  /* ================================================================== *
   *                   LOOP DATA LIVE-OUT DEPENDENCIES                  *
   * ================================================================== */



  /* ================================================================== *
   *                   LOOP LIVE OUT DEPENDENCIES                       *
   * ================================================================== */



  /* ================================================================== *
   *                   LOOP CARRY DEPENDENCIES                          *
   * ================================================================== */

  Loop_0.io.CarryDepenIn(0) <> binaryOp_inc37.io.Out(0)

  Loop_1.io.CarryDepenIn(0) <> binaryOp_inc2622.io.Out(0)

  Loop_2.io.CarryDepenIn(0) <> binaryOp_inc2913.io.Out(0)

  Loop_3.io.CarryDepenIn(0) <> binaryOp_inc325.io.Out(0)



  /* ================================================================== *
   *                   LOOP DATA CARRY DEPENDENCIES                     *
   * ================================================================== */

  phi_conv_s1_r__x_06725.io.InData(1) <> Loop_0.io.CarryDepenOut.elements("field0")(0)

  phi_conv_s1_r__y_06816.io.InData(1) <> Loop_1.io.CarryDepenOut.elements("field0")(0)

  phi_conv_s1_x_0698.io.InData(1) <> Loop_2.io.CarryDepenOut.elements("field0")(0)

  phi_conv_s1_y_0702.io.InData(1) <> Loop_3.io.CarryDepenOut.elements("field0")(0)



  /* ================================================================== *
   *                   BASICBLOCK -> ENABLE INSTRUCTION                 *
   * ================================================================== */

  br_0.io.enable <> bb_entry0.io.Out(0)


  ret_1.io.In.enable <> bb_for_cond_cleanup1.io.Out(0)


  const0.io.enable <> bb_for_body2.io.Out(0)

  const1.io.enable <> bb_for_body2.io.Out(1)

  phi_conv_s1_y_0702.io.enable <> bb_for_body2.io.Out(2)


  binaryOp_mul3.io.enable <> bb_for_body2.io.Out(3)


  br_4.io.enable <> bb_for_body2.io.Out(4)


  const2.io.enable <> bb_for_cond_cleanup33.io.Out(0)

  const3.io.enable <> bb_for_cond_cleanup33.io.Out(1)

  binaryOp_inc325.io.enable <> bb_for_cond_cleanup33.io.Out(2)


  icmp_exitcond736.io.enable <> bb_for_cond_cleanup33.io.Out(3)


  br_7.io.enable <> bb_for_cond_cleanup33.io.Out(4)


  const4.io.enable <> bb_for_body44.io.Out(0)

  phi_conv_s1_x_0698.io.enable <> bb_for_body44.io.Out(1)


  binaryOp_sub9.io.enable <> bb_for_body44.io.Out(2)


  binaryOp_add10.io.enable <> bb_for_body44.io.Out(3)


  Gep_arrayidx11.io.enable <> bb_for_body44.io.Out(4)


  br_12.io.enable <> bb_for_body44.io.Out(5)


  const5.io.enable <> bb_for_cond_cleanup75.io.Out(0)

  const6.io.enable <> bb_for_cond_cleanup75.io.Out(1)

  binaryOp_inc2913.io.enable <> bb_for_cond_cleanup75.io.Out(2)


  icmp_exitcond7214.io.enable <> bb_for_cond_cleanup75.io.Out(3)


  br_15.io.enable <> bb_for_cond_cleanup75.io.Out(4)


  const7.io.enable <> bb_for_body86.io.Out(0)

  const8.io.enable <> bb_for_body86.io.Out(1)

  phi_conv_s1_r__y_06816.io.enable <> bb_for_body86.io.Out(2)


  binaryOp_mul917.io.enable <> bb_for_body86.io.Out(3)


  binaryOp_add1018.io.enable <> bb_for_body86.io.Out(4)


  binaryOp_mul1119.io.enable <> bb_for_body86.io.Out(5)


  binaryOp_add1220.io.enable <> bb_for_body86.io.Out(6)


  br_21.io.enable <> bb_for_body86.io.Out(7)


  const9.io.enable <> bb_for_cond_cleanup157.io.Out(0)

  const10.io.enable <> bb_for_cond_cleanup157.io.Out(1)

  binaryOp_inc2622.io.enable <> bb_for_cond_cleanup157.io.Out(2)


  icmp_exitcond7123.io.enable <> bb_for_cond_cleanup157.io.Out(3)


  br_24.io.enable <> bb_for_cond_cleanup157.io.Out(4)


  const11.io.enable <> bb_for_body168.io.Out(0)

  const12.io.enable <> bb_for_body168.io.Out(1)

  const13.io.enable <> bb_for_body168.io.Out(2)

  phi_conv_s1_r__x_06725.io.enable <> bb_for_body168.io.Out(3)


  ld_26.io.enable <> bb_for_body168.io.Out(4)


  binaryOp_add1727.io.enable <> bb_for_body168.io.Out(5)


  Gep_arrayidx1828.io.enable <> bb_for_body168.io.Out(6)


  ld_29.io.enable <> bb_for_body168.io.Out(7)


  binaryOp_add1930.io.enable <> bb_for_body168.io.Out(8)


  Gep_arrayidx2031.io.enable <> bb_for_body168.io.Out(9)


  ld_32.io.enable <> bb_for_body168.io.Out(10)


  sextconv2133.io.enable <> bb_for_body168.io.Out(11)


  binaryOp_mul2234.io.enable <> bb_for_body168.io.Out(12)


  binaryOp_add2335.io.enable <> bb_for_body168.io.Out(13)


  st_36.io.enable <> bb_for_body168.io.Out(14)


  binaryOp_inc37.io.enable <> bb_for_body168.io.Out(15)


  icmp_exitcond38.io.enable <> bb_for_body168.io.Out(16)


  br_39.io.enable <> bb_for_body168.io.Out(17)




  /* ================================================================== *
   *                   CONNECTING PHI NODES                             *
   * ================================================================== */

  phi_conv_s1_y_0702.io.Mask <> bb_for_body2.io.MaskBB(0)

  phi_conv_s1_x_0698.io.Mask <> bb_for_body44.io.MaskBB(0)

  phi_conv_s1_r__y_06816.io.Mask <> bb_for_body86.io.MaskBB(0)

  phi_conv_s1_r__x_06725.io.Mask <> bb_for_body168.io.MaskBB(0)



  /* ================================================================== *
   *                   PRINT ALLOCA OFFSET                              *
   * ================================================================== */



  /* ================================================================== *
   *                   CONNECTING MEMORY CONNECTIONS                    *
   * ================================================================== */

  MemCtrl.io.ReadIn(0) <> ld_26.io.memReq

  ld_26.io.memResp <> MemCtrl.io.ReadOut(0)

  MemCtrl.io.ReadIn(1) <> ld_29.io.memReq

  ld_29.io.memResp <> MemCtrl.io.ReadOut(1)

  MemCtrl.io.ReadIn(2) <> ld_32.io.memReq

  ld_32.io.memResp <> MemCtrl.io.ReadOut(2)

  MemCtrl.io.WriteIn(0) <> st_36.io.memReq

  st_36.io.memResp <> MemCtrl.io.WriteOut(0)



  /* ================================================================== *
   *                   PRINT SHARED CONNECTIONS                         *
   * ================================================================== */



  /* ================================================================== *
   *                   CONNECTING DATA DEPENDENCIES                     *
   * ================================================================== */

  phi_conv_s1_y_0702.io.InData(0) <> const0.io.Out

  binaryOp_mul3.io.RightIO <> const1.io.Out

  binaryOp_inc325.io.RightIO <> const2.io.Out

  icmp_exitcond736.io.RightIO <> const3.io.Out

  phi_conv_s1_x_0698.io.InData(0) <> const4.io.Out

  binaryOp_inc2913.io.RightIO <> const5.io.Out

  icmp_exitcond7214.io.RightIO <> const6.io.Out

  phi_conv_s1_r__y_06816.io.InData(0) <> const7.io.Out

  binaryOp_mul917.io.RightIO <> const8.io.Out

  binaryOp_inc2622.io.RightIO <> const9.io.Out

  icmp_exitcond7123.io.RightIO <> const10.io.Out

  phi_conv_s1_r__x_06725.io.InData(0) <> const11.io.Out

  binaryOp_inc37.io.RightIO <> const12.io.Out

  icmp_exitcond38.io.RightIO <> const13.io.Out

  binaryOp_mul3.io.LeftIO <> phi_conv_s1_y_0702.io.Out(1)

  binaryOp_inc325.io.LeftIO <> phi_conv_s1_y_0702.io.Out(2)

  icmp_exitcond736.io.LeftIO <> binaryOp_inc325.io.Out(1)

  br_7.io.CmpIO <> icmp_exitcond736.io.Out(0)

  binaryOp_sub9.io.LeftIO <> phi_conv_s1_x_0698.io.Out(0)

  binaryOp_add10.io.LeftIO <> phi_conv_s1_x_0698.io.Out(1)

  binaryOp_inc2913.io.LeftIO <> phi_conv_s1_x_0698.io.Out(2)

  Gep_arrayidx11.io.idx(0) <> binaryOp_add10.io.Out(0)

  icmp_exitcond7214.io.LeftIO <> binaryOp_inc2913.io.Out(1)

  br_15.io.CmpIO <> icmp_exitcond7214.io.Out(0)

  binaryOp_mul917.io.LeftIO <> phi_conv_s1_r__y_06816.io.Out(0)

  binaryOp_add1018.io.LeftIO <> phi_conv_s1_r__y_06816.io.Out(1)

  binaryOp_inc2622.io.LeftIO <> phi_conv_s1_r__y_06816.io.Out(2)

  binaryOp_mul1119.io.LeftIO <> binaryOp_add1018.io.Out(0)

  binaryOp_add1220.io.RightIO <> binaryOp_mul1119.io.Out(0)

  icmp_exitcond7123.io.LeftIO <> binaryOp_inc2622.io.Out(1)

  br_24.io.CmpIO <> icmp_exitcond7123.io.Out(0)

  binaryOp_add1727.io.LeftIO <> phi_conv_s1_r__x_06725.io.Out(0)

  binaryOp_add1930.io.RightIO <> phi_conv_s1_r__x_06725.io.Out(1)

  binaryOp_inc37.io.LeftIO <> phi_conv_s1_r__x_06725.io.Out(2)

  binaryOp_add2335.io.RightIO <> ld_26.io.Out(0)

  Gep_arrayidx1828.io.idx(0) <> binaryOp_add1727.io.Out(0)

  ld_29.io.GepAddr <> Gep_arrayidx1828.io.Out(0)

  binaryOp_mul2234.io.LeftIO <> ld_29.io.Out(0)

  Gep_arrayidx2031.io.idx(0) <> binaryOp_add1930.io.Out(0)

  ld_32.io.GepAddr <> Gep_arrayidx2031.io.Out(0)

  sextconv2133.io.Input <> ld_32.io.Out(0)

  binaryOp_mul2234.io.RightIO <> sextconv2133.io.Out(0)

  binaryOp_add2335.io.LeftIO <> binaryOp_mul2234.io.Out(0)

  st_36.io.inData <> binaryOp_add2335.io.Out(0)

  icmp_exitcond38.io.LeftIO <> binaryOp_inc37.io.Out(1)

  br_39.io.CmpIO <> icmp_exitcond38.io.Out(0)

  st_36.io.Out(0).ready := true.B



  /* ================================================================== *
   *                   PRINTING OUTPUT INTERFACE                        *
   * ================================================================== */

  io.out <> ret_1.io.Out

}

import java.io.{File, FileWriter}

object extracted_convolutionTop extends App {
  val dir = new File("RTL/extracted_convolutionTop");
  dir.mkdirs
  implicit val p = Parameters.root((new MiniConfig).toInstance)
  val chirrtl = firrtl.Parser.parse(chisel3.Driver.emit(() => new extracted_convolutionDF()))

  val verilogFile = new File(dir, s"${chirrtl.main}.v")
  val verilogWriter = new FileWriter(verilogFile)
  val compileResult = (new firrtl.VerilogCompiler).compileAndEmit(firrtl.CircuitState(chirrtl, firrtl.ChirrtlForm))
  val compiledStuff = compileResult.getEmittedCircuit
  verilogWriter.write(compiledStuff.value)
  verilogWriter.close()
}
