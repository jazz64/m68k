package miggy.cpu.instructions.bcd;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SBCDTest extends BasicSetup {
    @Test
    void testReg() {
        setInstruction(0x8101);    //sbcd d1,d0
        SystemModel.CPU.setDataRegister(0, 0x0099);
        SystemModel.CPU.setDataRegister(1, 0x0001);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x0098, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testMem() {
        setInstruction(0x8109);    //sbcd -(a1),-(a0)
        SystemModel.CPU.setAddrRegister(0, codebase + 100);
        SystemModel.CPU.setAddrRegister(1, codebase + 108);
        SystemModel.MEM.poke(codebase + 98, 0x0100, Size.Word);
        SystemModel.MEM.poke(codebase + 106, 0x0001, Size.Word);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x0199, SystemModel.MEM.peek(codebase + 98, Size.Word), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");

        SystemModel.CPU.setPC(codebase);
        SystemModel.CPU.execute();
        assertEquals(0x0099, SystemModel.MEM.peek(codebase + 98, Size.Word), "Check result 2");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X 2");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N 2");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z 2");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V 2");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C 2");
    }

    @Test
    void testMem2() {
        setInstruction(0x8108);    //sbcd -(a0),-(a0)
        SystemModel.CPU.setAddrRegister(0, codebase + 100);
        SystemModel.MEM.poke(codebase + 98, 0x3916, Size.Word);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        //39-16 = 23
        assertEquals(0x2316, SystemModel.MEM.peek(codebase + 98, Size.Word), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
