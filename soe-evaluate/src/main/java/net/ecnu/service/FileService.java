package net.ecnu.service;

import net.ecnu.controller.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Result evaluate(MultipartFile file,String text,String pinyin,String mode);

}
