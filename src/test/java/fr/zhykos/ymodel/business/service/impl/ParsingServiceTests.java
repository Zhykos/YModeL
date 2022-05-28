package fr.zhykos.ymodel.business.service.impl;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.business.model.Classs;
import fr.zhykos.ymodel.business.model.MetaModel;
import fr.zhykos.ymodel.business.model.Method;
import fr.zhykos.ymodel.business.model.MethodParameter;
import fr.zhykos.ymodel.business.service.ParsingException;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ParsingServiceTests {

    @Test
    void parse() throws ParsingException {
        final File yamlFile = new File("src/test/resources/metamodel01.yml");
        final MetaModel metaModel = new ParsingService().parse(yamlFile);

        Assertions.assertEquals(2, metaModel.getClasses().size());

        final Classs class01 = metaModel.getClasses().get(0);
        Assertions.assertNull(class01.getInheritsClass());
        Assertions.assertNull(class01.getInherits());
        Assertions.assertEquals("Class01", class01.getName());
        Assertions.assertEquals(2, class01.getMethods().size());

        final Method method01 = class01.getMethods().get(0);
        Assertions.assertEquals("method01", method01.getName());
        Assertions.assertEquals("void", method01.getReturns());
        Assertions.assertEquals(2, method01.getParams().size());

        final MethodParameter param01 = method01.getParams().get(0);
        Assertions.assertEquals("param01", param01.getName());
        Assertions.assertEquals("int", param01.getType());

        final MethodParameter param02 = method01.getParams().get(1);
        Assertions.assertEquals("param02", param02.getName());
        Assertions.assertEquals("string", param02.getType());

        final Method method02 = class01.getMethods().get(1);
        Assertions.assertEquals("method02", method02.getName());
        Assertions.assertEquals("float", method02.getReturns());
        Assertions.assertEquals(0, method02.getParams().size());

        final Classs class02 = metaModel.getClasses().get(1);
        Assertions.assertEquals(class01, class02.getInheritsClass());
        Assertions.assertEquals("Class01", class02.getInherits());
        Assertions.assertEquals("Class02", class02.getName());
        Assertions.assertEquals(1, class02.getMethods().size());

        final Method method03 = class02.getMethods().get(0);
        Assertions.assertEquals("method03", method03.getName());
        Assertions.assertEquals("char", method03.getReturns());
        Assertions.assertEquals(1, method03.getParams().size());

        final MethodParameter param03 = method03.getParams().get(0);
        Assertions.assertEquals("param03", param03.getName());
        Assertions.assertEquals("int", param03.getType());
    }

}
