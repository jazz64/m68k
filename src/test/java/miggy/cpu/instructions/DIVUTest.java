package miggy.cpu.instructions;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class DIVUTest extends BasicSetup {
    @Test
    void testPos() {
        setInstruction(0x80c1);    //divu d1,d0

        SystemModel.CPU.setDataRegister(0, 0x8765);
        SystemModel.CPU.setDataRegister(1, 0x0003);

        SystemModel.CPU.execute();

        assertEquals(0x00022d21, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testOverflow() {
        setInstruction(0x80c1);    //divu d1,d0

        SystemModel.CPU.setDataRegister(0, 0xffff8765);
        SystemModel.CPU.setDataRegister(1, 0x0003);

        SystemModel.CPU.execute();

        assertEquals(0xffff8765, SystemModel.CPU.getDataRegister(0), "Check unaffected destination");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        // the n flag seems to be set every time an overflow is generated
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testDivByZero() {
        setInstruction(0x80c1);    //divu d1,d0

        SystemModel.CPU.setDataRegister(0, 0x12345678);
        SystemModel.CPU.setDataRegister(1, 0);

        SystemModel.CPU.execute();

        assertTrue(SystemModel.CPU.isSupervisorMode(), "Check CPU in supervisor mode");
        //vector number stored in vector addr for testing
        assertEquals(5, SystemModel.CPU.getPC(), "Check PC");
        //unaffected
        assertEquals(0x12345678, SystemModel.CPU.getDataRegister(0), "Check unaffected destination");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
