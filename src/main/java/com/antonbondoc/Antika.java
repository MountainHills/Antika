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
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The Antika application entry point.
 */
public class Antika {

    private static final Logger log = LoggerFactory.getLogger(Antika.class);

    private static final Option OPTION_HELP = Option.builder("h")
            .longOpt("help")
            .desc("List out the commands available for Antika")
            .build();

    private static final Option OPTION_MODE = Option.builder("m")
            .longOpt("mode")
            .hasArg()
            .argName("workflow")
            .optionalArg(false)
            .desc("Select the current workflow mode")
            .build();

    private static Options initializeOptions() {
        log.debug("Initialize the options for Antika");
        return new Options()
                .addOption(OPTION_HELP)
                .addOption(OPTION_MODE);
    }

    private static void processOptions(CommandLine cmd) {
        // No-op
    }

    private static void openWorkflow() {
        // No-op
    }

    private static void printHelp(Options options, String message) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("antika [option]", options);
        System.err.println(message);
        System.exit(1);
    }

    public static void main(String[] args) {
        Options options = initializeOptions();

        CommandLineParser parser = new DefaultParser();
        try {
            if (args.length == 0) {
                throw new ParseException("No command entered");
            }
            CommandLine cmd = parser.parse(options, args);
            processOptions(cmd);
        } catch (ParseException e) {
            printHelp(options, e.getMessage());
        }
    }

}