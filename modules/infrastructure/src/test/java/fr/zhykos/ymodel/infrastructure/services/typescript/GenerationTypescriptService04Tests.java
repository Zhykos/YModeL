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
import java.nio.file.Path;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EcoreFactory;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.commons.ComparisonHelper;
import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.commons.models.comparison.ComparisonOK;
import fr.zhykos.ymodel.commons.models.comparison.IComparisonResult;
import fr.zhykos.ymodel.domain.services.typescript.GenerationTypescriptService;
import fr.zhykos.ymodel.infrastructure.models.GeneratedFile;
import fr.zhykos.ymodel.infrastructure.services.GenerationService;
import fr.zhykos.ymodel.infrastructure.services.GenerationService.GenerationException;

class GenerationTypescriptService04Tests {

    @Test
    @DisplayName("Generate a metamodel into Typescript then verify external classes references")
    void generate() throws IOException, GenerationException {
        final EClass eClass = createEClass();

        final Returns<GeneratedFile, GenerationException> generation = GenerationService.generate(eClass,
                new GenerationTypescriptService());

        final IComparisonResult comparisonResult = ComparisonHelper.compareStringEqualsFileContentsAsExcepted(
                generation.then().getContents(), Path.of("src/test/resources/expected-typescript/Class05.ts"));

        MatcherAssert.assertThat(comparisonResult, Matchers.instanceOf(ComparisonOK.class));
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
