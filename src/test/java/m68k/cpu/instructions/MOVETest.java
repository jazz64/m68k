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
 * Federico Berti
 * <p>
 * Copyright 2021
 */
class MOVETest {

    AddressSpace bus;
    Cpu cpu;
    int stack = 0x231;

    @BeforeEach
    void setUp() {
        bus = new MemorySpace(ofKilobytes(1));
        cpu = new MC68000();
        cpu.setAddressSpace(bus);
        cpu.reset();
        cpu.setAddrRegisterLong(7, stack);
    }

    @Test
    void testMoveByteA7() {
        bus.writeWord(0, 0x1F00); //move.b	d0,-(a7)
        cpu.setAddrRegisterLong(0, stack);
        cpu.setDataRegisterLong(0, 0x1234);

        cpu.execute();

        //a7 is decremented by 2 instead of 1
        assertEquals(stack - 2, cpu.getAddrRegisterLong(7), "Check new stack value");
        assertEquals(0x34, cpu.readMemoryByte(cpu.getAddrRegisterLong(7)), "Check value on stack");
    }

    @Test
    void testMoveByte() {
        bus.writeWord(0, 0x1100); //move.b	d0,-(a0)
        cpu.setAddrRegisterLong(0, stack);
        cpu.setDataRegisterLong(0, 0x1234);
        cpu.execute();
        assertEquals(0x34, cpu.readMemoryByte(stack - 1), "Check for d0");
    }

    @Test
    void testMoveWord() {
        bus.writeWord(0, 0x3108); //move.w	a0,-(a0)
        cpu.setAddrRegisterLong(0, stack);
        cpu.execute();
        assertEquals(stack, cpu.readMemoryWord(stack - 2), "Check for a0");
    }

    @Test
    void testMoveLong() {
        bus.writeWord(0, 0x22c9); //move.l	a1,(a1)+
        cpu.setAddrRegisterLong(1, stack);
        cpu.execute();
        assertEquals(stack, cpu.readMemoryLong(stack), "Check for a1");
    }
}
