package uz.nt.uzumclone.service.mapper;

import uz.nt.uzumclone.dto.ProductVariantDto;

public interface CommonMapper<D, E> {
    D toDto(E e);
    E toEntity(D d);
}
