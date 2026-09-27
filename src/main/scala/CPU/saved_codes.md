## signal controller code switch case:

package CPU
import chisel3._
import chisel3.utils._

class SignalControllerIO extends Bundle {

    val opcode = Input(UInt(7.W))

  //Signal Controller Inputs
  val R_type  = Input(UInt(32.W))
  val I_type  = Input(UInt(32.W))
  val L_type  = Input(UInt(32.W))
  val S_type  = Input(UInt(32.W))
  val SB_type = Input(UInt(32.W))
  val UJ_type = Input(UInt(32.W))
  val JALR    = Input(UInt(32.W))
  val LUI     = Input(UInt(32.W))
  val AUI     = Input(UInt(32.W))


  //Signal Controller Outputs
  val reg_write     = Output(UInt(32.W)) // i/p: R, I, LOAD, UJ, JALR, LUI | thru OR gate
  val mem_read      = Output(UInt(32.W)) // load --> mem_read (in logisim)
  val mem_to_reg    = Output(UInt(32.W)) // load --> mem_to_reg (in logisim)
  val mem_write     = Output(UInt(32.W)) // Thru store
  val branch        = Output(UInt(32.W)) // trhu branch SB
  val operand_b     = Output(UInt(32.W)) // i/p: I, LOAD, store, LUI | thru OR gate
  val extend_Sel    = Output(UInt(32.W)) // i/p: store, LUI | thru splitter 2 bits
  val operand_a_sel = Output(UInt(32.W)) //i/p: JAL, JALR, LUI | thru PC
  val next_pc_sel   = Output(UInt(32.W)) //i/p: JAL, LUI, Branch (SB) | thru PC
  val ALU_operation = Output(UInt(32.W)) //i/p: all i/ps except AUI in logisim | thru PC

}


// opcode trait made to store there binary values and 7.W is width lenght, bxxxx is bin value 
trait Opcode {
  val R    = "b0110011".U(7.W)
  val I    = "b0010011".U(7.W)
  val load = "b0000011".U(7.W)
  val S    = "b0100011".U(7.W)
  val SB   = "b1100011".U(7.W) //branchh
  val UJ   = "b1101111".U(7.W) //JAL
  val JALR = "b1100111".U(7.W) //JALR
  val LUI  = "b0110111".U(7.W) 
  val AUI  = "b0010111".U(7.W) 
}


class SignalController extends Module with Opcode {

  val io = IO(new TypeControllerIO)

  // Default: everything disabled, if nothing happens atleast it goes zero 
  io.R_type  := 0.U
  io.I_type  := 0.U
  io.L_type  := 0.U
  io.S_type  := 0.U
  io.SB_type := 0.U
  io.UJ_type := 0.U
  io.JALR    := 0.U
  io.LUI     := 0.U
  io.AUI     := 0.U

  switch(io.opcode) {

    is(R) {
      io.R_type := 1.U
    }

    is(I) {
      io.I_type := 1.U
    }

    is(load) {
      io.L_type := 1.U
    }

    is(S) {
      io.S_type := 1.U
    }

    is(SB) {
      io.SB_type := 1.U
    }

    is(UJ) {
      io.UJ_type := 1.U
    }

    is(JALR) {
      io.JALR := 1.U
    }

    is(LUI) {
      io.LUI := 1.U
    }

    is(AUI) {
      io.AUI := 1.U
    }

    
  }
}





## ============================================================================================

package CPU
import chisel3._
import chisel3.utils._

class SignalControllerIO extends Bundle {

    val opcode = Input(UInt(7.W))

  //Signal Controller Inputs
  val R_type  = Input(UInt(32.W))
  val I_type  = Input(UInt(32.W))
  val LOAD  = Input(UInt(32.W))
  val STORE  = Input(UInt(32.W))
  val SB_type = Input(UInt(32.W))
  val UJ_type = Input(UInt(32.W))
  val JALR    = Input(UInt(32.W))
  val LUI     = Input(UInt(32.W))
  val AUI     = Input(UInt(32.W))


  //Signal Controller Outputs a/c to logisim
  val reg_write     = Output(UInt(32.W)) // i/p: R, I, LOAD, UJ, JALR, LUI | thru OR gate
  val mem_read      = Output(UInt(32.W)) // load --> mem_read (in logisim)
  val mem_to_reg    = Output(UInt(32.W)) // load --> mem_to_reg (in logisim)
  val mem_write     = Output(UInt(32.W)) // Thru store
  val branch        = Output(UInt(32.W)) // trhu branch SB
  val operand_b     = Output(UInt(32.W)) // i/p: I, LOAD, store, LUI | thru OR gate
  val extend_Sel    = Output(UInt(32.W)) // i/p: store, LUI | thru splitter 2 bits
  val operand_a_sel = Output(UInt(32.W)) // i/p: JAL, JALR, LUI | thru PC
  val next_pc_sel   = Output(UInt(32.W)) // i/p: JAL, LUI, Branch (SB) | thru PC
  val ALU_operation = Output(UInt(32.W)) // i/p: all i/ps except AUI in logisim | thru PC

}


