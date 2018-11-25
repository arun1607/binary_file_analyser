package com.app.learning.core;

public final class Constants {

    private Constants() {

    }

    /**
     * Relocatable file type.
     */
    public static final int FT_REL = 1;

    /**
     * Executable file type.
     */
    public static final int FT_EXEC = 2;
    /**
     * Shared object file type.
     */
    public static final int FT_DYN = 3;
    /**
     * Core file type.
     */
    public static final int FT_CORE = 4;

    public static final byte CLASS_32 = 1;
    /**
     * 64-bit objects.
     */
    public static final byte CLASS_64 = 2;

    /**
     * LSB data fileEncoding.
     */
    public static final byte DATA_LSB = 1;
    /**
     * MSB data fileEncoding.
     */
    public static final byte DATA_MSB = 2;

    /**
     * No architecture type.
     */
    public static final int ARCH_NONE = 0;
    /**
     * AT&amp;T architecture type.
     */
    public static final int ARCH_ATT = 1;
    /**
     * SPARC architecture type.
     */
    public static final int ARCH_SPARC = 2;
    /**
     * Intel 386 architecture type.
     */
    public static final int ARCH_INTEL_386 = 3;
    /**
     * Motorola 68000 architecture type.
     */
    public static final int ARCH_MOTOROLA_68K = 4;
    /**
     * Motorola 88000 architecture type.
     */
    public static final int ARCH_MOTOROLA_88K = 5;
    /**
     * Intel 860 architecture type.
     */
    public static final int ARCH_i860 = 7;
    /**
     * MIPS architecture type.
     */
    public static final int ARCH_MIPS = 8;
    public static final int ARCH_ARM = 0x28;
    public static final int ARCH_X86_64 = 0x3E;
    public static final int ARCH_AARCH64 = 0xB7;
}
