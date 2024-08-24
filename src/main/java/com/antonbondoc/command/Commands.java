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

import org.apache.commons.cli.Options;

import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * The commands for the Antika application.
 */
public class Commands {
    public static final NavigableMap<String, Type> TYPES;

    // TODO: Improvement can be done here to make the main class cleaner
    static {
        List<Type> typeList = List.of(
                HelpCommandHandler.TYPE,
                ListCommandHandler.TYPE,
                FlowCommandHandler.TYPE
        );

        TreeMap<String, Type> typesMap = new TreeMap<>();
        for (Type type : typeList) {
            typesMap.put(type.name(), type);
        }

        TYPES = Collections.unmodifiableNavigableMap(typesMap);
    }

    /**
     * An object which describes a type of command handler. This includes information like its name and help text
     */
    // TODO: This interface could be improved more
    public interface Type {
        String name();

        String description();

        Handler createHandler();
    }

    /**
     * Command handler objects are instantiated with specific arguments to execute commands.
     */
    public interface Handler {
        void run(Options options, String[] args);
    }
}
