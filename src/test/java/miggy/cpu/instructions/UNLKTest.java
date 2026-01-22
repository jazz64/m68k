package miggy.cpu.instructions;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

// $Revision: 21 $
class UNLKTest extends BasicSetup {
    @Test
    void testInstruction() {
        setInstruction(0x4e5e);    //unlk a6
        int stack = SystemModel.CPU.getAddrRegister(7);
        SystemModel.CPU.push(0x87654321, Size.Long);
        SystemModel.CPU.setAddrRegister(6, stack - 4);

        SystemModel.CPU.setCCR((byte) 0);
        SystemModel.CPU.execute();

        assertEquals(stack, SystemModel.CPU.getAddrRegister(7), "Check stack");
        assertEquals(0x87654321, SystemModel.CPU.getAddrRegister(6), "Check A6");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
