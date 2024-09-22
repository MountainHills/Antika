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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(WorkflowHandler.class);

    /**
     * Opens all the websites and applications of the given Antika workflow.
     *
     * @param workflow the chosen workflow
     * @param tools    the list of available tools
     */
    public void openTools(String workflow, List<Tool> tools) {
        log.debug("Filtering tools depending on workflow");
        List<Tool> filtered = tools.stream()
                .filter(t -> t.mode().equals(workflow))
                .toList();

        log.debug("Collect tool paths on respective lists");
        List<String> appPaths = new ArrayList<>();
        List<String> webUrls = new ArrayList<>();
        for (Tool tool : filtered) {
            switch (tool.toolType()) {
                case APP -> appPaths.add(tool.path());
                case WEB -> webUrls.add(tool.path());
            }
        }

        log.debug("Opening tools for workflow");
        openDesktopApp(appPaths);
        openWebApp(webUrls);
    }

    /**
     * Opens the applications from the given list of absolute paths.
     *
     * @param paths the absolute paths associated to the given workflow
     */
    private void openDesktopApp(List<String> paths) {
        log.debug("Opening desktop apps | paths: {}", paths);

        try {
            for (String app : paths) {
                ProcessBuilder process = new ProcessBuilder(app);
                process.start();
            }
        } catch (Exception e) {
            log.error("Unable to open application | {}", e.getMessage());
        }
    }

    /**
     * Opens the websites from the given list using the default browser.
     *
     * @param urls the websites associated to the given workflow
     */
    private void openWebApp(List<String> urls) {
        log.debug("Opening websites | urls: {}", urls);

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    // Open the URL in the default web browser
                    for (String url : urls) {
                        desktop.browse(new URI(url));
                    }
                } catch (IOException | URISyntaxException e) {
                    log.error("Unable to open website | {}", e.getMessage());
                }
            }
        } else {
            log.error("Desktop is not supported on this platform");
        }
    }
}
