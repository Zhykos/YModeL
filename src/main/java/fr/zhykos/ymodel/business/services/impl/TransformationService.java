package fr.zhykos.ymodel.business.services.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;

import fr.zhykos.ymodel.infra.models.yml.YmlClass;
import fr.zhykos.ymodel.infra.models.yml.YmlMetaModel;

/**
 * Transform the YAML metamodel into an EClass metamodel
 */
public class TransformationService {

    public List<EClass> transform(final YmlMetaModel ymlMetamodel) {
        final List<EClass> eClasses = ymlMetamodel.getClasses().stream().map(TransformationService::transform).toList();
        final Map<String, EClass> classesIdentityMap = eClasses.stream()
                .collect(Collectors.toMap(EClass::getName, Function.identity()));
        consolidateClassReferences(eClasses, classesIdentityMap);
        return eClasses;
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

    private static void consolidateClassReferences(final List<EClass> eClasses,
            final Map<String, EClass> classesIdentityMap) {
        eClasses.forEach(eClass -> {
            consolidateClassInherits(eClass.getESuperTypes(), classesIdentityMap);
            consolidateClassReference(eClass.getEReferences(), classesIdentityMap);
            consolidateClassReference(eClass.getEOperations(), classesIdentityMap);
            consolidateClassReference(
                    eClass.getEOperations().stream().flatMap(op -> op.getEParameters().stream()).toList(),
                    classesIdentityMap);
        });
    }

    private static void consolidateClassInherits(final List<EClass> superTypes,
            final Map<String, EClass> classesIdentityMap) {
        for (int index = 0; index < superTypes.size(); index++) {
            final EClass superType = superTypes.get(index);
            final String referenceNamed = superType.getName();
            final EClass mapping = classesIdentityMap.get(referenceNamed.replaceAll("^\\$(.+)$", "$1"));
            if (mapping != null) {
                superTypes.set(index, mapping);
            }
        }
    }

    private static void consolidateClassReference(final List<? extends ETypedElement> typedElements,
            final Map<String, EClass> classesIdentityMap) {
        typedElements.forEach(eTypedElement -> {
            final String referenceNamed = eTypedElement.getEType().getName();
            final EClass mapping = classesIdentityMap.get('$' + referenceNamed);
            if (mapping != null) {
                eTypedElement.setEType(mapping);
            }
        });
    }

}
