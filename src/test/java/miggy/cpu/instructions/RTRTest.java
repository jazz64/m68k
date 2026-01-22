package miggy.cpu.instructions;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RTRTest extends BasicSetup {
    @Test
    void testReturn() {
        setInstruction(0x4e77);    //rtr

        SystemModel.CPU.setCCR((byte) 0);
        SystemModel.CPU.push(codebase + 100, Size.Long);
        SystemModel.CPU.push(0x341f, Size.Word);

        SystemModel.CPU.execute();

        assertEquals(codebase + 100, SystemModel.CPU.getPC(), "Check PC restored");
        assertEquals(0x001f, SystemModel.CPU.getSR(), "Check CCR restored, SR unaffected");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
