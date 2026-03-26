package miggy.cpu.instructions;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

// $Revision: 21 $
class EXGTest extends BasicSetup {
    @Test
    void testInstruction() {
        setInstruction(0xc141);    // exg d0,d1

        SystemModel.CPU.setDataRegister(0, 0x98765432);
        SystemModel.CPU.setDataRegister(1, 0x12345678);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x12345678, SystemModel.CPU.getDataRegister(0), "Check d0");
        assertEquals(0x98765432, SystemModel.CPU.getDataRegister(1), "Check d1");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
