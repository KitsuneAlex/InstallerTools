/*
 * Copyright (c) Forge Development LLC
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package net.minecraftforge.installertools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConsoleTool {
    public static final Gson GSON = new GsonBuilder().create();

    public static void main(String[] args) throws IOException {
        Tasks task = null;
        String valid = Arrays.stream(Tasks.class.getEnumConstants()).map(Enum::name).collect(Collectors.joining(", "));
        List<String> extra = new ArrayList<>();

        for (int x = 0; x < args.length; x++) {
            if ("--task".equals(args[x])) {
                if (x == args.length - 1)
                    error("--task must specify a value, known values: " + valid);
                task = Tasks.valueOf(Tasks.class, args[x + 1].toUpperCase());
                x++;
            } else if (args[x].startsWith("--task=")) {
                task = Tasks.valueOf(Tasks.class, args[x].substring(7));
            } else {
                extra.add(args[x]);
            }
        }

        if (task == null)
            error("Must specify task using --task, known values: " + valid);

        log("Task: " + task.name());
        task.get().process(extra.toArray(new String[extra.size()]));
    }

    public static void error(String message) {
        log(message);
        throw new RuntimeException(message);
    }

    public static void log(String message) {
        System.out.println(message);
    }
}
