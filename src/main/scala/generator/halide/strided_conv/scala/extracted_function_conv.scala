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

abstract class extracted_function_convDFIO(implicit val p: Parameters) extends Module with CoreParams {
  val io = IO(new Bundle {
    val in = Flipped(Decoupled(new Call(List(32, 32, 32, 32, 32, 32))))
    val MemResp = Flipped(Valid(new MemResp))
    val MemReq = Decoupled(new MemReq)
    val out = Decoupled(new Call(List()))
  })
}

class extracted_function_convDF(implicit p: Parameters) extends extracted_function_convDFIO()(p) {


  /* ================================================================== *
   *                   PRINTING MEMORY MODULES                          *
   * ================================================================== */

  val MemCtrl = Module(new UnifiedController(ID = 0, Size = 32, NReads = 19, NWrites = 9)
  (WControl = new WriteMemoryController(NumOps = 9, BaseSize = 2, NumEntries = 2))
  (RControl = new ReadMemoryController(NumOps = 19, BaseSize = 2, NumEntries = 2))
  (RWArbiter = new ReadWriteArbiter()))

  io.MemReq <> MemCtrl.io.MemReq
  MemCtrl.io.MemResp <> io.MemResp

  val InputSplitter = Module(new SplitCallNew(List(1, 9, 1, 1, 1, 2)))
  InputSplitter.io.In <> io.in



  /* ================================================================== *
   *                   PRINTING LOOP HEADERS                            *
   * ================================================================== */

  val Loop_0 = Module(new LoopBlockNode(NumIns = List(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 1, 1, 1, 1, 1), NumOuts = List(), NumCarry = List(1), NumExits = 1, ID = 0))

  val Loop_1 = Module(new LoopBlockNode(NumIns = List(3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), NumOuts = List(), NumCarry = List(1), NumExits = 1, ID = 1))



  /* ================================================================== *
   *                   PRINTING BASICBLOCK NODES                        *
   * ================================================================== */

