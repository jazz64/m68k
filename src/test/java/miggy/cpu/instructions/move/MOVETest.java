package miggy.cpu.instructions.move;

import m68k.cpu.InstructionHandler;
import m68k.cpu.Size;
import m68k.cpu.TestRegistry;
import m68k.cpu.instructions.MOVE;
import m68k.cpu.rules.AddressingMode;
import m68k.cpu.rules.AddressingMode.AddressRegisterDirect;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static m68k.cpu.rules.AddressingMode.dataAlterableModes;
import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class MOVETest extends BasicSetup {
    @Test
    void testByte() {
        setInstruction(0x1001);    //move.b d1, d0
        SystemModel.CPU.setDataRegister(0, 0x87654321);
        SystemModel.CPU.setDataRegister(1, 0xc7c7c7c7);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x876543c7, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testWord() {
        setInstruction(0x3001);    //move.w d1, d0
        SystemModel.CPU.setDataRegister(0, 0x87654321);
        SystemModel.CPU.setDataRegister(1, 0xc7c7c7c7);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x8765c7c7, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testLong() {
        setInstruction(0x2001);    //move.l d1, d0
        SystemModel.CPU.setDataRegister(0, 0x87654321);
        SystemModel.CPU.setDataRegister(1, 0xc7c7c7c7);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0xc7c7c7c7, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testMem() {
        setInstruction(0x22d8);    //move.l (a0)+,(a1)+
        SystemModel.CPU.setAddrRegister(0, 32);
        SystemModel.CPU.setAddrRegister(1, 40);
        SystemModel.MEM.poke(32, 0x87654321, Size.Long);
        SystemModel.MEM.poke(40, 0x12345678, Size.Long);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x87654321, SystemModel.MEM.peek(40, Size.Long), "Check result");
        assertEquals(36, SystemModel.CPU.getAddrRegister(0), "Check A0");
        assertEquals(44, SystemModel.CPU.getAddrRegister(1), "Check A1");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void register_onCommonInstance_registersCorrectNumberOfVariants() {
        TestRegistry registry = new TestRegistry();
        InstructionHandler instance = new MOVE(SystemModel.CPU);
        int sourceModes = AddressingMode.allModes().size();
        int destinationModes = dataAlterableModes().size();
        int sizes = Size.values().length;
        int forbidden = AddressRegisterDirect.all().size() * destinationModes;
        int variants = sourceModes * destinationModes * sizes - forbidden;

        instance.register(registry);

        assertEquals(variants, registry.size());
    }
}
