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

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Timer;
import java.util.TimerTask;

public class Antika {

    private static final Logger log = LoggerFactory.getLogger(Antika.class);

    private static final Option OPTION_OUT = Option.builder("out")
            .hasArg()
            .argName("hours")
            .optionalArg(true)
            .type(Integer.class)
            .desc("Notifies me after given hours work shift. The <hours> argument is optional")
            .build();

    private static final Option OPTION_DISPLAY = Option.builder("m")
            .longOpt("message")
            .hasArg()
            .argName("message")
            .optionalArg(false)
            .desc("Notification message")
            .build();

    private static Options setupOptions() {
        return new Options()
                .addOption(OPTION_DISPLAY)
                .addOption(OPTION_OUT);
    }

    public static void main(String[] args) {
        Options options = setupOptions();

        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            processOptions(cmd, formatter, options);
        } catch (ParseException e) {
            formatter.printHelp("antika", options);
        }
    }

    private static void processOptions(CommandLine cmd, HelpFormatter formatter, Options options) throws ParseException {
        if (cmd.hasOption(OPTION_DISPLAY)) {
            String message = cmd.getOptionValue(OPTION_DISPLAY);
            log.info("message: {}", message);
        } else if (cmd.hasOption(OPTION_OUT)) {
            Integer hours = cmd.getParsedOptionValue(OPTION_OUT);
            log.debug("parsed hours: {}", hours);
            processOptionOut(hours == null ? 9 : hours);
        } else {
            formatter.printHelp("antika", options);
        }
    }

    private static void processOptionOut(int hours) {
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime offTime = ldt.plusSeconds(hours); // TODO: Change to hours

        long timeIn = ldt.toInstant(ZoneOffset.UTC).toEpochMilli();
        long timeOut = offTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        long difference = timeOut - timeIn;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(frame, "You can now go out!");
                System.exit(0);
            }
        };
        timer.schedule(task, difference);
    }

}