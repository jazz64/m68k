package miggy.cpu.instructions.cmp;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class CMPMTest extends BasicSetup {
    @Test
    void testByte() {
        setInstruction(0xb308);    //cmpm.b (a0)+, (a1)+
        SystemModel.CPU.setAddrRegister(0, 32);
        SystemModel.MEM.poke(32, 0x87654321, Size.Long);
        SystemModel.CPU.setAddrRegister(1, 40);
        SystemModel.MEM.poke(40, 0x87654321, Size.Long);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(33, SystemModel.CPU.getAddrRegister(0), "Check A0");
        assertEquals(41, SystemModel.CPU.getAddrRegister(1), "Check A1");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testWord() {
        setInstruction(0xb348);    //cmpm.w (a0)+,(a1)+
        SystemModel.CPU.setAddrRegister(0, 32);
        SystemModel.MEM.poke(32, 0x87654321, Size.Long);
        SystemModel.CPU.setAddrRegister(1, 40);
        SystemModel.MEM.poke(40, 0x12345678, Size.Long);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(34, SystemModel.CPU.getAddrRegister(0), "Check A0");
        assertEquals(42, SystemModel.CPU.getAddrRegister(1), "Check A1");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testLong() {
        setInstruction(0xb388);    //cmpm.l (a0)+,(a1)+
        SystemModel.CPU.setAddrRegister(0, 32);
        SystemModel.MEM.poke(32, 0x87654321, Size.Long);
        SystemModel.CPU.setAddrRegister(1, 40);
        SystemModel.MEM.poke(40, 0xcc00cc00, Size.Long);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(36, SystemModel.CPU.getAddrRegister(0), "Check A0");
        assertEquals(44, SystemModel.CPU.getAddrRegister(1), "Check A1");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }
}
