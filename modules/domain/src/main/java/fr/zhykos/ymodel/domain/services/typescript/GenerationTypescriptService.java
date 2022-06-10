package fr.zhykos.ymodel.domain.services.typescript;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

import fr.zhykos.ymodel.domain.models.typescript.TypescriptClass;
import fr.zhykos.ymodel.domain.models.typescript.TypescriptField;
import fr.zhykos.ymodel.domain.models.typescript.TypescriptMethod;
import fr.zhykos.ymodel.domain.models.typescript.TypescriptMethodParameter;
import fr.zhykos.ymodel.domain.services.IGenerationService;

/**
 * Service to generate Typescript files
 */
public final class GenerationTypescriptService implements IGenerationService<TypescriptClass, TypescriptMethod> {

    @Override
    public String getTemplateName() {
        return "typescript";
    }

    @Override
    public TypescriptClass generateClass(final String className, final Optional<String> inheritedClassName) {
        final TypescriptClass typescriptClass = new TypescriptClass();
        typescriptClass.setName(className);
        inheritedClassName.ifPresent(inherits -> {
            typescriptClass.setInherits(inherits);
            typescriptClass.getImports().add(inherits);
        });
        return typescriptClass;
    }

    @Override
    public void generateField(final TypescriptClass containingClass, final String fieldName,
            final String fieldType) {
        final TypescriptField typescriptMethod = new TypescriptField();
        typescriptMethod.setName(fieldName);
        typescriptMethod.setType(mapTypescriptType(fieldType, containingClass.getImports()));
        containingClass.getFields().add(typescriptMethod);
    }

    @Override
    public TypescriptMethod generateMethod(final TypescriptClass containingClass, final String methodName,
            final String methodReturnsType) {
        final TypescriptMethod typescriptMethod = new TypescriptMethod();
        typescriptMethod.setName(methodName);
        typescriptMethod.setReturns(mapTypescriptType(methodReturnsType, containingClass.getImports()));
        containingClass.getMethods().add(typescriptMethod);
        return typescriptMethod;
    }

    @Override
    public void postMethodGeneration(final TypescriptMethod createdMethod) {
        if (!createdMethod.getParameters().isEmpty()) {
            createdMethod.getParameters().get(createdMethod.getParameters().size() - 1)
                    .setLast(true);
        }
    }

    @Override
    public void generateMethodParameter(final TypescriptClass containingClass,
            final TypescriptMethod containingMethod,
            final String parameterName, final String parameterType) {
        final TypescriptMethodParameter typescriptMethodParameter = new TypescriptMethodParameter();
        typescriptMethodParameter.setName(parameterName);
        typescriptMethodParameter
                .setType(mapTypescriptType(parameterType, containingClass.getImports()));
        containingMethod.getParameters().add(typescriptMethodParameter);
    }

    private static String mapTypescriptType(final String type, final SortedSet<String> imports) {
        return switch (type) {
            case "int", "float" -> "number";
            case "char", "string" -> "string";
            default -> mapTypescriptOtherTypes(type, imports);
        };
    }

    private static String mapTypescriptOtherTypes(final String type, final SortedSet<String> imports) {
        final List<String> dontImport = List.of("void");
        if (!dontImport.contains(type)) {
            imports.add(type);
        }
        return type;
    }

}
