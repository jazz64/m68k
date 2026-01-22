package miggy.cpu.instructions.bcd;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ABCDTest extends BasicSetup {
    @Test
    void testReg() {
        setInstruction(0xc101);    //abcd d1,d0
        SystemModel.CPU.setDataRegister(0, 0x0099);
        SystemModel.CPU.setDataRegister(1, 0x0001);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0, SystemModel.CPU.getDataRegister(0), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testMem() {
        setInstruction(0xc109);    //abcd -(a1),-(a0)
        SystemModel.CPU.setAddrRegister(0, codebase + 100);
        SystemModel.CPU.setAddrRegister(1, codebase + 108);
        SystemModel.MEM.poke(codebase + 98, 0x0099, Size.Word);
        SystemModel.MEM.poke(codebase + 106, 0x001, Size.Word);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0, SystemModel.MEM.peek(codebase + 98, Size.Word), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");

        SystemModel.CPU.setPC(codebase);
        SystemModel.CPU.execute();
        assertEquals(0x0100, SystemModel.MEM.peek(codebase + 98, Size.Word), "Check result 2");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X 2");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N 2");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z 2");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V 2");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C 2");
    }

    @Test
    void testMem2() {
        setInstruction(0xc108);    //abcd -(a0),-(a0)
        SystemModel.CPU.setAddrRegister(0, codebase + 100);
        SystemModel.MEM.poke(codebase + 98, 0x2316, Size.Word);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        //23+16 = 39
        assertEquals(0x3916, SystemModel.MEM.peek(codebase + 98, Size.Word), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
