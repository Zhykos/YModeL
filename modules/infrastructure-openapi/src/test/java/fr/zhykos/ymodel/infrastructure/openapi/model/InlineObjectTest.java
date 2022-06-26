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

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InlineObjectTest {

    @Test
    void checkToString() {
        final InlineObject inline = new InlineObject().language("typescript")._file(new File("foo"));
        Assertions.assertEquals("class InlineObject {\n    language: typescript\n    _file: foo\n}", inline.toString());
    }

    @Test
    void toStringNull() {
        final InlineObject inline = new InlineObject();
        Assertions.assertEquals("class InlineObject {\n    language: null\n    _file: null\n}", inline.toString());
    }

    @Test
    void setFile() {
        final File file = new File("foo");
        final InlineObject inline = new InlineObject();
        inline.setFile(file);
        Assertions.assertEquals(file, inline.getFile());
    }

    @Test
    void setLanguage() {
        final InlineObject inline = new InlineObject();
        inline.setLanguage("language");
        Assertions.assertEquals("language", inline.getLanguage());
    }

}
