package miggy.cpu.instructions.move;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class MOVE_F_SRTest extends BasicSetup {
    @Test
    void testMove() {
        setInstruction(0x40c0);    //move sr,d0

        SystemModel.CPU.setSR((short) 0x000f);

        SystemModel.CPU.execute();

        assertEquals((short) 0x000f, SystemModel.CPU.getSR(), "Check D0");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
