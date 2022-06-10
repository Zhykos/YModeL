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
            return parse(yaml, yamlFile.getName());
        } catch (final Exception e) {
            return Returns.reject(new SyntaxException(e));
        }
    }

    /**
     * Parse a declaration metamodel
     *
     * @param yaml     Content to parse
     * @param filename Name of the file to parse (used for error messages)
     * @return Returns with: resolved metamodel ; Exception if an error occurred
     *         while reading the file
     */
    public Returns<YmlMetaModel, SyntaxException> parse(final String yaml, final String filename) {
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            final YmlFile file = mapper.readValue(yaml, YmlFile.class);
            return Returns.resolve(file.getMetamodel());
        } catch (final UnrecognizedPropertyException e) {
            final String message = "Unrecognized property '%s' line %d and column %d from file '%s'."
                    .formatted(e.getPropertyName(), e.getLocation().getLineNr(), e.getLocation().getColumnNr(),
                            filename);
            return Returns.reject(new SyntaxException(message));
        } catch (final Exception e) {
            return Returns.reject(new SyntaxException(e));
        }
    }

}