// opcode trait made to store there binary values and 7.W is width lenght, bxxxx is bin value 
trait Opcode {
  val R     = "b0110011".U(7.W)
  val I     = "b0010011".U(7.W)
  val load  = "b0000011".U(7.W)
  val store = "b0100011".U(7.W)
  val SB    = "b1100011".U(7.W) //branchh
  val UJ    = "b1101111".U(7.W) //JAL
  val JALR  = "b1100111".U(7.W) //JALR
  val LUI   = "b0110111".U(7.W) 
  val AUI   = "b0010111".U(7.W) 
}


class SignalController extends Module with Opcode {

  val io = IO(new SignalControllerIO)

  // Default: everything disabled, if nothing happens atleast it goes zero 
  io.R_type  := 0.U
  io.I_type  := 0.U
  io.L_type  := 0.U
  io.S_type  := 0.U
  io.SB_type := 0.U
  io.UJ_type := 0.U
  io.JALR    := 0.U
  io.LUI     := 0.U
  io.AUI     := 0.U


    //coding now
    io.reg_write := OR(io.R_type, io.I_type, io.LOAD, io.UJ_type, io.JALR, io.LUI)
    

    io.mem_write := io.STORE
    io.branch    := io.SB_type
    io.operand_b := OR(io.I_type, io.LOAD, io.STORE, io.LUI)
    // io.operand_a_sel := PC(io.JAL, io.JALR, io.LUI)
}










## PC TEST CASE my own: 

package CPU

import chisel3._
import chisel3.tester._
import org.scalatest.FreeSpec
import chisel3.experimental.BundleLiterals._

class PCTEST extends FreeSpec with ChiselScalatestTester {
  "PC Test" in {
    test(new PC()) { dut =>

      dut.io.PCin.poke(dut.io.PCnext.peek())
      dut.clock.step(1)

      dut.io.PCin.poke(dut.io.PCnext.peek())
      dut.clock.step(1)

      dut.io.PCin.poke(dut.io.PCnext.peek())
      dut.clock.step(1)

      dut.io.PCin.poke(dut.io.PCnext.peek())
      dut.clock.step(1)

      dut.io.PCin.poke(dut.io.PCnext.peek())
      dut.clock.step(1)

      dut.io.PCin.poke(dut.io.PCnext.peek())
      dut.clock.step(1)

      dut.io.PCin.poke(dut.io.PCnext.peek())
      dut.clock.step(1)

      dut.io.PCin.poke(dut.io.PCnext.peek())
      dut.clock.step(1)

      dut.io.PCin.poke(dut.io.PCnext.peek())
      dut.clock.step(1)

      dut.io.PCin.poke(dut.io.PCnext.peek())
      dut.clock.step(1)
    }
  }
}







## =========================== alu op gpt

package CPU

object ALUOP {

  val ALU_ADD    = "b00000".U(5.W)
  val ALU_SUB    = "b00001".U(5.W)
  val ALU_AND    = "b00010".U(5.W)
  val ALU_OR     = "b00011".U(5.W)
  val ALU_XOR    = "b00100".U(5.W)
  val ALU_SLT    = "b00101".U(5.W)
  val ALU_SLL    = "b00110".U(5.W)
  val ALU_SLTU   = "b00111".U(5.W)
  val ALU_SRL    = "b01000".U(5.W)
  val ALU_SRA    = "b01001".U(5.W)
  val ALU_COPY_A = "b01010".U(5.W)
  val ALU_COPY_B = "b01011".U(5.W)

  val ALU_BEQ    = "b10000".U(5.W)
  val ALU_BNE    = "b10001".U(5.W)
  val ALU_BLT    = "b10010".U(5.W)
  val ALU_BGE    = "b10011".U(5.W)
  val ALU_BLTU   = "b10100".U(5.W)
  val ALU_BGEU   = "b10101".U(5.W)
}



### J core: TOP FILE


package core

import chisel3._
import chisel3.util._

class Top(initFile: String = "fibonaciAssembly.txt") extends Module {

  val io = IO(new Bundle {
    val Reg_Out       = Output(SInt(32.W))
    val PC_Out        = Output(UInt(32.W))
    val instruction   = Output(UInt(32.W))
    val aluResult     = Output(UInt(32.W))
    val writeBackData = Output(SInt(32.W))
  })

  // ============================================================
  // MODULES
  // ============================================================
  val Pc         = Module(new ProgramCounter)
  val InsMem     = Module(new InstMem(initFile))
  val Regfile    = Module(new RegFile)
  val ImmGen     = Module(new Imm_gen)
  val controller = Module(new Controller)
  val AluControl = Module(new ALUControl)
  val ALU        = Module(new ALU)
  val DataMem    = Module(new DataMem)
  val TypeDecode = Module(new TypeControl)

  // ============================================================
  // BASIC SIGNALS
  // ============================================================
  val pcValue     = Pc.io.pc
  val pcPlus4     = Pc.io.pc4
  val instruction = InsMem.io.inst

