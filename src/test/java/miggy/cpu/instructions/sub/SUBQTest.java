package miggy.cpu.instructions.sub;

import m68k.cpu.*;
import m68k.cpu.instructions.quick.SUBQ;
import m68k.cpu.rules.AddressingMode.AddressRegisterDirect;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static m68k.cpu.rules.AddressingMode.alterableModes;
import static org.junit.jupiter.api.Assertions.*;

class SUBQTest extends BasicSetup {
    @Test
    void testByte() {
        setInstruction(0x5100);    //subq.b #8, d0
        SystemModel.CPU.setDataRegister(0, 0x12345678);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x12345670, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testWord() {
        setInstruction(0x5f40);    //subq.w #7, d0
        SystemModel.CPU.setDataRegister(0, 0x12340002);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x1234fffb, SystemModel.CPU.getDataRegister(0), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void testLong() {
        setInstruction(0x5d80);    //subq.l #6, d0
        SystemModel.CPU.setDataRegister(0, 0x80000003);

        SystemModel.CPU.setCCR((byte) 0);

        SystemModel.CPU.execute();

        assertEquals(0x7ffffffd, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
    }

    @Test
    void register_onCommonInstance_registersCorrectNumberOfVariants() {
        TestRegistry registry = new TestRegistry();
        InstructionHandler instance = new SUBQ(SystemModel.CPU);
        int sourceModes = 8;
        int eaDestinationModes = alterableModes().size();
        int sizes = Size.values().length;
        int forbidden = AddressRegisterDirect.all().size();
        int variants = sourceModes * (eaDestinationModes * sizes - forbidden);

        instance.register(registry);

        assertEquals(variants, registry.size());
    }

    @Test
    void disassemble_bytewise_returnsCorrectDisassembledInstruction() {
        final int opcode = 20736;
        setInstruction(opcode); // subq.b   #8,d0
        Instruction instruction = SystemModel.CPU.getInstructionAt(codebase);

        DisassembledInstruction result = instruction.disassemble(codebase, opcode);

        assertEquals("subq.b", result.instruction);
        assertEquals("#8", result.op1.operand);
        assertEquals("d0", result.op2.operand);
    }
}
