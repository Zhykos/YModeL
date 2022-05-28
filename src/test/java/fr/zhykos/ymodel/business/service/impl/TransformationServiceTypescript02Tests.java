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
import fr.zhykos.ymodel.business.service.ETargetLanguage;
import fr.zhykos.ymodel.business.service.Returns;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class TransformationServiceTypescript02Tests {

    @Test
    @DisplayName("Transform a metamodel into Typescript then verify if fields are generated")
    void transformIntoTypescript() throws IOException {
        final MetaModel metaModel = createMetaModel();

        final List<String> transformations = new TransformationService().transform(metaModel,
                ETargetLanguage.TYPESCRIPT).stream().map(Returns::then).toList();
        Assertions.assertEquals(1, transformations.size());

        final String expectedTypescript = Files
                .readString(Path.of("src/test/resources/expected-typescript/Class03.ts"));

        final String transformation = transformations.get(0);
        Assertions.assertNotEquals("", transformation);
        Assertions.assertEquals(expectedTypescript, transformation);
    }

    private static MetaModel createMetaModel() {
        final Classs classs = new Classs();
        classs.setName("Class03");

        final Field field01 = new Field("field01", "int");
        final Field field02 = new Field("field02", "string");

        final MetaModel metaModel = new MetaModel();
        metaModel.getClasses().add(classs);
        classs.getFields().add(field01);
        classs.getFields().add(field02);
        return metaModel;
    }

}
