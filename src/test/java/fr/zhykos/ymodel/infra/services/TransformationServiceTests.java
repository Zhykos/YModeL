package fr.zhykos.ymodel.infra.services;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.infra.models.yml.YmlClass;
import fr.zhykos.ymodel.infra.models.yml.YmlField;
import fr.zhykos.ymodel.infra.models.yml.YmlMetaModel;
import fr.zhykos.ymodel.infra.models.yml.YmlMethod;
import fr.zhykos.ymodel.infra.models.yml.YmlMethodParameter;

class TransformationServiceTests {

    @Test
    @DisplayName("Transform a YML metamodel into an EMF metamodel")
    void transform() {
        final YmlMetaModel ymlMetaModel = createMetaModel();
        final List<EClass> eClasses = new TransformationService().transform(ymlMetaModel);
        Assertions.assertEquals(2, eClasses.size());

        final EClass class01 = eClasses.get(0);
        Assertions.assertEquals(0, class01.getESuperTypes().size());
        Assertions.assertEquals("Class01", class01.getName());
        Assertions.assertEquals(2, class01.getEOperations().size());

        final EOperation method01 = class01.getEOperations().get(0);
        Assertions.assertEquals("method01", method01.getName());
        Assertions.assertEquals("void", method01.getEType().getName());
        Assertions.assertEquals(2, method01.getEParameters().size());

        final EParameter param01 = method01.getEParameters().get(0);
        Assertions.assertEquals("param01", param01.getName());
        Assertions.assertEquals("int", param01.getEType().getName());

        final EParameter param02 = method01.getEParameters().get(1);
        Assertions.assertEquals("param02", param02.getName());
        Assertions.assertEquals("string", param02.getEType().getName());

        final EOperation method02 = class01.getEOperations().get(1);
        Assertions.assertEquals("method02", method02.getName());
        Assertions.assertEquals("float", method02.getEType().getName());
        Assertions.assertEquals(0, method02.getEParameters().size());

        final EClass class02 = eClasses.get(1);
        Assertions.assertEquals(1, class02.getESuperTypes().size());
        Assertions.assertEquals(class01, class02.getESuperTypes().get(0));
        Assertions.assertEquals("Class02", class02.getName());
        Assertions.assertEquals(1, class02.getEOperations().size());

        final EOperation method03 = class02.getEOperations().get(0);
        Assertions.assertEquals("method03", method03.getName());
        Assertions.assertEquals("char", method03.getEType().getName());
        Assertions.assertEquals(1, method03.getEParameters().size());

        final EParameter param03 = method03.getEParameters().get(0);
        Assertions.assertEquals("param03", param03.getName());
        Assertions.assertEquals("int", param03.getEType().getName());
    }

    private static YmlMetaModel createMetaModel() {
        final YmlClass class01 = new YmlClass();
        class01.setName("Class01");

        final YmlMethod method01 = new YmlMethod();
        method01.setName("method01");
        method01.setReturns("void");

        final YmlMethodParameter param01 = new YmlMethodParameter();
        param01.setName("param01");
        param01.setType("int");

        final YmlMethodParameter param02 = new YmlMethodParameter();
        param02.setName("param02");
        param02.setType("string");

        final YmlMethod method02 = new YmlMethod();
        method02.setName("method02");
        method02.setReturns("float");

        final YmlClass class02 = new YmlClass();
        class02.setName("Class02");
        class02.setInherits("$Class01");

        final YmlMethod method03 = new YmlMethod();
        method03.setName("method03");
        method03.setReturns("char");

        final YmlMethodParameter param03 = new YmlMethodParameter();
        param03.setName("param03");
        param03.setType("int");

        final YmlField field = new YmlField("field", "string");

        final YmlMetaModel metaModel = new YmlMetaModel();
        metaModel.getClasses().add(class01);
        metaModel.getClasses().add(class02);
        class01.getMethods().add(method01);
        class01.getMethods().add(method02);
        class02.getMethods().add(method03);
        class02.getFields().add(field);
        method01.getParameters().add(param01);
        method01.getParameters().add(param02);
        method03.getParameters().add(param03);
        return metaModel;
    }

}
