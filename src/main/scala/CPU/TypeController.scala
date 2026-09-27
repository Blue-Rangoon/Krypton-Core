package CPU

import chisel3._
import chisel3.util._

class TypeControllerIO extends Bundle {
  val opcode = Input(UInt(7.W))

  val R_type  = Output(UInt(32.W))
  val I_type  = Output(UInt(32.W))
  val L_type  = Output(UInt(32.W))
  val S_type  = Output(UInt(32.W))
  val SB_type = Output(UInt(32.W))
  val UJ_type = Output(UInt(32.W))
  val JALR    = Output(UInt(32.W))
  val LUI     = Output(UInt(32.W))
  val AUI     = Output(UInt(32.W))
}

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

class TypeController extends Module with Opcode {

  val io = IO(new TypeControllerIO)

  // Default: everything disabled
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