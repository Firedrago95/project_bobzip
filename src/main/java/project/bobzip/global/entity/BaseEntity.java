package project.bobzip.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class BaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        createdTime = now;
        updatedTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedTime = LocalDateTime.now().withNano(0);
    }
}
