package m68k.cpu.rules;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressingModeTest {
    @Test
    void dataModes_returnsExactSize() {
        List<AddressingMode> result = AddressingMode.dataModes();

        assertEquals(53, result.size());
    }

    @Test
    void alterableModes_returnsExactSize() {
        List<AddressingMode> result = AddressingMode.alterableModes();

        assertEquals(58, result.size());
    }

    @Test
    void dataAlterableModes_returnsExactSize() {
        List<AddressingMode> result = AddressingMode.dataAlterableModes();

        assertEquals(50, result.size());
    }

    @Test
    void alterableMemoryModes_returnsExactSize() {
        List<AddressingMode> result = AddressingMode.alterableMemoryModes();

        assertEquals(42, result.size());
    }

    @Test
    void controlModes_returnsExactSize() {
        List<AddressingMode> result = AddressingMode.controlModes();

        assertEquals(28, result.size());
    }

    @Test
    void controlAlterableModes_returnsExactSize() {
        List<AddressingMode> result = AddressingMode.controlAlterableModes();

        assertEquals(26, result.size());
    }
}
