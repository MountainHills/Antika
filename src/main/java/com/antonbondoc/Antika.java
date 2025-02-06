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

import com.antonbondoc.handler.FileHandler;
import com.antonbondoc.handler.WorkflowHandler;
import com.antonbondoc.handler.YamlFileHandler;
import com.antonbondoc.model.Workflow;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Set;


/**
 * The Antika application entry point.
 */
public class Antika {
    private static final Option OPTION_HELP = Option.builder("h")
            .longOpt("help")
            .desc("List out the options available for Antika")
            .build();

    private static final Option OPTION_INIT = Option.builder("i")
            .longOpt("init")
            .desc("Create the initial workflow file for Antika")
            .build();

    private static final Option OPTION_LIST = Option.builder("ls")
            .longOpt("list")
            .desc("List out the available workflow modes")
            .build();

    private static final Option OPTION_MODE = Option.builder("m")
            .longOpt("mode")
            .hasArg()
            .argName("workflow-mode")
            .optionalArg(false)
            .desc("Select the current workflow mode")
            .build();

    private static Options initializeOptions() {
        return new Options()
                .addOption(OPTION_HELP)
                .addOption(OPTION_INIT)
                .addOption(OPTION_LIST)
                .addOption(OPTION_MODE);
    }

    private static final FileHandler fileHandler = new YamlFileHandler();
    private static final WorkflowHandler workflowHandler = new WorkflowHandler();

    /**
     * Handle the arguments given by the user to use Antika
     *
     * @param cmd the command line containing the argument parameters passed by the user
     */
    private static void processOptions(Options options, CommandLine cmd) {
        if (cmd.hasOption(OPTION_HELP)) {
            printHelp(options);
        } else if (cmd.hasOption(OPTION_INIT)) {
            fileHandler.createWorkflowFile();
        } else if (cmd.hasOption(OPTION_LIST)) {
            printWorkflowModes();
        } else if (cmd.hasOption(OPTION_MODE)) {
            String workflow = cmd.getOptionValue(OPTION_MODE).trim();
            openWorkflow(workflow);
        } else {
            System.out.println("Use antika --help (or -h) for a list of possible options");
            printHelp(options);
        }
    }

    /**
     * Prints out the list of available flags (options) for Antika
     *
     * @param options the available flags for Antika
     */
    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("antika [option]", options);
    }

    /**
     * Open all tools related to the given workflow.
     * <p>
     * If the workflow is not valid, it would list out the available workflow selections
     *
     * @param mode the chosen workflow mode
     */
    private static void openWorkflow(String mode) {
        Workflow workflow = fileHandler.getWorkflow(mode);
        workflowHandler.openTools(workflow);
    }

    /**
     * Prints out the list of available workflow modes for Antika
     */
    private static void printWorkflowModes() {
        Set<String> workflowModes = fileHandler.getWorkflowModes();
        int idx = 0;
        System.out.println("List of Antika Workflows");
        for (String mode : workflowModes) {
            System.out.printf("%d. %s%n", ++idx, mode);
        }
    }

    public static void main(String[] args) {
        Options options = initializeOptions();

        CommandLineParser parser = new DefaultParser();
        try {
            if (args.length == 0) {
                throw new ParseException("No option selected");
            }
            CommandLine cmd = parser.parse(options, args);
            processOptions(options, cmd);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            printHelp(options);
            System.exit(-1);
        }
    }

}