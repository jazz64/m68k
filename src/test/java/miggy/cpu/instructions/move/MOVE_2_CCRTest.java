package miggy.cpu.instructions.move;

import m68k.cpu.Cpu;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MOVE_2_CCRTest extends BasicSetup {
    @Test
    void testMove() {
        setInstruction(0x44c0);    //move d0,ccr
        SystemModel.CPU.setDataRegister(0, 0x0015);

        SystemModel.CPU.setSR(0xff00);

        SystemModel.CPU.execute();

        assertEquals(0xff15 & Cpu.SR_MASK, SystemModel.CPU.getSR(), "Check SR");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}
