package com.app.learning.config;

import com.app.learning.core.ElfParser;
import com.app.learning.core.FileType;
import com.app.learning.handler.ElfFileHandler;
import com.app.learning.handler.FileHandler;
import com.app.learning.handler.PEFileHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    public FileHandler fileHandler(FileType fileType, String file) {
        if (FileType.ELF.equals(fileType)) {
            return new ElfFileHandler(file, fileType);
        } else if (FileType.PE.equals(fileType)) {
            return new PEFileHandler(file, fileType);
        } else
            return null;
    }

    @Bean
    @Scope("prototype")
    public ElfParser parser(final byte[] data) {
        return new ElfParser(data);
    }

}
