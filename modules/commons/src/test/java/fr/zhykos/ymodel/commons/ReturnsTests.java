package fr.zhykos.ymodel.commons;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReturnsTests {

    @Test
    @DisplayName("Create a Returns with a Result")
    void resolve() throws Exception {
        // Given
        final String result = "foo";

        // When
        final Returns<String, Exception> returns = Returns.resolve(result);

        // Then
        Assertions.assertEquals(result, returns.then());
    }

    @Test
    @DisplayName("Create a Returns with an Exception")
    void reject() throws Exception {
        // When
        final Returns<String, IOException> returns = Returns.reject(new IOException());

        // Then
        Assertions.assertThrows(IOException.class, () -> returns.catchh());
    }

}
