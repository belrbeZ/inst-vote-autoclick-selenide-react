/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.belrbez.dev.autovote.config.configuration.Property
 *  com.belrbez.dev.autovote.service.model.parser.DataModel
 *  com.belrbez.dev.autovote.service.model.parser.JsonDataHandler
 *  com.belrbez.dev.autovote.util.PathFileResolverService
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.apache.commons.io.IOUtils
 */
package com.belrbez.dev.autovote.service.model.parser;

import com.belrbez.dev.autovote.config.configuration.Property;
import com.belrbez.dev.autovote.util.PathFileResolverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JsonDataHandler {
    static JsonDataHandler instance = null;
    private DataModel data = null;
    private String json;
    private Property pathToData = Property.optional("dev.autovote.data");

    private JsonDataHandler() {
        this.initData();
    }

    public static synchronized JsonDataHandler getInstance() {
        if (instance == null) {
            instance = new JsonDataHandler();
        }
        return instance;
    }

    private void initData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (this.pathToData.presented()) {
                this.json = PathFileResolverService.getFileValue(this.pathToData.value());
            } else {
                try (InputStream inputStream = JsonDataHandler.class.getClassLoader().getResourceAsStream("data.json")) {
                    this.json = IOUtils.toString(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
                }
            }
            this.data = mapper.readValue(this.json, DataModel.class);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public DataModel getData() {
        return this.data;
    }

    public String getJson() {
        return this.json;
    }

    public String getDataFilePath() {
        return this.pathToData.valueNullable();
    }

    public Property getPathToData() {
        return this.pathToData;
    }
}

