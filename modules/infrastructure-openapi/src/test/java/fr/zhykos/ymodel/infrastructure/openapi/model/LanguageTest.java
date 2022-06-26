package fr.zhykos.ymodel.infrastructure.openapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.infrastructure.openapi.model.Language;

class LanguageTest {

    @Test
    void checkToString() {
        final Language language = new Language().name(Language.NameEnum.TYPESCRIPT);
        Assertions.assertEquals("class Language {\n    name: typescript\n}", language.toString());
    }

    @Test
    void toStringNull() {
        final Language language = new Language();
        Assertions.assertEquals("class Language {\n    name: null\n}", language.toString());
    }

    @Test
    void name() {
        final Language language = new Language();
        language.setName(Language.NameEnum.TYPESCRIPT);
        Assertions.assertEquals(Language.NameEnum.TYPESCRIPT, language.getName());
    }

}
