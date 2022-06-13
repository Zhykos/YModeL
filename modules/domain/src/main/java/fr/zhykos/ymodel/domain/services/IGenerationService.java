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
package fr.zhykos.ymodel.domain.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Generic service to generate files
 *
 * @param <C> C: the type of the generated class
 * @param <M> M: the type of the generated method
 */
public interface IGenerationService<C, M> {

    /**
     * @return The template name: the mustache template filename
     */
    String getTemplateName();

    /**
     * Generate a class model
     *
     * @param className          Class name
     * @param inheritedClassName Inherited class name
     * @return The class
     */
    C generateClass(String className, Optional<String> inheritedClassName);

    /**
     * Generate a field / attribute model
     *
     * @param containingClass The class containing this new field
     * @param fieldName       Field name
     * @param fieldType       Field type (exact declaration from the YML definition
     *                        file)
     */
    void generateField(C containingClass, String fieldName, String fieldType);

    /**
     * Generate a method model
     *
     * @param containingClass   The class containing this new method
     * @param methodName        Method name
     * @param methodReturnsType Method type (exact declaration from the YML
     *                          definition file)
     * @return The method
     */
    M generateMethod(C containingClass, String methodName, String methodReturnsType);

    /**
     * Execute some code after creating a method. Do nothing by default.
     *
     * @param createdMethod The created method
     */
    default void postMethodGeneration(M createdMethod) {
        // Do nothing
    }

    /**
     * Generate a method parameter model
     *
     * @param containingClass  The class containing the method also in parameter
     *                         here
     * @param containingMethod The method containing this new parameter
     * @param parameterName    Parameter name
     * @param parameterType    Parameter type (exact declaration from the YML
     *                         definition file)
     */
    void generateMethodParameter(C containingClass, M containingMethod, String parameterName, String parameterType);

    /**
     * @return File content of the template used to generate the class
     * @throws IOException Error while reading the file
     */
    default String getTemplateContents() throws IOException {
        return Files.readString(
                Path.of("../domain/src/main/resources/templates/%s.mustache".formatted(getTemplateName())));
    }

}
