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
package fr.zhykos.ymodel.infrastructure.openapi;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EClass;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

import fr.zhykos.ymodel.commons.Returns;
import fr.zhykos.ymodel.domain.models.ITemplateClass;
import fr.zhykos.ymodel.domain.services.IGenerationService;
import fr.zhykos.ymodel.domain.services.typescript.GenerationTypescriptService;
import fr.zhykos.ymodel.infrastructure.models.GeneratedFile;
import fr.zhykos.ymodel.infrastructure.models.yml.YmlMetaModel;
import fr.zhykos.ymodel.infrastructure.openapi.api.MetamodelApi;
import fr.zhykos.ymodel.infrastructure.openapi.model.Language;
import fr.zhykos.ymodel.infrastructure.services.GenerationException;
import fr.zhykos.ymodel.infrastructure.services.GenerationService;
import fr.zhykos.ymodel.infrastructure.services.ParsingService;
import fr.zhykos.ymodel.infrastructure.services.SemanticListException;
import fr.zhykos.ymodel.infrastructure.services.SyntaxException;
import fr.zhykos.ymodel.infrastructure.services.TransformationService;
import fr.zhykos.ymodel.infrastructure.services.ZipResultService;

public class MetamodelService implements MetamodelApi {

    @Override
    public File generateMetamodel(final GenerateMetamodelMultipartForm multipartForm) {
        final Returns<YmlMetaModel, SyntaxException> parsingResult = new ParsingService().parse(multipartForm._file);
        try {
            final Path memoryZipPath = transformMetaModel(parsingResult.then(), multipartForm.language);
            return null; // TODO
        } catch (Exception e) {
            throw new WebApplicationException("Cannot generate metamodel", e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private static Path transformMetaModel(final YmlMetaModel ymlMetamodel, final Language targetLanguage)
            throws GenerationException, IOException, SemanticListException {
        final Returns<List<EClass>, SemanticListException> transformationResult = new TransformationService()
                .transform(ymlMetamodel);
        return generateEClasses(transformationResult.then(), targetLanguage);
    }

    private static Path generateEClasses(final List<EClass> eClasses, final Language targetLanguage)
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

    private static Path zipResults(final List<GeneratedFile> generatedFiles) throws IOException {
        try (FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix())) {
            final Path directoryZipPath = fileSystem.getPath("zip");
            Files.createDirectory(directoryZipPath);
            final Path memoryZipPath = Files.createTempFile(directoryZipPath, "result", ".zip");
            try (OutputStream outputStream = Files.newOutputStream(memoryZipPath)) {
                new ZipResultService().zip(generatedFiles, outputStream);
            }
            return memoryZipPath;
        }
    }

}