package com.app.learning.core;

import java.util.HashMap;
import java.util.Map;

public enum ArchitectureType {
    EM_NONE(1, "No machine"),
    EM_M32(2, "AT&T WE 32100"),
    EM_SPARC(3, "SPARC"),
    EM_386(4, "Intel 80386"),
    EM_68K(5, "Motorola 68000"),
    EM_88K(6, "Motorola 88000"),
    EM_IAMCU(7, "Intel MCU"),
    EM_860(8, "Intel 80860"),
    EM_MIPS(9, "MIPS I Architecture")
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
