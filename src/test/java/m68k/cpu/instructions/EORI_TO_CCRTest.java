package m68k.cpu.instructions;

import m68k.cpu.DisassembledInstruction;
import m68k.cpu.Instruction;
import miggy.BasicSetup;
import miggy.SystemModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EORI_TO_CCRTest extends BasicSetup {
    @Test
    void disassemble_returnsCorrectDisassembledInstruction() {
        int opcode = 0xA3C;
        setInstructionParamW(opcode, 0x0067); // eori.b   #$67,ccr
        Instruction instruction = SystemModel.CPU.getInstructionAt(codebase);

        DisassembledInstruction result = instruction.disassemble(codebase, opcode);

        assertEquals("eori.b", result.instruction);
        assertEquals("#$67", result.op1.operand);
        assertEquals("ccr", result.op2.operand);
    }

}