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
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class YamlHandler implements FileHandler {

    private final File WORKFLOW_FILE = Paths.get(CURRENT_DIRECTORY, "workflows.yaml").toFile();

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    @Override
    public Workflow getWorkflow(String mode) {
        Workflow workflow = null;
        try {
            WorkflowWrapper wrapper = mapper.readValue(WORKFLOW_FILE, WorkflowWrapper.class);
            List<Workflow> workflows = wrapper.getWorkflows();

            workflow = workflows.stream()
                    .filter(w -> w.getMode().equalsIgnoreCase(mode))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Workflow does not exist"));

        } catch (IOException | IllegalArgumentException ex) {
            System.err.printf("'%s' workflow does not exist", mode);
            System.exit(-1);
        }
        return workflow;
    }

    @Override
    public void createWorkflowFile() {
        if (WORKFLOW_FILE.exists()) {
            System.err.println("The workflow file already exists");
            System.exit(-1);
        }

        // TODO: Implement YAML file creation
    }
}
