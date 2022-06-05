package fr.zhykos.ymodel.business.services;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.SortedSet;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;

import fr.zhykos.ymodel.business.models.typescript.TypescriptClass;
import fr.zhykos.ymodel.business.models.typescript.TypescriptField;
import fr.zhykos.ymodel.business.models.typescript.TypescriptMethod;
import fr.zhykos.ymodel.business.models.typescript.TypescriptMethodParameter;
import fr.zhykos.ymodel.infra.Returns;

/**
 * Service to generate Typescript files
 */
public final class GenerationTypescriptService {

    /**
     * Generate all classes
     *
     * @param eClasses Classes to generate
     * @return A list of Returns objects: a string with the generated Typescript
     *         content ; or an IOException if an error occurred.
     */
    public List<Returns<String, IOException>> generate(final List<EClass> eClasses) {
        return eClasses.stream().map(this::generate).toList();
    }

    /**
     * Generate a class
     *
     * @param classs The class to generate
     * @return A Returns objects: a string with the generated Typescript
     *         content ; or an IOException if an error occurred.
     */
    public Returns<String, IOException> generate(final EClass classs) {
        final TypescriptClass typescriptClass = new TypescriptClass();
        typescriptClass.setName(classs.getName());
        if (!classs.getESuperTypes().isEmpty()) {
            final String inherits = classs.getESuperTypes().get(0).getName();
            typescriptClass.setInherits(inherits);
            typescriptClass.getImports().add(inherits);
        }
        classs.getEOperations()
                .forEach(operation -> {
                    final TypescriptMethod typescriptMethod = new TypescriptMethod();
                    typescriptMethod.setName(operation.getName());
                    typescriptMethod.setReturns(mapTypescriptType(operation.getEType(), typescriptClass.getImports()));
                    typescriptClass.getMethods().add(typescriptMethod);

                    operation.getEParameters().forEach(parameter -> {
                        final TypescriptMethodParameter typescriptMethodParameter = new TypescriptMethodParameter();
                        typescriptMethodParameter.setName(parameter.getName());
                        typescriptMethodParameter
                                .setType(mapTypescriptType(parameter.getEType(), typescriptClass.getImports()));
                        typescriptMethod.getParameters().add(typescriptMethodParameter);
                    });

                    if (!typescriptMethod.getParameters().isEmpty()) {
                        typescriptMethod.getParameters().get(typescriptMethod.getParameters().size() - 1)
                                .setLast(true);
                    }
                });
        classs.getEAttributes()
                .forEach(attribute -> {
                    final TypescriptField typescriptMethod = new TypescriptField();
                    typescriptMethod.setName(attribute.getName());
                    typescriptMethod.setType(mapTypescriptType(attribute.getEType(), typescriptClass.getImports()));
                    typescriptClass.getFields().add(typescriptMethod);
                });
        return executeTemplate(typescriptClass, "typescript");
    }

    private static String mapTypescriptType(final EClassifier type, final SortedSet<String> imports) {
        return switch (type.getName()) {
            case "int", "float" -> "number";
            case "char", "string" -> "string";
            default -> mapTypescriptType(type.getName(), imports);
        };
    }

    private static String mapTypescriptType(final String type, final SortedSet<String> imports) {
        final List<String> dontImport = List.of("void");
        if (!dontImport.contains(type)) {
            imports.add(type);
        }
        return type;
    }

    private static Returns<String, IOException> executeTemplate(final TypescriptClass templateModel,
            final String mustacheFilename) {
        try {
            final MustacheFactory mustacheFactory = new DefaultMustacheFactory();
            final Mustache mustache = mustacheFactory
                    .compile("src/main/resources/templates/%s.mustache".formatted(mustacheFilename));
            final StringWriter stringWriter = new StringWriter();
            mustache.execute(stringWriter, templateModel).flush();
            return Returns.resolve(stringWriter.toString().replace("&#39;", "'"));
        } catch (final IOException exception) {
            return Returns.reject(exception);
        }
    }
}
