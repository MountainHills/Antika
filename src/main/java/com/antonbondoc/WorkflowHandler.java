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


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler functions for workflow requests.
 */
public class WorkflowHandler {

    /**
     * Opens all the websites and applications of the given Antika workflow.
     *
     * @param workflow the chosen workflow
     * @param tools    the list of available tools
     */
    public void openTools(String workflow, List<Tool> tools) {
        List<Tool> filtered = tools.stream()
                .filter(t -> t.mode().equals(workflow))
                .toList();

        List<String> appPaths = new ArrayList<>();
        List<String> webUrls = new ArrayList<>();
        for (Tool tool : filtered) {
            switch (tool.toolType()) {
                case APP -> appPaths.add(tool.path());
                case WEB -> webUrls.add(tool.path());
            }
        }

        openDesktopApp(appPaths);
        openWebApp(webUrls);
    }

    /**
     * Opens the applications from the given list of absolute paths.
     *
     * @param paths the absolute paths associated to the given workflow
     */
    private void openDesktopApp(List<String> paths) {

        for (String app : paths) {
            try {
                ProcessBuilder process = new ProcessBuilder(app);
                process.start();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Opens the websites from the given list using the default browser.
     *
     * @param urls the websites associated to the given workflow
     */
    private void openWebApp(List<String> urls) {

        Desktop desktop = Desktop.getDesktop();
        for (String url : urls) {
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException ignored) {
            }
        }
    }

}
