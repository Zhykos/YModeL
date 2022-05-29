package fr.zhykos.ymodel.infra.services;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import fr.zhykos.ymodel.infra.Returns;
import fr.zhykos.ymodel.infra.models.yml.YmlFile;
import fr.zhykos.ymodel.infra.models.yml.YmlMetaModel;

public class ParsingService {

    public Returns<YmlMetaModel, IOException> parse(final File yamlFile) {
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            final YmlFile file = mapper.readValue(yamlFile, YmlFile.class);
            return Returns.resolve(file.getMetamodel());
        } catch (IOException e) {
            return Returns.reject(e);
        }
    }

}
