package m68k.cpu.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public abstract sealed class AddressingMode {
    static final class DataRegisterDirect extends AddressingMode {
        public final int number;

        DataRegisterDirect(int number) {
            this.number = number;
        }

        @Override
        public int getMode() {
            return 0;
        }

        @Override
        public int getRegister() {
            return number;
        }

        public static IntStream range() {
            return IntStream.rangeClosed(0, 7);
        }

        public static List<DataRegisterDirect> all() {
            return range().mapToObj(DataRegisterDirect::new).toList();
        }
    }

    public static final class AddressRegisterDirect extends AddressingMode {
        public final int number;

        AddressRegisterDirect(int number) {
            this.number = number;
        }

        @Override
        public int getMode() {
            return 1;
        }

        @Override
        public int getRegister() {
            return number;
        }

        @Override
        public boolean isDataOperand() {
            return false;
        }

        public static IntStream range() {
            return IntStream.rangeClosed(0, 7);
        }

        public static List<AddressRegisterDirect> all() {
            return range().mapToObj(AddressRegisterDirect::new).toList();
        }

    }

    static final class AddressRegisterIndirect extends AddressingMode {
        public final int number;

        AddressRegisterIndirect(int number) {
            this.number = number;
        }

        @Override
        public int getMode() {
            return 2;
        }

        @Override
        public int getRegister() {
            return number;
        }

        @Override
        public boolean isControlAddressing() {
            return true;
        }

        @Override
        public boolean isMemoryOperand() {
            return true;
        }

        public static IntStream range() {
            return IntStream.rangeClosed(0, 7);
        }

        public static List<AddressRegisterIndirect> all() {
            return range().mapToObj(AddressRegisterIndirect::new).toList();
        }
    }

    static final class AddressRegisterIndirectWithPostincrement extends AddressingMode {
        public final int number;

        AddressRegisterIndirectWithPostincrement(int number) {
            this.number = number;
        }

        @Override
        public int getMode() {
            return 3;
        }

        @Override
        public int getRegister() {
            return number;
        }

        @Override
        public boolean isMemoryOperand() {
            return true;
        }

        public static IntStream range() {
            return IntStream.rangeClosed(0, 7);
        }

        public static List<AddressRegisterIndirectWithPostincrement> all() {
            return range().mapToObj(AddressRegisterIndirectWithPostincrement::new).toList();
        }

    }

    static final class AddressRegisterIndirectWithPredecrement extends AddressingMode {
        public final int number;

        AddressRegisterIndirectWithPredecrement(int number) {
            this.number = number;
        }

        @Override
        public int getMode() {
            return 4;
        }

        @Override
        public int getRegister() {
            return number;
        }

        @Override
        public boolean isMemoryOperand() {
            return true;
        }

        public static IntStream range() {
            return IntStream.rangeClosed(0, 7);
        }

        public static List<AddressRegisterIndirectWithPredecrement> all() {
            return range().mapToObj(AddressRegisterIndirectWithPredecrement::new).toList();
        }
    }

    static final class AddressRegisterIndirectWithDisplacement extends AddressingMode {
        public final int number;

        AddressRegisterIndirectWithDisplacement(int number) {
            this.number = number;
        }

        @Override
        public int getMode() {
            return 5;
        }

        @Override
        public int getRegister() {
            return number;
        }

        @Override
        public boolean isControlAddressing() {
            return true;
        }

        @Override
        public boolean isMemoryOperand() {
            return true;
        }

        public static IntStream range() {
            return IntStream.rangeClosed(0, 7);
        }

        public static List<AddressRegisterIndirectWithDisplacement> all() {
            return range().mapToObj(AddressRegisterIndirectWithDisplacement::new).toList();
        }
    }

    static final class AddressRegisterIndirectWithIndex extends AddressingMode {
        public final int number;

        AddressRegisterIndirectWithIndex(int number) {
            this.number = number;
        }

        @Override
        public int getMode() {
            return 6;
        }

        @Override
        public int getRegister() {
            return number;
        }

        @Override
        public boolean isControlAddressing() {
            return true;
        }

        @Override
        public boolean isMemoryOperand() {
            return true;
        }

        public static IntStream range() {
            return IntStream.rangeClosed(0, 7);
        }

        public static List<AddressRegisterIndirectWithIndex> all() {
            return range().mapToObj(AddressRegisterIndirectWithIndex::new).toList();
        }
    }

    static final class AbsoluteShortAddress extends AddressingMode {

        @Override
        public int getMode() {
            return 7;
        }

        @Override
        public int getRegister() {
            return 0;
        }

        @Override
        public boolean isControlAddressing() {
            return true;
        }

        @Override
        public boolean isMemoryOperand() {
            return true;
        }
    }

    static final class AbsoluteLongAddress extends AddressingMode {
        @Override
        public int getMode() {
            return 7;
        }

        @Override
        public int getRegister() {
            return 1;
        }

        @Override
        public boolean isControlAddressing() {
            return true;
        }

        @Override
        public boolean isMemoryOperand() {
            return true;
        }
    }

    static final class ProgramCounterWithDisplacement extends AddressingMode {
        @Override
        public int getMode() {
            return 7;
        }

        @Override
        public int getRegister() {
            return 2;
        }

        @Override
        public boolean isControlAddressing() {
            return true;
        }

        @Override
        public boolean isMemoryOperand() {
            return true;
        }

        @Override
        public boolean isAlterable() {
            return false;
        }
    }

    static final class ProgramCounterWithIndex extends AddressingMode {
        @Override
        public int getMode() {
            return 7;
        }

        @Override
        public int getRegister() {
            return 3;
        }

        @Override
        public boolean isControlAddressing() {
            return true;
        }

        @Override
        public boolean isMemoryOperand() {
            return true;
        }

        @Override
        public boolean isAlterable() {
            return false;
        }
    }

    static final class ImmediateData extends AddressingMode {
        @Override
        public int getMode() {
            return 7;
        }

        @Override
        public int getRegister() {
            return 4;
        }

        @Override
        public boolean isAlterable() {
            return false;
        }
    }

    public abstract int getMode();

    public abstract int getRegister();

    public boolean isDataOperand() {
        return true;
    }

    public boolean isMemoryOperand() {
        return false;
    }

    public boolean isAlterable() {
        return true;
    }

    public boolean isControlAddressing() {
        return false;
    }

    public boolean isDataAlterable() {
        return isDataOperand() && isAlterable();
    }

    public boolean isMemoryAlterable() {
        return isMemoryOperand() && isAlterable();
    }

    public boolean isControlAlterable() {
        return isControlAddressing() && isAlterable();
    }

    public static List<AddressingMode> dataModes() {
        return allModes().stream()
                .filter(AddressingMode::isDataOperand)
                .toList();
    }

    public static List<AddressingMode> alterableModes() {
        return allModes().stream()
                .filter(AddressingMode::isAlterable)
                .toList();
    }

    public static List<AddressingMode> dataAlterableModes() {
        return allModes().stream()
                .filter(AddressingMode::isDataAlterable)
                .toList();
    }

    public static List<AddressingMode> alterableMemoryModes() {
        return allModes().stream()
                .filter(AddressingMode::isMemoryAlterable)
                .toList();
    }

    public static List<AddressingMode> controlModes() {
        return allModes().stream()
                .filter(AddressingMode::isControlAddressing)
                .toList();
    }

    public static List<AddressingMode> controlAlterableModes() {
        return allModes().stream()
                .filter(AddressingMode::isControlAlterable)
                .toList();
    }

    public static List<AddressingMode> allModes() {
        final List<AddressingMode> collector = new ArrayList<>();

        collector.addAll(DataRegisterDirect.all());
        collector.addAll(AddressRegisterDirect.all());
        collector.addAll(AddressRegisterIndirect.all());
        collector.addAll(AddressRegisterIndirectWithPostincrement.all());
        collector.addAll(AddressRegisterIndirectWithPredecrement.all());
        collector.addAll(AddressRegisterIndirectWithDisplacement.all());
        collector.addAll(AddressRegisterIndirectWithIndex.all());

        collector.add(new AbsoluteShortAddress());
        collector.add(new AbsoluteLongAddress());
        collector.add(new ProgramCounterWithDisplacement());
        collector.add(new ProgramCounterWithIndex());
        collector.add(new ImmediateData());

        return collector;
    }
}
