package miggy.cpu.instructions.move;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class MOVE_2_SRTest extends BasicSetup {
    @Test
    void testMove() {
        setInstruction(0x46c0);    //move d0,sr
        SystemModel.CPU.setDataRegister(0, 0x2015);

        SystemModel.CPU.setSR((short) 0x2000);    //set supervisor bit

        SystemModel.CPU.execute();

        assertEquals((short) 0x2015, SystemModel.CPU.getSR(), "Check SR");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testMoveException() {
        setInstruction(0x46c0);    //move d0,sr
        SystemModel.CPU.setDataRegister(0, 0x2015);

        SystemModel.CPU.setSR((short) 0);

        SystemModel.CPU.execute();

        //supervisor bit set
        assertEquals(0x2000, SystemModel.CPU.getSR(), "Check SR");
        //privilege violation exception
        assertEquals(8, SystemModel.CPU.getPC(), "Check PC");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
