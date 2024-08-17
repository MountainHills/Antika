package com.antonbondoc;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Antika {

    private static final Logger log = LoggerFactory.getLogger(Antika.class);

    private static final Option OPTION_DISPLAY = Option.builder("m")
            .longOpt("message")
            .hasArg()
            .argName("message")
            .desc("Notification message")
            .required(false)
            .build();

    private static Options setupOptions() {
        return new Options()
                .addOption(OPTION_DISPLAY);
    }

    public static void main(String[] args) throws ParseException {
        log.debug("Starting Antika...");
        Options options = setupOptions();

        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption(OPTION_DISPLAY)) {
            String message = cmd.getOptionValue(OPTION_DISPLAY);
            System.out.println("message = " + message);
        } else {
            formatter.printHelp("antika", options);
        }
    }
}