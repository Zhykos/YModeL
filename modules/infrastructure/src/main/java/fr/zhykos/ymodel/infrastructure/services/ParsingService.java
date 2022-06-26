/*
 * Copyright 2022 Thomas "Zhykos" Cicognani.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.zhykos.ymodel.infrastructure.services;

import java.io.File;
import java.nio.file.Files;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.infrastructure.models.yml.YmlFile;
import fr.zhykos.ymodel.infrastructure.models.yml.YmlMetaModel;

/**
 * Service which parse declaration YML metamodel file
 */
public final class ParsingService {

    /**
     * Parse a declaration metamodel file
     *
     * @param yamlFile File to parse
     * @return Returns with: resolved metamodel ; Exception if an error occurred
     *         while reading the file
     */
    public Returns<YmlMetaModel, SyntaxException> parse(final File yamlFile) {
        try {
            final String yaml = Files.readString(yamlFile.toPath());
            return parse(yaml, yamlFile);
        } catch (final Exception e) {
            return Returns.reject(new SyntaxException(e));
        }
    }

    /**
     * Parse a declaration metamodel
     *
     * @param yaml     Content to parse
     * @param yamlFile File to parse
     * @return Returns with: resolved metamodel ; Exception if an error occurred
     *         while reading the file
     */
    public Returns<YmlMetaModel, SyntaxException> parse(final String yaml, final File yamlFile) {
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            final YmlFile file = mapper.readValue(yaml, YmlFile.class);
            final YmlMetaModel metamodel = file.getMetamodel();
            metamodel.setOriginFile(yamlFile);
            return Returns.resolve(metamodel);
        } catch (final UnrecognizedPropertyException e) {
            final String message = "Unrecognized property '%s' line %d and column %d from file '%s'."
                    .formatted(e.getPropertyName(), e.getLocation().getLineNr(), e.getLocation().getColumnNr(),
                            yamlFile.getName());
            return Returns.reject(new SyntaxException(message));
        } catch (final Exception e) {
            return Returns.reject(new SyntaxException(e));
        }
    }

    /**
     * Syntax exception thrown when a syntax error is detected in a YML file
     */
    public static final class SyntaxException extends Exception {

        /**
         * Constructor
         *
         * @param cause Cause of the exception
         */
        private SyntaxException(final Throwable cause) {
            super(cause);
        }

        /**
         * Constructor
         *
         * @param message Message of the exception
         */
        private SyntaxException(final String message) {
            super(message);
        }

    }

}
