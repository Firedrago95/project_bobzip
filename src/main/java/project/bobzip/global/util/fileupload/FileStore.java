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


    public UploadFile addThumbnail(MultipartFile thumbnail, boolean isRecipeThumbnail) throws IOException {
        if (isRecipeThumbnail) {
            return addFile(thumbnail, recipeThumbnailDir);
        }
        return addFile(thumbnail, stepThumbnailDir);
    }

    public UploadFile updateStepThumbnail(UploadFile uploadFile, MultipartFile multipartFile) throws IOException {
        deleteFile(stepThumbnailDir, uploadFile.getStoredFileName());
        return addFile(multipartFile, stepThumbnailDir);
    }

    public UploadFile updateRecipeThumbnail(UploadFile uploadFile, MultipartFile multipartFile) throws IOException {
        deleteFile(recipeThumbnailDir, uploadFile.getStoredFileName());
        return addFile(multipartFile, recipeThumbnailDir);
    }

    private UploadFile addFile(MultipartFile thumbnail, String fileDir) throws IOException{
        if (thumbnail.isEmpty()) {
            return null;
        }

        String originalFilename = thumbnail.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        thumbnail.transferTo(new File(getStorePath(fileDir, storeFilename)));
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

    private String getStorePath(String fileDir, String storeFilename) {
        return fileDir + storeFilename;
    }

    private void deleteFile(String fileDir, String storeFilename) {
        File file = new File(getStorePath(fileDir, storeFilename));
        if (file.exists()) {
            file.delete();
        }
    }
}
