package CPU

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.FreeSpec


class TypeControllerTEST extends FreeSpec with ChiselScalatestTester {

  "Type Controller test" in {

    test(new TypeController) { dut =>

      // R-Type
      dut.io.opcode.poke("b0110011".U)
      dut.io.R_type.expect(1.U)
      dut.io.I_type.expect(0.U)
      dut.io.L_type.expect(0.U)
      dut.io.S_type.expect(0.U)
      dut.io.SB_type.expect(0.U)
      dut.io.UJ_type.expect(0.U)
      dut.io.JALR.expect(0.U)
      dut.io.LUI.expect(0.U)
      dut.io.AUI.expect(0.U)

      // I-Type
      dut.io.opcode.poke("b0010011".U)
      dut.io.R_type.expect(0.U)
      dut.io.I_type.expect(1.U)

      // Load
      dut.io.opcode.poke("b0000011".U)
      dut.io.L_type.expect(1.U)
      dut.io.I_type.expect(0.U)

      // S-Type
      dut.io.opcode.poke("b0100011".U)
      dut.io.S_type.expect(1.U)
      dut.io.L_type.expect(0.U)

      // SB-Type
      dut.io.opcode.poke("b1100011".U)
      dut.io.SB_type.expect(1.U)
      dut.io.S_type.expect(0.U)

      // UJ / JAL
      dut.io.opcode.poke("b1101111".U)
      dut.io.UJ_type.expect(1.U)
      dut.io.SB_type.expect(0.U)

      // JALR
      dut.io.opcode.poke("b1100111".U)
      dut.io.JALR.expect(1.U)
      dut.io.UJ_type.expect(0.U)

      // LUI
      dut.io.opcode.poke("b0110111".U)
      dut.io.LUI.expect(1.U)
      dut.io.JALR.expect(0.U)

      // AUI / AUIPC
      dut.io.opcode.poke("b0010111".U)
      dut.io.AUI.expect(1.U)
      dut.io.LUI.expect(0.U)
    }
  }
}