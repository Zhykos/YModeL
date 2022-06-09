package fr.zhykos.ymodel.infrastructure.services.typescript;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.business.services.typescript.GenerationTypescriptService;
import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.infrastructure.services.GenerationException;
import fr.zhykos.ymodel.infrastructure.services.GenerationService;

class GenerationTypescriptService04Tests {

    @Test
    @DisplayName("Generate a metamodel into Typescript then verify external classes references")
    void generate() throws IOException, GenerationException {
        final EClass eClass = createEClass();

        final Returns<String, GenerationException> generation = GenerationService.generate(eClass,
                new GenerationTypescriptService());

        final String expectedTypescript = Files
                .readString(Path.of("src/test/resources/expected-typescript/Class05.ts"));

        final String typescript = generation.then();
        Assertions.assertNotEquals("", typescript);
        Assertions.assertEquals(expectedTypescript, typescript);
    }

    private static EClass createEClass() {
        final EClass classs = EcoreFactory.eINSTANCE.createEClass();
        classs.setName("Class05");

        final EAttribute field = EcoreFactory.eINSTANCE.createEAttribute();
        field.setName("field01");

        final EClass type01 = EcoreFactory.eINSTANCE.createEClass();
        type01.setName("Class02");

        final EOperation method = EcoreFactory.eINSTANCE.createEOperation();
        method.setName("method01");

        final EClass type02 = EcoreFactory.eINSTANCE.createEClass();
        type02.setName("Class01");

        final EParameter methodParameter = EcoreFactory.eINSTANCE.createEParameter();
        methodParameter.setName("param01");

        final EClass type03 = EcoreFactory.eINSTANCE.createEClass();
        type03.setName("Class03");

        classs.getEStructuralFeatures().add(field);
        classs.getEOperations().add(method);
        method.getEParameters().add(methodParameter);
        field.setEType(type01);
        method.setEType(type02);
        methodParameter.setEType(type03);
        return classs;
    }

}
