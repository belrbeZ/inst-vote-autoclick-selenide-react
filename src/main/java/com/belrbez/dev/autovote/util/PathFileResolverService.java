/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.util.PathFileResolverService
 *  org.apache.commons.io.IOUtils
 */
package com.belrbez.dev.autovote.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PathFileResolverService {
    public static String getAbsolutePath(String path) {
        if (path == null) {
            return new File(".").getAbsolutePath();
        }
        String absolutePath = null;
        try {
            if (path.contains(":/") || path.contains(":\\")) {
                absolutePath = Paths.get(path).toAbsolutePath().toFile().getAbsolutePath();
            } else if (path.startsWith("config/")) {
                String pathToAppDirectory = new File(".").getCanonicalPath();
                absolutePath = Paths.get(String.format("%s/%s", pathToAppDirectory, path)).toAbsolutePath().toFile().getAbsolutePath();
            } else {
                absolutePath = PathFileResolverService.class.getClassLoader().getResource(path).getPath();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return absolutePath;
    }

    public static String getFileValue(String pathToData) {
        String json;
        block29:
        {
            if (pathToData == null) {
                return null;
            }
            json = null;
            try {
                if (pathToData.contains(":/") || pathToData.contains(":\\")) {
                    json = new String(Files.readAllBytes(Paths.get(pathToData)), StandardCharsets.UTF_8);
                    break block29;
                }
                if (pathToData.startsWith("config/")) {
                    String pathToAppDirectory = new File(".").getCanonicalPath();
                    try (FileInputStream inputStream = new FileInputStream(String.format("%s/%s", pathToAppDirectory, pathToData))) {
                        json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                        break block29;
                    }
                }
                try (InputStream inputStream = PathFileResolverService.class.getClassLoader().getResourceAsStream(pathToData)) {
                    json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return json;
    }
}

