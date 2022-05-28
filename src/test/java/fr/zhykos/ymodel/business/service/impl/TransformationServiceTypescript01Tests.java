package fr.zhykos.ymodel.business.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.business.model.yml.YmlClass;
import fr.zhykos.ymodel.business.model.yml.YmlMetaModel;
import fr.zhykos.ymodel.business.model.yml.YmlMethod;
import fr.zhykos.ymodel.business.model.yml.YmlMethodParameter;
import fr.zhykos.ymodel.business.service.ETargetLanguage;
import fr.zhykos.ymodel.business.service.Returns;

class TransformationServiceTypescript01Tests {

    @Test
    void transformIntoTypescript() throws IOException {
        final YmlMetaModel metaModel = createMetaModel();

        final List<String> transformations = new TransformationService().transform(metaModel,
                ETargetLanguage.TYPESCRIPT).stream().map(Returns::then).toList();
        Assertions.assertEquals(2, transformations.size());

        final String expectedTypescript01 = Files
                .readString(Path.of("src/test/resources/expected-typescript/Class01.ts"));
        final String expectedTypescript02 = Files
                .readString(Path.of("src/test/resources/expected-typescript/Class02.ts"));

        final String transformation01 = transformations.get(0);
        final String transformation02 = transformations.get(1);
        Assertions.assertNotEquals("", transformation01);
        Assertions.assertNotEquals("", transformation02);
        Assertions.assertEquals(expectedTypescript01, transformation01);
        Assertions.assertEquals(expectedTypescript02, transformation02);
    }

    private static YmlMetaModel createMetaModel() {
        final YmlClass class01 = new YmlClass();
        class01.setName("Class01");

        final YmlMethod method01 = new YmlMethod();
        method01.setName("method01");
        method01.setReturns("void");

        final YmlMethodParameter param01 = new YmlMethodParameter();
        param01.setName("param01");
        param01.setType("int");

        final YmlMethodParameter param02 = new YmlMethodParameter();
        param02.setName("param02");
        param02.setType("string");

        final YmlMethod method02 = new YmlMethod();
        method02.setName("method02");
        method02.setReturns("float");

        final YmlClass class02 = new YmlClass();
        class02.setName("Class02");
        // class02.setInheritsClass(class01);

        final YmlMethod method03 = new YmlMethod();
        method03.setName("method03");
        method03.setReturns("char");

        final YmlMethodParameter param03 = new YmlMethodParameter();
        param03.setName("param03");
        param03.setType("int");

        final YmlMetaModel metaModel = new YmlMetaModel();
        metaModel.getClasses().add(class01);
        metaModel.getClasses().add(class02);
        class01.getMethods().add(method01);
        class01.getMethods().add(method02);
        class02.getMethods().add(method03);
        method01.getParameters().add(param01);
        method01.getParameters().add(param02);
        method03.getParameters().add(param03);
        return metaModel;
    }

}
