package miggy.cpu.instructions.cmp;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CMPATest extends BasicSetup {
    @Test
    void testWord() {
        setInstruction(0xb0c9);    //cmp.w a1,a0
        SystemModel.CPU.setAddrRegister(0, 0x87654321);
        SystemModel.CPU.setAddrRegister(1, 0x87658321);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testLong() {
        setInstruction(0xb1c9);    //cmp.l a1, a0
        SystemModel.CPU.setAddrRegister(0, 0x87654321);
        SystemModel.CPU.setAddrRegister(1, 0xcc00cc00);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testMem() {
        setInstruction(0xb2d0);    //cmp (a0),d0
        SystemModel.CPU.setAddrRegister(0, 32);
        SystemModel.CPU.setAddrRegister(1, 0x87654321);
        SystemModel.MEM.poke(32, 0x87654321, Size.Long);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
