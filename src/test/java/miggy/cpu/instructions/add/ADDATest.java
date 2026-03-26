package miggy.cpu.instructions.add;

import miggy.BasicSetup;
import miggy.SystemModel;
import miggy.SystemModel.CpuFlag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// $Revision: 21 $
class ADDATest extends BasicSetup {
    @Test
    void testWord() {
        //test: changed to poke
        setInstruction(0xd0c0);    //adda.w d0, a0
        SystemModel.CPU.setDataRegister(0, 0xc234);
        SystemModel.CPU.setAddrRegister(0, 0x56785678);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x567818ac, SystemModel.CPU.getAddrRegister(0), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }

    @Test
    void testLong() {
        setInstruction(0xd1c0);    //adda.l d0, a0
        SystemModel.CPU.setDataRegister(0, 0x12345678);
        SystemModel.CPU.setAddrRegister(0, 0x56785678);
        SystemModel.CPU.setCCR((byte) 0x1f);

        SystemModel.CPU.execute();

        assertEquals(0x68acacf0, SystemModel.CPU.getAddrRegister(0), "Check result");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.V), "Check V");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.N), "Check N");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.Z), "Check Z");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.C), "Check C");
        assertTrue(SystemModel.CPU.isSet(CpuFlag.X), "Check X");
    }
}
