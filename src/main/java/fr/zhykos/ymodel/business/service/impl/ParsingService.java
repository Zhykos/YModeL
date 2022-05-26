package fr.zhykos.ymodel.business.service.impl;

import java.io.File;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import fr.zhykos.ymodel.business.model.Classs;
import fr.zhykos.ymodel.business.model.MetaModel;
import fr.zhykos.ymodel.business.service.IParsingService;
import fr.zhykos.ymodel.business.service.ParsingException;
import fr.zhykos.ymodel.infra.model.YmlFile;

public class ParsingService implements IParsingService {

    @Override
    public MetaModel parse(final File yamlFile) throws ParsingException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            final YmlFile file = mapper.readValue(yamlFile, YmlFile.class);
            final MetaModel result = file.getMetamodel();
            consolidateInheritance(result);
            return result;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private void consolidateInheritance(final MetaModel metaModel) {
        final Map<String, Classs> classesIdentityMap = metaModel.getClasses().stream()
                .collect(Collectors.toMap(Classs::getName, Function.identity()));
        metaModel.getClasses().forEach(classs -> {
            final String inherits = classs.getInherits();
            if (inherits != null) {
                classs.setInheritsClass(classesIdentityMap.get(inherits));
            }
        });
    }

}
