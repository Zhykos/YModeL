package fr.zhykos.ymodel.infra.services;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import fr.zhykos.ymodel.infra.Returns;

/**
 * Generic service to generate files
 *
 * @param <C> C: the type of the generated class
 * @param <M> M: the type of the generated method
 */
public abstract class AbstractGenerationService<C, M> {

    /**
     * @return The template name: the mustache template filename
     */
    protected abstract String getTemplateName();

    /**
     * Generate a class model
     *
     * @param className          Class name
     * @param inheritedClassName Inherited class name
     * @return The class
     */
    protected abstract C generateClass(String className, Optional<String> inheritedClassName);

    /**
     * Generate a field / attribute model
     *
     * @param containingClass The class containing this new field
     * @param fieldName       Field name
     * @param fieldType       Field type (exact declaration from the YML definition
     *                        file)
     */
    protected abstract void generateField(C containingClass, String fieldName, String fieldType);

    /**
     * Generate a method model
     *
     * @param containingClass   The class containing this new method
     * @param methodName        Method name
     * @param methodReturnsType Method type (exact declaration from the YML
     *                          definition file)
     * @return The method
     */
    protected abstract M generateMethod(C containingClass, String methodName,
            String methodReturnsType);

    /**
     * Execute some code after creating a method
     *
     * @param createdMethod The created method
     */
    protected abstract void postMethodGeneration(M createdMethod);

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
    protected abstract void generateMethodParameter(C containingClass, M containingMethod,
            String parameterName, String parameterType);

    /**
     * Generate all classes
     *
     * @param eClasses Classes to generate
     * @return A list of Returns objects: a string with the generated content ; or
     *         an IOException if an error occurred.
     */
    public final List<Returns<String, IOException>> generate(final List<EClass> eClasses) {
        return eClasses.stream().map(this::generate).toList();
    }

    /**
     * Generate a class
     *
     * @param classs The class to generate
     * @return A Returns objects: a string with the generated content ; or an
     *         IOException if an error occurred.
     */
    public final Returns<String, IOException> generate(final EClass classs) {
        String inherits = null;
        if (!classs.getESuperTypes().isEmpty()) {
            inherits = classs.getESuperTypes().get(0).getName();
        }
        final C generatedClass = generateClass(classs.getName(), Optional.ofNullable(inherits));
        classs.getEOperations()
                .forEach(operation -> {
                    final M generatedMethod = generateMethod(generatedClass, operation.getName(),
                            operation.getEType().getName());

                    operation.getEParameters()
                            .forEach(parameter -> generateMethodParameter(generatedClass, generatedMethod,
                                    parameter.getName(), parameter.getEType().getName()));

                    postMethodGeneration(generatedMethod);
                });
        classs.getEAttributes()
                .forEach(attribute -> generateField(generatedClass, attribute.getName(),
                        attribute.getEType().getName()));
        return executeTemplate(generatedClass, getTemplateName());
    }

    private Returns<String, IOException> executeTemplate(final C templateClass,
            final String mustacheFilename) {
        try {
            final MustacheFactory mustacheFactory = new DefaultMustacheFactory();
            final Mustache mustache = mustacheFactory
                    .compile("src/main/resources/templates/%s.mustache".formatted(mustacheFilename));
            final StringWriter stringWriter = new StringWriter();
            mustache.execute(stringWriter, templateClass).flush();
            return Returns.resolve(stringWriter.toString().replace("&#39;", "'"));
        } catch (final IOException exception) {
            return Returns.reject(exception);
        }
    }

}
