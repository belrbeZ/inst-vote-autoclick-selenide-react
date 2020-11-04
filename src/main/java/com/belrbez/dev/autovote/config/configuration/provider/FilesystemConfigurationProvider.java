/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.configuration.provider.AbstractConfigurationProvider
 *  com.belrbez.dev.autovote.config.configuration.provider.FilesystemConfigurationProvider
 *  com.belrbez.dev.autovote.util.PathFileResolverService
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.belrbez.dev.autovote.config.configuration.provider;

import com.belrbez.dev.autovote.util.PathFileResolverService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/*
 * Exception performing whole class analysis ignored.
 */
public class FilesystemConfigurationProvider
        extends AbstractConfigurationProvider {
    private static final Log LOG = LogFactory.getLog(FilesystemConfigurationProvider.class);
    private static final String CONVENTION = "autovote-dev.properties";
    private static final String PROPERTY_DIRECT = "dev.autovote.configuration";

    static {
    }

    public FilesystemConfigurationProvider() {
    }

    private static Map<String, String> loadIfPossible(File file) {
        Properties properties;
        properties = new Properties();
        try {
            try (FileInputStream input = new FileInputStream(file)) {
                properties.load(input);
            }
        } catch (Exception exception) {
            LOG.warn("cannot read configuration properties from file", exception);
        }
        return FilesystemConfigurationProvider.map(properties);
    }

    private static Map<String, String> addResourceIfPresent() {
        InputStream inputStream = FilesystemConfigurationProvider.class.getResourceAsStream("/autovote-dev.properties");
        if (inputStream != null) {
            Properties properties = new Properties();
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                LOG.warn("cannot read configuration properties from resources", e);
            }
            return FilesystemConfigurationProvider.map(properties);
        }
        return new HashMap<String, String>();
    }

    private static List<File> effectiveFiles() {
        ArrayList<File> result = new ArrayList<File>();
        Optional direct = FilesystemConfigurationProvider.directFile();
        if (direct.isPresent()) {
            result.add((File) direct.get());
        } else {
            result.addAll(FilesystemConfigurationProvider.availableConventionFiles());
        }
        return result;
    }

    private static Optional<File> directFile() {
        String name = System.getProperty("dev.autovote.configuration");
        return name != null ? Optional.of(Paths.get(PathFileResolverService.getAbsolutePath(name)).toFile()) : Optional.empty();
    }

    private static List<File> availableConventionFiles() {
        ArrayList<File> result = new ArrayList<File>();
        FilesystemConfigurationProvider.conventionFileAtHome().ifPresent(result::add);
        result.addAll(FilesystemConfigurationProvider.conventionFilesByWorkingDirectory());
        return result;
    }

    private static Optional<File> conventionFileAtHome() {
        return FilesystemConfigurationProvider.conventionFile(Paths.get(FilesystemConfigurationProvider.systemProperty("user.home"), new String[0]));
    }

    private static List<File> conventionFilesByWorkingDirectory() {
        ArrayList<File> result = new ArrayList<File>();
        FilesystemConfigurationProvider.locateConventionFilesRecursively(Paths.get(FilesystemConfigurationProvider.systemProperty("user.dir"), new String[0]), result);
        return result;
    }

    private static void locateConventionFilesRecursively(Path path, List<File> files) {
        Path parent = path.getParent();
        if (parent != null) {
            FilesystemConfigurationProvider.locateConventionFilesRecursively(parent, files);
        }
        FilesystemConfigurationProvider.conventionFile(path).ifPresent(files::add);
    }

    private static Optional<File> conventionFile(Path path) {
        File file = path.resolve("autovote-dev.properties").toFile();
        return file.exists() ? Optional.of(file) : Optional.empty();
    }

    public Map<String, String> get() {
        HashMap<String, String> result = new HashMap<String, String>(FilesystemConfigurationProvider.addResourceIfPresent());
        for (File file : FilesystemConfigurationProvider.effectiveFiles()) {
            result.putAll(FilesystemConfigurationProvider.loadIfPossible(file));
        }
        return result;
    }
}

