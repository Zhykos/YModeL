package fr.zhykos.ymodel.infra.services;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import fr.zhykos.ymodel.business.model.yml.YmlMetaModel;
import fr.zhykos.ymodel.infra.model.YmlFile;

public final class ParsingService {

    private ParsingService() {
        // Do nothing
    }

    public static YmlMetaModel parse(final File yamlFile) throws ParsingException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            final YmlFile file = mapper.readValue(yamlFile, YmlFile.class);
            return file.getMetamodel();
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

}
