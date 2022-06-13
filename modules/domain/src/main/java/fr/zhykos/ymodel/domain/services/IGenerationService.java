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
