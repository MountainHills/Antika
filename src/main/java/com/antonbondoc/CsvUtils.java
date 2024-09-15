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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Utility functions for CSV I/O.
 */
public class CsvUtils {

    private static Logger log = LoggerFactory.getLogger(CsvUtils.class);

    private enum Headers {
        MODE, TYPE, PATH
    }

    private static final String CURRENT_DIRECTORY = System.getProperty("user.dir");

    private static final File WORKFLOW_FILE = Paths.get(CURRENT_DIRECTORY, "workflow.csv").toFile();

    private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.builder()
            .setHeader(Headers.class)
            .setSkipHeaderRecord(true)
            .setIgnoreEmptyLines(true)
            .build();

    private static final Tool EXAMPLE_TOOL = new Tool("example", Type.APP, "notepad.exe");

    /**
     * Gets all the unique modes (workflow) available
     *
     * @return set of unique workflows available in Antika
     */
    public static Set<String> getWorkflows() {
        return getTools().stream()
                .map(Tool::mode)
                .collect(Collectors.toSet());
    }

    /**
     * Reads the 'workflow.csv' file to get the list of tools
     * <p>
     * If the 'workflow.csv' file does not exist, then generate a placeholder file.
     *
     * @return list of tools available in workflow.csv
     */
    private static List<Tool> getTools() {
        if (!WORKFLOW_FILE.exists()) {
            createWorkflowFile();
        }

        List<Tool> tools = new ArrayList<>();
        try (final Reader in = new FileReader(WORKFLOW_FILE, StandardCharsets.UTF_8)) {
            final CSVParser csvParser = new CSVParser(in, CSV_FORMAT);
            for (CSVRecord record : csvParser) {
                if (hasEmptyValues(record)) continue;

                String mode = record.get(Headers.MODE);
                String type = record.get(Headers.TYPE);
                String path = record.get(Headers.PATH);
                tools.add(new Tool(mode, Type.get(type), path));
            }
        } catch (IOException ex) {
            System.err.println("ex = " + ex.getMessage());
        }
        return tools;
    }

    /**
     * Checks whether the record has an empty value
     *
     * @param record the tool provided in the csv file
     * @return true if at least one value in the record is empty, false otherwise.
     */
    private static boolean hasEmptyValues(CSVRecord record) {
        return Stream.of(record.values())
                .anyMatch(v -> v.trim().isEmpty());
    }

    /**
     * Create the `workflow.csv` file that would hold the tools configurations
     */
    private static void createWorkflowFile() {
        try (FileWriter out = new FileWriter(WORKFLOW_FILE, StandardCharsets.UTF_8)) {
            CSVPrinter printer = new CSVPrinter(out, CSV_FORMAT);
            printer.printRecord((Object[]) Headers.values());
            printer.printRecord((Object[]) EXAMPLE_TOOL.values());
        } catch (IOException ex) {
            System.err.println("ex = " + ex.getMessage());
        }
    }
}
