package fr.zhykos.ymodel.business.service.impl;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import fr.zhykos.ymodel.business.model.yml.YmlMetaModel;
import fr.zhykos.ymodel.business.service.IParsingService;
import fr.zhykos.ymodel.business.service.ParsingException;
import fr.zhykos.ymodel.infra.model.YmlFile;

public class ParsingService implements IParsingService {

    @Override
    public YmlMetaModel parse(final File yamlFile) throws ParsingException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            final YmlFile file = mapper.readValue(yamlFile, YmlFile.class);
            return file.getMetamodel();
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

}
