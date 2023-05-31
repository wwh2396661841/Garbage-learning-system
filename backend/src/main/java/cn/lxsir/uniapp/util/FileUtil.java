package cn.lxsir.uniapp.util;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtil {

    public static String saveFile(String savePath, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = generateUniqueFileName(fileExtension);
        String filePath = savePath + File.separator + uniqueFileName;

        File destFile = new File(filePath);
        file.transferTo(destFile);

        return filePath;
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    private static String generateUniqueFileName(String fileExtension) {
        String uniqueFileName = UUID.randomUUID().toString();
        if (!fileExtension.isEmpty()) {
            uniqueFileName += "." + fileExtension;
        }
        return uniqueFileName;
    }
}
