package miggy.cpu.instructions.bitshift;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ROXLTest extends BasicSetup {
    @Test
    void testByte() {
        setInstruction(0xe330);    //roxl.b d1,d0
        SystemModel.CPU.setDataRegister(0, 0x876543e8);
        SystemModel.CPU.setDataRegister(1, 4);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x87654387, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testWord() {
        setInstruction(0xe150);    //roxl.w #8,d0
        SystemModel.CPU.setDataRegister(0, 0x87654321);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x87652121, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testLong() {
        setInstruction(0xe3b0);    //roxl.l d1,d0
        SystemModel.CPU.setDataRegister(0, 0x87654321);
        SystemModel.CPU.setDataRegister(1, 18);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x0c850eca, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testMem() {
        setInstruction(0xe5d0);    //roxl (a0)
        SystemModel.CPU.setAddrRegister(0, 32);
        SystemModel.MEM.poke(32, 0x87654321, Size.Long);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x0eca4321, SystemModel.MEM.peek(32, Size.Long), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }
}
