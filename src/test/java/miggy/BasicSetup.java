package miggy;

import m68k.cpu.Size;
import miggy.memory.TestMem;

import static m68k.common.DataSize.ofMegabytes;

// $Revision: 21 $
public class BasicSetup {
    protected int codebase;

    public BasicSetup() {
        setUp();
    }

    protected void setUp() {
        SystemModel.MEM = TestMem.create(ofMegabytes(2));
        SystemModel.CPU = new TestCpu(SystemModel.MEM);

        //test vectors just containing the vector number
        for (int v = 0; v < 256; v++) {
            int addr = v << 2;
            //test: changed to poke
            SystemModel.MEM.poke(addr, v, Size.Long);
        }

        SystemModel.CPU.setAddrRegisterWord(7, 2048);    //set up the stack
        SystemModel.CPU.setUSP(0x0800);
        SystemModel.CPU.setSSP(0x0780);
        codebase = 0x0400;
        SystemModel.CPU.setPC(codebase);
    }

    protected void setInstruction(int opcode) {
        SystemModel.MEM.poke(codebase, opcode, Size.Word);
    }

    protected void setInstructionParamW(int opcode, int param) {
        SystemModel.MEM.poke(codebase, opcode, Size.Word);
        SystemModel.MEM.poke(codebase + 2, param, Size.Word);
    }

    protected void setInstructionParamL(int opcode, int param) {
        SystemModel.MEM.poke(codebase, opcode, Size.Word);
        SystemModel.MEM.poke(codebase + 2, param, Size.Long);
    }
}
