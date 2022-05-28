package fr.zhykos.ymodel.business.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.business.model.Classs;
import fr.zhykos.ymodel.business.model.Field;
import fr.zhykos.ymodel.business.model.MetaModel;
import fr.zhykos.ymodel.business.model.Method;
import fr.zhykos.ymodel.business.model.MethodParameter;
import fr.zhykos.ymodel.business.service.ETargetLanguage;
import fr.zhykos.ymodel.business.service.Returns;

class TransformationServiceTypescript04Tests {

    @Test
    @DisplayName("Transform a metamodel into Typescript then verify external classes references")
    void transformIntoTypescript() throws IOException {
        final MetaModel metaModel = createMetaModel();

        final List<String> transformations = new TransformationService().transform(metaModel,
                ETargetLanguage.TYPESCRIPT).stream().map(Returns::then).toList();
        Assertions.assertEquals(1, transformations.size());

        final String expectedTypescript = Files
                .readString(Path.of("src/test/resources/expected-typescript/Class05.ts"));

        final String transformation = transformations.get(0);
        Assertions.assertNotEquals("", transformation);
        Assertions.assertEquals(expectedTypescript, transformation);
    }

    private static MetaModel createMetaModel() {
        final Classs classs = new Classs();
        classs.setName("Class05");

        final Field field = new Field("field01", "$Class02");

        final Method method = new Method();
        method.setName("method01");
        method.setReturns("$Class01");

        final MethodParameter methodParameter = new MethodParameter();
        methodParameter.setName("param01");
        methodParameter.setType("$Class03");

        final MetaModel metaModel = new MetaModel();
        metaModel.getClasses().add(classs);
        classs.getFields().add(field);
        classs.getMethods().add(method);
        method.getParams().add(methodParameter);
        return metaModel;
    }

}
