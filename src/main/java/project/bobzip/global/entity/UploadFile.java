package project.bobzip.global.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
public class UploadFile {

    private String uploadFileName;
    private String storedFileName;

    public UploadFile() {}
    public UploadFile(String uploadFileName, String storedFileName) {
        this.uploadFileName = uploadFileName;
        this.storedFileName = storedFileName;
    }
}
