/*
 * MIT License
 *
 * Copyright (c) [2024] [Anton Bondoc]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.antonbondoc;

import com.antonbondoc.command.HelpCommandHandler;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


/**
 * The Antika application entry point.
 */
public class Antika {
    private static final Options MAIN_COMMANDS;

    private static final HelpFormatter MAIN_HELP_FORMATTER = new HelpFormatter();

    static {
        MAIN_COMMANDS = new Options()
                .addOption("help", "Display this help message")
                .addOption("list", "List of available workflows")
                .addOption("flow", "Enter workflow application");

        // Remove the '-' prefix in help formatter, to make it more command-like when help is printed
        MAIN_HELP_FORMATTER.setOptPrefix("");
    }

    public static void main(String[] args) {
        args = new String[] {"flow"};
        CommandLineParser parser = new DefaultParser();
        try {
            if (args.length == 0) {
                throw new ParseException("No command entered");
            }
            addHyphenToMainCommand(args);
            CommandLine cmd = parser.parse(MAIN_COMMANDS, args);
            processOptions(cmd);
        } catch (ParseException e) {
            handleMainCommandsExceptions(e.getMessage());
        }
    }

    /**
     * Only processes the first argument of the command line
     */
    private static void processOptions(CommandLine cmd) {
        if (cmd.hasOption("help")) {
            var handler = new HelpCommandHandler();
            handler.run(MAIN_COMMANDS, cmd.getArgs());
        }

        if (cmd.hasOption("list")) {
            var handler = new HelpCommandHandler();
            handler.run(MAIN_COMMANDS, cmd.getArgs());
        }

        if (cmd.hasOption("flow")) {
            var handler = new HelpCommandHandler();
            handler.run(MAIN_COMMANDS, cmd.getArgs());
        }

        handleMainCommandsExceptions("No command entered");
    }

    /**
     * Adds a hyphen '-' to the first argument of the command line
     */
    private static void addHyphenToMainCommand(String[] args) {
        if (args[0] != null) {
            String temp = "-" + args[0];
            args[0] = temp;
        }
    }

    private static void handleMainCommandsExceptions(String message) {
        MAIN_HELP_FORMATTER.printHelp("antika [command]", MAIN_COMMANDS);
        System.err.println(message);
        System.exit(1);
    }
}