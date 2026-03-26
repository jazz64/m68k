package miggy.cpu.instructions;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class MULSTest extends BasicSetup {
    @Test
    void testPos() {
        setInstruction(0xc1c1);    //muls d1,d0

        SystemModel.CPU.setDataRegister(0, 0x7765);
        SystemModel.CPU.setDataRegister(1, 0x0345);

        SystemModel.CPU.execute();

        assertEquals(0x01865d39, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testNeg() {
        setInstruction(0xc1c1);    //muls d1,d0

        SystemModel.CPU.setDataRegister(0, 0xffff8765);
        SystemModel.CPU.setDataRegister(1, 0x0033);

        SystemModel.CPU.execute();

        assertEquals(0xffe7f91f, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
