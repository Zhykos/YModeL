package fr.zhykos.ymodel.infrastructure.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.infrastructure.models.yml.YmlMetaModel;

class TransformationService02Tests {

    @Test
    @DisplayName("Transform a YML metamodel into an EMF metamodel and check referenced types")
    void transform() throws SemanticListException, IOException {
        final File yamlFile = new File("src/test/resources/metamodel02.yml");
        final Returns<YmlMetaModel, IOException> parseReturns = new ParsingService().parse(yamlFile);
        final YmlMetaModel ymlMetaModel = parseReturns.then();

        final Returns<List<EClass>, SemanticListException> eClassesReturns = new TransformationService()
                .transform(ymlMetaModel);
        final List<EClass> eClasses = eClassesReturns.then();
        Assertions.assertEquals(2, eClasses.size());

        final EClass class01 = eClasses.get(0);
        Assertions.assertEquals(0, class01.getESuperTypes().size());
        Assertions.assertEquals("Class01", class01.getName());
        Assertions.assertEquals(0, class01.getEOperations().size());
        Assertions.assertEquals(0, class01.getEAttributes().size());

        final EClass class02 = eClasses.get(1);
        Assertions.assertEquals(0, class02.getESuperTypes().size());
        Assertions.assertEquals("Class02", class02.getName());
        Assertions.assertEquals(1, class02.getEOperations().size());

        final EOperation method01 = class02.getEOperations().get(0);
        Assertions.assertEquals("method01", method01.getName());
        Assertions.assertEquals(class01, method01.getEType());
        Assertions.assertEquals(1, method01.getEParameters().size());

        final EParameter param03 = method01.getEParameters().get(0);
        Assertions.assertEquals("param01", param03.getName());
        Assertions.assertEquals(class01, param03.getEType());
    }

}
