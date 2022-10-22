package net.ecnu.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Request {
    private MultipartFile audio;
    private String text;
    private String pinyin;
    private String mode;
}
