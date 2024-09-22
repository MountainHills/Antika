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

import java.util.List;
import java.util.Set;


/**
 * The Antika application entry point.
 */
public class Antika {

    private static final Logger log = LoggerFactory.getLogger(Antika.class);

    private static final Option OPTION_HELP = Option.builder("h")
            .longOpt("help")
            .desc("List out the commands available for Antika")
            .build();

    private static final Option OPTION_INIT = Option.builder("i")
            .longOpt("init")
            .desc("Create the initial workflow file for Antika")
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
                .addOption(OPTION_INIT)
                .addOption(OPTION_MODE);
    }

    private static final ToolCSVHandler toolCSVHandler = new ToolCSVHandler();
    private static final WorkflowHandler workflowHandler = new WorkflowHandler();

    /**
     * Handle the arguments given by the user to use Antika
     *
     * @param cmd the command line containing the argument parameters passed by the user
     */
    private static void processOptions(Options options, CommandLine cmd) {
        if (cmd.hasOption(OPTION_HELP)) {
            log.debug("Printing help option");
            printHelp(options);
        } else if (cmd.hasOption(OPTION_INIT)) {
            log.debug("Initializing workflow file");
            toolCSVHandler.createWorkflowFile();
        } else if (cmd.hasOption(OPTION_MODE)) {
            log.debug("Opening workflow tools");
            String workflow = cmd.getOptionValue(OPTION_MODE).trim();
            openWorkflow(workflow);
        }

        log.info("Closing application...");
        System.exit(0);
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
     * @param workflow the chosen workflow
     */
    private static void openWorkflow(String workflow) {
        List<Tool> tools = toolCSVHandler.getTools();
        Set<String> availableWorkflows = toolCSVHandler.getWorkflows(tools);

        if (availableWorkflows.contains(workflow)) {
            log.debug("Opening tools related to workflow: {}", workflow);
            workflowHandler.openTools(workflow, tools);
            System.exit(0);
        } else {
            printWorkflows(availableWorkflows);
            System.err.printf("Workflow: '%s' is not a valid workflow", workflow);
            System.exit(-1);
        }
    }

    /**
     * Prints out the list of available workflows for Antika
     *
     * @param workflows the set of available workflows
     */
    private static void printWorkflows(Set<String> workflows) {
        if (workflows.isEmpty()) {
            System.out.println("There are no created Antika workflows.");
            return;
        }

        int idx = 0;
        System.out.println("List of Antika Workflows");
        for (String workflow : workflows) {
            System.out.printf("%d. %s%n", ++idx, workflow);
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