package miggy.cpu.instructions.add;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class ADDXTest extends BasicSetup {
    @Test
    void testByte() {
        setInstruction(0xd101);    //addx.b d1, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);
        SystemModel.CPU.setDataRegister(1, 0x78);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x123456F1, SystemModel.CPU.getDataRegister(0), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testWord() {
        setInstruction(0xd141);    //addx.w d1, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);
        SystemModel.CPU.setDataRegister(1, 0xaa78);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x123400F1, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testLong() {
        setInstruction(0xd181);    //addx.l d1, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);
        SystemModel.CPU.setDataRegister(1, 0xf8765432);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x0aaaaaab, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testBytePreDec() {
        int addr = 0x800;
        int a2Exp = addr - 2;
        SystemModel.MEM.poke(a2Exp, 0x12345678, Size.Long);
        setInstruction(0xd50a);   //addx.b  -(A2), -(A2)
        SystemModel.CPU.setAddrRegister(2, addr);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(a2Exp, SystemModel.CPU.getAddrRegisterLong(2), "Check result");
        assertEquals(0x12 + 0x34 + 1, SystemModel.MEM.peek(a2Exp, Size.Byte));
    }

    @Test
    void testWordPreDec() {
        int addr = 0x800;
        int anExp = addr - 4;
        SystemModel.MEM.poke(anExp, 0x12345678, Size.Long);
        setInstruction(0xdb4d);   //addx.w  -(A5), -(A5)
        SystemModel.CPU.setAddrRegister(5, addr);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(anExp, SystemModel.CPU.getAddrRegisterLong(5), "Check result");
        assertEquals(0x1234 + 0x5678 + 1, SystemModel.MEM.peek(anExp, Size.Word));
    }

    @Test
    void testLongPreDec() {
        int addr = 0x800;
        int anExp = addr - 8;
        SystemModel.MEM.poke(anExp, 0x12345678, Size.Long);
        SystemModel.MEM.poke(anExp + 4, 0x87654321, Size.Long);
        setInstruction(0xdd8e);   //addx.l  -(A6), -(A6)
        SystemModel.CPU.setAddrRegister(6, addr);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(anExp, SystemModel.CPU.getAddrRegisterLong(6), "Check result");
        assertEquals(0x87654321 + 0x12345678 + 1, SystemModel.MEM.peek(anExp, Size.Long)); // 0x9999999A
    }
}
