package fr.zhykos.ymodel.business.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.business.model.Classs;
import fr.zhykos.ymodel.business.model.MetaModel;
import fr.zhykos.ymodel.business.model.Method;
import fr.zhykos.ymodel.business.model.MethodParameter;
import fr.zhykos.ymodel.business.service.ETargetLanguage;
import fr.zhykos.ymodel.business.service.Returns;

class TransformationServiceTests {

    @Test
    void transformIntoTypescript() throws IOException {
        final MetaModel metaModel = createMetaModel();

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

    private static MetaModel createMetaModel() {
        final Classs class01 = new Classs();
        class01.setName("Class01");

        final Method method01 = new Method();
        method01.setName("method01");
        method01.setReturns("void");

        final MethodParameter param01 = new MethodParameter();
        param01.setName("param01");
        param01.setType("int");

        final MethodParameter param02 = new MethodParameter();
        param02.setName("param02");
        param02.setType("string");

        final Method method02 = new Method();
        method02.setName("method02");
        method02.setReturns("float");

        final Classs class02 = new Classs();
        class02.setName("Class02");
        class02.setInheritsClass(class01);

        final Method method03 = new Method();
        method03.setName("method03");
        method03.setReturns("char");

        final MethodParameter param03 = new MethodParameter();
        param03.setName("param03");
        param03.setType("int");

        final MetaModel metaModel = new MetaModel();
        metaModel.getClasses().add(class01);
        metaModel.getClasses().add(class02);
        class01.getMethods().add(method01);
        class01.getMethods().add(method02);
        class02.getMethods().add(method03);
        method01.getParams().add(param01);
        method01.getParams().add(param02);
        method03.getParams().add(param03);
        return metaModel;
    }

}
