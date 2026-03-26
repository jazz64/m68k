package miggy.cpu.instructions.dbcc;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DBxxTest extends BasicSetup {
    @Test
    void testDBEQ_Branch() {
        SystemModel.MEM.poke(0, 0x7003, Size.Word);    //moveq #3, d0
        SystemModel.MEM.poke(2, 0x7200, Size.Word);    //moveq #0, d1
        SystemModel.MEM.poke(4, 0x5281, Size.Word);    //addq.l #1,d1 - loop
        SystemModel.MEM.poke(6, 0x0c01, Size.Word);    //cmpi.b #3,d1
        SystemModel.MEM.poke(8, 0x0003, Size.Word);    //
        SystemModel.MEM.poke(10, 0x57c8, Size.Word);//dbeq d0,loop
        SystemModel.MEM.poke(12, 0xfff8, Size.Word);    //
        SystemModel.MEM.poke(14, 0x7000, Size.Word);    //moveq #0, d0

        SystemModel.CPU.setDataRegister(0, 3);
        SystemModel.CPU.setDataRegister(1, 1);
        SystemModel.CPU.setPC(10);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(4, SystemModel.CPU.getPC(), "Check PC");
        assertEquals(2, SystemModel.CPU.getDataRegister(0), "Check D0");
        assertEquals(1, SystemModel.CPU.getDataRegister(1), "Check D1");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testDBEQ_Exit_Condition() {
        SystemModel.MEM.poke(0, 0x7003, Size.Word);    //moveq #3, d0
        SystemModel.MEM.poke(2, 0x7200, Size.Word);    //moveq #0, d1
        SystemModel.MEM.poke(4, 0x5281, Size.Word);    //addq.l #1,d1 - loop
        SystemModel.MEM.poke(6, 0x0c01, Size.Word);    //cmpi.b #3,d1
        SystemModel.MEM.poke(8, 0x0003, Size.Word);    //
        SystemModel.MEM.poke(10, 0x57c8, Size.Word);//dbeq d0,loop
        SystemModel.MEM.poke(12, 0xfff8, Size.Word);    //
        SystemModel.MEM.poke(14, 0x7000, Size.Word);    //moveq #0, d0

        SystemModel.CPU.setDataRegister(0, 3);
        SystemModel.CPU.setDataRegister(1, 1);
        SystemModel.CPU.setPC(10);
        SystemModel.CPU.setCCR((byte) 4);    //Z set

        SystemModel.CPU.execute();

        assertEquals(14, SystemModel.CPU.getPC(), "Check PC");
        assertEquals(3, SystemModel.CPU.getDataRegister(0), "Check D0");
        assertEquals(1, SystemModel.CPU.getDataRegister(1), "Check D1");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testDBEQ_Exit_Counter() {
        SystemModel.MEM.poke(0, 0x7003, Size.Word);    //moveq #3, d0
        SystemModel.MEM.poke(2, (short) 0x7200, Size.Word);    //moveq #0, d1
        SystemModel.MEM.poke(4, (short) 0x5281, Size.Word);    //addq.l #1,d1 - loop
        SystemModel.MEM.poke(6, (short) 0x0c01, Size.Word);    //cmpi.b #3,d1
        SystemModel.MEM.poke(8, (short) 0x0003, Size.Word);    //
        SystemModel.MEM.poke(10, (short) 0x57c8, Size.Word);//dbeq d0,loop
        SystemModel.MEM.poke(12, (short) 0xfff8, Size.Word);    //
        SystemModel.MEM.poke(14, (short) 0x7000, Size.Word);    //moveq #0, d0

        SystemModel.CPU.setDataRegister(0, 0);
        SystemModel.CPU.setDataRegister(1, 1);
        SystemModel.CPU.setPC(10);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(14, SystemModel.CPU.getPC(), "Check PC");
        assertEquals(0x0000ffff, SystemModel.CPU.getDataRegister(0), "Check D0");
        assertEquals(1, SystemModel.CPU.getDataRegister(1), "Check D1");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }
}