package uz.nt.uzumclone.service;

import org.springframework.web.multipart.MultipartFile;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.dto.UploadImageDto;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ImageService {
    ResponseDto<Integer> fileUpload(UploadImageDto file);
    ResponseDto<byte[]> getFileById(Integer id,String size) throws IOException;
}
