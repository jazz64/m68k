package miggy.cpu.instructions.and;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ANDTest extends BasicSetup {
    @Test
    void testByte() {
        setInstruction(0xc001);    //and.b d1, d0
        SystemModel.CPU.setDataRegister(0, 0x12345688);
        SystemModel.CPU.setDataRegister(1, 0x7f);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x12345608, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testWord() {
        setInstruction(0xc041);    //and.w d1, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);
        SystemModel.CPU.setDataRegister(1, 0xaa78);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x12340278, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testLong() {
        setInstruction(0xc081);    //and.l d1, d0
        SystemModel.CPU.setDataRegister(0, 0x82345678);
        SystemModel.CPU.setDataRegister(1, 0xf8765432);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x80345430, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }
}
