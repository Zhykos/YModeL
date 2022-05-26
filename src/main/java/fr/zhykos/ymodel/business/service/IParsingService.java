package fr.zhykos.ymodel.business.service;

import java.io.File;

import fr.zhykos.ymodel.business.model.MetaModel;

public interface IParsingService {

    MetaModel parse(File yamlFile) throws ParsingException;

}
