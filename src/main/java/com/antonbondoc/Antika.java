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

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Map;


/**
 * The Antika application entry point
 */
public class Antika {
    private static final Logger log = LoggerFactory.getLogger(Antika.class);

    private static final Options MAIN_COMMANDS;

    static {
        MAIN_COMMANDS = new Options()
                .addOption("help", "Display this help message")
                .addOption("list", "List of available workflows")
                .addOption("workflow", "Enter workflow application");
    }

    private static final Map<String, String> COMMANDS = Map.of(
            "help", "Help Class",
            "list", "List Class",
            "mode", "Mode Class"
    );

    public static void main(String[] args) {
        if (args.length == 0) {
            handleCommandExceptions("There are no command line arguments");
        }
        String command = args[0].toLowerCase(Locale.ROOT);
        if (!COMMANDS.containsKey(command)) {
            handleCommandExceptions("The command is invalid");
        }
        String handler = COMMANDS.get(command);
        System.out.println("handler = " + handler);
    }

    private static void handleCommandExceptions(String message) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setOptPrefix("");
        formatter.printHelp("antika [command]", MAIN_COMMANDS);
        System.err.println(message);
        System.exit(1);
    }
}