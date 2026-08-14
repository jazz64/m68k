package miggy.cpu.instructions.sub;

import m68k.cpu.InstructionHandler;
import m68k.cpu.Size;
import m68k.cpu.TestRegistry;
import m68k.cpu.instructions.SUB;
import m68k.cpu.rules.AddressingMode;
import m68k.cpu.rules.AddressingMode.AddressRegisterDirect;
import m68k.cpu.rules.AddressingMode.DataRegisterDirect;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static m68k.cpu.rules.AddressingMode.alterableMemoryModes;
import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class SUBTest extends BasicSetup {
    @Test
    void testByte() {
        setInstruction(0x9001);    //sub.b d1, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);
        SystemModel.CPU.setDataRegister(1, 0x78);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x12345600, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testWord() {
        setInstruction(0x9041);    //sub.w d1, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);
        SystemModel.CPU.setDataRegister(1, 0xaa78);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x1234ac00, SystemModel.CPU.getDataRegister(0), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testLong() {
        setInstruction(0x9081);    //sub.l d1, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);
        SystemModel.CPU.setDataRegister(1, 0x87654321);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x8acf1357, SystemModel.CPU.getDataRegister(0), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void register_onCommonInstance_registersCorrectNumberOfVariants() {
        TestRegistry registry = new TestRegistry();
        InstructionHandler instance = new SUB(SystemModel.CPU);
        int registerModes = DataRegisterDirect.all().size();
        int eaSourceModes = AddressingMode.allModes().size();
        int eaDestinationModes = alterableMemoryModes().size();
        int sizes = Size.values().length;
        int forbidden = AddressRegisterDirect.all().size() * registerModes;
        int variantsToRegister = (eaSourceModes * registerModes * sizes) - forbidden;
        int variantsFromRegister = eaDestinationModes * registerModes * sizes;

        instance.register(registry);

        assertEquals(variantsFromRegister + variantsToRegister, registry.size());
    }
}
