package CPU

import chisel3._
import chisel3.util._

class MainControllerIO extends Bundle {

  val opcode = Input(UInt(7.W))

  val mem_write    = Output(Bool())
  val branch       = Output(Bool())
  val mem_read     = Output(Bool())
  val RegWrite     = Output(Bool())
  val mem_to_Reg   = Output(Bool())

  val ALU_operation = Output(UInt(3.W))

  val operand_A_sel = Output(UInt(2.W))
  val operand_B_sel = Output(Bool())

  val extend_sel  = Output(UInt(2.W))
  val next_pc_sel = Output(UInt(2.W))
}

class Controller extends Module {

  val io = IO(new MainControllerIO)

  val typeControl = Module(new TypeController)

  typeControl.io.opcode := io.opcode

  val signalControl = Module(new SignalController)

  signalControl.io.R_Type := typeControl.io.R_type
  signalControl.io.I_Type := typeControl.io.I_type
  signalControl.io.Load   := typeControl.io.L_type
  signalControl.io.Store  := typeControl.io.S_type
  signalControl.io.Branch := typeControl.io.SB_type
  signalControl.io.Jal    := typeControl.io.UJ_type
  signalControl.io.Jalr   := typeControl.io.JALR
  signalControl.io.LUI    := typeControl.io.LUI

  io.mem_write     := signalControl.io.mem_write
  io.branch        := signalControl.io.branch
  io.mem_read      := signalControl.io.mem_read
  io.RegWrite      := signalControl.io.RegWrite
  io.mem_to_Reg    := signalControl.io.mem_to_Reg
  io.ALU_operation := signalControl.io.ALU_operation
  io.operand_A_sel := signalControl.io.operand_A_sel
  io.operand_B_sel := signalControl.io.operand_B_sel
  io.extend_sel    := signalControl.io.extend_sel
  io.next_pc_sel   := signalControl.io.next_pc_sel
}