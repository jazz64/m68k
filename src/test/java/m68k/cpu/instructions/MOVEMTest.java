package m68k.cpu.instructions;

import m68k.cpu.Cpu;
import m68k.cpu.MC68000;
import m68k.memory.AddressSpace;
import m68k.memory.MemorySpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static m68k.common.DataSize.ofKilobytes;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ${FILE}
 * <p>
 * Federico Berti
 * <p>
 * Copyright 2020
 */
class MOVEMTest {

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
    void testR2MWordPreDec() {
        bus.writeLong(0, 0x48a0_0080); //movem.w	a0,-(a0)
        cpu.setAddrRegisterLong(0, stack);
        cpu.execute();
        assertEquals(stack, cpu.readMemoryWord(stack - 2), "Check for a0");
    }

    @Test
    void testR2MLongPreDec() {
        bus.writeLong(0, 0x48e0_0080); //movem.l	a0,-(a0)
        cpu.setAddrRegisterLong(0, stack);
        cpu.execute();
        assertEquals(stack, cpu.readMemoryLong(stack - 4), "Check for a0");
    }
}
