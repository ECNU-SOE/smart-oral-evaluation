package net.ecnu.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Object uploadVoiceRecord(MultipartFile file);

    void evaluate();
}
