package fr.zhykos.ymodel.infrastructure.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.infrastructure.models.yml.YmlMetaModel;

class TransformationService03Tests {

    @Test
    @DisplayName("Transform a YML metamodel into an EMF metamodel and check unreferenced types")
    void transform() throws IOException {
        final File yamlFile = new File("src/test/resources/metamodel03.yml");
        final Returns<YmlMetaModel, IOException> parseReturns = new ParsingService().parse(yamlFile);
        final YmlMetaModel ymlMetaModel = parseReturns.then();

        final Returns<List<EClass>, SemanticListException> eClasses = new TransformationService()
                .transform(ymlMetaModel);
        Assertions.assertThrows(SemanticListException.class, () -> eClasses.catchh());

        try {
            eClasses.catchh();
        } catch (SemanticListException e) {
            Assertions.assertEquals(
                    "Unknown class reference for inheritance: $Class01\nUnknown class reference '$Class01' in element 'field01'.\nUnknown class reference '$Class01' in element 'method01'.\nUnknown class reference '$Class01' in element 'param01'.",
                    e.getMessage());
        }
    }

}
