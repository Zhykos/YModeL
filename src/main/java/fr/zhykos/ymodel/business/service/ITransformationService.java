package fr.zhykos.ymodel.business.service;

import java.io.IOException;
import java.util.List;

import fr.zhykos.ymodel.business.model.MetaModel;

public interface ITransformationService {

    List<Returns<String, IOException>> transform(MetaModel metaModel, ETargetLanguage targetLanguage);

}
