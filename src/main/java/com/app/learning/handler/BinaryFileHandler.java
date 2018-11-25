package com.app.learning.handler;

import com.app.learning.core.FileType;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BinaryFileHandler implements CommandLineRunner {

    private Options options;
    private CommandLineParser parser;
    private CommandLine cmd;
    private HelpFormatter formatter;

    @Autowired
    private FileAnalyser fileAnalyser;

    /**
     * Initiates the sub-command
     */
    @PostConstruct
    public void init() {
        options = new Options();
        parser = new DefaultParser();
        formatter = new HelpFormatter();

        final Option fileOption = Option.builder("f").longOpt("file")
                .argName("file")
                .required()
                .hasArg()
                .desc("File to be analyzed")
                .build();

        options.addOption(fileOption);
    }

    @Override
    public void run(final String... args) throws Exception {
        try {
            cmd = parser.parse(options, args);
        } catch (UnrecognizedOptionException | MissingArgumentException | MissingOptionException e) {
            formatter.printHelp("binary-file-inspector", options);
            System.exit(1);
        }

        if (cmd.hasOption("f") && !cmd.getOptionValue("f").isEmpty()) {
            System.out.println("AnalyzerCommand is running on the file: " + cmd.getOptionValue("f"));
            String filePath = cmd.getOptionValue("f");
            FileHandler fileHandler = fileAnalyser.getFileHandler(filePath);
            if (fileHandler != null && FileType.ELF.equals(fileHandler.getFileType())) {
                fileHandler.extractMetaData();
            }
        }
    }
}
