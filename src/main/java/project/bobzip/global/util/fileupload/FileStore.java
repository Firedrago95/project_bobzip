package project.bobzip.global.util.fileupload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.bobzip.global.entity.UploadFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${recipeThumbnail.dir}")
    private String recipeThumbnailDir;
    @Value("${stepThumbnail.dir}")
    private String stepThumbnailDir;

    public String getRecipeThumbnailPath(String filename) {return recipeThumbnailDir + filename;}

    public String getStepThumbnailPath(String filename) {return stepThumbnailDir + filename;}

    public UploadFile addThumbnail(MultipartFile thumbnail) throws IOException {
        if (thumbnail.isEmpty()) {
            return null;
        }


        String originalFilename = thumbnail.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        thumbnail.transferTo(new File(getRecipeThumbnailPath(storeFilename)));
        return new UploadFile(originalFilename, storeFilename);
    }

    private String createStoreFilename(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index + 1);
    }
}
