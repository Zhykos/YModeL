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
package fr.zhykos.ymodel.domain.services.typescript;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.domain.models.typescript.TypescriptClass;
import fr.zhykos.ymodel.domain.models.typescript.TypescriptMethod;
import fr.zhykos.ymodel.domain.models.typescript.TypescriptMethodParameter;

class GenerationTypescriptServiceTests {

    @Test
    @DisplayName("Template name")
    void templateName() {
        Assertions.assertEquals("typescript", new GenerationTypescriptService().getTemplateName());
    }

    @Test
    @DisplayName("Generate class")
    void generateClass() {
        final GenerationTypescriptService service = new GenerationTypescriptService();
        final TypescriptClass typescriptClass = service.generateClass("MyClass", Optional.empty());
        Assertions.assertEquals("MyClass", typescriptClass.getName());
        Assertions.assertNull(typescriptClass.getInherits());
        Assertions.assertTrue(typescriptClass.getImports().isEmpty());
    }

    @Test
    @DisplayName("Generate class with inherited class")
    void generateClassWithInheritance() {
        final GenerationTypescriptService service = new GenerationTypescriptService();
        final TypescriptClass typescriptClass = service.generateClass("MyClass", Optional.of("ExtendedClass"));
        Assertions.assertEquals("MyClass", typescriptClass.getName());
        Assertions.assertEquals("ExtendedClass", typescriptClass.getInherits());
        Assertions.assertIterableEquals(List.of("ExtendedClass"), typescriptClass.getImports());
    }

    @Test
    @DisplayName("Generate field")
    void generateField() {
        final GenerationTypescriptService service = new GenerationTypescriptService();
        final TypescriptClass typescriptClass = service.generateClass("MyClass", Optional.empty());
        service.generateField(typescriptClass, "myField", "string");
        Assertions.assertEquals(1, typescriptClass.getFields().size());
        Assertions.assertEquals("myField", typescriptClass.getFields().get(0).getName());
        Assertions.assertEquals("string", typescriptClass.getFields().get(0).getType());
    }

    @Test
    @DisplayName("Generate method")
    void generateMethod() {
        final GenerationTypescriptService service = new GenerationTypescriptService();
        final TypescriptClass typescriptClass = service.generateClass("MyClass", Optional.empty());
        final TypescriptMethod typescriptMethod = service.generateMethod(typescriptClass, "myMethod", "string");
        Assertions.assertEquals("myMethod", typescriptMethod.getName());
        Assertions.assertEquals("string", typescriptMethod.getReturns());
        Assertions.assertTrue(typescriptMethod.getParameters().isEmpty());
        Assertions.assertTrue(typescriptClass.getImports().isEmpty());
    }

    @Test
    @DisplayName("Generate method with parameters")
    void generateMethodWithParameters() {
        final GenerationTypescriptService service = new GenerationTypescriptService();
        final TypescriptClass typescriptClass = service.generateClass("MyClass", Optional.empty());
        final TypescriptMethod typescriptMethod = service.generateMethod(typescriptClass, "myMethod", "string");
        service.generateMethodParameter(typescriptClass, typescriptMethod, "string", "string");
        Assertions.assertEquals("myMethod", typescriptMethod.getName());
        Assertions.assertEquals("string", typescriptMethod.getReturns());
        final List<TypescriptMethodParameter> parameters = typescriptMethod.getParameters();
        Assertions.assertEquals(1, parameters.size());
        Assertions.assertEquals("string", parameters.get(0).getName());
        Assertions.assertEquals("string", parameters.get(0).getType());
    }

    @Test
    @DisplayName("Do nothing after generating a method")
    void postMethodGeneration() {
        final GenerationTypescriptService service = new GenerationTypescriptService();
        final TypescriptClass typescriptClass = service.generateClass("MyClass", Optional.empty());
        final TypescriptMethod typescriptMethod = service.generateMethod(typescriptClass, "myMethod", "string");
        service.postMethodGeneration(typescriptMethod);
        final List<TypescriptMethodParameter> parameters = typescriptMethod.getParameters();
        Assertions.assertEquals(0, parameters.size());
    }

    @Test
    @DisplayName("Set last parameter after generating a method")
    void postMethodGenerationSetLast() {
        final GenerationTypescriptService service = new GenerationTypescriptService();
        final TypescriptClass typescriptClass = service.generateClass("MyClass", Optional.empty());
        final TypescriptMethod typescriptMethod = service.generateMethod(typescriptClass, "myMethod", "string");
        service.generateMethodParameter(typescriptClass, typescriptMethod, "string1", "string1");
        service.generateMethodParameter(typescriptClass, typescriptMethod, "string2", "string2");
        service.postMethodGeneration(typescriptMethod);
        final List<TypescriptMethodParameter> parameters = typescriptMethod.getParameters();
        Assertions.assertEquals(2, parameters.size());
        Assertions.assertFalse(parameters.get(0).isLast());
        Assertions.assertTrue(parameters.get(1).isLast());
    }

    @Test
    @DisplayName("Generate number field")
    void generateNumberField() {
        final GenerationTypescriptService service = new GenerationTypescriptService();
        final TypescriptClass typescriptClass = service.generateClass("MyClass", Optional.empty());
        service.generateField(typescriptClass, "myField", "int");
        Assertions.assertEquals(1, typescriptClass.getFields().size());
        Assertions.assertEquals("number", typescriptClass.getFields().get(0).getType());
    }

    @Test
    @DisplayName("Generate void method")
    void generateVoidMethod() {
        final GenerationTypescriptService service = new GenerationTypescriptService();
        final TypescriptClass typescriptClass = service.generateClass("MyClass", Optional.empty());
        final TypescriptMethod typescriptMethod = service.generateMethod(typescriptClass, "myMethod", "void");
        Assertions.assertEquals("myMethod", typescriptMethod.getName());
        Assertions.assertEquals("void", typescriptMethod.getReturns());
        Assertions.assertTrue(typescriptMethod.getParameters().isEmpty());
        Assertions.assertTrue(typescriptClass.getImports().isEmpty());
    }

}
