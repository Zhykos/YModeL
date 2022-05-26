package fr.zhykos.ymodel.business.service;

import java.util.List;

import fr.zhykos.ymodel.business.model.MetaModel;

public interface ITransformationService {

    List<String> transform(MetaModel metaModel, ETargetLanguage targetLanguage);

}
