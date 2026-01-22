package miggy.cpu.instructions;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class CHKTest extends BasicSetup {
    @Test
    void testNeg() {
        setInstruction(0x4181);    //chk d1,d0
        SystemModel.CPU.setDataRegister(0, 0xc321);
        SystemModel.CPU.setDataRegister(1, 0x5678);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertTrue(SystemModel.CPU.isSupervisorMode(), "Check CPU in supervisor mode");
        //vector number stored in vector addr for testing
        assertEquals(6, SystemModel.CPU.getPC(), "Check PC");

        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testGreater() {
        setInstruction(0x4181);    //chk d1,d0
        SystemModel.CPU.setDataRegister(0, 0x6321);
        SystemModel.CPU.setDataRegister(1, 0x5678);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertTrue(SystemModel.CPU.isSupervisorMode(), "Check CPU in supervisor mode");
        //vector number stored in vector addr for testing
        assertEquals(6, SystemModel.CPU.getPC(), "Check PC");

        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testNoException() {
        setInstruction(0x4181);    //chk d1,d0
        SystemModel.CPU.setDataRegister(0, 0x4321);
        SystemModel.CPU.setDataRegister(1, 0x5678);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertFalse(SystemModel.CPU.isSupervisorMode(), "Check CPU not in supervisor mode");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
