package miggy;

import m68k.cpu.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// $Revision: 21 $

class SignTest extends BasicSetup {
    @Test
    void testSignExtendByte() {
        SystemModel.MEM.poke(codebase, 0x80, Size.Byte);

        int val = SystemModel.MEM.peek(codebase, Size.Byte);

        assertTrue(val >= 0, "Byte not sign extended");

        val = (byte) SystemModel.MEM.peek(codebase, Size.Byte);

        assertTrue(val < 0, "Byte sign extended");
    }

    @Test
    void testSignExtendWord() {
        SystemModel.MEM.poke(codebase, 0x8000, Size.Word);

        int val = SystemModel.MEM.peek(codebase, Size.Word);

        assertTrue(val >= 0, "Word not sign extended");

        val = (short) SystemModel.MEM.peek(codebase, Size.Word);

        assertTrue(val < 0, "Word sign extended");
    }

    @Test
    void testSignExtendFetchByte() {
        //fetch will always read 2 bytes if Size.Byte
        SystemModel.MEM.poke(codebase, 0x0080, Size.Word);

        int val = SystemModel.CPU.fetch(Size.Byte);
        assertTrue(val >= 0, "Byte not sign extended");
        assertEquals(0x0080, val, "Byte fetch");

        //reset PC
        SystemModel.CPU.setPC(codebase);
        val = (byte) SystemModel.CPU.fetch(Size.Byte);
        assertTrue(val < 0, "Byte sign extended");
    }

    @Test
    void testSignExtendFetchWord() {
        SystemModel.MEM.poke(codebase, 0x8000, Size.Word);

        int val = SystemModel.CPU.fetch(Size.Word);
        assertTrue(val >= 0, "Word not sign extended");
        assertEquals(0x8000, val, "Word fetch");

        //reset PC
        SystemModel.CPU.setPC(codebase);
        val = (short) SystemModel.CPU.fetch(Size.Word);
        assertTrue(val < 0, "Word sign extended");
    }

    @Test
    void testSizeSignExtend() {
        int val = 0x0080;

        int result = SystemModel.CPU.signExtendByte(val);
        assertTrue(result < 0, "Byte sign extended");

        val = 0x8000;
        result = SystemModel.CPU.signExtendWord(val);
        assertTrue(result < 0, "Word sign extended");
    }
}
