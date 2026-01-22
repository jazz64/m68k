package miggy.cpu.instructions;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class LINKTest extends BasicSetup {
    @Test
    void testInstruction() {
        setInstructionParamW(0x4e56, 0xfff8);    //link a6,#-8
        SystemModel.CPU.setAddrRegister(6, 0x87654321);

        SystemModel.CPU.setCCR((byte) 0);
        int stack = SystemModel.CPU.getAddrRegister(7);

        SystemModel.CPU.execute();

        assertEquals(stack - 12, SystemModel.CPU.getAddrRegister(7), "Check stack");
        assertEquals(stack - 4, SystemModel.CPU.getAddrRegister(6), "Check A6");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
