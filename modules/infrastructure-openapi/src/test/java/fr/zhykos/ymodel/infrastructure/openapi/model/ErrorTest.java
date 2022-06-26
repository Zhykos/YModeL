package fr.zhykos.ymodel.infrastructure.openapi.model;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.infrastructure.openapi.model.Error;

class ErrorTest {

    @Test
    void checkToString() {
        final Error inline = new Error().message("foo").code(12);
        Assertions.assertEquals("class Error {\n    code: 12\n    message: foo\n}", inline.toString());
    }

    @Test
    void toStringNull() {
        final Error inline = new Error();
        Assertions.assertEquals("class Error {\n    code: null\n    message: null\n}", inline.toString());
    }

    @Test
    void setMessage() {
        final Error inline = new Error();
        inline.setMessage("foo");
        Assertions.assertEquals("foo", inline.getMessage());
    }

    @Test
    void setCode() {
        final Error inline = new Error();
        inline.setCode(12);
        Assertions.assertEquals(12, inline.getCode());
    }

}
