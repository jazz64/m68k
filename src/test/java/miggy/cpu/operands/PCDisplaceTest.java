package miggy.cpu.operands;

import m68k.cpu.Size;
import miggy.BasicSetup;
import org.junit.jupiter.api.Test;

import static miggy.SystemModel.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

// $Revision: 21 $
class PCDisplaceTest extends BasicSetup {
    @Test
    void testInstruction() {
        MEM.poke(codebase + 0x00e4, 0x41faff1c, Size.Long);    //lea $-e4(pc),a0
        MEM.poke(codebase, 0x00c00000, Size.Long);
        CPU.setAddrRegister(0, 0x87654321);
        CPU.setPC(codebase + 0x00e4);
        CPU.setCCR((byte) 0);

        CPU.execute();

        assertEquals(codebase + 2, CPU.getAddrRegister(0), "Check result");
        assertFalse(CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(CPU.isSet(CpuFlag.C), "Check C");
    }
}
