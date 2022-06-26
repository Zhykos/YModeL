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
package fr.zhykos.ymodel.infrastructure.services;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.domain.models.ITemplateClass;
import fr.zhykos.ymodel.domain.services.IGenerationService;
import fr.zhykos.ymodel.domain.services.typescript.GenerationTypescriptService;
import fr.zhykos.ymodel.infrastructure.models.ELanguage;
import fr.zhykos.ymodel.infrastructure.models.GeneratedFile;
import fr.zhykos.ymodel.infrastructure.models.yml.YmlMetaModel;

public class YmodelService {

    public byte[] generateMetamodel(final File ymlFile, final ELanguage targetLanguage)
            throws GenerationException, IOException, SemanticListException, SyntaxException {
        final Returns<YmlMetaModel, SyntaxException> parsingResult = new ParsingService().parse(ymlFile);
        return transformMetaModel(parsingResult.then(), targetLanguage);
    }

    private static byte[] transformMetaModel(final YmlMetaModel ymlMetamodel, final ELanguage targetLanguage)
            throws GenerationException, IOException, SemanticListException {
        final Returns<List<EClass>, SemanticListException> transformationResult = new TransformationService()
                .transform(ymlMetamodel);
        return generateEClasses(transformationResult.then(), targetLanguage);
    }

    private static byte[] generateEClasses(final List<EClass> eClasses, final ELanguage targetLanguage)
            throws GenerationException, IOException {
        // final IGenerationService<?, ?> generationService = switch
        // (targetLanguage.getName()) {
        // case TYPESCRIPT -> new GenerationTypescriptService();
        // };
        final IGenerationService<? extends ITemplateClass, ?> generationService = new GenerationTypescriptService();
        final List<Returns<GeneratedFile, GenerationException>> generationResult = GenerationService.generate(eClasses,
                generationService);
        final List<GeneratedFile> generatedFiles = new ArrayList<>();
        for (final Returns<GeneratedFile, GenerationException> returns : generationResult) {
            generatedFiles.add(returns.then());
        }
        return zipResults(generatedFiles);
    }

    private static byte[] zipResults(final List<GeneratedFile> generatedFiles) throws IOException {
        try (FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix())) {
            final Path directoryZipPath = fileSystem.getPath("zip");
            Files.createDirectory(directoryZipPath);
            final Path memoryZipPath = Files.createTempFile(directoryZipPath, "result", ".zip");
            try (OutputStream outputStream = Files.newOutputStream(memoryZipPath)) {
                new ZipResultService().zip(generatedFiles, outputStream);
            }
            return Files.readAllBytes(memoryZipPath);
        }
    }

}
