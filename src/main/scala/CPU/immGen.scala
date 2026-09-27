package CPU

import chisel3._
import chisel3.util._
import Opcode._

class ImmGenIO extends Bundle {
  val instr = Input(UInt(32.W))
  val immd_se = Output(UInt(32.W))
}

class ImmGen extends Module {
  val io = IO(new ImmGenIO)

  // Start coding here

  io.immd_se := 0.U

  switch(Cat(io.instr(6, 0))) {

    is(I) {
      io.immd_se := Cat(
        Fill(20, Mux(io.instr(31), 1.U, 0.U)),
        io.instr(31, 20)
      )
    }

    is(Load) {
      io.immd_se := Cat(
        Fill(20, Mux(io.instr(31), 1.U, 0.U)),
        io.instr(31, 20)
      )
    }

    is(JALR) {
      io.immd_se := Cat(
        Fill(20, Mux(io.instr(31), 1.U, 0.U)),
        io.instr(31, 20)
      )
    }

    is(S) {
      io.immd_se := Cat(
        Fill(20, Mux(io.instr(31), 1.U, 0.U)),
        io.instr(31, 25),
        io.instr(11, 7)
      )
    }

    is(SB) {
      io.immd_se := Cat(
        Fill(20, Mux(io.instr(31), 1.U, 0.U)),
        io.instr(31),
        io.instr(7),
        io.instr(30, 25),
        io.instr(11, 8),
        0.U
      )
    }

    is(AUIPC) {
      io.immd_se := Cat(
        io.instr(31, 12),
        Fill(12, 0.U)
      )
    }

    is(LUI) {
      io.immd_se := Cat(
        io.instr(31, 12),
        Fill(12, 0.U)
      )
    }

    is(JAL) {
      io.immd_se := Cat(
        Fill(12, Mux(io.instr(31), 1.U, 0.U)),
        io.instr(19, 12),
        io.instr(20),
        io.instr(30, 21),
        0.U
      )
    }
  }
}