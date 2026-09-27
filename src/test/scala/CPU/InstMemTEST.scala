package CPU

import chisel3._
import chisel3.tester._
import chisel3.experimental.BundleLiterals._
import org.scalatest.FreeSpec

class InstMemTEST extends FreeSpec with ChiselScalatestTester {
  "Inst Mem Test" in {
    test(
      new InstMem(
        "/home/blue-rangoon/Documents/Scala-Chisel-Learning-Journey-main/src/test/resources/inst.txt"
      )
    ) { dut =>
      dut.io.addr.poke(0.U)
      dut.clock.step(2)
      dut.io.inst.expect(0x06400513.U)

      dut.io.addr.poke(1.U)
      dut.clock.step(2)
      dut.io.inst.expect(0x06400500.U)

    }
  }
}