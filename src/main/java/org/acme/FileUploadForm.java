package org.acme;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class FileUploadForm {
    // @RestForm
    // @PartType(MediaType.TEXT_PLAIN)
    // public String description;

    @RestForm("image")
    public FileUpload file;
}
