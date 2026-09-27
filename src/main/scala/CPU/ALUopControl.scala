package CPU

import chisel3._
import chisel3.util._

class ALUclassIO extends Bundle {

    val R_type = Input(UInt(1.W))
    val I_type = Input(UInt(1.W))
    val LOAD   = Input(UInt(1.W))
    val STORE  = Input(UInt(1.W))
    val Branch = Input(UInt(1.W))
    val JALR   = Input(UInt(1.W))
    val JAL    = Input(UInt(1.W))
    val LUI    = Input(UInt(1.W))


    val alu_op = Output(UInt(3.W))
}

class ALUopControl extends Module {

    val io = IO(new ALUclassIO)

    io.alu_op(2) := ~io.R_type &
              ~io.I_type &
              ~io.Branch &
              ~io.JALR &
              ~io.JAL

    io.alu_op(1) := ~io.R_type &
              ~io.I_type &
              ~io.LOAD &
              ~io.STORE

    io.alu_op(0) := ~io.R_type &
              ~io.LOAD &
              ~io.Branch &
              ~io.LUI

}