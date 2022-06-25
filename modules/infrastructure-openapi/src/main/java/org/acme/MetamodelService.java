package org.acme;

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