package m68k.cpu;

import java.util.HashMap;
import java.util.Map;

public class TestRegistry implements InstructionSet {
    private final Map<Integer, Instruction> map = new HashMap<>();

    @Override
    public void addInstruction(int opcode, Instruction instruction) {
        map.put(opcode, instruction);
    }

    public int size() {
        return map.size();
    }
}
