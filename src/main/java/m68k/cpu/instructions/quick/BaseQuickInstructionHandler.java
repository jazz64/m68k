package m68k.cpu.instructions.quick;

import m68k.cpu.*;

abstract class BaseQuickInstructionHandler implements InstructionHandler {
    private final String mnemonic;

    BaseQuickInstructionHandler(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    protected final DisassembledInstruction disassembleOp(Cpu cpu, int address, int opcode, Size sz) {
        final int s = immediateFrom(opcode);

        DisassembledOperand src = new DisassembledOperand("#" + s);
        DisassembledOperand dst = cpu.disassembleDstEA(address + 2, (opcode >> 3) & 0x07, (opcode & 0x07), sz);

        return new DisassembledInstruction(address, opcode, mnemonic + sz.ext(), src, dst);
    }

    protected int immediateFrom(int opcode) {
        int s = (opcode >> 9 & 0x07);
        if (s == 0)
            s = 8;
        return s;
    }
}
