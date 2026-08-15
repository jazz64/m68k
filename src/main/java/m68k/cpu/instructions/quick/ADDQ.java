package m68k.cpu.instructions.quick;

import m68k.cpu.*;
import m68k.cpu.rules.AddressingMode;

import static m68k.cpu.rules.AddressingMode.alterableModes;
import static m68k.cpu.rules.ByteOperationsOnAddressesForbidden.filtered;

/*
//  M68k - Java Amiga MachineCore
//  Copyright (c) 2008-2010, Tony Headford
//  All rights reserved.
//
//  Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
//  following conditions are met:
//
//    o  Redistributions of source code must retain the above copyright notice, this list of conditions and the
//       following disclaimer.
//    o  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
//       following disclaimer in the documentation and/or other materials provided with the distribution.
//    o  Neither the name of the M68k Project nor the names of its contributors may be used to endorse or promote
//       products derived from this software without specific prior written permission.
//
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
//  INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
//  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
//  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
//  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
//  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
//  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//
*/
public class ADDQ extends BaseQuickInstructionHandler {
    protected final Cpu cpu;

    public ADDQ(Cpu cpu) {
        super("addq");
        this.cpu = cpu;
    }

    @Override
    public final void register(InstructionSet is) {
        int base;
        Instruction i;

        // destination ea
        for (Size sz : Size.values()) {
            switch (sz) {
                case Byte -> {
                    // addq byte
                    base = 0x5000;
                    i = new Instruction() {
                        @Override
                        public int execute(int opcode) {
                            return addq_byte(opcode);
                        }

                        @Override
                        public DisassembledInstruction disassemble(int address, int opcode) {
                            return disassembleOp(cpu, address, opcode, Size.Byte);
                        }
                    };
                }
                case Word -> {
                    // addq word
                    base = 0x5040;
                    i = new Instruction() {
                        @Override
                        public int execute(int opcode) {
                            return addq_word(opcode);
                        }

                        @Override
                        public DisassembledInstruction disassemble(int address, int opcode) {
                            return disassembleOp(cpu, address, opcode, Size.Word);
                        }
                    };
                }
                default -> {
                    // addq long
                    base = 0x5080;
                    i = new Instruction() {
                        @Override
                        public int execute(int opcode) {
                            return addq_long(opcode);
                        }

                        @Override
                        public DisassembledInstruction disassemble(int address, int opcode) {
                            return disassembleOp(cpu, address, opcode, Size.Long);
                        }
                    };
                }
            }

            for (AddressingMode ea : filtered(alterableModes(), sz)) {
                for (int imm = 0; imm < 8; imm++) {
                    is.addInstruction(base + (imm << 9) + (ea.getMode() << 3) + ea.getRegister(), i);
                }
            }
        }
    }

    protected final int addq_byte(int opcode) {
        final int s = immediateFrom(opcode);

        Operand dst = cpu.resolveDstEA((opcode >> 3) & 0x07, (opcode & 0x07), Size.Byte);
        int d = dst.getByteSigned();
        int r = s + d;
        dst.setByte(r);
        cpu.calcFlags(InstructionType.ADD, s, d, r, Size.Byte);
        return (dst.isRegisterMode() ? 4 : 8 + dst.getTiming());
    }

    protected final int addq_word(int opcode) {
        // ADDQ where the destination is an address register does not affect the flags and the ENTIRE address
        // reg is affected by the addition (same not true for byte sized, as there is no byte sized addq with address reg).
        final int s = immediateFrom(opcode);

        int mode = (opcode >> 3) & 0x07;
        if (mode != 1) {
            Operand dst = cpu.resolveDstEA(mode, (opcode & 0x07), Size.Word);
            int d = dst.getWordSigned();
            int r = s + d;
            dst.setWord(r);
            cpu.calcFlags(InstructionType.ADD, s, d, r, Size.Word);
            return (dst.isRegisterMode() ? 4 : 8 + dst.getTiming());
        } else {
            int reg = opcode & 0x07;
            cpu.setAddrRegisterLong(reg, cpu.getAddrRegisterLong(reg) + s);
            return 4;
        }
    }

    protected final int addq_long(int opcode) {
        final int s = immediateFrom(opcode);

        int mode = (opcode >> 3) & 0x07;
        Operand dst = cpu.resolveDstEA(mode, (opcode & 0x07), Size.Long);
        int d = dst.getLong();
        int r = s + d;
        dst.setLong(r);

        // if destination is An then no CC affected
        if (mode != 1)
            cpu.calcFlags(InstructionType.ADD, s, d, r, Size.Long);

        return (dst.isRegisterMode() ? 8 : 12 + dst.getTiming());
    }
}