  val opcode = instruction(6, 0)
  val rd     = instruction(11, 7)
  val funct3 = instruction(14, 12)
  val rs1    = instruction(19, 15)
  val rs2    = instruction(24, 20)

  val rdata1    = Regfile.io.rdata1
  val rdata2    = Regfile.io.rdata2
  val immediate = ImmGen.io.imm

  val aluOut      = ALU.io.out.asUInt
  val memReadData = DataMem.io.data_out

  // Instruction classes decoded locally (independent of controller quirks)
  val isJal   = TypeDecode.io.Jal
  val isJalr  = TypeDecode.io.Jalr
  val isLink  = isJal || isJalr
  val isLui   = opcode === "b0110111".U
  val isAuipc = opcode === "b0010111".U

  // ============================================================
  // WIRES
  // ============================================================
  val writeBackData = Wire(SInt(32.W))
  val operandA      = Wire(SInt(32.W))
  val operandB      = Wire(SInt(32.W))
  val nextPC        = Wire(UInt(32.W))

  // ============================================================
  // PC / INSTRUCTION MEMORY
  // ============================================================
  Pc.io.input   := nextPC
  InsMem.io.addr := pcValue

  // ============================================================
  // DECODE
  // ============================================================
  TypeDecode.io.opcode  := opcode
  controller.io.opcode  := opcode
  ImmGen.io.instruction := instruction

  // ============================================================
  // REGISTER FILE
  // ============================================================
  Regfile.io.raddr1 := rs1
  Regfile.io.raddr2 := rs2
  Regfile.io.waddr  := rd
  // controller.RegWrite OR'ed with link/LUI/AUIPC as a safety net
  Regfile.io.wen    := controller.io.RegWrite || isLink || isLui || isAuipc
  Regfile.io.wdata  := writeBackData

  // ============================================================
  // ALU CONTROL
  // funct7 (bit 30) only matters for R-type and for srai/srli.
  // For other I-type (e.g. addi with imm bit 30 set) it must be 0.
  // ============================================================
  AluControl.io.aluOp  := controller.io.ALU_operation
  AluControl.io.funct3 := funct3
  AluControl.io.funct7 := Mux(
    TypeDecode.io.R_Type || (TypeDecode.io.I_Type && funct3 === "b101".U),
    instruction(30),
    false.B
  )

  // ============================================================
  // ALU OPERANDS
  // A: 00/01 = rs1, 10 = PC (AUIPC), 11 = 0 (LUI)
  // B: 0 = rs2, 1 = immediate
  // ============================================================
  operandA := Mux(
    controller.io.operand_A_sel === "b10".U,
    pcValue.asSInt,
    Mux(controller.io.operand_A_sel === "b11".U, 0.S, rdata1.asSInt)
  )

  operandB := Mux(controller.io.operand_B_sel, immediate.asSInt, rdata2.asSInt)

  ALU.io.in1        := operandA
  ALU.io.in2        := operandB
  ALU.io.aluControl := AluControl.io.aluCtrl

  // ============================================================
  // DATA MEMORY (word addressed: byte address >> 2)
  // ============================================================
  DataMem.io.addr := aluOut(9, 2)

  DataMem.io.data_in(0) := rdata2.asUInt
  DataMem.io.data_in(1) := rdata2.asUInt
  DataMem.io.data_in(2) := rdata2.asUInt
  DataMem.io.data_in(3) := rdata2.asUInt
  DataMem.io.data_selector := 0.U

  DataMem.io.wr_en := controller.io.mem_write

  // ============================================================
  // WRITE BACK
  // priority: JAL/JALR (PC+4) > LUI (imm) > AUIPC (pc+imm) > load > ALU
  // ============================================================
  val auipcResult = (pcValue.asSInt + immediate.asSInt)

  writeBackData := MuxCase(
    aluOut.asSInt,
    Seq(
      isLink                     -> pcPlus4.asSInt,
      isLui                      -> immediate.asSInt,
      isAuipc                    -> auipcResult,
      controller.io.mem_to_Reg   -> memReadData.asSInt
    )
  )

  // ============================================================
  // BRANCH / NEXT PC
  // ============================================================
  val brTaken     = ALU.io.Branch.asBool
  val branchTaken = controller.io.branch && brTaken

  val branchTarget = (pcValue.asSInt + immediate.asSInt).asUInt
  val jalTarget    = (pcValue.asSInt + immediate.asSInt).asUInt
  val jalrTarget   = (rdata1.asUInt + immediate.asUInt) & "hFFFFFFFE".U

  nextPC := Mux(isJalr, jalrTarget,
            Mux(isJal, jalTarget,
            Mux(branchTaken, branchTarget, pcPlus4)))

  // ============================================================
  // OUTPUTS
  // ============================================================
  io.PC_Out        := pcValue
  io.instruction   := instruction
  io.aluResult     := aluOut
  io.writeBackData := writeBackData
  io.Reg_Out       := rdata1
}