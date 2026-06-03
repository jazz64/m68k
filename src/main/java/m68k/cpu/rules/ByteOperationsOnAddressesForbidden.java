package m68k.cpu.rules;

import m68k.cpu.Size;

import java.util.List;

/**
 * Utility class that enforces the M68000 rule forbidding byte-sized operations
 * on address registers.
 * <p>
 * This class provides static helper methods to filter or validate addressing
 * modes so that code emulating the Motorola 68000 CPU can explicitly prevent
 * illegal byte operations on address registers.
 * <p>
 * PRM 5th ed; B-8 (ADD), B-36 (CMP), B-61 (MOVE), B-103 (SUB)
 */
public class ByteOperationsOnAddressesForbidden {
    private ByteOperationsOnAddressesForbidden() {
        /* This utility class should not be instantiated */
    }

    private static boolean isNotAddressRegisterDirectMode(AddressingMode mode) {
        return !(mode instanceof AddressingMode.AddressRegisterDirect);
    }

    /**
     * Returns a list containing only addressing modes that are legal for the
     * given operand size according to the M68000 rule that forbids byte-sized
     * operations on Address Register Direct mode.
     *
     * @param modes the input collection of addressing modes; must not be null
     * @param size  the operand size to check; must not be null
     * @return an immutable list of addressing modes allowed for the given size;
     * if {@code size} is BYTE, any Address Register Direct modes are removed
     */
    public static List<AddressingMode> filtered(List<AddressingMode> modes, Size size) {
        return size == Size.Byte
                ? modes.stream()
                  .filter(ByteOperationsOnAddressesForbidden::isNotAddressRegisterDirectMode)
                  .toList()
                : modes;
    }
}
