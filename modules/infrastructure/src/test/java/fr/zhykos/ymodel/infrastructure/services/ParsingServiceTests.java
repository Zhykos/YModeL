package fr.zhykos.ymodel.infrastructure.services;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.infrastructure.models.yml.YmlClass;
import fr.zhykos.ymodel.infrastructure.models.yml.YmlMetaModel;
import fr.zhykos.ymodel.infrastructure.models.yml.YmlMethod;
import fr.zhykos.ymodel.infrastructure.models.yml.YmlMethodParameter;

class ParsingServiceTests {

    @Test
    @DisplayName("Parse a YML metamodel declaration file")
    void parse() {
        final File yamlFile = new File("src/test/resources/metamodel01.yml");
        final Returns<YmlMetaModel, IOException> parseReturns = new ParsingService().parse(yamlFile);
        final YmlMetaModel metaModel = parseReturns.then();

        Assertions.assertEquals(2, metaModel.getClasses().size());

        final YmlClass class01 = metaModel.getClasses().get(0);
        Assertions.assertNull(class01.getInherits());
        Assertions.assertEquals("Class01", class01.getName());
        Assertions.assertEquals(2, class01.getMethods().size());

        final YmlMethod method01 = class01.getMethods().get(0);
        Assertions.assertEquals("method01", method01.getName());
        Assertions.assertEquals("void", method01.getReturns());
        Assertions.assertEquals(2, method01.getParameters().size());

        final YmlMethodParameter param01 = method01.getParameters().get(0);
        Assertions.assertEquals("param01", param01.getName());
        Assertions.assertEquals("int", param01.getType());

        final YmlMethodParameter param02 = method01.getParameters().get(1);
        Assertions.assertEquals("param02", param02.getName());
        Assertions.assertEquals("string", param02.getType());

        final YmlMethod method02 = class01.getMethods().get(1);
        Assertions.assertEquals("method02", method02.getName());
        Assertions.assertEquals("float", method02.getReturns());
        Assertions.assertEquals(0, method02.getParameters().size());

        final YmlClass class02 = metaModel.getClasses().get(1);
        Assertions.assertEquals("$Class01", class02.getInherits());
        Assertions.assertEquals("Class02", class02.getName());
        Assertions.assertEquals(1, class02.getMethods().size());

        final YmlMethod method03 = class02.getMethods().get(0);
        Assertions.assertEquals("method03", method03.getName());
        Assertions.assertEquals("char", method03.getReturns());
        Assertions.assertEquals(1, method03.getParameters().size());

        final YmlMethodParameter param03 = method03.getParameters().get(0);
        Assertions.assertEquals("param03", param03.getName());
        Assertions.assertEquals("int", param03.getType());
    }

}
