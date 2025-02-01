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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class YamlHandler implements FileHandler {

    private final File WORKFLOW_FILE = Paths.get(CURRENT_DIRECTORY, "workflows.yaml").toFile();

    private final YAMLFactory yamlFactory = new YAMLFactory()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);

    private final ObjectMapper mapper = new ObjectMapper(yamlFactory)
            .enable(SerializationFeature.INDENT_OUTPUT);

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
        WorkflowWrapper wrapper = null;
        try {
            if (!WORKFLOW_FILE.exists()) {
                throw new IOException("Workflow file does not exist");
            }
            wrapper = mapper.readValue(WORKFLOW_FILE, WorkflowWrapper.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.print("Unable to read workflow file");
            System.exit(-1);
        }
        return wrapper.getWorkflows();
    }

    @Override
    public void createWorkflowFile() {
        if (WORKFLOW_FILE.exists()) {
            System.err.println("The workflow file already exists");
            System.exit(-1);
        }

        try {
            final WorkflowWrapper WRAPPER = new WorkflowWrapper(List.of(EXAMPLE));
            mapper.writeValue(WORKFLOW_FILE, WRAPPER);
            System.out.print("Created workflows.yaml file");
        } catch (IOException e) {
            System.err.print("Unable to create workflows.yaml file");
            System.exit(-1);
        }
    }
}
