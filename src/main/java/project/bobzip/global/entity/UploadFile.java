package project.bobzip.global.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
public class UploadFile {

    private String uploadFileName;
    private String storedFileName;

    public UploadFile() {
        uploadFileName = "default.png";
        storedFileName = "default.png";
    }

    public UploadFile(String uploadFileName, String storedFileName) {
        this.uploadFileName = uploadFileName;
        this.storedFileName = storedFileName;
    }
}
