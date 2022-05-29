package fr.zhykos.ymodel.business.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.infra.Returns;
import fr.zhykos.ymodel.infra.models.yml.YmlClass;
import fr.zhykos.ymodel.infra.models.yml.YmlField;
import fr.zhykos.ymodel.infra.models.yml.YmlMetaModel;

class GenerationTypescriptService02Tests {

    @Test
    @DisplayName("Transform a metamodel into Typescript then verify if fields are generated")
    void transformIntoTypescript() throws IOException {
        final YmlMetaModel metaModel = createMetaModel();

        final List<String> transformations = new GenerationTypescriptService().generate(metaModel).stream()
                .map(Returns::then).toList();
        Assertions.assertEquals(1, transformations.size());

        final String expectedTypescript = Files
                .readString(Path.of("src/test/resources/expected-typescript/Class03.ts"));

        final String transformation = transformations.get(0);
        Assertions.assertNotEquals("", transformation);
        Assertions.assertEquals(expectedTypescript, transformation);
    }

    private static YmlMetaModel createMetaModel() {
        final YmlClass classs = new YmlClass();
        classs.setName("Class03");

        final YmlField field01 = new YmlField("field01", "int");
        final YmlField field02 = new YmlField("field02", "string");

        final YmlMetaModel metaModel = new YmlMetaModel();
        metaModel.getClasses().add(classs);
        classs.getFields().add(field01);
        classs.getFields().add(field02);
        return metaModel;
    }

}
