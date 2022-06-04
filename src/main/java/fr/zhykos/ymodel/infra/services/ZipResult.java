package fr.zhykos.ymodel.infra.services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import fr.zhykos.ymodel.infra.models.GeneratedFile;

public class ZipResult {

    public void zip(final List<GeneratedFile> generatedFiles, final OutputStream outputStream) throws IOException {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            for (final GeneratedFile generatedFile : generatedFiles) {
                final ZipEntry zipEntry = new ZipEntry(generatedFile.getFilename());
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(generatedFile.getContents().getBytes());
            }
        }
    }

}
