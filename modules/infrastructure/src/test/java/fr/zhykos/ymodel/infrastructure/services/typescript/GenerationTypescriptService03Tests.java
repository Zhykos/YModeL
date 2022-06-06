package fr.zhykos.ymodel.infrastructure.services.typescript;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.business.services.typescript.GenerationTypescriptService;
import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.infrastructure.services.GenerationService;

class GenerationTypescriptService03Tests {

    @Test
    @DisplayName("Generate a metamodel into Typescript then verify if fields and methods are generated")
    void generate() throws IOException {
        final EClass eClass = createEClass();

        final Returns<String, IOException> generation = GenerationService.generate(eClass,
                new GenerationTypescriptService());

        final String expectedTypescript = Files
                .readString(Path.of("src/test/resources/expected-typescript/Class04.ts"));

        final String typescript = generation.then();
        Assertions.assertNotEquals("", typescript);
        Assertions.assertEquals(expectedTypescript, typescript);
    }

    private static EClass createEClass() {
        final EClass classs = EcoreFactory.eINSTANCE.createEClass();
        classs.setName("Class04");

        final EAttribute field = EcoreFactory.eINSTANCE.createEAttribute();
        field.setName("field01");

        final EDataType type01 = EcoreFactory.eINSTANCE.createEDataType();
        type01.setName("int");

        final EOperation method = EcoreFactory.eINSTANCE.createEOperation();
        method.setName("method01");

        final EDataType type02 = EcoreFactory.eINSTANCE.createEDataType();
        type02.setName("string");

        final EParameter methodParameter = EcoreFactory.eINSTANCE.createEParameter();
        methodParameter.setName("param01");

        final EDataType type03 = EcoreFactory.eINSTANCE.createEDataType();
        type03.setName("float");

        classs.getEStructuralFeatures().add(field);
        classs.getEOperations().add(method);
        method.getEParameters().add(methodParameter);
        field.setEType(type01);
        method.setEType(type02);
        methodParameter.setEType(type03);
        return classs;
    }

}
