package CPU

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class PCTest extends AnyFlatSpec with ChiselScalatestTester {

  "PC Test" in {

    test(new PC) { dut =>

      // Initial PC
      dut.io.pc_out.expect(0.U)
      dut.io.pc4.expect(4.U)

      // Give PC a new value
      dut.io.pc_in.poke(100.U)
      dut.clock.step()

      dut.io.pc_out.expect(100.U)
      dut.io.pc4.expect(104.U)

      // Change PC again
      dut.io.pc_in.poke(200.U)
      dut.clock.step()

      dut.io.pc_out.expect(200.U)
      dut.io.pc4.expect(204.U)
    }
  }
}