  val bb_entry0 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 19, BID = 0))

  val bb_for_cond_cleanup1 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 1, BID = 1))

  val bb_for_body2 = Module(new BasicBlockNode(NumInputs = 2, NumOuts = 16, NumPhi = 1, BID = 2))

  val bb_for_cond_cleanup113 = Module(new BasicBlockNoMaskFastNode(NumInputs = 1, NumOuts = 5, BID = 3))

  val bb_for_body124 = Module(new BasicBlockNode(NumInputs = 2, NumOuts = 93, NumPhi = 1, BID = 4))



  /* ================================================================== *
   *                   PRINTING INSTRUCTION NODES                       *
   * ================================================================== */

  //  %mul = mul nsw i32 %_18, %_16, !dbg !1229, !UID !1230
  val binaryOp_mul0 = Module(new ComputeNode(NumOuts = 1, ID = 0, opCode = "mul")(sign = false, Debug = false))

  //  %add = add nsw i32 %mul, %_13, !dbg !1232, !UID !1233
  val binaryOp_add1 = Module(new ComputeNode(NumOuts = 1, ID = 1, opCode = "add")(sign = false, Debug = false))

  //  %arrayidx25 = getelementptr inbounds i32, i32* %_kernel, i32 1, !UID !1236
  val Gep_arrayidx252 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 2)(ElementSize = 4, ArraySize = List()))

  //  %arrayidx38 = getelementptr inbounds i32, i32* %_kernel, i32 2, !UID !1237
  val Gep_arrayidx383 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 3)(ElementSize = 4, ArraySize = List()))

  //  %arrayidx51 = getelementptr inbounds i32, i32* %_kernel, i32 3, !UID !1238
  val Gep_arrayidx514 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 4)(ElementSize = 4, ArraySize = List()))

  //  %arrayidx62 = getelementptr inbounds i32, i32* %_kernel, i32 4, !UID !1239
  val Gep_arrayidx625 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 5)(ElementSize = 4, ArraySize = List()))

  //  %arrayidx74 = getelementptr inbounds i32, i32* %_kernel, i32 5, !UID !1240
  val Gep_arrayidx746 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 6)(ElementSize = 4, ArraySize = List()))

  //  %arrayidx86 = getelementptr inbounds i32, i32* %_kernel, i32 6, !UID !1241
  val Gep_arrayidx867 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 7)(ElementSize = 4, ArraySize = List()))

  //  %arrayidx97 = getelementptr inbounds i32, i32* %_kernel, i32 7, !UID !1242
  val Gep_arrayidx978 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 8)(ElementSize = 4, ArraySize = List()))

  //  %arrayidx109 = getelementptr inbounds i32, i32* %_kernel, i32 8, !UID !1243
  val Gep_arrayidx1099 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 9)(ElementSize = 4, ArraySize = List()))

  //  br label %for.body, !dbg !1244, !UID !1245, !BB_UID !1246
  val br_10 = Module(new UBranchNode(ID = 10))

  //  ret void, !dbg !1247, !UID !1248, !BB_UID !1249
  val ret_11 = Module(new RetNode2(retTypes = List(), ID = 11))

  //  %_conv_s1_y.0313 = phi i32 [ 0, %entry ], [ %inc120, %for.cond.cleanup11 ], !UID !1250
  val phi_conv_s1_y_031312 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 4, ID = 12, Res = true))

  //  %mul1 = shl nuw nsw i32 %_conv_s1_y.0313, 1, !dbg !1251, !UID !1252
  val binaryOp_mul113 = Module(new ComputeNode(NumOuts = 2, ID = 13, opCode = "shl")(sign = false, Debug = false))

  //  %add2 = or i32 %mul1, 1, !dbg !1254, !UID !1255
  val binaryOp_add214 = Module(new ComputeNode(NumOuts = 1, ID = 14, opCode = "or")(sign = false, Debug = false))

  //  %mul3 = mul nsw i32 %add2, %_18, !dbg !1257, !UID !1258
  val binaryOp_mul315 = Module(new ComputeNode(NumOuts = 1, ID = 15, opCode = "mul")(sign = false, Debug = false))

  //  %sub = sub nsw i32 %mul3, %add, !dbg !1260, !UID !1261
  val binaryOp_sub16 = Module(new ComputeNode(NumOuts = 1, ID = 16, opCode = "sub")(sign = false, Debug = false))

  //  %add4 = add nuw nsw i32 %mul1, 2, !dbg !1263, !UID !1264
  val binaryOp_add417 = Module(new ComputeNode(NumOuts = 1, ID = 17, opCode = "add")(sign = false, Debug = false))

  //  %mul5 = mul nsw i32 %add4, %_18, !dbg !1266, !UID !1267
  val binaryOp_mul518 = Module(new ComputeNode(NumOuts = 1, ID = 18, opCode = "mul")(sign = false, Debug = false))

  //  %sub6 = sub nsw i32 %mul5, %add, !dbg !1269, !UID !1270
  val binaryOp_sub619 = Module(new ComputeNode(NumOuts = 1, ID = 19, opCode = "sub")(sign = false, Debug = false))

  //  %mul7 = mul nsw i32 %_conv_s1_y.0313, %_18, !dbg !1272, !UID !1273
  val binaryOp_mul720 = Module(new ComputeNode(NumOuts = 1, ID = 20, opCode = "mul")(sign = false, Debug = false))

  //  %mul8 = mul nuw nsw i32 %_conv_s1_y.0313, 31, !dbg !1275, !UID !1276
  val binaryOp_mul821 = Module(new ComputeNode(NumOuts = 1, ID = 21, opCode = "mul")(sign = false, Debug = false))

  //  br label %for.body12, !dbg !1279, !UID !1280, !BB_UID !1281
  val br_22 = Module(new UBranchNode(ID = 22))

  //  %inc120 = add nuw nsw i32 %_conv_s1_y.0313, 1, !dbg !1282, !UID !1283
  val binaryOp_inc12023 = Module(new ComputeNode(NumOuts = 2, ID = 23, opCode = "add")(sign = false, Debug = false))

  //  %exitcond314 = icmp eq i32 %inc120, 31, !dbg !1284, !UID !1285
  val icmp_exitcond31424 = Module(new ComputeNode(NumOuts = 1, ID = 24, opCode = "eq")(sign = false, Debug = false))

  //  br i1 %exitcond314, label %for.cond.cleanup, label %for.body, !dbg !1244, !llvm.loop !1286, !UID !1288, !BB_UID !1289
  val br_25 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 25))

  //  %_conv_s1_x.0312 = phi i32 [ 0, %for.body ], [ %inc, %for.body12 ], !UID !1290
  val phi_conv_s1_x_031226 = Module(new PhiFastNode(NumInputs = 2, NumOutputs = 4, ID = 26, Res = true))

  //  %add13 = add nuw nsw i32 %_conv_s1_x.0312, %mul8, !dbg !1291, !UID !1292
  val binaryOp_add1327 = Module(new ComputeNode(NumOuts = 1, ID = 27, opCode = "add")(sign = false, Debug = false))

  //  %arrayidx = getelementptr inbounds i32, i32* %_conv, i32 %add13, !dbg !1294, !UID !1295
  val Gep_arrayidx28 = Module(new GepNode(NumIns = 1, NumOuts = 10, ID = 28)(ElementSize = 4, ArraySize = List()))

  //  %0 = load i32, i32* %arrayidx, align 4, !dbg !1294, !tbaa !1296, !UID !1300
  val ld_29 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 29, RouteID = 0))

  //  %1 = load i32, i32* %_kernel, align 4, !dbg !1302, !tbaa !1296, !UID !1303
  val ld_30 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 30, RouteID = 1))

  //  %add15 = add nsw i32 %_conv_s1_x.0312, %mul7, !dbg !1305, !UID !1306
  val binaryOp_add1531 = Module(new ComputeNode(NumOuts = 1, ID = 31, opCode = "add")(sign = false, Debug = false))

  //  %mul16 = shl nsw i32 %add15, 1, !dbg !1308, !UID !1309
  val binaryOp_mul1632 = Module(new ComputeNode(NumOuts = 1, ID = 32, opCode = "shl")(sign = false, Debug = false))

  //  %sub17 = sub nsw i32 %mul16, %add, !dbg !1311, !UID !1312
  val binaryOp_sub1733 = Module(new ComputeNode(NumOuts = 3, ID = 33, opCode = "sub")(sign = false, Debug = false))

  //  %arrayidx18 = getelementptr inbounds i8, i8* %_input, i32 %sub17, !dbg !1314, !UID !1315
  val Gep_arrayidx1834 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 34)(ElementSize = 1, ArraySize = List()))

  //  %2 = load i8, i8* %arrayidx18, align 1, !dbg !1314, !tbaa !1316, !UID !1317
  val ld_35 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 35, RouteID = 2))

  //  %conv19 = zext i8 %2 to i32, !dbg !1319, !UID !1320
  val sextconv1936 = Module(new ZextNode(NumOuts = 1))

  //  %mul20 = mul nsw i32 %1, %conv19, !dbg !1322, !UID !1323
  val binaryOp_mul2037 = Module(new ComputeNode(NumOuts = 1, ID = 37, opCode = "mul")(sign = false, Debug = false))

  //  %add21 = add nsw i32 %mul20, %0, !dbg !1325, !UID !1326
  val binaryOp_add2138 = Module(new ComputeNode(NumOuts = 2, ID = 38, opCode = "add")(sign = false, Debug = false))

  //  store i32 %add21, i32* %arrayidx, align 4, !dbg !1328, !tbaa !1296, !UID !1329
  val st_39 = Module(new UnTypStore(NumPredOps = 0, NumSuccOps = 0, ID = 39, RouteID = 0))

  //  %3 = load i32, i32* %arrayidx25, align 4, !dbg !1332, !tbaa !1296, !UID !1333
  val ld_40 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 40, RouteID = 3))

  //  %add29 = add nsw i32 %sub17, 1, !dbg !1338, !UID !1339
  val binaryOp_add2941 = Module(new ComputeNode(NumOuts = 1, ID = 41, opCode = "add")(sign = false, Debug = false))

  //  %arrayidx30 = getelementptr inbounds i8, i8* %_input, i32 %add29, !dbg !1341, !UID !1342
  val Gep_arrayidx3042 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 42)(ElementSize = 1, ArraySize = List()))

  //  %4 = load i8, i8* %arrayidx30, align 1, !dbg !1341, !tbaa !1316, !UID !1343
  val ld_43 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 43, RouteID = 4))

  //  %conv32 = zext i8 %4 to i32, !dbg !1345, !UID !1346
  val sextconv3244 = Module(new ZextNode(NumOuts = 1))

  //  %mul33 = mul nsw i32 %3, %conv32, !dbg !1348, !UID !1349
  val binaryOp_mul3345 = Module(new ComputeNode(NumOuts = 1, ID = 45, opCode = "mul")(sign = false, Debug = false))

  //  %add34 = add nsw i32 %mul33, %add21, !dbg !1351, !UID !1352
  val binaryOp_add3446 = Module(new ComputeNode(NumOuts = 2, ID = 46, opCode = "add")(sign = false, Debug = false))

  //  store i32 %add34, i32* %arrayidx, align 4, !dbg !1354, !tbaa !1296, !UID !1355
  val st_47 = Module(new UnTypStore(NumPredOps = 0, NumSuccOps = 0, ID = 47, RouteID = 1))

  //  %5 = load i32, i32* %arrayidx38, align 4, !dbg !1358, !tbaa !1296, !UID !1359
  val ld_48 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 48, RouteID = 5))

  //  %add42 = add nsw i32 %sub17, 2, !dbg !1364, !UID !1365
  val binaryOp_add4249 = Module(new ComputeNode(NumOuts = 1, ID = 49, opCode = "add")(sign = false, Debug = false))

  //  %arrayidx43 = getelementptr inbounds i8, i8* %_input, i32 %add42, !dbg !1367, !UID !1368
  val Gep_arrayidx4350 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 50)(ElementSize = 1, ArraySize = List()))

  //  %6 = load i8, i8* %arrayidx43, align 1, !dbg !1367, !tbaa !1316, !UID !1369
  val ld_51 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 51, RouteID = 6))

  //  %conv45 = zext i8 %6 to i32, !dbg !1371, !UID !1372
  val sextconv4552 = Module(new ZextNode(NumOuts = 1))

  //  %mul46 = mul nsw i32 %5, %conv45, !dbg !1374, !UID !1375
  val binaryOp_mul4653 = Module(new ComputeNode(NumOuts = 1, ID = 53, opCode = "mul")(sign = false, Debug = false))

  //  %add47 = add nsw i32 %mul46, %add34, !dbg !1377, !UID !1378
  val binaryOp_add4754 = Module(new ComputeNode(NumOuts = 2, ID = 54, opCode = "add")(sign = false, Debug = false))

  //  store i32 %add47, i32* %arrayidx, align 4, !dbg !1380, !tbaa !1296, !UID !1381
  val st_55 = Module(new UnTypStore(NumPredOps = 0, NumSuccOps = 0, ID = 55, RouteID = 2))

  //  %7 = load i32, i32* %arrayidx51, align 4, !dbg !1384, !tbaa !1296, !UID !1385
  val ld_56 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 56, RouteID = 7))

  //  %mul52 = shl nuw nsw i32 %_conv_s1_x.0312, 1, !dbg !1387, !UID !1388
  val binaryOp_mul5257 = Module(new ComputeNode(NumOuts = 2, ID = 57, opCode = "shl")(sign = false, Debug = false))

  //  %add53 = add nsw i32 %mul52, %sub, !dbg !1390, !UID !1391
  val binaryOp_add5358 = Module(new ComputeNode(NumOuts = 3, ID = 58, opCode = "add")(sign = false, Debug = false))

  //  %arrayidx54 = getelementptr inbounds i8, i8* %_input, i32 %add53, !dbg !1393, !UID !1394
  val Gep_arrayidx5459 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 59)(ElementSize = 1, ArraySize = List()))

  //  %8 = load i8, i8* %arrayidx54, align 1, !dbg !1393, !tbaa !1316, !UID !1395
  val ld_60 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 60, RouteID = 8))

  //  %conv56 = zext i8 %8 to i32, !dbg !1397, !UID !1398
  val sextconv5661 = Module(new ZextNode(NumOuts = 1))

  //  %mul57 = mul nsw i32 %7, %conv56, !dbg !1400, !UID !1401
  val binaryOp_mul5762 = Module(new ComputeNode(NumOuts = 1, ID = 62, opCode = "mul")(sign = false, Debug = false))

  //  %add58 = add nsw i32 %mul57, %add47, !dbg !1403, !UID !1404
  val binaryOp_add5863 = Module(new ComputeNode(NumOuts = 2, ID = 63, opCode = "add")(sign = false, Debug = false))

  //  store i32 %add58, i32* %arrayidx, align 4, !dbg !1406, !tbaa !1296, !UID !1407
  val st_64 = Module(new UnTypStore(NumPredOps = 0, NumSuccOps = 0, ID = 64, RouteID = 3))

  //  %9 = load i32, i32* %arrayidx62, align 4, !dbg !1410, !tbaa !1296, !UID !1411
  val ld_65 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 65, RouteID = 9))

  //  %add65 = add nsw i32 %add53, 1, !dbg !1415, !UID !1416
  val binaryOp_add6566 = Module(new ComputeNode(NumOuts = 1, ID = 66, opCode = "add")(sign = false, Debug = false))

  //  %arrayidx66 = getelementptr inbounds i8, i8* %_input, i32 %add65, !dbg !1418, !UID !1419
  val Gep_arrayidx6667 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 67)(ElementSize = 1, ArraySize = List()))

  //  %10 = load i8, i8* %arrayidx66, align 1, !dbg !1418, !tbaa !1316, !UID !1420
  val ld_68 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 68, RouteID = 10))

  //  %conv68 = zext i8 %10 to i32, !dbg !1422, !UID !1423
  val sextconv6869 = Module(new ZextNode(NumOuts = 1))

  //  %mul69 = mul nsw i32 %9, %conv68, !dbg !1425, !UID !1426
  val binaryOp_mul6970 = Module(new ComputeNode(NumOuts = 1, ID = 70, opCode = "mul")(sign = false, Debug = false))

  //  %add70 = add nsw i32 %mul69, %add58, !dbg !1428, !UID !1429
  val binaryOp_add7071 = Module(new ComputeNode(NumOuts = 2, ID = 71, opCode = "add")(sign = false, Debug = false))

  //  store i32 %add70, i32* %arrayidx, align 4, !dbg !1431, !tbaa !1296, !UID !1432
  val st_72 = Module(new UnTypStore(NumPredOps = 0, NumSuccOps = 0, ID = 72, RouteID = 4))

  //  %11 = load i32, i32* %arrayidx74, align 4, !dbg !1435, !tbaa !1296, !UID !1436
  val ld_73 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 73, RouteID = 11))

  //  %add77 = add nsw i32 %add53, 2, !dbg !1440, !UID !1441
  val binaryOp_add7774 = Module(new ComputeNode(NumOuts = 1, ID = 74, opCode = "add")(sign = false, Debug = false))

  //  %arrayidx78 = getelementptr inbounds i8, i8* %_input, i32 %add77, !dbg !1443, !UID !1444
  val Gep_arrayidx7875 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 75)(ElementSize = 1, ArraySize = List()))

  //  %12 = load i8, i8* %arrayidx78, align 1, !dbg !1443, !tbaa !1316, !UID !1445
  val ld_76 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 76, RouteID = 12))

  //  %conv80 = zext i8 %12 to i32, !dbg !1447, !UID !1448
  val sextconv8077 = Module(new ZextNode(NumOuts = 1))

  //  %mul81 = mul nsw i32 %11, %conv80, !dbg !1450, !UID !1451
  val binaryOp_mul8178 = Module(new ComputeNode(NumOuts = 1, ID = 78, opCode = "mul")(sign = false, Debug = false))

  //  %add82 = add nsw i32 %mul81, %add70, !dbg !1453, !UID !1454
  val binaryOp_add8279 = Module(new ComputeNode(NumOuts = 2, ID = 79, opCode = "add")(sign = false, Debug = false))

  //  store i32 %add82, i32* %arrayidx, align 4, !dbg !1456, !tbaa !1296, !UID !1457
  val st_80 = Module(new UnTypStore(NumPredOps = 0, NumSuccOps = 0, ID = 80, RouteID = 5))

  //  %13 = load i32, i32* %arrayidx86, align 4, !dbg !1460, !tbaa !1296, !UID !1461
  val ld_81 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 81, RouteID = 13))

  //  %add88 = add nsw i32 %mul52, %sub6, !dbg !1464, !UID !1465
  val binaryOp_add8882 = Module(new ComputeNode(NumOuts = 3, ID = 82, opCode = "add")(sign = false, Debug = false))

  //  %arrayidx89 = getelementptr inbounds i8, i8* %_input, i32 %add88, !dbg !1467, !UID !1468
  val Gep_arrayidx8983 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 83)(ElementSize = 1, ArraySize = List()))

  //  %14 = load i8, i8* %arrayidx89, align 1, !dbg !1467, !tbaa !1316, !UID !1469
  val ld_84 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 84, RouteID = 14))

  //  %conv91 = zext i8 %14 to i32, !dbg !1471, !UID !1472
  val sextconv9185 = Module(new ZextNode(NumOuts = 1))

  //  %mul92 = mul nsw i32 %13, %conv91, !dbg !1474, !UID !1475
  val binaryOp_mul9286 = Module(new ComputeNode(NumOuts = 1, ID = 86, opCode = "mul")(sign = false, Debug = false))

  //  %add93 = add nsw i32 %mul92, %add82, !dbg !1477, !UID !1478
  val binaryOp_add9387 = Module(new ComputeNode(NumOuts = 2, ID = 87, opCode = "add")(sign = false, Debug = false))

  //  store i32 %add93, i32* %arrayidx, align 4, !dbg !1480, !tbaa !1296, !UID !1481
  val st_88 = Module(new UnTypStore(NumPredOps = 0, NumSuccOps = 0, ID = 88, RouteID = 6))

  //  %15 = load i32, i32* %arrayidx97, align 4, !dbg !1484, !tbaa !1296, !UID !1485
  val ld_89 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 89, RouteID = 15))

  //  %add100 = add nsw i32 %add88, 1, !dbg !1489, !UID !1490
  val binaryOp_add10090 = Module(new ComputeNode(NumOuts = 1, ID = 90, opCode = "add")(sign = false, Debug = false))

  //  %arrayidx101 = getelementptr inbounds i8, i8* %_input, i32 %add100, !dbg !1492, !UID !1493
  val Gep_arrayidx10191 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 91)(ElementSize = 1, ArraySize = List()))

  //  %16 = load i8, i8* %arrayidx101, align 1, !dbg !1492, !tbaa !1316, !UID !1494
  val ld_92 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 92, RouteID = 16))

  //  %conv103 = zext i8 %16 to i32, !dbg !1496, !UID !1497
  val sextconv10393 = Module(new ZextNode(NumOuts = 1))

  //  %mul104 = mul nsw i32 %15, %conv103, !dbg !1499, !UID !1500
  val binaryOp_mul10494 = Module(new ComputeNode(NumOuts = 1, ID = 94, opCode = "mul")(sign = false, Debug = false))

  //  %add105 = add nsw i32 %mul104, %add93, !dbg !1502, !UID !1503
  val binaryOp_add10595 = Module(new ComputeNode(NumOuts = 2, ID = 95, opCode = "add")(sign = false, Debug = false))

  //  store i32 %add105, i32* %arrayidx, align 4, !dbg !1505, !tbaa !1296, !UID !1506
  val st_96 = Module(new UnTypStore(NumPredOps = 0, NumSuccOps = 0, ID = 96, RouteID = 7))

  //  %17 = load i32, i32* %arrayidx109, align 4, !dbg !1509, !tbaa !1296, !UID !1510
  val ld_97 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 97, RouteID = 17))

  //  %add112 = add nsw i32 %add88, 2, !dbg !1514, !UID !1515
  val binaryOp_add11298 = Module(new ComputeNode(NumOuts = 1, ID = 98, opCode = "add")(sign = false, Debug = false))

  //  %arrayidx113 = getelementptr inbounds i8, i8* %_input, i32 %add112, !dbg !1517, !UID !1518
  val Gep_arrayidx11399 = Module(new GepNode(NumIns = 1, NumOuts = 1, ID = 99)(ElementSize = 1, ArraySize = List()))

  //  %18 = load i8, i8* %arrayidx113, align 1, !dbg !1517, !tbaa !1316, !UID !1519
  val ld_100 = Module(new UnTypLoad(NumPredOps = 0, NumSuccOps = 0, NumOuts = 1, ID = 100, RouteID = 18))

  //  %conv115 = zext i8 %18 to i32, !dbg !1521, !UID !1522
  val sextconv115101 = Module(new ZextNode(NumOuts = 1))

  //  %mul116 = mul nsw i32 %17, %conv115, !dbg !1524, !UID !1525
  val binaryOp_mul116102 = Module(new ComputeNode(NumOuts = 1, ID = 102, opCode = "mul")(sign = false, Debug = false))

  //  %add117 = add nsw i32 %mul116, %add105, !dbg !1527, !UID !1528
  val binaryOp_add117103 = Module(new ComputeNode(NumOuts = 1, ID = 103, opCode = "add")(sign = false, Debug = false))

  //  store i32 %add117, i32* %arrayidx, align 4, !dbg !1530, !tbaa !1296, !UID !1531
  val st_104 = Module(new UnTypStore(NumPredOps = 0, NumSuccOps = 0, ID = 104, RouteID = 8))

  //  %inc = add nuw nsw i32 %_conv_s1_x.0312, 1, !dbg !1532, !UID !1533
  val binaryOp_inc105 = Module(new ComputeNode(NumOuts = 2, ID = 105, opCode = "add")(sign = false, Debug = false))

  //  %exitcond = icmp eq i32 %inc, 31, !dbg !1534, !UID !1535
  val icmp_exitcond106 = Module(new ComputeNode(NumOuts = 1, ID = 106, opCode = "eq")(sign = false, Debug = false))

  //  br i1 %exitcond, label %for.cond.cleanup11, label %for.body12, !dbg !1279, !llvm.loop !1536, !UID !1538, !BB_UID !1539
  val br_107 = Module(new CBranchNodeVariable(NumTrue = 1, NumFalse = 1, NumPredecessor = 0, ID = 107))



  /* ================================================================== *
   *                   PRINTING CONSTANTS NODES                         *
   * ================================================================== */

  //i32 1
  val const0 = Module(new ConstFastNode(value = 1, ID = 0))

  //i32 2
  val const1 = Module(new ConstFastNode(value = 2, ID = 1))

  //i32 3
  val const2 = Module(new ConstFastNode(value = 3, ID = 2))

  //i32 4
  val const3 = Module(new ConstFastNode(value = 4, ID = 3))

  //i32 5
  val const4 = Module(new ConstFastNode(value = 5, ID = 4))

  //i32 6
  val const5 = Module(new ConstFastNode(value = 6, ID = 5))

  //i32 7
  val const6 = Module(new ConstFastNode(value = 7, ID = 6))

  //i32 8
  val const7 = Module(new ConstFastNode(value = 8, ID = 7))

  //i32 0
  val const8 = Module(new ConstFastNode(value = 0, ID = 8))

  //i32 1
  val const9 = Module(new ConstFastNode(value = 1, ID = 9))

  //i32 1
  val const10 = Module(new ConstFastNode(value = 1, ID = 10))

  //i32 2
  val const11 = Module(new ConstFastNode(value = 2, ID = 11))

  //i32 31
  val const12 = Module(new ConstFastNode(value = 31, ID = 12))

  //i32 1
  val const13 = Module(new ConstFastNode(value = 1, ID = 13))

  //i32 31
  val const14 = Module(new ConstFastNode(value = 31, ID = 14))

  //i32 0
  val const15 = Module(new ConstFastNode(value = 0, ID = 15))

  //i32 1
  val const16 = Module(new ConstFastNode(value = 1, ID = 16))

  //i32 1
  val const17 = Module(new ConstFastNode(value = 1, ID = 17))

  //i32 2
  val const18 = Module(new ConstFastNode(value = 2, ID = 18))

  //i32 1
  val const19 = Module(new ConstFastNode(value = 1, ID = 19))

  //i32 1
  val const20 = Module(new ConstFastNode(value = 1, ID = 20))

  //i32 2
  val const21 = Module(new ConstFastNode(value = 2, ID = 21))

  //i32 1
  val const22 = Module(new ConstFastNode(value = 1, ID = 22))

  //i32 2
  val const23 = Module(new ConstFastNode(value = 2, ID = 23))

  //i32 1
  val const24 = Module(new ConstFastNode(value = 1, ID = 24))

  //i32 31
  val const25 = Module(new ConstFastNode(value = 31, ID = 25))



  /* ================================================================== *
   *                   BASICBLOCK -> PREDICATE INSTRUCTION              *
   * ================================================================== */

  bb_entry0.io.predicateIn(0) <> InputSplitter.io.Out.enable



  /* ================================================================== *
   *                   BASICBLOCK -> PREDICATE LOOP                     *
   * ================================================================== */

  bb_for_cond_cleanup1.io.predicateIn(0) <> Loop_1.io.loopExit(0)

  bb_for_body2.io.predicateIn(1) <> Loop_1.io.activate_loop_start

  bb_for_body2.io.predicateIn(0) <> Loop_1.io.activate_loop_back

  bb_for_cond_cleanup113.io.predicateIn(0) <> Loop_0.io.loopExit(0)

  bb_for_body124.io.predicateIn(1) <> Loop_0.io.activate_loop_start

  bb_for_body124.io.predicateIn(0) <> Loop_0.io.activate_loop_back



  /* ================================================================== *
   *                   PRINTING PARALLEL CONNECTIONS                    *
   * ================================================================== */



  /* ================================================================== *
   *                   LOOP -> PREDICATE INSTRUCTION                    *
   * ================================================================== */

  Loop_0.io.enable <> br_22.io.Out(0)

  Loop_0.io.loopBack(0) <> br_107.io.FalseOutput(0)

  Loop_0.io.loopFinish(0) <> br_107.io.TrueOutput(0)

  Loop_1.io.enable <> br_10.io.Out(0)

  Loop_1.io.loopBack(0) <> br_25.io.FalseOutput(0)

  Loop_1.io.loopFinish(0) <> br_25.io.TrueOutput(0)



  /* ================================================================== *
   *                   ENDING INSTRUCTIONS                              *
   * ================================================================== */



  /* ================================================================== *
   *                   LOOP INPUT DATA DEPENDENCIES                     *
   * ================================================================== */

  Loop_0.io.InLiveIn(0) <> binaryOp_mul821.io.Out(0)

  Loop_0.io.InLiveIn(1) <> binaryOp_mul720.io.Out(0)

  Loop_0.io.InLiveIn(2) <> binaryOp_sub16.io.Out(0)

  Loop_0.io.InLiveIn(3) <> binaryOp_sub619.io.Out(0)

  Loop_0.io.InLiveIn(4) <> Loop_1.io.OutLiveIn.elements("field12")(0)

  Loop_0.io.InLiveIn(5) <> Loop_1.io.OutLiveIn.elements("field1")(0)

  Loop_0.io.InLiveIn(6) <> Loop_1.io.OutLiveIn.elements("field9")(0)

  Loop_0.io.InLiveIn(7) <> Loop_1.io.OutLiveIn.elements("field8")(0)

  Loop_0.io.InLiveIn(8) <> Loop_1.io.OutLiveIn.elements("field7")(0)

  Loop_0.io.InLiveIn(9) <> Loop_1.io.OutLiveIn.elements("field11")(0)

  Loop_0.io.InLiveIn(10) <> Loop_1.io.OutLiveIn.elements("field4")(0)

  Loop_0.io.InLiveIn(11) <> Loop_1.io.OutLiveIn.elements("field2")(0)

  Loop_0.io.InLiveIn(12) <> Loop_1.io.OutLiveIn.elements("field6")(0)

  Loop_0.io.InLiveIn(13) <> Loop_1.io.OutLiveIn.elements("field10")(0)

  Loop_0.io.InLiveIn(14) <> Loop_1.io.OutLiveIn.elements("field3")(0)

  Loop_0.io.InLiveIn(15) <> Loop_1.io.OutLiveIn.elements("field5")(0)

  Loop_1.io.InLiveIn(0) <> InputSplitter.io.Out.data.elements("field5")(0)

  Loop_1.io.InLiveIn(1) <> binaryOp_add1.io.Out(0)

  Loop_1.io.InLiveIn(2) <> InputSplitter.io.Out.data.elements("field0")(0)

  Loop_1.io.InLiveIn(3) <> InputSplitter.io.Out.data.elements("field1")(0)

  Loop_1.io.InLiveIn(4) <> InputSplitter.io.Out.data.elements("field2")(0)

  Loop_1.io.InLiveIn(5) <> Gep_arrayidx252.io.Out(0)

  Loop_1.io.InLiveIn(6) <> Gep_arrayidx383.io.Out(0)

  Loop_1.io.InLiveIn(7) <> Gep_arrayidx514.io.Out(0)

  Loop_1.io.InLiveIn(8) <> Gep_arrayidx625.io.Out(0)

  Loop_1.io.InLiveIn(9) <> Gep_arrayidx746.io.Out(0)

  Loop_1.io.InLiveIn(10) <> Gep_arrayidx867.io.Out(0)

  Loop_1.io.InLiveIn(11) <> Gep_arrayidx978.io.Out(0)

  Loop_1.io.InLiveIn(12) <> Gep_arrayidx1099.io.Out(0)



  /* ================================================================== *
   *                   LOOP DATA LIVE-IN DEPENDENCIES                   *
   * ================================================================== */

  binaryOp_add1327.io.RightIO <> Loop_0.io.OutLiveIn.elements("field0")(0)

  binaryOp_add1531.io.RightIO <> Loop_0.io.OutLiveIn.elements("field1")(0)

  binaryOp_add5358.io.RightIO <> Loop_0.io.OutLiveIn.elements("field2")(0)

  binaryOp_add8882.io.RightIO <> Loop_0.io.OutLiveIn.elements("field3")(0)

  ld_97.io.GepAddr <> Loop_0.io.OutLiveIn.elements("field4")(0)

  binaryOp_sub1733.io.RightIO <> Loop_0.io.OutLiveIn.elements("field5")(0)

  ld_73.io.GepAddr <> Loop_0.io.OutLiveIn.elements("field6")(0)

  ld_65.io.GepAddr <> Loop_0.io.OutLiveIn.elements("field7")(0)

  ld_56.io.GepAddr <> Loop_0.io.OutLiveIn.elements("field8")(0)

  ld_89.io.GepAddr <> Loop_0.io.OutLiveIn.elements("field9")(0)

  Gep_arrayidx1834.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field10")(0)

  Gep_arrayidx3042.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field10")(1)

  Gep_arrayidx4350.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field10")(2)

  Gep_arrayidx5459.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field10")(3)

  Gep_arrayidx6667.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field10")(4)

  Gep_arrayidx7875.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field10")(5)

  Gep_arrayidx8983.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field10")(6)

  Gep_arrayidx10191.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field10")(7)

  Gep_arrayidx11399.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field10")(8)

  Gep_arrayidx28.io.baseAddress <> Loop_0.io.OutLiveIn.elements("field11")(0)

  ld_48.io.GepAddr <> Loop_0.io.OutLiveIn.elements("field12")(0)

  ld_81.io.GepAddr <> Loop_0.io.OutLiveIn.elements("field13")(0)

  ld_30.io.GepAddr <> Loop_0.io.OutLiveIn.elements("field14")(0)

  ld_40.io.GepAddr <> Loop_0.io.OutLiveIn.elements("field15")(0)

  binaryOp_mul315.io.RightIO <> Loop_1.io.OutLiveIn.elements("field0")(0)

  binaryOp_mul518.io.RightIO <> Loop_1.io.OutLiveIn.elements("field0")(1)

  binaryOp_mul720.io.RightIO <> Loop_1.io.OutLiveIn.elements("field0")(2)

  binaryOp_sub16.io.RightIO <> Loop_1.io.OutLiveIn.elements("field1")(1)

  binaryOp_sub619.io.RightIO <> Loop_1.io.OutLiveIn.elements("field1")(2)



  /* ================================================================== *
   *                   LOOP DATA LIVE-OUT DEPENDENCIES                  *
   * ================================================================== */



  /* ================================================================== *
   *                   LOOP LIVE OUT DEPENDENCIES                       *
   * ================================================================== */



  /* ================================================================== *
   *                   LOOP CARRY DEPENDENCIES                          *
   * ================================================================== */

  Loop_0.io.CarryDepenIn(0) <> binaryOp_inc105.io.Out(0)

  Loop_1.io.CarryDepenIn(0) <> binaryOp_inc12023.io.Out(0)



  /* ================================================================== *
   *                   LOOP DATA CARRY DEPENDENCIES                     *
   * ================================================================== */

  phi_conv_s1_x_031226.io.InData(1) <> Loop_0.io.CarryDepenOut.elements("field0")(0)

  phi_conv_s1_y_031312.io.InData(1) <> Loop_1.io.CarryDepenOut.elements("field0")(0)



  /* ================================================================== *
   *                   BASICBLOCK -> ENABLE INSTRUCTION                 *
   * ================================================================== */

  const0.io.enable <> bb_entry0.io.Out(0)

  const1.io.enable <> bb_entry0.io.Out(1)

  const2.io.enable <> bb_entry0.io.Out(2)

  const3.io.enable <> bb_entry0.io.Out(3)

  const4.io.enable <> bb_entry0.io.Out(4)

  const5.io.enable <> bb_entry0.io.Out(5)

  const6.io.enable <> bb_entry0.io.Out(6)

  const7.io.enable <> bb_entry0.io.Out(7)

  binaryOp_mul0.io.enable <> bb_entry0.io.Out(8)


  binaryOp_add1.io.enable <> bb_entry0.io.Out(9)


  Gep_arrayidx252.io.enable <> bb_entry0.io.Out(10)


  Gep_arrayidx383.io.enable <> bb_entry0.io.Out(11)


  Gep_arrayidx514.io.enable <> bb_entry0.io.Out(12)


  Gep_arrayidx625.io.enable <> bb_entry0.io.Out(13)


  Gep_arrayidx746.io.enable <> bb_entry0.io.Out(14)


  Gep_arrayidx867.io.enable <> bb_entry0.io.Out(15)


  Gep_arrayidx978.io.enable <> bb_entry0.io.Out(16)


  Gep_arrayidx1099.io.enable <> bb_entry0.io.Out(17)


  br_10.io.enable <> bb_entry0.io.Out(18)


  ret_11.io.In.enable <> bb_for_cond_cleanup1.io.Out(0)


  const8.io.enable <> bb_for_body2.io.Out(0)

  const9.io.enable <> bb_for_body2.io.Out(1)

  const10.io.enable <> bb_for_body2.io.Out(2)

  const11.io.enable <> bb_for_body2.io.Out(3)

  const12.io.enable <> bb_for_body2.io.Out(4)

  phi_conv_s1_y_031312.io.enable <> bb_for_body2.io.Out(5)


  binaryOp_mul113.io.enable <> bb_for_body2.io.Out(6)


  binaryOp_add214.io.enable <> bb_for_body2.io.Out(7)


  binaryOp_mul315.io.enable <> bb_for_body2.io.Out(8)


  binaryOp_sub16.io.enable <> bb_for_body2.io.Out(9)


  binaryOp_add417.io.enable <> bb_for_body2.io.Out(10)


  binaryOp_mul518.io.enable <> bb_for_body2.io.Out(11)


  binaryOp_sub619.io.enable <> bb_for_body2.io.Out(12)


  binaryOp_mul720.io.enable <> bb_for_body2.io.Out(13)


  binaryOp_mul821.io.enable <> bb_for_body2.io.Out(14)


  br_22.io.enable <> bb_for_body2.io.Out(15)


  const13.io.enable <> bb_for_cond_cleanup113.io.Out(0)

  const14.io.enable <> bb_for_cond_cleanup113.io.Out(1)

  binaryOp_inc12023.io.enable <> bb_for_cond_cleanup113.io.Out(2)


  icmp_exitcond31424.io.enable <> bb_for_cond_cleanup113.io.Out(3)


  br_25.io.enable <> bb_for_cond_cleanup113.io.Out(4)


  const15.io.enable <> bb_for_body124.io.Out(0)

  const16.io.enable <> bb_for_body124.io.Out(1)

  const17.io.enable <> bb_for_body124.io.Out(2)

  const18.io.enable <> bb_for_body124.io.Out(3)

  const19.io.enable <> bb_for_body124.io.Out(4)

  const20.io.enable <> bb_for_body124.io.Out(5)

  const21.io.enable <> bb_for_body124.io.Out(6)

  const22.io.enable <> bb_for_body124.io.Out(7)

  const23.io.enable <> bb_for_body124.io.Out(8)

  const24.io.enable <> bb_for_body124.io.Out(9)

  const25.io.enable <> bb_for_body124.io.Out(10)

  phi_conv_s1_x_031226.io.enable <> bb_for_body124.io.Out(11)


  binaryOp_add1327.io.enable <> bb_for_body124.io.Out(12)


  Gep_arrayidx28.io.enable <> bb_for_body124.io.Out(13)


  ld_29.io.enable <> bb_for_body124.io.Out(14)


  ld_30.io.enable <> bb_for_body124.io.Out(15)


  binaryOp_add1531.io.enable <> bb_for_body124.io.Out(16)


  binaryOp_mul1632.io.enable <> bb_for_body124.io.Out(17)


  binaryOp_sub1733.io.enable <> bb_for_body124.io.Out(18)


  Gep_arrayidx1834.io.enable <> bb_for_body124.io.Out(19)


  ld_35.io.enable <> bb_for_body124.io.Out(20)


  sextconv1936.io.enable <> bb_for_body124.io.Out(21)


  binaryOp_mul2037.io.enable <> bb_for_body124.io.Out(22)


  binaryOp_add2138.io.enable <> bb_for_body124.io.Out(23)


  st_39.io.enable <> bb_for_body124.io.Out(24)


  ld_40.io.enable <> bb_for_body124.io.Out(25)


  binaryOp_add2941.io.enable <> bb_for_body124.io.Out(26)


  Gep_arrayidx3042.io.enable <> bb_for_body124.io.Out(27)


  ld_43.io.enable <> bb_for_body124.io.Out(28)


  sextconv3244.io.enable <> bb_for_body124.io.Out(29)


  binaryOp_mul3345.io.enable <> bb_for_body124.io.Out(30)


  binaryOp_add3446.io.enable <> bb_for_body124.io.Out(31)


  st_47.io.enable <> bb_for_body124.io.Out(32)


  ld_48.io.enable <> bb_for_body124.io.Out(33)


  binaryOp_add4249.io.enable <> bb_for_body124.io.Out(34)


  Gep_arrayidx4350.io.enable <> bb_for_body124.io.Out(35)


  ld_51.io.enable <> bb_for_body124.io.Out(36)


  sextconv4552.io.enable <> bb_for_body124.io.Out(37)


  binaryOp_mul4653.io.enable <> bb_for_body124.io.Out(38)


  binaryOp_add4754.io.enable <> bb_for_body124.io.Out(39)


  st_55.io.enable <> bb_for_body124.io.Out(40)


  ld_56.io.enable <> bb_for_body124.io.Out(41)


  binaryOp_mul5257.io.enable <> bb_for_body124.io.Out(42)


  binaryOp_add5358.io.enable <> bb_for_body124.io.Out(43)


  Gep_arrayidx5459.io.enable <> bb_for_body124.io.Out(44)


  ld_60.io.enable <> bb_for_body124.io.Out(45)


  sextconv5661.io.enable <> bb_for_body124.io.Out(46)


  binaryOp_mul5762.io.enable <> bb_for_body124.io.Out(47)


  binaryOp_add5863.io.enable <> bb_for_body124.io.Out(48)


  st_64.io.enable <> bb_for_body124.io.Out(49)


  ld_65.io.enable <> bb_for_body124.io.Out(50)


  binaryOp_add6566.io.enable <> bb_for_body124.io.Out(51)


  Gep_arrayidx6667.io.enable <> bb_for_body124.io.Out(52)


  ld_68.io.enable <> bb_for_body124.io.Out(53)


  sextconv6869.io.enable <> bb_for_body124.io.Out(54)


  binaryOp_mul6970.io.enable <> bb_for_body124.io.Out(55)


  binaryOp_add7071.io.enable <> bb_for_body124.io.Out(56)


  st_72.io.enable <> bb_for_body124.io.Out(57)


  ld_73.io.enable <> bb_for_body124.io.Out(58)


  binaryOp_add7774.io.enable <> bb_for_body124.io.Out(59)


  Gep_arrayidx7875.io.enable <> bb_for_body124.io.Out(60)


  ld_76.io.enable <> bb_for_body124.io.Out(61)


  sextconv8077.io.enable <> bb_for_body124.io.Out(62)


  binaryOp_mul8178.io.enable <> bb_for_body124.io.Out(63)


  binaryOp_add8279.io.enable <> bb_for_body124.io.Out(64)


  st_80.io.enable <> bb_for_body124.io.Out(65)


  ld_81.io.enable <> bb_for_body124.io.Out(66)


  binaryOp_add8882.io.enable <> bb_for_body124.io.Out(67)


  Gep_arrayidx8983.io.enable <> bb_for_body124.io.Out(68)


  ld_84.io.enable <> bb_for_body124.io.Out(69)


  sextconv9185.io.enable <> bb_for_body124.io.Out(70)


  binaryOp_mul9286.io.enable <> bb_for_body124.io.Out(71)


  binaryOp_add9387.io.enable <> bb_for_body124.io.Out(72)


  st_88.io.enable <> bb_for_body124.io.Out(73)


  ld_89.io.enable <> bb_for_body124.io.Out(74)


  binaryOp_add10090.io.enable <> bb_for_body124.io.Out(75)


  Gep_arrayidx10191.io.enable <> bb_for_body124.io.Out(76)


  ld_92.io.enable <> bb_for_body124.io.Out(77)


  sextconv10393.io.enable <> bb_for_body124.io.Out(78)


  binaryOp_mul10494.io.enable <> bb_for_body124.io.Out(79)


  binaryOp_add10595.io.enable <> bb_for_body124.io.Out(80)


  st_96.io.enable <> bb_for_body124.io.Out(81)


  ld_97.io.enable <> bb_for_body124.io.Out(82)


  binaryOp_add11298.io.enable <> bb_for_body124.io.Out(83)


  Gep_arrayidx11399.io.enable <> bb_for_body124.io.Out(84)


  ld_100.io.enable <> bb_for_body124.io.Out(85)


  sextconv115101.io.enable <> bb_for_body124.io.Out(86)


  binaryOp_mul116102.io.enable <> bb_for_body124.io.Out(87)


  binaryOp_add117103.io.enable <> bb_for_body124.io.Out(88)


  st_104.io.enable <> bb_for_body124.io.Out(89)


  binaryOp_inc105.io.enable <> bb_for_body124.io.Out(90)


  icmp_exitcond106.io.enable <> bb_for_body124.io.Out(91)


  br_107.io.enable <> bb_for_body124.io.Out(92)




  /* ================================================================== *
   *                   CONNECTING PHI NODES                             *
   * ================================================================== */

  phi_conv_s1_y_031312.io.Mask <> bb_for_body2.io.MaskBB(0)

  phi_conv_s1_x_031226.io.Mask <> bb_for_body124.io.MaskBB(0)



  /* ================================================================== *
   *                   PRINT ALLOCA OFFSET                              *
   * ================================================================== */



  /* ================================================================== *
   *                   CONNECTING MEMORY CONNECTIONS                    *
   * ================================================================== */

  MemCtrl.io.ReadIn(0) <> ld_29.io.memReq

  ld_29.io.memResp <> MemCtrl.io.ReadOut(0)

  MemCtrl.io.ReadIn(1) <> ld_30.io.memReq

  ld_30.io.memResp <> MemCtrl.io.ReadOut(1)

  MemCtrl.io.ReadIn(2) <> ld_35.io.memReq

  ld_35.io.memResp <> MemCtrl.io.ReadOut(2)

  MemCtrl.io.WriteIn(0) <> st_39.io.memReq

  st_39.io.memResp <> MemCtrl.io.WriteOut(0)

  MemCtrl.io.ReadIn(3) <> ld_40.io.memReq

  ld_40.io.memResp <> MemCtrl.io.ReadOut(3)

  MemCtrl.io.ReadIn(4) <> ld_43.io.memReq

  ld_43.io.memResp <> MemCtrl.io.ReadOut(4)

  MemCtrl.io.WriteIn(1) <> st_47.io.memReq

  st_47.io.memResp <> MemCtrl.io.WriteOut(1)

  MemCtrl.io.ReadIn(5) <> ld_48.io.memReq

  ld_48.io.memResp <> MemCtrl.io.ReadOut(5)

  MemCtrl.io.ReadIn(6) <> ld_51.io.memReq

  ld_51.io.memResp <> MemCtrl.io.ReadOut(6)

  MemCtrl.io.WriteIn(2) <> st_55.io.memReq

  st_55.io.memResp <> MemCtrl.io.WriteOut(2)

  MemCtrl.io.ReadIn(7) <> ld_56.io.memReq

  ld_56.io.memResp <> MemCtrl.io.ReadOut(7)

  MemCtrl.io.ReadIn(8) <> ld_60.io.memReq

  ld_60.io.memResp <> MemCtrl.io.ReadOut(8)

  MemCtrl.io.WriteIn(3) <> st_64.io.memReq

  st_64.io.memResp <> MemCtrl.io.WriteOut(3)

  MemCtrl.io.ReadIn(9) <> ld_65.io.memReq

  ld_65.io.memResp <> MemCtrl.io.ReadOut(9)

  MemCtrl.io.ReadIn(10) <> ld_68.io.memReq

  ld_68.io.memResp <> MemCtrl.io.ReadOut(10)

  MemCtrl.io.WriteIn(4) <> st_72.io.memReq

  st_72.io.memResp <> MemCtrl.io.WriteOut(4)

  MemCtrl.io.ReadIn(11) <> ld_73.io.memReq

  ld_73.io.memResp <> MemCtrl.io.ReadOut(11)

  MemCtrl.io.ReadIn(12) <> ld_76.io.memReq

  ld_76.io.memResp <> MemCtrl.io.ReadOut(12)

  MemCtrl.io.WriteIn(5) <> st_80.io.memReq

  st_80.io.memResp <> MemCtrl.io.WriteOut(5)

  MemCtrl.io.ReadIn(13) <> ld_81.io.memReq

  ld_81.io.memResp <> MemCtrl.io.ReadOut(13)

  MemCtrl.io.ReadIn(14) <> ld_84.io.memReq

  ld_84.io.memResp <> MemCtrl.io.ReadOut(14)

  MemCtrl.io.WriteIn(6) <> st_88.io.memReq

  st_88.io.memResp <> MemCtrl.io.WriteOut(6)

  MemCtrl.io.ReadIn(15) <> ld_89.io.memReq

  ld_89.io.memResp <> MemCtrl.io.ReadOut(15)

  MemCtrl.io.ReadIn(16) <> ld_92.io.memReq

  ld_92.io.memResp <> MemCtrl.io.ReadOut(16)

  MemCtrl.io.WriteIn(7) <> st_96.io.memReq

  st_96.io.memResp <> MemCtrl.io.WriteOut(7)

  MemCtrl.io.ReadIn(17) <> ld_97.io.memReq

  ld_97.io.memResp <> MemCtrl.io.ReadOut(17)

  MemCtrl.io.ReadIn(18) <> ld_100.io.memReq

  ld_100.io.memResp <> MemCtrl.io.ReadOut(18)

  MemCtrl.io.WriteIn(8) <> st_104.io.memReq

  st_104.io.memResp <> MemCtrl.io.WriteOut(8)



  /* ================================================================== *
   *                   PRINT SHARED CONNECTIONS                         *
   * ================================================================== */



  /* ================================================================== *
   *                   CONNECTING DATA DEPENDENCIES                     *
   * ================================================================== */

  Gep_arrayidx252.io.idx(0) <> const0.io.Out

  Gep_arrayidx383.io.idx(0) <> const1.io.Out

  Gep_arrayidx514.io.idx(0) <> const2.io.Out

  Gep_arrayidx625.io.idx(0) <> const3.io.Out

  Gep_arrayidx746.io.idx(0) <> const4.io.Out

  Gep_arrayidx867.io.idx(0) <> const5.io.Out

  Gep_arrayidx978.io.idx(0) <> const6.io.Out

  Gep_arrayidx1099.io.idx(0) <> const7.io.Out

  phi_conv_s1_y_031312.io.InData(0) <> const8.io.Out

  binaryOp_mul113.io.RightIO <> const9.io.Out

  binaryOp_add214.io.RightIO <> const10.io.Out

  binaryOp_add417.io.RightIO <> const11.io.Out

  binaryOp_mul821.io.RightIO <> const12.io.Out

  binaryOp_inc12023.io.RightIO <> const13.io.Out

  icmp_exitcond31424.io.RightIO <> const14.io.Out

  phi_conv_s1_x_031226.io.InData(0) <> const15.io.Out

  binaryOp_mul1632.io.RightIO <> const16.io.Out

  binaryOp_add2941.io.RightIO <> const17.io.Out

  binaryOp_add4249.io.RightIO <> const18.io.Out

  binaryOp_mul5257.io.RightIO <> const19.io.Out

  binaryOp_add6566.io.RightIO <> const20.io.Out

  binaryOp_add7774.io.RightIO <> const21.io.Out

  binaryOp_add10090.io.RightIO <> const22.io.Out

  binaryOp_add11298.io.RightIO <> const23.io.Out

  binaryOp_inc105.io.RightIO <> const24.io.Out

  icmp_exitcond106.io.RightIO <> const25.io.Out

  binaryOp_add1.io.LeftIO <> binaryOp_mul0.io.Out(0)

  binaryOp_mul113.io.LeftIO <> phi_conv_s1_y_031312.io.Out(0)

  binaryOp_mul720.io.LeftIO <> phi_conv_s1_y_031312.io.Out(1)

  binaryOp_mul821.io.LeftIO <> phi_conv_s1_y_031312.io.Out(2)

  binaryOp_inc12023.io.LeftIO <> phi_conv_s1_y_031312.io.Out(3)

  binaryOp_add214.io.LeftIO <> binaryOp_mul113.io.Out(0)

  binaryOp_add417.io.LeftIO <> binaryOp_mul113.io.Out(1)

  binaryOp_mul315.io.LeftIO <> binaryOp_add214.io.Out(0)

  binaryOp_sub16.io.LeftIO <> binaryOp_mul315.io.Out(0)

  binaryOp_mul518.io.LeftIO <> binaryOp_add417.io.Out(0)

  binaryOp_sub619.io.LeftIO <> binaryOp_mul518.io.Out(0)

  icmp_exitcond31424.io.LeftIO <> binaryOp_inc12023.io.Out(1)

  br_25.io.CmpIO <> icmp_exitcond31424.io.Out(0)

  binaryOp_add1327.io.LeftIO <> phi_conv_s1_x_031226.io.Out(0)

  binaryOp_add1531.io.LeftIO <> phi_conv_s1_x_031226.io.Out(1)

  binaryOp_mul5257.io.LeftIO <> phi_conv_s1_x_031226.io.Out(2)

  binaryOp_inc105.io.LeftIO <> phi_conv_s1_x_031226.io.Out(3)

  Gep_arrayidx28.io.idx(0) <> binaryOp_add1327.io.Out(0)

  ld_29.io.GepAddr <> Gep_arrayidx28.io.Out(0)

  st_39.io.GepAddr <> Gep_arrayidx28.io.Out(1)

  st_47.io.GepAddr <> Gep_arrayidx28.io.Out(2)

  st_55.io.GepAddr <> Gep_arrayidx28.io.Out(3)

  st_64.io.GepAddr <> Gep_arrayidx28.io.Out(4)

  st_72.io.GepAddr <> Gep_arrayidx28.io.Out(5)

  st_80.io.GepAddr <> Gep_arrayidx28.io.Out(6)

  st_88.io.GepAddr <> Gep_arrayidx28.io.Out(7)

  st_96.io.GepAddr <> Gep_arrayidx28.io.Out(8)

  st_104.io.GepAddr <> Gep_arrayidx28.io.Out(9)

  binaryOp_add2138.io.RightIO <> ld_29.io.Out(0)

  binaryOp_mul2037.io.LeftIO <> ld_30.io.Out(0)

  binaryOp_mul1632.io.LeftIO <> binaryOp_add1531.io.Out(0)

  binaryOp_sub1733.io.LeftIO <> binaryOp_mul1632.io.Out(0)

  Gep_arrayidx1834.io.idx(0) <> binaryOp_sub1733.io.Out(0)

  binaryOp_add2941.io.LeftIO <> binaryOp_sub1733.io.Out(1)

  binaryOp_add4249.io.LeftIO <> binaryOp_sub1733.io.Out(2)

  ld_35.io.GepAddr <> Gep_arrayidx1834.io.Out(0)

  sextconv1936.io.Input <> ld_35.io.Out(0)

  binaryOp_mul2037.io.RightIO <> sextconv1936.io.Out(0)

  binaryOp_add2138.io.LeftIO <> binaryOp_mul2037.io.Out(0)

  st_39.io.inData <> binaryOp_add2138.io.Out(0)

  binaryOp_add3446.io.RightIO <> binaryOp_add2138.io.Out(1)

  binaryOp_mul3345.io.LeftIO <> ld_40.io.Out(0)

  Gep_arrayidx3042.io.idx(0) <> binaryOp_add2941.io.Out(0)

  ld_43.io.GepAddr <> Gep_arrayidx3042.io.Out(0)

  sextconv3244.io.Input <> ld_43.io.Out(0)

  binaryOp_mul3345.io.RightIO <> sextconv3244.io.Out(0)

  binaryOp_add3446.io.LeftIO <> binaryOp_mul3345.io.Out(0)

  st_47.io.inData <> binaryOp_add3446.io.Out(0)

  binaryOp_add4754.io.RightIO <> binaryOp_add3446.io.Out(1)

  binaryOp_mul4653.io.LeftIO <> ld_48.io.Out(0)

  Gep_arrayidx4350.io.idx(0) <> binaryOp_add4249.io.Out(0)

  ld_51.io.GepAddr <> Gep_arrayidx4350.io.Out(0)

  sextconv4552.io.Input <> ld_51.io.Out(0)

  binaryOp_mul4653.io.RightIO <> sextconv4552.io.Out(0)

  binaryOp_add4754.io.LeftIO <> binaryOp_mul4653.io.Out(0)

  st_55.io.inData <> binaryOp_add4754.io.Out(0)

  binaryOp_add5863.io.RightIO <> binaryOp_add4754.io.Out(1)

  binaryOp_mul5762.io.LeftIO <> ld_56.io.Out(0)

  binaryOp_add5358.io.LeftIO <> binaryOp_mul5257.io.Out(0)

  binaryOp_add8882.io.LeftIO <> binaryOp_mul5257.io.Out(1)

  Gep_arrayidx5459.io.idx(0) <> binaryOp_add5358.io.Out(0)

  binaryOp_add6566.io.LeftIO <> binaryOp_add5358.io.Out(1)

  binaryOp_add7774.io.LeftIO <> binaryOp_add5358.io.Out(2)

  ld_60.io.GepAddr <> Gep_arrayidx5459.io.Out(0)

  sextconv5661.io.Input <> ld_60.io.Out(0)

  binaryOp_mul5762.io.RightIO <> sextconv5661.io.Out(0)

  binaryOp_add5863.io.LeftIO <> binaryOp_mul5762.io.Out(0)

  st_64.io.inData <> binaryOp_add5863.io.Out(0)

  binaryOp_add7071.io.RightIO <> binaryOp_add5863.io.Out(1)

  binaryOp_mul6970.io.LeftIO <> ld_65.io.Out(0)

  Gep_arrayidx6667.io.idx(0) <> binaryOp_add6566.io.Out(0)

  ld_68.io.GepAddr <> Gep_arrayidx6667.io.Out(0)

  sextconv6869.io.Input <> ld_68.io.Out(0)

  binaryOp_mul6970.io.RightIO <> sextconv6869.io.Out(0)

  binaryOp_add7071.io.LeftIO <> binaryOp_mul6970.io.Out(0)

  st_72.io.inData <> binaryOp_add7071.io.Out(0)

  binaryOp_add8279.io.RightIO <> binaryOp_add7071.io.Out(1)

  binaryOp_mul8178.io.LeftIO <> ld_73.io.Out(0)

  Gep_arrayidx7875.io.idx(0) <> binaryOp_add7774.io.Out(0)

  ld_76.io.GepAddr <> Gep_arrayidx7875.io.Out(0)

  sextconv8077.io.Input <> ld_76.io.Out(0)

  binaryOp_mul8178.io.RightIO <> sextconv8077.io.Out(0)

  binaryOp_add8279.io.LeftIO <> binaryOp_mul8178.io.Out(0)

  st_80.io.inData <> binaryOp_add8279.io.Out(0)

  binaryOp_add9387.io.RightIO <> binaryOp_add8279.io.Out(1)

  binaryOp_mul9286.io.LeftIO <> ld_81.io.Out(0)

  Gep_arrayidx8983.io.idx(0) <> binaryOp_add8882.io.Out(0)

  binaryOp_add10090.io.LeftIO <> binaryOp_add8882.io.Out(1)

  binaryOp_add11298.io.LeftIO <> binaryOp_add8882.io.Out(2)

  ld_84.io.GepAddr <> Gep_arrayidx8983.io.Out(0)

  sextconv9185.io.Input <> ld_84.io.Out(0)

  binaryOp_mul9286.io.RightIO <> sextconv9185.io.Out(0)

  binaryOp_add9387.io.LeftIO <> binaryOp_mul9286.io.Out(0)

  st_88.io.inData <> binaryOp_add9387.io.Out(0)

  binaryOp_add10595.io.RightIO <> binaryOp_add9387.io.Out(1)

  binaryOp_mul10494.io.LeftIO <> ld_89.io.Out(0)

  Gep_arrayidx10191.io.idx(0) <> binaryOp_add10090.io.Out(0)

  ld_92.io.GepAddr <> Gep_arrayidx10191.io.Out(0)

  sextconv10393.io.Input <> ld_92.io.Out(0)

  binaryOp_mul10494.io.RightIO <> sextconv10393.io.Out(0)

  binaryOp_add10595.io.LeftIO <> binaryOp_mul10494.io.Out(0)

  st_96.io.inData <> binaryOp_add10595.io.Out(0)

  binaryOp_add117103.io.RightIO <> binaryOp_add10595.io.Out(1)

  binaryOp_mul116102.io.LeftIO <> ld_97.io.Out(0)

  Gep_arrayidx11399.io.idx(0) <> binaryOp_add11298.io.Out(0)

  ld_100.io.GepAddr <> Gep_arrayidx11399.io.Out(0)

  sextconv115101.io.Input <> ld_100.io.Out(0)

  binaryOp_mul116102.io.RightIO <> sextconv115101.io.Out(0)

  binaryOp_add117103.io.LeftIO <> binaryOp_mul116102.io.Out(0)

  st_104.io.inData <> binaryOp_add117103.io.Out(0)

  icmp_exitcond106.io.LeftIO <> binaryOp_inc105.io.Out(1)

  br_107.io.CmpIO <> icmp_exitcond106.io.Out(0)

  Gep_arrayidx252.io.baseAddress <> InputSplitter.io.Out.data.elements("field1")(1)

  Gep_arrayidx383.io.baseAddress <> InputSplitter.io.Out.data.elements("field1")(2)

  Gep_arrayidx514.io.baseAddress <> InputSplitter.io.Out.data.elements("field1")(3)

  Gep_arrayidx625.io.baseAddress <> InputSplitter.io.Out.data.elements("field1")(4)

  Gep_arrayidx746.io.baseAddress <> InputSplitter.io.Out.data.elements("field1")(5)

  Gep_arrayidx867.io.baseAddress <> InputSplitter.io.Out.data.elements("field1")(6)

  Gep_arrayidx978.io.baseAddress <> InputSplitter.io.Out.data.elements("field1")(7)

  Gep_arrayidx1099.io.baseAddress <> InputSplitter.io.Out.data.elements("field1")(8)

  binaryOp_add1.io.RightIO <> InputSplitter.io.Out.data.elements("field3")(0)

  binaryOp_mul0.io.RightIO <> InputSplitter.io.Out.data.elements("field4")(0)

  binaryOp_mul0.io.LeftIO <> InputSplitter.io.Out.data.elements("field5")(1)

  st_39.io.Out(0).ready := true.B

  st_47.io.Out(0).ready := true.B

  st_55.io.Out(0).ready := true.B

  st_64.io.Out(0).ready := true.B

  st_72.io.Out(0).ready := true.B

  st_80.io.Out(0).ready := true.B

  st_88.io.Out(0).ready := true.B

  st_96.io.Out(0).ready := true.B

  st_104.io.Out(0).ready := true.B



  /* ================================================================== *
   *                   PRINTING OUTPUT INTERFACE                        *
   * ================================================================== */

  io.out <> ret_11.io.Out

}

import java.io.{File, FileWriter}

object extracted_function_convTop extends App {
  val dir = new File("RTL/extracted_function_convTop");
  dir.mkdirs
  implicit val p = Parameters.root((new MiniConfig).toInstance)
  val chirrtl = firrtl.Parser.parse(chisel3.Driver.emit(() => new extracted_function_convDF()))

  val verilogFile = new File(dir, s"${chirrtl.main}.v")
  val verilogWriter = new FileWriter(verilogFile)
  val compileResult = (new firrtl.VerilogCompiler).compileAndEmit(firrtl.CircuitState(chirrtl, firrtl.ChirrtlForm))
  val compiledStuff = compileResult.getEmittedCircuit
  verilogWriter.write(compiledStuff.value)
  verilogWriter.close()
}
