package uz.nt.uzumclone.service;

import uz.nt.uzumclone.dto.BrandDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Brand;

import java.util.List;

public interface BrandServices {

    Brand addBrand(String name);

    List<BrandDto> getAllBrands();
}
