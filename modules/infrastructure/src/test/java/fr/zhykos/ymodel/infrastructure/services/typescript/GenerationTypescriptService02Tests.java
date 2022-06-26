/*
 * Copyright 2022 Thomas "Zhykos" Cicognani.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.zhykos.ymodel.infrastructure.services.typescript;

import java.io.IOException;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.domain.services.typescript.GenerationTypescriptService;
import fr.zhykos.ymodel.infrastructure.models.GeneratedFile;
import fr.zhykos.ymodel.infrastructure.services.GenerationService;
import fr.zhykos.ymodel.infrastructure.services.GenerationService.GenerationException;
import fr.zhykos.ymodel.infrastructure.services.helpers.GenerationHelpers;

class GenerationTypescriptService02Tests {

    @Test
    @DisplayName("Generate a metamodel into Typescript then verify if fields are generated")
    void generate() throws IOException, GenerationException {
        final EClass eClass = createEClass();

        final Returns<GeneratedFile, GenerationException> generation = GenerationService.generate(eClass,
                new GenerationTypescriptService());

        GenerationHelpers.assertStringEqualsFileContentsAsExcepted(generation.then().getContents(),
                "src/test/resources/expected-typescript/Class03.ts");
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
