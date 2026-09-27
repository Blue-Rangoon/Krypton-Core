package CPU

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class SignalControllerTest extends AnyFlatSpec with ChiselScalatestTester {

  "SignalController TEST" in {

    test(new SignalController) { dut =>


      // R-Type instruction
      dut.io.R_Type.poke(true.B)

      dut.io.Load.poke(false.B)
      dut.io.Store.poke(false.B)
      dut.io.Branch.poke(false.B)
      dut.io.I_Type.poke(false.B)
      dut.io.Jalr.poke(false.B)
      dut.io.Jal.poke(false.B)
      dut.io.LUI.poke(false.B)

      dut.clock.step()

      dut.io.RegWrite.expect(true.B)
      dut.io.mem_write.expect(false.B)
      dut.io.mem_read.expect(false.B)
      dut.io.mem_to_Reg.expect(false.B)
      dut.io.operand_B_sel.expect(false.B)



      // Load instruction
      dut.io.R_Type.poke(false.B)
      dut.io.Load.poke(true.B)

      dut.clock.step()

      dut.io.mem_read.expect(true.B)
      dut.io.mem_to_Reg.expect(true.B)
      dut.io.RegWrite.expect(true.B)
      dut.io.operand_B_sel.expect(true.B)
      dut.io.mem_write.expect(false.B)



      // Store instruction
      dut.io.Load.poke(false.B)
      dut.io.Store.poke(true.B)

      dut.clock.step()

      dut.io.mem_write.expect(true.B)
      dut.io.operand_B_sel.expect(true.B)
      dut.io.mem_read.expect(false.B)
      dut.io.RegWrite.expect(false.B)



      // I-Type instruction
      dut.io.Store.poke(false.B)
      dut.io.I_Type.poke(true.B)

      dut.clock.step()

      dut.io.RegWrite.expect(true.B)
      dut.io.operand_B_sel.expect(true.B)
      dut.io.mem_write.expect(false.B)
      dut.io.mem_read.expect(false.B)



      // Branch instruction
      dut.io.I_Type.poke(false.B)
      dut.io.Branch.poke(true.B)

      dut.clock.step()

      dut.io.branch.expect(true.B)
      dut.io.RegWrite.expect(false.B)
      dut.io.mem_write.expect(false.B)
      dut.io.mem_read.expect(false.B)



      // JAL instruction
      dut.io.Branch.poke(false.B)
      dut.io.Jal.poke(true.B)

      dut.clock.step()

      dut.io.RegWrite.expect(true.B)



      // JALR instruction
      dut.io.Jal.poke(false.B)
      dut.io.Jalr.poke(true.B)

      dut.clock.step()

      dut.io.RegWrite.expect(true.B)



      // LUI instruction
      dut.io.Jalr.poke(false.B)
      dut.io.LUI.poke(true.B)

      dut.clock.step()

      dut.io.RegWrite.expect(true.B)
      dut.io.operand_B_sel.expect(true.B)
      dut.io.mem_write.expect(false.B)
      dut.io.mem_read.expect(false.B)
    }
  }
}