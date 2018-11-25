package com.app.learning.core;

import com.app.learning.exception.ElfParsingException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static com.app.learning.core.Constants.CLASS_32;
import static com.app.learning.core.Constants.CLASS_64;

public class ElfParserTest {


    @Test(expected = ElfParsingException.class)
    public void parsePEFile() throws Exception {
        File input = new File("src/test/resources/WinRun4J.exe");
        final ElfParser elfParser = new ElfParser(Files.readAllBytes(input.toPath()));
        elfParser.parse();
    }

    @Test
    public void parseElfFile() throws Exception {
        File input = new File("src/test/resources/android_arm_tset");
        final ElfParser elfParser = new ElfParser(Files.readAllBytes(input.toPath()));
        elfParser.parse();
        Assert.assertEquals(CLASS_32,elfParser.getFileClass());
    }

    @Test
    public void parseElfFile_1() throws Exception {
        File input = new File("src/test/resources/android_arm_libncurses");
        final ElfParser elfParser = new ElfParser(Files.readAllBytes(input.toPath()));
        elfParser.parse();
        Assert.assertEquals(CLASS_32, elfParser.getFileClass());
    }

    @Test
    public void parseElfFile_2() throws Exception {
        File input = new File("src/test/resources/linux_amd64_bindash");
        final ElfParser elfParser = new ElfParser(Files.readAllBytes(input.toPath()));
        elfParser.parse();
        Assert.assertEquals(CLASS_64, elfParser.getFileClass());
    }
}