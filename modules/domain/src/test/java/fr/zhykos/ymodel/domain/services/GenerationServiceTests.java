package fr.zhykos.ymodel.domain.services;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GenerationServiceTests {

    private final IGenerationService<Object, Object> generationService = new IGenerationService<>() {
        @Override
        public String getTemplateName() {
            return null;
        }

        @Override
        public String generateClass(final String className, final Optional<String> inheritedClassName) {
            return null;
        }

        @Override
        public void generateField(final Object containingClass, final String fieldName, final String fieldType) {
            // Do nothing
        }

        @Override
        public String generateMethod(final Object containingClass, final String methodName,
                final String methodReturnsType) {
            return null;
        }

        @Override
        public void generateMethodParameter(final Object containingClass, final Object containingMethod,
                final String parameterName, final String parameterType) {
            // Do nothing
        }
    };

    @Test
    @DisplayName("Template contents")
    void getTemplateContents() throws IOException {
        final IGenerationService<Object, Object> serviceSpy = Mockito.spy(generationService);
        Mockito.doReturn("typescript").when(serviceSpy).getTemplateName();
        Assertions.assertNotNull(serviceSpy.getTemplateContents());
        Mockito.verify(serviceSpy).getTemplateName();
    }

    @Test
    @DisplayName("Template contents exception")
    void getTemplateContentsException() {
        final IGenerationService<Object, Object> serviceSpy = Mockito.spy(generationService);
        Mockito.doReturn("foo").when(serviceSpy).getTemplateName();
        Assertions.assertThrows(IOException.class, () -> serviceSpy.getTemplateContents());
    }

    @Test
    @DisplayName("Post method generation does nothing")
    void postMethodGeneration() {
        final Object parameterMock = Mockito.mock(Object.class);
        generationService.postMethodGeneration(parameterMock);
        Mockito.verifyNoInteractions(parameterMock);
    }

}
