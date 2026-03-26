package miggy.cpu.instructions;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JSRTest extends BasicSetup {
    @Test
    void testInstruction() {
        setInstruction(0x4e90);    //jsr (a0)
        SystemModel.CPU.setAddrRegister(0, codebase + 50);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(codebase + 50, SystemModel.CPU.getPC(), "Check PC");
        assertEquals(codebase + 2, SystemModel.MEM.peek(SystemModel.CPU.getAddrRegister(7), Size.Long), "Check Stack");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
