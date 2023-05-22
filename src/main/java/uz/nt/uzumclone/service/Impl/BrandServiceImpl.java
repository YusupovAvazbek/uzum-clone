package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.additional.AppStatusCodes;
import uz.nt.uzumclone.additional.AppStatusMessages;
import uz.nt.uzumclone.dto.BrandDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.exceptions.DatabaseException;
import uz.nt.uzumclone.model.Brand;
import uz.nt.uzumclone.repository.BrandRepository;
import uz.nt.uzumclone.service.BrandServices;
import uz.nt.uzumclone.service.mapper.BrandMapper;

import java.util.List;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandServices {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public Brand addBrand(String name) {
        return brandRepository.addIfNewBrand(name)
                .orElseThrow(() -> new DatabaseException("Could not save brand to database!"));
    }

    @Override
    public List<BrandDto> getAllBrands() {
        return brandRepository.findAllBrands().stream().map(brandMapper::toDto).toList();
    }
}
