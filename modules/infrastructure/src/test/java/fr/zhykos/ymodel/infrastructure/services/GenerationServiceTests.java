package fr.zhykos.ymodel.infrastructure.services;

import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.zhykos.ymodel.domain.services.typescript.GenerationTypescriptService;
import fr.zhykos.ymodel.commons.Returns;

class GenerationServiceTests {

    @Test
    @DisplayName("Fails with an exception while executing template")
    void exceptionExecutingTemplate() throws Exception {
        final GenerationTypescriptService mockGenerationService = Mockito.mock(GenerationTypescriptService.class);
        Mockito.doThrow(new RuntimeException()).when(mockGenerationService).getTemplateContents();

        final Returns<String, GenerationException> generation = GenerationService.generate(
                EcoreFactory.eINSTANCE.createEClass(), mockGenerationService);
        Assertions.assertThrows(GenerationException.class, () -> generation.catchh());
    }

}
