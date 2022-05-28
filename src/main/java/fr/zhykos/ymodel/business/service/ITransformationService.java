package fr.zhykos.ymodel.business.service;

import java.io.IOException;
import java.util.List;

import fr.zhykos.ymodel.business.model.yml.YmlMetaModel;

public interface ITransformationService {

    List<Returns<String, IOException>> transform(YmlMetaModel metaModel, ETargetLanguage targetLanguage);

}
