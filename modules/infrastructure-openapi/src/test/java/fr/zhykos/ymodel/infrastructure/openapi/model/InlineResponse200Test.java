package fr.zhykos.ymodel.infrastructure.openapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.infrastructure.openapi.model.InlineResponse200;

class InlineResponse200Test {

    private static final byte[] BYTE_ARRAY = new byte[] { 'a' };

    @Test
    void checkToString() {
        final InlineResponse200 inline = new InlineResponse200().zip(BYTE_ARRAY);
        Assertions.assertEquals("class InlineResponse200 {\n    zip: " + BYTE_ARRAY + "\n}", inline.toString());
    }

    @Test
    void toStringNull() {
        final InlineResponse200 inline = new InlineResponse200();
        Assertions.assertEquals("class InlineResponse200 {\n    zip: null\n}", inline.toString());
    }

    @Test
    void zip() {
        final InlineResponse200 inline = new InlineResponse200();
        inline.setZip(BYTE_ARRAY);
        Assertions.assertEquals(BYTE_ARRAY, inline.getZip());
    }

}
