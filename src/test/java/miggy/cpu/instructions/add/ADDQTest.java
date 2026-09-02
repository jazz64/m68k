package miggy.cpu.instructions.add;

import m68k.cpu.*;
import m68k.cpu.instructions.quick.ADDQ;
import m68k.cpu.rules.AddressingMode;
import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static m68k.cpu.rules.AddressingMode.alterableModes;
import static org.junit.jupiter.api.Assertions.*;

class ADDQTest extends BasicSetup {
    @Test
    void testByte() {
        setInstruction(0x5e00);    //addq.b #7, d0
        SystemModel.CPU.setDataRegister(0, 0x123456fc);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x12345603, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testWord() {
        setInstruction(0x5e40);    //addq.w #7, d0
        SystemModel.CPU.setDataRegister(0, 0x1234fffc);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x12340003, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testLong() {
        setInstruction(0x5e80);    //addq.l #7, d0
        SystemModel.CPU.setDataRegister(0, 0x1234fffc);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x12350003, SystemModel.CPU.getDataRegister(0), "Check result");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertFalse(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void register_onCommonInstance_registersCorrectNumberOfVariants() {
        TestRegistry registry = new TestRegistry();
        InstructionHandler instance = new ADDQ(SystemModel.CPU);
        int sourceModes = 8;
        int eaDestinationModes = alterableModes().size();
        int sizes = Size.values().length;
        int forbidden = AddressingMode.AddressRegisterDirect.all().size();
        int variants = sourceModes * (eaDestinationModes * sizes - forbidden);

        instance.register(registry);

        assertEquals(variants, registry.size());
    }

    @Test
    void disassemble_wordwise_returnsCorrectDisassembledInstruction() {
        final int opcode = 0x5e46;
        setInstruction(opcode); // addq.w   #7,d6
        Instruction instruction = SystemModel.CPU.getInstructionAt(codebase);

        DisassembledInstruction result = instruction.disassemble(codebase, opcode);

        assertEquals("addq.w", result.instruction);
        assertEquals("#7", result.op1.operand);
        assertEquals("d6", result.op2.operand);
    }
}
