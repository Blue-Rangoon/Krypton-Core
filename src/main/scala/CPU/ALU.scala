package CPU

import chisel3._
import chisel3.util._

trait Config {
  val WLEN = 32
  val ALUOP_SIG_LEN = 5
}

import ALUOP._

class ALUIO extends Bundle with Config {
  val inA    = Input(SInt(WLEN.W))
  val in_B   = Input(SInt(WLEN.W))
  val alu_Op = Input(UInt(ALUOP_SIG_LEN.W))
  val out    = Output(SInt(WLEN.W))
  val branch = Output(Bool())
}

class ALU extends Module with Config {
  val io = IO(new ALUIO)

  io.out := 0.S
  io.branch := false.B

  switch(io.alu_Op) {

    is(ALU_ADD) {
      io.out := io.inA + io.in_B
    }

    is(ALU_SUB) {
      io.out := io.inA - io.in_B
    }

    is(ALU_AND) {
      io.out := io.inA & io.in_B
    }

    is(ALU_OR) {
      io.out := io.inA | io.in_B
    }

    is(ALU_XOR) {
      io.out := io.inA ^ io.in_B
    }

    is(ALU_SLT) {
      io.out := Mux(io.inA < io.in_B, 1.S, 0.S)
    }

    is(ALU_SLL) {
      io.out := io.inA << io.in_B(4, 0)
    }

    is(ALU_SLTU) {
      io.out := Mux(
        io.inA.asUInt < io.in_B.asUInt,
        1.S,
        0.S
      )
    }

    is(ALU_SRL) {
      io.out := (io.inA.asUInt >> io.in_B(4, 0)).asSInt
    }

    is(ALU_SRA) {
      io.out := io.inA >> io.in_B(4, 0)
    }

    is(ALU_COPY_A) {
      io.out := io.inA
    }

    is(ALU_COPY_B) {
      io.out := io.in_B
    }

    is(ALU_BEQ) {
      io.branch := io.inA === io.in_B
    }

    is(ALU_BNE) {
      io.branch := io.inA =/= io.in_B
    }

    is(ALU_BLT) {
      io.branch := io.inA < io.in_B
    }

    is(ALU_BGE) {
      io.branch := io.inA >= io.in_B
    }

    is(ALU_BLTU) {
      io.branch := io.inA.asUInt < io.in_B.asUInt
    }

    is(ALU_BGEU) {
      io.branch := io.inA.asUInt >= io.in_B.asUInt
    }
  }
}