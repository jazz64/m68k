package miggy.cpu.instructions.btst;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class BTSTTest extends BasicSetup {
    @Test
    void testDyn() {
        setInstruction(0x0300);    //btst d1,d0
        SystemModel.CPU.setDataRegister(0, 0x0010);
        SystemModel.CPU.setDataRegister(1, 4);

        SystemModel.CPU.setCCR((byte) 4);

        SystemModel.CPU.execute();

        assertEquals(0x0010, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testStatic() {
        setInstructionParamW(0x0800, 0x001f);    //btst #$1f,d0
        SystemModel.CPU.setDataRegister(0, 0);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0, SystemModel.CPU.getDataRegister(0), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testDyn2() {
        int val = 0x73;
        setInstructionParamW(0x013c, val);    //btst    D0, #$73
        for (int i = 0; i < 8; i++) {
            SystemModel.CPU.setPC(codebase);
            SystemModel.CPU.setDataRegister(0, i);
            SystemModel.CPU.setCCR((byte) 0);
            SystemModel.CPU.execute();
            assertEquals(i, SystemModel.CPU.getDataRegister(0), "Check result");
            boolean zFlag = (val & (1 << i)) == 0;
            checkFlags(i, zFlag);
        }
    }

    private void checkFlags(int bit, boolean isZero) {
        boolean expected = SystemModel.CPU.isSet(CpuFlag.Z);
        assertEquals(expected, isZero, bit + ", Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }
}

