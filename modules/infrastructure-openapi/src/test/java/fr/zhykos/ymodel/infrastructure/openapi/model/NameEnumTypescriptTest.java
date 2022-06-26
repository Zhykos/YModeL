package fr.zhykos.ymodel.infrastructure.openapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.infrastructure.openapi.model.Language.NameEnum;

class NameEnumTypescriptTest {

    @Test
    void fromValue() {
        Assertions.assertEquals(NameEnum.TYPESCRIPT, NameEnum.fromValue("typescript"));
    }

    @Test
    void fromValueException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> NameEnum.fromValue("foo"));
    }

}
