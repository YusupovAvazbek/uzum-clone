package uz.nt.uzumclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.uzumclone.enums.ImageQualityType;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UploadImageDto {
    private Integer ProductDetailId;
    private ImageQualityType quality;
    private MultipartFile image;

}
