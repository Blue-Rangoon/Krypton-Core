package CPU

import chisel3._

class InstDec_ALUCtrl extends Bundle {

  val funct3 = Input(UInt(3.W))
  val funct7 = Input(UInt(7.W))
}