package CPU

import chisel3._
import chisel3.util._

class ALUControlIO extends Bundle {
  val aluOp   = Input(UInt(3.W))
  val funct3  = Input(UInt(3.W))
  val funct7  = Input(Bool())

  val aluCtrl = Output(UInt(5.W))
}

class ALUControl extends Module {
  val io = IO(new ALUControlIO)

  val isR  = io.aluOp === "b000".U
  val isI  = io.aluOp === "b001".U
  val isBr = io.aluOp === "b010".U

  val f7 = Mux(
    isR,
    io.funct7,
    Mux(
      isI && io.funct3 === "b101".U,
      io.funct7,
      false.B
    )
  )

  io.aluCtrl := "b00000".U

  when(isR || isI) {
    when(io.funct3 === "b000".U) {
      when(f7) {
        io.aluCtrl := ALUOP.ALU_SUB
      }.otherwise {
        io.aluCtrl := ALUOP.ALU_ADD
      }
    }

    when(io.funct3 === "b001".U) {
      io.aluCtrl := ALUOP.ALU_SLL
    }

    when(io.funct3 === "b010".U) {
      io.aluCtrl := ALUOP.ALU_SLT
    }

    when(io.funct3 === "b011".U) {
      io.aluCtrl := ALUOP.ALU_SLTU
    }

    when(io.funct3 === "b100".U) {
      io.aluCtrl := ALUOP.ALU_XOR
    }

    when(io.funct3 === "b101".U) {
      when(f7) {
        io.aluCtrl := ALUOP.ALU_SRA
      }.otherwise {
        io.aluCtrl := ALUOP.ALU_SRL
      }
    }

    when(io.funct3 === "b110".U) {
      io.aluCtrl := ALUOP.ALU_OR
    }

    when(io.funct3 === "b111".U) {
      io.aluCtrl := ALUOP.ALU_AND
    }
  }

  when(isBr) {
    when(io.funct3 === "b000".U) {
      io.aluCtrl := ALUOP.ALU_BEQ
    }

    when(io.funct3 === "b001".U) {
      io.aluCtrl := ALUOP.ALU_BNE
    }

    when(io.funct3 === "b100".U) {
      io.aluCtrl := ALUOP.ALU_BLT
    }

    when(io.funct3 === "b101".U) {
      io.aluCtrl := ALUOP.ALU_BGE
    }

    when(io.funct3 === "b110".U) {
      io.aluCtrl := ALUOP.ALU_BLTU
    }

    when(io.funct3 === "b111".U) {
      io.aluCtrl := ALUOP.ALU_BGEU
    }
  }
}