package miggy.cpu.instructions;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class TRAPVTest extends BasicSetup {
    @Test
    void testTrap() {
        setInstruction(0x4e76);    //trapv

        SystemModel.CPU.setCCR((byte) 2);    //set v

        SystemModel.CPU.execute();

        assertTrue(SystemModel.CPU.isSupervisorMode(), "Check CPU in supervisor mode");
        //vector number stored in vector addr for testing
        assertEquals(7, SystemModel.CPU.getPC(), "Check PC");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testNoTrap() {
        setInstruction(0x4e76);    //trapv

        SystemModel.CPU.setCCR((byte) 0);    //v not set

        SystemModel.CPU.execute();

        assertEquals(codebase + 2, SystemModel.CPU.getPC(), "Check PC");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
