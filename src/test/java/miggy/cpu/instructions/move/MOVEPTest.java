package miggy.cpu.instructions.move;

import m68k.cpu.DisassembledInstruction;
import m68k.cpu.Instruction;
import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

// $Revision: 21 $
class MOVEPTest extends BasicSetup {
    @Test
    void testWordToMem() {
        setInstructionParamW(0x0188, 0x0000);    //movep.w d0,(a0)
        SystemModel.CPU.setDataRegister(0, 0x87654321);
        SystemModel.CPU.setAddrRegister(0, codebase + 100);
        SystemModel.MEM.poke(codebase + 100, 0xffffffff, Size.Long);
        SystemModel.MEM.poke(codebase + 104, 0xffffffff, Size.Long);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        int val = SystemModel.MEM.peek(codebase + 100, Size.Long);
        assertEquals(0x43ff21ff, val, "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testWordToReg() {
        setInstructionParamW(0x0308, 0x0000);    //movep.w (a0),d1
        SystemModel.CPU.setAddrRegister(0, codebase + 100);
        SystemModel.CPU.setDataRegister(1, 0xc0c0c0c0);
        SystemModel.MEM.poke(codebase + 100, 0x43ff21ff, Size.Long);
        SystemModel.MEM.poke(codebase + 104, 0xffffffff, Size.Long);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0xc0c04321, SystemModel.CPU.getDataRegister(1), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testLongToMem() {
        setInstructionParamW(0x01c8, 0x0000);    //movep.l d0,(a0)
        SystemModel.CPU.setDataRegister(0, 0x87654321);
        //odd address
        SystemModel.CPU.setAddrRegister(0, codebase + 101);
        SystemModel.MEM.poke(codebase + 100, 0xffffffff, Size.Long);
        SystemModel.MEM.poke(codebase + 104, 0xffffffff, Size.Long);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        int val = SystemModel.MEM.peek(codebase + 100, Size.Long);
        assertEquals(0xff87ff65, val, "Check result 1");
        val = SystemModel.MEM.peek(codebase + 104, Size.Long);
        assertEquals(0xff43ff21, val, "Check result 2");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testLongToReg() {
        setInstructionParamW(0x0348, 0x0000);    //movep.l (a0),d1
        //odd address
        SystemModel.CPU.setAddrRegister(0, codebase + 101);
        SystemModel.CPU.setDataRegister(1, 0xc0c0c0c0);
        SystemModel.MEM.poke(codebase + 100, 0xff87ff65, Size.Long);
        SystemModel.MEM.poke(codebase + 104, 0xff43ff21, Size.Long);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x87654321, SystemModel.CPU.getDataRegister(1), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void disassemble_wordToRegister_returnsCorrectDisassembledInstruction() {
        int opcode = 0x108;
        setInstructionParamW(opcode, 0x4523); // movep.w $4523(a0),d0
        Instruction instruction = SystemModel.CPU.getInstructionAt(codebase);

        DisassembledInstruction result = instruction.disassemble(codebase, opcode);

        assertEquals("movep.w", result.instruction);
        assertEquals("$4523(a0)", result.op1.operand);
        assertEquals("d0", result.op2.operand);
    }

    @Test
    void disassemble_longToMemory_returnsCorrectDisassembledInstruction() {
        int opcode = 0x1C8;
        setInstructionParamW(opcode, 0x4523); // movep.l d0,$4523(a0)
        Instruction instruction = SystemModel.CPU.getInstructionAt(codebase);

        DisassembledInstruction result = instruction.disassemble(codebase, opcode);

        assertEquals("movep.l", result.instruction);
        assertEquals("d0", result.op1.operand);
        assertEquals("$4523(a0)", result.op2.operand);
    }
}
