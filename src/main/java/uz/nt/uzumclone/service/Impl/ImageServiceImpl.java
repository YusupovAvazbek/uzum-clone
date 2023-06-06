package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.uzumclone.additional.AppStatusMessages;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.dto.UploadImageDto;
import uz.nt.uzumclone.model.Image;
import uz.nt.uzumclone.model.ProductDetails;
import uz.nt.uzumclone.repository.ImageRepository;
import uz.nt.uzumclone.service.ImageService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ExecutorService executorService;
    @Override
    public ResponseDto<Integer> fileUpload(UploadImageDto file) {
        Image imageEntity = new Image();
        imageEntity.setExt(file.getImage().getOriginalFilename().substring(file.getImage().getOriginalFilename().lastIndexOf(".")));
        imageEntity.setCreatedAt(LocalDateTime.now());

        try{
            Future<String> large = executorService.submit(()-> {
                System.out.println("task 1 running: "+Thread.currentThread().getName()+" thread");
                return saveLargeSize(file.getImage(), imageEntity.getExt());
            });
            Future<String> medium = executorService.submit(()-> {
                System.out.println("task 2 running: "+Thread.currentThread().getName()+" thread");
                return saveMediumSize(file.getImage(), imageEntity.getExt());
            });
            Future<String> small = executorService.submit(()-> {
                System.out.println("task 3 running: "+Thread.currentThread().getName()+" thread");
                return saveSmallSize(file.getImage(), imageEntity.getExt());
            });

            imageEntity.setPathLarge(large.get());
            imageEntity.setPathMedium(medium.get());
            imageEntity.setPathSmall(small.get());

            imageEntity.setProductDetails(new ProductDetails(1));

            executorService.shutdown();
            Image savedImage = imageRepository.save(imageEntity);

            return ResponseDto.<Integer>builder()
                    .data(savedImage.getId())
                    .message(OK)
                    .success(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.<Integer>builder()
                    .code(UNEXPECTED_ERROR_CODE)
                    .message(UNEXPECTED_ERROR)
                    .message("Error while saving file: " + e.getMessage())
                    .build();
        }

    }

    @Override
    public ResponseDto<byte[]> getFileById(Integer fileId, String size) throws IOException {
        if (fileId == null || size == null) {
            return ResponseDto.<byte[]>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }
        FileInputStream image = null;

        Optional<Image> optional = imageRepository.findById(fileId);

        if (optional.isEmpty()) {
            return ResponseDto.<byte[]>builder()
                    .message(AppStatusMessages.NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        }
        if (size.toUpperCase().equals("LARGE")){
            String pathlarge = optional.get().getPathLarge();
            image = new FileInputStream(pathlarge);
        }
        if (size.toUpperCase().equals("MEDIUM")) {
            String pathmedium = optional.get().getPathMedium();
            image = new FileInputStream(pathmedium);
        }
        if (size.toUpperCase().equals("SMALL")) {
            String pathsmall = optional.get().getPathSmall();
            image = new FileInputStream(pathsmall);
        }


        byte[] file = image.readAllBytes();

        return ResponseDto.<byte[]>builder()
                .message(AppStatusMessages.OK)
                .code(OK_CODE)
                .data(file)
                .success(true)
                .build();
    }
    private String saveLargeSize(MultipartFile file, String ext) {
        String filePathLarge;
        try {
            Files.copy(file.getInputStream(), Path.of(filePathLarge = imagePath("images/large", ext)));
            System.out.println(Thread.currentThread().getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePathLarge;
    }

    private String saveMediumSize(MultipartFile file, String ext) {
        String filePathMedium;
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file.getInputStream());
            BufferedImage bufferedImage1 = resizeImage(bufferedImage, bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
            ImageIO.write(bufferedImage1, ext.substring(1), new java.io.File(filePathMedium = imagePath("images/medium", ext)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePathMedium;
    }

    private String saveSmallSize(MultipartFile file, String ext) {
        String filePathSmall;
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file.getInputStream());
            BufferedImage bufferedImage1 = resizeImage(bufferedImage, bufferedImage.getWidth() / 2 / 2, bufferedImage.getHeight() / 2 / 2);
            ImageIO.write(bufferedImage1, ext.substring(1), new java.io.File(filePathSmall = imagePath("images/small", ext)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePathSmall;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        return resizedImage;
    }
    public static String imagePath(String folder, String ext) {
        LocalDate localDate = LocalDate.now();
        String path = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        java.io.File file = new java.io.File(folder + "/" + path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uuid = UUID.randomUUID().toString();
        return file.getPath() + "\\" + uuid + ext;
    }

}
