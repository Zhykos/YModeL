package fr.zhykos.ymodel.infrastructure.services;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SemanticListExceptionTests {

    @Test
    @DisplayName("Mandate a list of exceptions")
    void test() {
        final List<SemanticException> emptyList = Collections.emptyList();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new SemanticListException(emptyList));
    }

}
