package project.bobzip.global.config;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
public class JasyptConfig {

    private final Environment env;

    public JasyptConfig(Environment env) {
        this.env = env;
    }

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor() {

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        // 시스템 속성에서 암호화 비밀번호를 가져옵니다.
        String encryptKey = env.getProperty("jasypt.encryptor.password");

        // 로그로 암호화 키가 전달되었는지 확인
        if (encryptKey == null || encryptKey.isEmpty()) {
            log.error("The encryption key (jasypt.encryptor.password) is not set or is empty!");
        } else {
            log.info("The encryption key (jasypt.encryptor.password) has been set successfully.");
        }

        config.setPassword(encryptKey); // 암호키가 필요한 method -> 암호키 필드를 값으로 전달
        config.setAlgorithm("PBEWithMD5AndDES"); // 처리에 필요한 알고리즘
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        // Log decryption status for specific properties
        logDecryptionStatus(encryptor, "spring.datasource.url");
        logDecryptionStatus(encryptor, "spring.datasource.username");
        logDecryptionStatus(encryptor, "spring.datasource.password");

        return encryptor;
    }

    private void logDecryptionStatus(StringEncryptor encryptor, String propertyKey) {
        String encryptedProperty = env.getProperty(propertyKey);
        if (encryptedProperty != null && encryptedProperty.startsWith("ENC(") && encryptedProperty.endsWith(")")) {
            try {
                String decryptedProperty = encryptor.decrypt(encryptedProperty.substring(4, encryptedProperty.length() - 1));
                log.info("Successfully decrypted property: {}", propertyKey);
            } catch (Exception e) {
                log.error("Failed to decrypt property: {}", propertyKey, e);
            }
        } else {
            log.warn("Property {} is not encrypted or has an invalid format", propertyKey);
        }
    }
}
