package net.ecnu.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

public class FileUtil {
    public static File MultipartFile2File(MultipartFile multipartFile) {
        File file = null;
        //判断是否为null
        if (multipartFile.equals("") || multipartFile.getSize() <= 0) {
            return file;
        }
        //MultipartFile转换为File
        InputStream ins = null;
        OutputStream os = null;
        try {
            ins = multipartFile.getInputStream();
            file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public static byte[] File2byte(File tradeFile) {
        byte[] buffer = null;
        try (FileInputStream fis = new FileInputStream(tradeFile);
             ByteArrayOutputStream bos = new ByteArrayOutputStream();) {
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    public static File transferToFile(MultipartFile multipartFile) {
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            originalFilename = originalFilename != null ? originalFilename : "tmpFile";
            String[] filename = originalFilename.split("\\.");
            file = File.createTempFile(filename[0], "." + filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
