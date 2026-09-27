package CPU

import chisel3._
import chisel3.util.experimental.loadMemoryFromFile
import scala.io.Source

class InstMemIO extends Bundle {
  val addr = Input(UInt(32.W))
  val inst = Output(UInt(32.W))
}

class InstMem(initFile: String) extends Module {
  val io = IO(new InstMemIO)

  val imem = Mem(32, UInt(32.W))
  loadMemoryFromFile(imem, initFile)
  io.inst := imem(io.addr)
}