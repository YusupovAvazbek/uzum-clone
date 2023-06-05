package uz.nt.uzumclone.service;

import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.projections.ProductProjection;
import java.util.List;
public interface FavouriteService {
    ResponseDto<List<ProductProjection>> getFavouriteByUser(Integer userId);
    ResponseDto<Boolean> like(Integer userId, Integer productId);

    ResponseDto<Boolean> unlike(Integer userId, Integer productId);
}
