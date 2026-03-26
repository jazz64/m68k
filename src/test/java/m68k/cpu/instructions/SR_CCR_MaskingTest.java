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
class SR_CCR_MaskingTest {

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
    void testCCR() {
        cpu.setCCRegister(0xFF);
        assertEquals(0xFF & Cpu.CCR_MASK, cpu.getCCRegister());
        cpu.setSR(0x27FF);
        assertEquals(0x27FF & Cpu.CCR_MASK, cpu.getCCRegister());
    }

    @Test
    void testSR() {
        cpu.setSR(0xFFFF);
        assertEquals(0xFFFF & Cpu.SR_MASK, cpu.getSR());
        cpu.setCCRegister(0xFF);
        int sr = cpu.getSR();
        assertEquals(sr, cpu.getSR());
    }
}
