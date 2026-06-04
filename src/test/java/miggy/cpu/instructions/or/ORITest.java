package miggy.cpu.instructions.or;

import m68k.cpu.InstructionHandler;
import m68k.cpu.Size;
import m68k.cpu.TestRegistry;
import m68k.cpu.instructions.ORI;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static m68k.cpu.rules.AddressingMode.dataAlterableModes;
import static org.junit.jupiter.api.Assertions.*;

// $Revision: 21 $
class ORITest extends BasicSetup {
    @Test
    void testByte() {
        setInstructionParamW(0x0000, 0x0078);    //ori.b #$78, d0
        SystemModel.CPU.setDataRegister(0, 0x87654321);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x87654379, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testWord() {
        setInstructionParamW(0x0040, 0xaa78);    //ori.w #$aa78, d0
        SystemModel.CPU.setDataRegister(0, 0x87654321);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x8765eb79, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testLong() {
        setInstructionParamL(0x0080, 0x12345678);    //eori.l #$12345678, d0
        SystemModel.CPU.setDataRegister(0, 0x87654321);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x97755779, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void register_onCommonInstance_registersCorrectNumberOfVariants() {
        TestRegistry registry = new TestRegistry();
        InstructionHandler instance = new ORI(SystemModel.CPU);
        int destinationModes = dataAlterableModes().size();
        int sizes = Size.values().length;
        int variants = destinationModes * sizes;

        instance.register(registry);

        assertEquals(variants, registry.size());
    }
}
