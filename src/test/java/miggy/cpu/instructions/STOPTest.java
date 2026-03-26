package miggy.cpu.instructions;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class STOPTest extends BasicSetup {
    @Test
    void testReturn() {
        setInstructionParamW(0x4e72, 0x201f);    //stop

        SystemModel.CPU.setCCR((byte) 0);
        SystemModel.CPU.setSupervisorMode(true);

        SystemModel.CPU.execute();

        assertTrue(SystemModel.CPU.isSupervisorMode(), "Check CPU in supervisor mode");
        assertEquals(0x201f, SystemModel.CPU.getSR(), "Check SR set");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testPrivViolation1() {
        setInstructionParamW(0x4e72, 0x201f);    //stop

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

    @Test
    void testPrivViolation2() {
        setInstructionParamW(0x4e72, 0x001f);    //stop

        SystemModel.CPU.setCCR((byte) 0);
        SystemModel.CPU.setSupervisorMode(true);

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
