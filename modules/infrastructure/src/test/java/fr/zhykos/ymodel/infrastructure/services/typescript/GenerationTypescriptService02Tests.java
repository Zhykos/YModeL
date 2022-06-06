package fr.zhykos.ymodel.infrastructure.services.typescript;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.business.services.typescript.GenerationTypescriptService;
import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.infrastructure.services.GenerationException;
import fr.zhykos.ymodel.infrastructure.services.GenerationService;

class GenerationTypescriptService02Tests {

    @Test
    @DisplayName("Generate a metamodel into Typescript then verify if fields are generated")
    void generate() throws IOException {
        final EClass eClass = createEClass();

        final Returns<String, GenerationException> generation = GenerationService.generate(eClass,
                new GenerationTypescriptService());

        final String expectedTypescript = Files
                .readString(Path.of("src/test/resources/expected-typescript/Class03.ts"));

        final String typescript = generation.then();
        Assertions.assertNotEquals("", typescript);
        Assertions.assertEquals(expectedTypescript, typescript);
    }

    private static EClass createEClass() {
        final EClass classs = EcoreFactory.eINSTANCE.createEClass();
        classs.setName("Class03");

        final EAttribute field01 = EcoreFactory.eINSTANCE.createEAttribute();
        field01.setName("field01");

        final EDataType type01 = EcoreFactory.eINSTANCE.createEDataType();
        type01.setName("int");

        final EAttribute field02 = EcoreFactory.eINSTANCE.createEAttribute();
        field02.setName("field02");

        final EDataType type02 = EcoreFactory.eINSTANCE.createEDataType();
        type02.setName("string");

        classs.getEStructuralFeatures().add(field01);
        classs.getEStructuralFeatures().add(field02);
        field01.setEType(type01);
        field02.setEType(type02);
        return classs;
    }

}
