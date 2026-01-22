package miggy.cpu.instructions;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RTETest extends BasicSetup {
    @Test
    void testReturn() {
        setInstruction(0x4e73);    //rte

        SystemModel.CPU.setCCR((byte) 0);
        SystemModel.CPU.setSupervisorMode(true);
        SystemModel.CPU.push(codebase + 100, Size.Long);
        SystemModel.CPU.push(0x001f, Size.Word);

        SystemModel.CPU.execute();

        assertFalse(SystemModel.CPU.isSupervisorMode(), "Check CPU not in supervisor mode");
        assertEquals(codebase + 100, SystemModel.CPU.getPC(), "Check PC restored");
        assertEquals(0x001f, SystemModel.CPU.getSR(), "Check SR restored");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testPrivViolation() {
        setInstruction(0x4e73);    //rte

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertTrue(SystemModel.CPU.isSupervisorMode(), "Check CPU in supervisor mode");
        //vector number stored in vector addr for testing
        assertEquals(8, SystemModel.CPU.getPC(), "Check PC");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
