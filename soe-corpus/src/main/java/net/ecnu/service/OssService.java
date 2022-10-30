package net.ecnu.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    Object uploadAudio(MultipartFile file);
}
