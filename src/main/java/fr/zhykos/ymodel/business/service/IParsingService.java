package fr.zhykos.ymodel.business.service;

import java.io.File;

import fr.zhykos.ymodel.business.model.yml.YmlMetaModel;

public interface IParsingService {

    YmlMetaModel parse(File yamlFile) throws ParsingException;

}
