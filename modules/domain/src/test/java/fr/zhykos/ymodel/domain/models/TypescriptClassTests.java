package fr.zhykos.ymodel.domain.models;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.zhykos.ymodel.domain.models.typescript.TypescriptClass;

class TypescriptClassTests {

    @Test
    @DisplayName("Class does not have imports")
    void hasNoImports() {
        final TypescriptClass typescriptClass = new TypescriptClass();
        Assertions.assertFalse(typescriptClass.hasImports());
    }

    @Test
    @DisplayName("Class has imports")
    void hasImports() {
        final TypescriptClass typescriptClass = new TypescriptClass();
        typescriptClass.getImports().add("foo");
        Assertions.assertIterableEquals(List.of("foo"), typescriptClass.getImports());
        Assertions.assertTrue(typescriptClass.hasImports());
    }

}
