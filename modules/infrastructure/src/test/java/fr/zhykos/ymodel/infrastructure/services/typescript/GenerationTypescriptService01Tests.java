package fr.zhykos.ymodel.infrastructure.services.typescript;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

class GenerationTypescriptService01Tests {

    @Test
    @DisplayName("Generate a metamodel into Typescript then verify methods")
    void generate() throws IOException {
        final List<EClass> eClasses = createEClasses();

        final List<String> generations = GenerationService.generate(eClasses, new GenerationTypescriptService())
                .stream().map(Returns::then).toList();
        Assertions.assertEquals(2, generations.size());

        final String expectedTypescript01 = Files
                .readString(Path.of("src/test/resources/expected-typescript/Class01.ts"));
        final String expectedTypescript02 = Files
                .readString(Path.of("src/test/resources/expected-typescript/Class02.ts"));

        final String generation01 = generations.get(0);
        final String generation02 = generations.get(1);
        Assertions.assertNotEquals("", generation01);
        Assertions.assertNotEquals("", generation02);
        Assertions.assertEquals(expectedTypescript01, generation01);
        Assertions.assertEquals(expectedTypescript02, generation02);
    }

    private static List<EClass> createEClasses() {
        final EClass class01 = EcoreFactory.eINSTANCE.createEClass();
        class01.setName("Class01");

        final EOperation method01 = EcoreFactory.eINSTANCE.createEOperation();
        method01.setName("method01");

        final EDataType type01 = EcoreFactory.eINSTANCE.createEDataType();
        type01.setName("void");

        final EParameter param01 = EcoreFactory.eINSTANCE.createEParameter();
        param01.setName("param01");

        final EDataType type02 = EcoreFactory.eINSTANCE.createEDataType();
        type02.setName("int");

        final EParameter param02 = EcoreFactory.eINSTANCE.createEParameter();
        param02.setName("param02");

        final EDataType type03 = EcoreFactory.eINSTANCE.createEDataType();
        type03.setName("string");

        final EOperation method02 = EcoreFactory.eINSTANCE.createEOperation();
        method02.setName("method02");

        final EDataType type04 = EcoreFactory.eINSTANCE.createEDataType();
        type04.setName("float");

        final EClass class02 = EcoreFactory.eINSTANCE.createEClass();
        class02.setName("Class02");
        class02.getESuperTypes().add(class01);

        final EOperation method03 = EcoreFactory.eINSTANCE.createEOperation();
        method03.setName("method03");

        final EDataType type05 = EcoreFactory.eINSTANCE.createEDataType();
        type05.setName("char");

        final EParameter param03 = EcoreFactory.eINSTANCE.createEParameter();
        param03.setName("param03");

        final EDataType type06 = EcoreFactory.eINSTANCE.createEDataType();
        type06.setName("int");

        final List<EClass> eClasses = new ArrayList<>();
        eClasses.add(class01);
        eClasses.add(class02);
        class01.getEOperations().add(method01);
        class01.getEOperations().add(method02);
        class02.getEOperations().add(method03);
        method01.getEParameters().add(param01);
        method01.getEParameters().add(param02);
        method03.getEParameters().add(param03);
        method01.setEType(type01);
        method02.setEType(type04);
        method03.setEType(type05);
        param01.setEType(type02);
        param02.setEType(type03);
        param03.setEType(type06);
        return eClasses;
    }

}