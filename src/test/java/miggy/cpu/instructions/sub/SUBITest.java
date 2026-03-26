package miggy.cpu.instructions.sub;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class SUBITest extends BasicSetup {
    @Test
    void testByte() {
        setInstructionParamW(0x0400, 0x00c8);    //subi.b #$c8, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x123456b0, SystemModel.CPU.getDataRegister(0), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testWord() {
        setInstructionParamW(0x0440, 0xc876);    //subi.w #$c876, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x12348e02, SystemModel.CPU.getDataRegister(0), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testLong() {
        setInstructionParamL(0x0480, 0x87654321);    //subi.l #$87654321, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x8acf1357, SystemModel.CPU.getDataRegister(0), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
