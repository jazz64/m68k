package m68k.cpu.rules;

import m68k.cpu.Size;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteOperationsOnAddressesForbiddenTest {
    @Test
    void filtered_onByteSize_returnsEightModesLeftOut() {
        List<AddressingMode> modes = AddressingMode.allModes();

        List<AddressingMode> result = ByteOperationsOnAddressesForbidden.filtered(modes, Size.Byte);

        assertEquals(modes.size() - 8, result.size());
    }

    @Test
    void filtered_onWordSize_returnsAllModes() {
        List<AddressingMode> modes = AddressingMode.allModes();

        List<AddressingMode> result = ByteOperationsOnAddressesForbidden.filtered(modes, Size.Word);

        assertEquals(modes.size(), result.size());
    }
}
