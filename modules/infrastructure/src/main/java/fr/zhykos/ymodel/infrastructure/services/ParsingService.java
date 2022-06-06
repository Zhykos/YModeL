package fr.zhykos.ymodel.infrastructure.services;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public Returns<YmlMetaModel, IOException> parse(final File yamlFile) {
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            final YmlFile file = mapper.readValue(yamlFile, YmlFile.class);
            return Returns.resolve(file.getMetamodel());
        } catch (final IOException e) {
            return Returns.reject(e);
        }
    }

}
