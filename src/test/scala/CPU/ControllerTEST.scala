package CPU

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class ControllerTest extends AnyFlatSpec with ChiselScalatestTester {

  "Verify Main Controller" in {

    test(new Controller) { dut =>

      // R-Type
      dut.io.opcode.poke("h33".U)

      dut.io.RegWrite.expect(true.B)
      dut.io.mem_write.expect(false.B)
      dut.io.mem_read.expect(false.B)
      dut.io.mem_to_Reg.expect(false.B)
      dut.io.branch.expect(false.B)
      dut.io.operand_B_sel.expect(false.B)
      dut.io.extend_sel.expect("b00".U)
      dut.io.next_pc_sel.expect("b00".U)


      // I-Type
      dut.io.opcode.poke("h13".U)

      dut.io.RegWrite.expect(true.B)
      dut.io.mem_write.expect(false.B)
      dut.io.mem_read.expect(false.B)
      dut.io.mem_to_Reg.expect(false.B)
      dut.io.branch.expect(false.B)
      dut.io.operand_B_sel.expect(true.B)
      dut.io.extend_sel.expect("b00".U)
      dut.io.next_pc_sel.expect("b00".U)


      // Load
      dut.io.opcode.poke("h03".U)

      dut.io.RegWrite.expect(true.B)
      dut.io.mem_write.expect(false.B)
      dut.io.mem_read.expect(true.B)
      dut.io.mem_to_Reg.expect(true.B)
      dut.io.branch.expect(false.B)
      dut.io.operand_B_sel.expect(true.B)
      dut.io.extend_sel.expect("b01".U)
      dut.io.next_pc_sel.expect("b00".U)


      // Store
      dut.io.opcode.poke("h23".U)

      dut.io.RegWrite.expect(false.B)
      dut.io.mem_write.expect(true.B)
      dut.io.mem_read.expect(false.B)
      dut.io.mem_to_Reg.expect(false.B)
      dut.io.branch.expect(false.B)

      // Your SignalController uses immediate for Store
      dut.io.operand_B_sel.expect(true.B)

      dut.io.extend_sel.expect("b10".U)
      dut.io.next_pc_sel.expect("b00".U)


      // Branch
      dut.io.opcode.poke("h63".U)

      dut.io.RegWrite.expect(false.B)
      dut.io.mem_write.expect(false.B)
      dut.io.mem_read.expect(false.B)
      dut.io.mem_to_Reg.expect(false.B)
      dut.io.branch.expect(true.B)
      dut.io.operand_B_sel.expect(false.B)
      dut.io.extend_sel.expect("b00".U)
      dut.io.next_pc_sel.expect("b01".U)


      // JAL
      dut.io.opcode.poke("h6f".U)

      dut.io.RegWrite.expect(true.B)
      dut.io.mem_write.expect(false.B)
      dut.io.mem_read.expect(false.B)
      dut.io.mem_to_Reg.expect(false.B)
      dut.io.branch.expect(false.B)
      dut.io.extend_sel.expect("b00".U)
      dut.io.next_pc_sel.expect("b11".U)


      // JALR
      dut.io.opcode.poke("h67".U)

      dut.io.RegWrite.expect(true.B)
      dut.io.mem_write.expect(false.B)
      dut.io.mem_read.expect(false.B)
      dut.io.mem_to_Reg.expect(false.B)
      dut.io.branch.expect(false.B)
      dut.io.extend_sel.expect("b00".U)
      dut.io.next_pc_sel.expect("b01".U)


      // LUI
      dut.io.opcode.poke("h37".U)

      dut.io.RegWrite.expect(true.B)
      dut.io.mem_write.expect(false.B)
      dut.io.mem_read.expect(false.B)
      dut.io.mem_to_Reg.expect(false.B)
      dut.io.branch.expect(false.B)
      dut.io.operand_B_sel.expect(true.B)
      dut.io.extend_sel.expect("b00".U)
      dut.io.next_pc_sel.expect("b00".U)
    }
  }
}