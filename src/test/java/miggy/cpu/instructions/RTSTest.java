package miggy.cpu.instructions;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class RTSTest extends BasicSetup {
    @Test
    void testReturn() {
        setInstruction(0x4e75);    //rts

        SystemModel.CPU.setCCR((byte) 0);
        SystemModel.CPU.push(codebase + 100, Size.Long);

        SystemModel.CPU.execute();

        assertEquals(codebase + 100, SystemModel.CPU.getPC(), "Check PC restored");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
