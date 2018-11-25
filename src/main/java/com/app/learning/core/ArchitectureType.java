package com.app.learning.core;

import java.util.HashMap;
import java.util.Map;

public enum ArchitectureType {
    EM_NONE(0, "No machine"),
    EM_M32(1, "AT&T WE 32100"),
    EM_SPARC(2, "SPARC"),
    EM_386(3, "Intel 80386"),
    EM_68K(4, "Motorola 68000"),
    EM_88K(5, "Motorola 88000"),
    EM_IAMCU(6, "Intel MCU"),
    EM_860(7, "Intel 80860"),
    EM_MIPS(8, "MIPS I Architecture"),
    EM_S370(9, "IBM System/370 Processor"),
    EM_MIPS_RS3_LE(10, "MIPS RS3000 Little-endian"),
    EM_PARISC(15, "Hewlett-Packard PA-RISC"),
    EM_VPP500(17, "Fujitsu VPP500"),
    EM_SPARC32PLUS(18, "Enhanced instruction set SPARC"),
    EM_960(19, "Intel 80960"),
    EM_PPC(20, "PowerPC"),
    EM_PPC64(21, "64-bit PowerPC"),
    EM_S390(22, "IBM System/390 Processor"),
    EM_SPU(23, "IBM SPU/SPC"),
    EM_V800(36, "NEC V800"),
    EM_FR20(37, "Fujitsu FR20"),
    EM_RH32(38, "TRW RH-32"),
    EM_RCE(39, "Motorola RCE"),
    EM_ARM(40, "ARM 32-bit architecture (AARCH32)"),
    EM_X86_64(62, "AMD x86-64 architecture")
    ;


    private final int code;
    private final String description;

    ArchitectureType(final int code, final String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    private static Map<Integer, ArchitectureType> map = new HashMap<>();

    static {
        for (final ArchitectureType architectureType : ArchitectureType.values()) {
            map.put(architectureType.code, architectureType);
        }
    }

    public static ArchitectureType getInstance(int code) {
        return map.get(code);
    }
}
