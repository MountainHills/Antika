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

import java.util.List;
import java.util.Set;

public interface FileHandler {

    String CURRENT_DIRECTORY = System.getProperty("user.dir");

    /**
     * An example workflow to be written to the workflow file. The notepad.exe may not work with other OS aside from Windows.
     */
    Workflow EXAMPLE = new Workflow("example", List.of("notepad.exe"), List.of("www.google.com"));

    /**
     * Read the workflow file and retrieves the set of available workflow modes
     *
     * @return the set of workflow modes available
     */
    Set<String> getWorkflowModes();

    /**
     * Return the workflow details given a workflow mode.
     *
     * @param mode the selected workflow mode
     * @return the workflow containing the applications and websites to open.
     */
    Workflow getWorkflow(String mode);

    /**
     * Creates a workflow file with the extension of the chosen file handler.
     */
    void createWorkflowFile();
}
