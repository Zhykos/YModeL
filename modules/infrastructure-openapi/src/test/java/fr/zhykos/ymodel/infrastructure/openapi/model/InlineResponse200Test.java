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
package fr.zhykos.ymodel.infrastructure.openapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
