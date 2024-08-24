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

package com.antonbondoc.command;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * Implements the root help command.
 */
public class HelpCommandHandler implements Commands.Handler {
    public static final Commands.Type TYPE = new HelpCommandType();

    public static class HelpCommandType implements Commands.Type {

        @Override
        public String name() {
            return "help";
        }

        @Override
        public String description() {
            return "Display this help message";
        }

        @Override
        public Commands.Handler createHandler() {
            return new HelpCommandHandler();
        }
    }

    @Override
    public void run(Options options, String[] args) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setOptPrefix("");
        formatter.printHelp("antika [command]", options);
        System.exit(0);
    }
}
