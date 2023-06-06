package uz.nt.uzumclone.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.dto.UploadImageDto;
import uz.nt.uzumclone.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageResources {
    private final ImageService imageService;

    @PostMapping(consumes = {"multipart/form-data", "application/json"})
    public ResponseDto<Integer> uploadFile(@ModelAttribute UploadImageDto uploadImageDto){
        return imageService.fileUpload(uploadImageDto);
    }

    @GetMapping
    public ResponseDto<byte[]> getFileById(@RequestParam Integer id,@RequestParam String size) throws IOException {
        return imageService.getFileById(id,size);
    }
}
