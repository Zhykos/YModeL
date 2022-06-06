package fr.zhykos.ymodel.infrastructure.services;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import fr.zhykos.ymodel.infrastructure.models.GeneratedFile;

/**
 * Service to create a zip file with the generated files in it
 */
public class ZipResultService {

    /**
     * Create a zip file with the generated files in it
     *
     * @param generatedFiles Generated files to zip
     * @param outputStream   Stream in which the zip file is created
     * @throws IOException Error while creating the zip
     */
    public void zip(final List<GeneratedFile> generatedFiles, final OutputStream outputStream) throws IOException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            for (final GeneratedFile generatedFile : generatedFiles) {
                final ZipEntry zipEntry = new ZipEntry(generatedFile.getFilename());
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(generatedFile.getContents().getBytes(StandardCharsets.UTF_8));
            }
        }
    }

}
