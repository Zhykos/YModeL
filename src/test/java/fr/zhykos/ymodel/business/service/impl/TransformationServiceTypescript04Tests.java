package fr.zhykos.ymodel.business.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.business.model.yml.YmlClass;
import fr.zhykos.ymodel.business.model.yml.YmlField;
import fr.zhykos.ymodel.business.model.yml.YmlMetaModel;
import fr.zhykos.ymodel.business.model.yml.YmlMethod;
import fr.zhykos.ymodel.business.model.yml.YmlMethodParameter;
import fr.zhykos.ymodel.business.service.ETargetLanguage;
import fr.zhykos.ymodel.business.service.Returns;

class TransformationServiceTypescript04Tests {

    @Test
    @DisplayName("Transform a metamodel into Typescript then verify external classes references")
    void transformIntoTypescript() throws IOException {
        final YmlMetaModel metaModel = createMetaModel();

        final List<String> transformations = new TransformationService().transform(metaModel,
                ETargetLanguage.TYPESCRIPT).stream().map(Returns::then).toList();
        Assertions.assertEquals(1, transformations.size());

        final String expectedTypescript = Files
                .readString(Path.of("src/test/resources/expected-typescript/Class05.ts"));

        final String transformation = transformations.get(0);
        Assertions.assertNotEquals("", transformation);
        Assertions.assertEquals(expectedTypescript, transformation);
    }

    private static YmlMetaModel createMetaModel() {
        final YmlClass classs = new YmlClass();
        classs.setName("Class05");

        final YmlField field = new YmlField("field01", "$Class02");

        final YmlMethod method = new YmlMethod();
        method.setName("method01");
        method.setReturns("$Class01");

        final YmlMethodParameter methodParameter = new YmlMethodParameter();
        methodParameter.setName("param01");
        methodParameter.setType("$Class03");

        final YmlMetaModel metaModel = new YmlMetaModel();
        metaModel.getClasses().add(classs);
        classs.getFields().add(field);
        classs.getMethods().add(method);
        method.getParameters().add(methodParameter);
        return metaModel;
    }

}
