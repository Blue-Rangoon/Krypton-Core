package CPU

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import Opcode._

class ImmGenTest extends AnyFlatSpec with ChiselScalatestTester {

  "TEST ImmGen" in {

    test(new ImmGen) { dut =>

      // I-type instruction
      // Immediate = 10
      dut.io.instr.poke("h00A00093".U)
      dut.clock.step()
      dut.io.immd_se.expect(10.U)

      // Load instruction
      // Immediate = 8
      dut.io.instr.poke("h0080A103".U)
      dut.clock.step()
      dut.io.immd_se.expect(8.U)

      // JALR instruction
      // Immediate = 4
      dut.io.instr.poke("h00408067".U)
      dut.clock.step()
      dut.io.immd_se.expect(4.U)

      // S-type instruction
      // Store instruction with immediate = 12
      dut.io.instr.poke("h00C0A623".U)
      dut.clock.step()
      dut.io.immd_se.expect(12.U)

      // SB-type instruction
      // Branch instruction with immediate = 4
      dut.io.instr.poke("h00000263".U)
      dut.clock.step()
      dut.io.immd_se.expect(4.U)

      // LUI instruction
      // Upper immediate = 0x12345000
      dut.io.instr.poke("h123450B7".U)
      dut.clock.step()
      dut.io.immd_se.expect("h12345000".U)

      // AUIPC instruction
      // Upper immediate = 0x12345000
      dut.io.instr.poke("h12345097".U)
      dut.clock.step()
      dut.io.immd_se.expect("h12345000".U)

      // JAL instruction
      // Jump immediate = 8
      dut.io.instr.poke("h008000EF".U)
      dut.clock.step()
      dut.io.immd_se.expect(8.U)
    }
  }
}