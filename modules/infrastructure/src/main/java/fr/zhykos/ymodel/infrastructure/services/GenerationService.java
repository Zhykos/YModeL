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
package fr.zhykos.ymodel.infrastructure.services;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.domain.models.ITemplateClass;
import fr.zhykos.ymodel.domain.services.IGenerationService;
import fr.zhykos.ymodel.infrastructure.models.GeneratedFile;

/**
 * Generic service to generate files
 */
public final class GenerationService {

    private GenerationService() {
        // Utils class
    }

    /**
     * Generate all classes
     *
     * @param <C>               C: the type of the generated class. Must implements
     *                          {@link ITemplateClass}
     * @param <M>               M: the type of the generated method
     *
     * @param eClasses          Classes to generate
     * @param generationService Specific service to generate the classes: language
     *                          service target
     * @return A list of Returns objects: a {@link GeneratedFile} with the generated
     *         content ; or an GenerationException if an error occurred.
     */
    public static <C extends ITemplateClass, M> List<Returns<GeneratedFile, GenerationException>> generate(
            final List<EClass> eClasses, final IGenerationService<C, M> generationService) {
        return eClasses.stream().map(eClass -> generate(eClass, generationService)).toList();
    }

    /**
     * Generate a class
     *
     * @param <C>               C: the type of the generated class. Must implements
     *                          {@link ITemplateClass}
     * @param <M>               M: the type of the generated method
     *
     * @param classs            The class to generate
     * @param generationService Specific service to generate the classes: language
     *                          service target
     * @return A Returns objects: a {@link GeneratedFile} with the generated content
     *         ; or an GenerationException if an error occurred.
     */
    public static <C extends ITemplateClass, M> Returns<GeneratedFile, GenerationException> generate(
            final EClass classs, final IGenerationService<C, M> generationService) {
        String inherits = null;
        if (!classs.getESuperTypes().isEmpty()) {
            inherits = classs.getESuperTypes().get(0).getName();
        }
        final C generatedClass = generationService.generateClass(classs.getName(), Optional.ofNullable(inherits));
        classs.getEOperations()
                .forEach(operation -> {
                    final M generatedMethod = generationService.generateMethod(generatedClass, operation.getName(),
                            operation.getEType().getName());

                    operation.getEParameters()
                            .forEach(parameter -> generationService.generateMethodParameter(generatedClass,
                                    generatedMethod, parameter.getName(), parameter.getEType().getName()));

                    generationService.postMethodGeneration(generatedMethod);
                });
        classs.getEAttributes()
                .forEach(attribute -> generationService.generateField(generatedClass, attribute.getName(),
                        attribute.getEType().getName()));
        return executeTemplate(generatedClass, generationService);
    }

    private static <C extends ITemplateClass, M> Returns<GeneratedFile, GenerationException> executeTemplate(
            final C templateClass, final IGenerationService<C, M> generationService) {
        try {
            final MustacheFactory mustacheFactory = new DefaultMustacheFactory();
            final Mustache mustache = mustacheFactory.compile(new StringReader(generationService.getTemplateContents()),
                    generationService.getTemplateName());
            final StringWriter stringWriter = new StringWriter();
            mustache.execute(stringWriter, templateClass).flush();
            final GeneratedFile generatedFile = new GeneratedFile(templateClass.getFileClassName(),
                    stringWriter.toString().replace("&#39;", "'"));
            return Returns.resolve(generatedFile);
        } catch (final Exception exception) {
            return Returns.reject(new GenerationException(exception));
        }
    }

    /**
     * Exception while generating a new Class
     */
    public static final class GenerationException extends Exception {

        /**
         * New Exception caused by...
         *
         * @param cause The cause
         */
        private GenerationException(final Throwable cause) {
            super(cause);
        }

    }

}
