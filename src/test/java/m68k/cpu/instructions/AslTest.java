package m68k.cpu.instructions;

import m68k.cpu.Cpu;
import m68k.cpu.MC68000;
import m68k.memory.AddressSpace;
import m68k.memory.MemorySpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static m68k.common.DataSize.ofKilobytes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ${FILE}
 * <p>
 * Check Flags for shift/rotate instructions when shift value = 0
 * <p>
 * Federico Berti
 * <p>
 * Copyright 2019
 */
class AslTest {

    AddressSpace bus;
    Cpu cpu;
    int stack = 0x200;

    @BeforeEach
    void setUp() {
        bus = new MemorySpace(ofKilobytes(1));
        cpu = new MC68000();
        cpu.setAddressSpace(bus);
        cpu.reset();
        cpu.setAddrRegisterLong(7, stack);
    }

    @Test
    void testAsl() {
        int opcode = 0xe1d5; //asl.w (a5)
        int memLoc = 0x10;
        int data = 0xFF_8000;
        cpu.writeMemoryWord(0, opcode);
        cpu.setAddrRegisterLong(5, memLoc);
        bus.writeLong(memLoc - 2, data);

        cpu.execute();

        assertEquals(0xFF_0000, cpu.readMemoryLong(memLoc - 2));
        assertEquals(memLoc, cpu.getAddrRegisterLong(5));
        assertTrue(cpu.isFlagSet(Cpu.Z_FLAG));
        assertTrue(cpu.isFlagSet(Cpu.X_FLAG));
    }

    @Test
    void testLsl() {
        int opcode = 0xE3D5; //lsl.w (a5)
        int memLoc = 0x10;
        int data = 0xFF_8000;
        cpu.writeMemoryWord(0, opcode);
        cpu.setAddrRegisterLong(5, memLoc);
        bus.writeLong(memLoc - 2, data);

        cpu.execute();

        assertEquals(0xFF_0000, cpu.readMemoryLong(memLoc - 2));
        assertEquals(memLoc, cpu.getAddrRegisterLong(5));
        assertTrue(cpu.isFlagSet(Cpu.Z_FLAG));
        assertTrue(cpu.isFlagSet(Cpu.X_FLAG));
    }
}
