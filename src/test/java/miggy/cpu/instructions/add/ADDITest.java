package miggy.cpu.instructions.add;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class ADDITest extends BasicSetup {
    @Test
    void testByte() {
        setInstructionParamW(0x0600, 0x0078);    //addi.b #$78, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x123456F0, SystemModel.CPU.getDataRegister(0), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testWord() {
        setInstructionParamW(0x0640, 0xaa78);    //addi.w #$aa78, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x123400F0, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testLong() {
        //test: changed to poke
        setInstructionParamL(0x0680, 0xf8765432);    //addi.l #$f8765432, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x0aaaaaaa, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }
}
