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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;

import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.infrastructure.models.yml.YmlClass;
import fr.zhykos.ymodel.infrastructure.models.yml.YmlMetaModel;
import one.util.streamex.StreamEx;

/**
 * Transform the YAML metamodel into an EClass model
 */
public final class TransformationService {

    /**
     * Transform the YAML metamodel into an EClass model
     *
     * @param ymlMetamodel The YAML metamodel
     * @return List of EClasses represented by the YML metamodel ; or Semantic
     *         exception if the YML metamodel is not valid
     */
    public Returns<List<EClass>, SemanticListException> transform(final YmlMetaModel ymlMetamodel) {
        final List<EClass> eClasses = ymlMetamodel.getClasses().stream().map(TransformationService::transform).toList();
        final Map<String, EClass> classesIdentityMap = eClasses.stream()
                .collect(Collectors.toMap(EClass::getName, Function.identity()));
        final List<String> exceptions = consolidateClassReferences(eClasses, classesIdentityMap);
        return new Returns<>(Optional.of(eClasses),
                exceptions.isEmpty() ? Optional.empty()
                        : Optional.of(new SemanticListException(ymlMetamodel.getOriginFile(), exceptions)));
    }

    private static EClass transform(final YmlClass ymlClass) {
        final var eClass = EcoreFactory.eINSTANCE.createEClass();
        eClass.setName(ymlClass.getName());

        if (ymlClass.getInherits() != null) {
            final var superClass = EcoreFactory.eINSTANCE.createEClass();
            superClass.setName(ymlClass.getInherits());
            eClass.getESuperTypes().add(superClass);
        }

        ymlClass.getFields().forEach(field -> {
            final EStructuralFeature feature;
            if (field.getType().startsWith("$")) {
                feature = EcoreFactory.eINSTANCE.createEReference();
            } else {
                feature = EcoreFactory.eINSTANCE.createEAttribute();
            }
            feature.setName(field.getName());
            feature.setEType(getType(field.getType()));
            eClass.getEStructuralFeatures().add(feature);
        });

        ymlClass.getMethods().forEach(method -> {
            final var operation = EcoreFactory.eINSTANCE.createEOperation();
            operation.setName(method.getName());
            operation.setEType(getType(method.getReturns()));
            eClass.getEOperations().add(operation);

            method.getParameters().forEach(parameter -> {
                final var eParameter = EcoreFactory.eINSTANCE.createEParameter();
                eParameter.setName(parameter.getName());
                eParameter.setEType(getType(parameter.getType()));
                operation.getEParameters().add(eParameter);
            });
        });

        return eClass;
    }

    private static EClassifier getType(final String sourceType) {
        final EClassifier type;
        if (sourceType.startsWith("$")) {
            type = EcoreFactory.eINSTANCE.createEClass();
        } else {
            type = EcoreFactory.eINSTANCE.createEDataType();
        }
        type.setName(sourceType);
        return type;
    }

    private static List<String> consolidateClassReferences(final List<EClass> eClasses,
            final Map<String, EClass> classesIdentityMap) {
        return StreamEx
                .of(eClasses.stream()
                        .map(eClass -> consolidateClassInherits(eClass.getESuperTypes(), classesIdentityMap)))
                .append(eClasses.stream()
                        .map(eClass -> consolidateClassReference(eClass.getEReferences(), classesIdentityMap)))
                .append(eClasses.stream()
                        .map(eClass -> consolidateClassReference(eClass.getEOperations(), classesIdentityMap)))
                .append(eClasses.stream().map(eClass -> consolidateClassReference(
                        eClass.getEOperations().stream().flatMap(op -> op.getEParameters().stream()).toList(),
                        classesIdentityMap)))
                .toFlatList(x -> x);
    }

    private static List<String> consolidateClassInherits(final List<EClass> superTypes,
            final Map<String, EClass> classesIdentityMap) {
        final List<String> exceptions = new ArrayList<>();
        for (int index = 0; index < superTypes.size(); index++) {
            final EClass superType = superTypes.get(index);
            final String referenceNamed = superType.getName();
            final EClass mapping = classesIdentityMap.get(referenceNamed.replaceAll("^\\$(.+)$", "$1"));
            if (mapping == null) {
                exceptions.add(treatSemanticException(" - Unknown class reference for inheritance: " + referenceNamed,
                        referenceNamed));
            } else {
                superTypes.set(index, mapping);
            }
        }
        return exceptions.stream().filter(Objects::nonNull).toList();
    }

    private static List<String> consolidateClassReference(final List<? extends ETypedElement> typedElements,
            final Map<String, EClass> classesIdentityMap) {
        return typedElements.stream().map(eTypedElement -> {
            final String referenceNamed = eTypedElement.getEType().getName();
            final EClass mapping = classesIdentityMap.get(referenceNamed.replaceAll("^\\$(.+)$", "$1"));
            if (mapping == null) {
                return treatSemanticException(
                        " - Unknown class reference '%s' in element '%s'".formatted(referenceNamed,
                                eTypedElement.getName()),
                        referenceNamed);
            }
            eTypedElement.setEType(mapping);
            return null;
        }).filter(Objects::nonNull).toList();
    }

    /**
     * Treat a semantic exception
     *
     * @param message        The message of the exception
     * @param referenceNamed The name of the reference
     * @return The semantic exception ; or null if no exception was raised
     */
    private static String treatSemanticException(final String message, final String referenceNamed) {
        return switch (referenceNamed) {
            case "void", "float", "char", "int", "string" -> null;
            default -> message;
        };
    }

    /**
     * SemanticException is thrown when a semantic error is detected.
     * That means that the model is not valid.
     */
    public static final class SemanticListException extends Exception {

        /**
         * Origin file of the error.
         */
        private final File originFile;

        /**
         * List of exceptions.
         */
        private final List<String> subExceptions;

        /**
         * New exception with a list of specific exceptions.
         *
         * @param file       Origin file of the error.
         * @param exceptions The list of exceptions
         */
        private SemanticListException(final File file, final List<String> exceptions) {
            this.originFile = file;
            this.subExceptions = exceptions;
        }

        @Override
        public String getMessage() {
            return "Transformation error for file '%s':%n%s."
                    .formatted(this.originFile.getName(),
                            this.subExceptions.stream().collect(Collectors.joining(System.lineSeparator())));
        }

    }

}
