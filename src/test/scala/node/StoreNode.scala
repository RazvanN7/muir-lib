package node

/**
  * Created by nvedula on 12/5/17.
  */
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester, OrderedDecoupledHWIOTester}
import org.scalatest.{Matchers, FlatSpec}

import config._


class StoreNodeTests(c: StoreNode) extends PeekPokeTester(c) {
  var is_k = false
  for (t <- 0 until 12) {

    //IF ready is set
    // send address
    if (peek(c.io.gepAddr.ready) == 1) {
      printf(s"\n t: ${t} gepAddr Fire \n")
      poke(c.io.gepAddr.valid, true)
      poke(c.io.gepAddr.bits, 12)
    }

    //IF ready is set
    // send data
    if (peek(c.io.inData.ready) == 1) {
      printf(s"\n t: ${t} inData Fire \n")
      poke(c.io.inData.valid, true)
      poke(c.io.inData.bits, 24)
    }

    //Response is before request -- so that it is true in the next cycle
    //NOte: Response should be received atleast after a cycle of memory request
    // Otherwise it is undefined behaviour
    if (is_k) {
      //since response is available atleast next cycle onwards
      poke(c.io.memResp.valid, true)

      printf(s"\n t: ${t} MemResponse received \n")
      printf(s"t: ${t}  io.Memresp_valid: ${peek(c.io.memResp.valid)} \n")
      is_k = false

    }

    //Memory is always ready to receive the memory requests
    //TODO make them as single signal
    poke(c.io.memReq.ready, true)
    //When StoreNode requests the data print the contents

    if (peek(c.io.memReq.valid) == 1) {
      if (!is_k) is_k = true

      printf(s"\n t: ${t} MemRequest Sent \n")
      printf(s"t: ${t}  io.Memreq_addr: ${peek(c.io.memReq.bits.address)} " +
        s"io.Memreq_data: ${peek(c.io.memReq.bits.data)}  io.memReq.bits.mask : ${peek(c.io.memReq.bits.node)} \n")


    }



    //at some clock - send src mem-op is done executing
    if (t > 4) {
      if (peek(c.io.predMemOp(0).ready) == 1) {
        poke(c.io.predMemOp(0).valid, true)
        printf(s"\n t:${t} predMemOp(0) Fire \n")
      }
      else {
        poke(c.io.predMemOp(0).valid, false)
      }

    }
    else {

      poke(c.io.predMemOp(0).valid, false)
    }


    //poke for output after clock 7
    if (t > 7) {
      poke(c.io.memOpAck.ready, true)
      if (peek(c.io.memOpAck.valid) == 1) {
        printf(s"\n  t: ${t} MemNode Reset \n")
        printf(s"t: ${t} io.memOpAck.valid " +
          s"${peek(c.io.memOpAck.valid)} " +
          s"io.memOpAck.ready: ${peek(c.io.memOpAck.ready)} \n")

      }
    }



    //    printf(s"t: ${t} io.gepAddr.bits: ${peek(c.io.gepAddr.bits)}, " +
    //      s"io.gepAddr.valid: ${peek(c.io.gepAddr.valid)} io.gepAddr.ready: " +
    //      s"${peek(c.io.gepAddr.ready)} \n")


    //    printf(s"t: ${t} io.inData.bits: ${peek(c.io.inData.bits)}, " +
    //      s"io.inData.valid: ${peek(c.io.inData.valid)} io.inData.ready: " +
    //      s"${peek(c.io.inData.ready)} \n")
    //
    //    printf(s"t: ${t} io.predMemOp(0).valid: ${peek(c.io.predMemOp(0).valid)} " +
    //      s"io.predMemOp(0).ready: " +
    //      s"${peek(c.io.predMemOp(0).ready)}")


    if (peek(c.io.predMemOp(0).valid) == 1) {
      printf(s"t: ${t}  io.predMemOp(0).valid: ${peek(c.io.predMemOp(0).valid)} " +
        s" io.predMemOp(0).ready: ${peek(c.io.predMemOp(0).ready)} \n ")
    }
    step(1)
  }

}


class StoreNodeTester extends  FlatSpec with Matchers {
  implicit val p = config.Parameters.root((new MiniConfig).toInstance)
  it should "Store Node tester" in {
    chisel3.iotesters.Driver(() => new StoreNode()) { c =>
      new StoreNodeTests(c)
    } should be(true)
  }
}
