package m68k.cpu.instructions;

import m68k.cpu.DisassembledInstruction;
import m68k.cpu.Instruction;
import miggy.BasicSetup;
import miggy.SystemModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EORI_TO_SRTest extends BasicSetup {
    @Test
    void disassemble_returnsCorrectDisassembledInstruction() {
        int opcode = 0xA7C;
        setInstructionParamW(opcode, 0x4567); // eori.w   #$4567,sr
        Instruction instruction = SystemModel.CPU.getInstructionAt(codebase);

        DisassembledInstruction result = instruction.disassemble(codebase, opcode);

        assertEquals("eori.w", result.instruction);
        assertEquals("#$4567", result.op1.operand);
        assertEquals("sr", result.op2.operand);
    }
}
