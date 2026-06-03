package m68k.cpu.instructions;

import m68k.cpu.DisassembledInstruction;
import m68k.cpu.Instruction;
import miggy.BasicSetup;
import miggy.SystemModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ORI_TO_CCRTest extends BasicSetup {
    @Test
    void disassemble_returnsCorrectDisassembledInstruction() {
        int opcode = 0x03C;
        setInstructionParamW(opcode, 0x2345); // ori.b #$45,ccr (high byte [$23] to be ignored for disassembly)
        Instruction instruction = SystemModel.CPU.getInstructionAt(codebase);

        DisassembledInstruction result = instruction.disassemble(codebase, opcode);

        assertEquals("ori.b", result.instruction);
        assertEquals("#$45", result.op1.operand);
        assertEquals("ccr", result.op2.operand);
    }
}
