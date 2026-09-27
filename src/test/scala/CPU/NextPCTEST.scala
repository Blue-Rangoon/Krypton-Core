package CPU

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class NextPCTest extends AnyFlatSpec with ChiselScalatestTester {

  "NextPC TEST" in {

    test(new NextPC) { dut =>

      // Normal PC + 4
      dut.io.Branch.poke(false.B)
      dut.io.Jal.poke(false.B)
      dut.io.Jalr.poke(false.B)
      dut.io.next_Pc.expect("b00".U)

      // Branch
      dut.io.Branch.poke(true.B)
      dut.io.next_Pc.expect("b01".U)

      // JALR
      dut.io.Branch.poke(false.B)
      dut.io.Jalr.poke(true.B)
      dut.io.next_Pc.expect("b01".U)

      // JAL
      dut.io.Jalr.poke(false.B)
      dut.io.Jal.poke(true.B)
      dut.io.next_Pc.expect("b11".U)
    }
  }
}