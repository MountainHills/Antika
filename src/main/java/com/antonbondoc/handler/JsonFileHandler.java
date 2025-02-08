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

package com.antonbondoc.handler;

import com.antonbondoc.model.Workflow;
import com.antonbondoc.model.WorkflowWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonFileHandler implements FileHandler {

    private final File WORKFLOW_FILE = Paths.get(CURRENT_DIRECTORY, "workflows.json").toFile();

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Override
    public Set<String> getWorkflowModes() {
        List<Workflow> workflows = getWorkflowsFromFile();
        return workflows.stream()
                .map(Workflow::getMode)
                .collect(Collectors.toSet());
    }

    @Override
    public Workflow getWorkflow(String mode) {
        Workflow workflow = null;
        try {
            List<Workflow> workflows = getWorkflowsFromFile();
            workflow = workflows.stream()
                    .filter(w -> w.getMode().equalsIgnoreCase(mode))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Workflow does not exist"));
        } catch (IllegalArgumentException ex) {
            System.err.printf("'%s' workflow does not exist", mode);
            System.exit(-1);
        }
        return workflow;
    }

    private List<Workflow> getWorkflowsFromFile() {
        List<Workflow> workflows = new ArrayList<>();
        try (Reader reader = new FileReader(WORKFLOW_FILE)) {
            WorkflowWrapper wrapper = gson.fromJson(reader, WorkflowWrapper.class);
            workflows = wrapper.getWorkflows();
        } catch (IOException e) {
            System.err.print(e.getMessage());
            System.exit(-1);
        }
        return workflows;
    }

    @Override
    public void createWorkflowFile() {
        if (WORKFLOW_FILE.exists()) {
            System.err.println("The workflow file already exists");
            System.exit(-1);
        }
        try (Writer writer = new FileWriter(WORKFLOW_FILE)) {
            final WorkflowWrapper WRAPPER = new WorkflowWrapper(List.of(EXAMPLE));
            String json = gson.toJson(WRAPPER);
            writer.write(json);
            System.out.print("Created workflows.json file");
        } catch (IOException e) {
            System.err.print("Unable to create workflows.json file");
            System.exit(-1);
        }
    }
}
