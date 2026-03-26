package miggy.cpu.instructions.bcc;

import m68k.cpu.Size;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BxxTest extends BasicSetup {
    @Test
    void testBCC_Branch() {
        SystemModel.MEM.poke(codebase + 6, 0x64f8, Size.Word);    //bcc begin
        SystemModel.CPU.setPC(codebase + 6);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(codebase, SystemModel.CPU.getPC(), "Check PC");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testBCC_Branch_w() {
        SystemModel.MEM.poke(codebase + 6, 0x6400, Size.Word);    //bcc.w begin
        SystemModel.MEM.poke(codebase + 8, 0xfff8, Size.Word);    //bcc.w begin

        SystemModel.CPU.setPC(codebase + 6);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(codebase, SystemModel.CPU.getPC(), "Check PC");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testBCS_NoBranch() {
        SystemModel.MEM.poke(codebase + 6, 0x65f8, Size.Word);    //bcs begin

        SystemModel.CPU.setPC(codebase + 6);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(codebase + 8, SystemModel.CPU.getPC(), "Check PC");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testBCS_NoBranch_w() {
        SystemModel.MEM.poke(codebase + 6, 0x6500, Size.Word);    //bcs.w begin
        SystemModel.MEM.poke(codebase + 8, 0xfff8, Size.Word);

        SystemModel.CPU.setPC(codebase + 6);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(codebase + 10, SystemModel.CPU.getPC(), "Check PC");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testBSR() {
        SystemModel.MEM.poke(codebase + 6, 0x6100, Size.Word);    //bsr
        SystemModel.MEM.poke(codebase + 8, 0x0004, Size.Word);

        SystemModel.CPU.setPC(codebase + 6);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(codebase + 0x0c, SystemModel.CPU.getPC(), "Check PC");
        assertEquals(0x7fc, SystemModel.CPU.getAddrRegister(7), "Check A7");
        assertEquals(codebase + 0x0a, SystemModel.MEM.peek(0x7fc, Size.Long), "Check Stack Top");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testBRA() {
        SystemModel.MEM.poke(codebase + 6, 0x6000, Size.Word);    //bra
        SystemModel.MEM.poke(codebase + 8, 0x0004, Size.Word);
        SystemModel.CPU.setPC(codebase + 6);
        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(codebase + 0x0c, SystemModel.CPU.getPC(), "Check PC");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }
}
