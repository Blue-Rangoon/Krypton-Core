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

trait Config {
  val WLEN = 32
  val ALUOP_SIG_LEN = 5
}