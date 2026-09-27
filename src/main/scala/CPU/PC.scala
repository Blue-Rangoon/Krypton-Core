package CPU

import chisel3._

class PC extends Module {

    val io = IO(new Bundle {

    val pc_in  = Input(UInt(32.W))
    val pc_out     = Output(UInt(32.W))
    val pc4    = Output(UInt(32.W))  // PC + 4

})

  val pcReg = RegInit(0.U(32.W))

  io.pc_out  := pcReg
  io.pc4     := pcReg + 4.U

  pcReg := io.pc_in
}