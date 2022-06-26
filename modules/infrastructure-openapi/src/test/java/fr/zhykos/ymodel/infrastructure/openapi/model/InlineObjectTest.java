package fr.zhykos.ymodel.infrastructure.openapi.model;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.infrastructure.openapi.model.InlineObject;
import fr.zhykos.ymodel.infrastructure.openapi.model.Language;

class InlineObjectTest {

    @Test
    void checkToString() {
        final Language language = new Language().name(Language.NameEnum.TYPESCRIPT);
        final InlineObject inline = new InlineObject().language(language)._file(new File("foo"));
        Assertions.assertEquals("class InlineObject {\n    language: class Language {\n        name: typescript\n    }\n    _file: foo\n}", inline.toString());
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
        final Language language = new Language().name(Language.NameEnum.TYPESCRIPT);
        final InlineObject inline = new InlineObject();
        inline.setLanguage(language);
        Assertions.assertEquals(language, inline.getLanguage());
    }

}
