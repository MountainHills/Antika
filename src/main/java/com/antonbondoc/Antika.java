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
                .addOption("list", "Display list of available workflows")
                .addOption("flow", "Enter workflow application");
    }

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            if (args.length == 0) {
                throw new ParseException("No command entered");
            }
            CommandLine cmd = parser.parse(MAIN_COMMANDS, args);
            processOptions(cmd);
        } catch (ParseException e) {
            handleMainCommandsExceptions(e.getMessage());
        }
    }

    private static void processOptions(CommandLine cmd) {
        // No-op
    }

    private static void handleMainCommandsExceptions(String message) {
        MAIN_HELP_FORMATTER.printHelp("antika [command]", MAIN_COMMANDS);
        System.err.println(message);
        System.exit(1);
    }
}