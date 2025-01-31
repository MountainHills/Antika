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

package com.antonbondoc.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum ToolType {
    APP,
    WEB;

    private final static Map<String, ToolType> TYPE_MAP;

    static {
        Map<String, ToolType> temp = new HashMap<>();
        for (ToolType toolType : ToolType.values()) {
            temp.put(toolType.name().toUpperCase(), toolType);
        }
        TYPE_MAP = Collections.unmodifiableMap(temp);
    }

    public static ToolType get(String name) {
        if (name == null) {
            throw new NullPointerException("Tool type name could not be null");
        }
        String key = name.trim().toUpperCase();
        if (!TYPE_MAP.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Invalid '%s' tool type", key));
        }
        return TYPE_MAP.get(key);
    }
}
