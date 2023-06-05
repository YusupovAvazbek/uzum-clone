package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.projections.ProductProjection;
import uz.nt.uzumclone.repository.FavoriteCustomRepository;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.service.FavouriteService;

import java.util.List;

import static uz.nt.uzumclone.additional.AppStatusCodes.OK_CODE;
import static uz.nt.uzumclone.additional.AppStatusMessages.OK;
@Service
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {
    private final FavoriteCustomRepository favoriteCustomRepository;
    @Override
    public ResponseDto<List<ProductProjection>> getFavouriteByUser(Integer userId) {
        try {
            List<ProductProjection> products = favoriteCustomRepository.getFavouriteByUser(userId);
            return ResponseDto.<List<ProductProjection>>builder()
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .data(products)
                    .build();
        }catch (Exception e){
            return ResponseDto.<List<ProductProjection>>builder()
                    .success(false)
                    .code(OK_CODE)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Boolean> like(Integer userId, Integer productId) {
        boolean like = favoriteCustomRepository.like(userId, productId);
        if(like){
            return ResponseDto.<Boolean>builder()
                    .message(OK)
                    .success(true)
                    .build();
        }else{
            return ResponseDto.<Boolean>builder()
                    .message(OK)
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<Boolean> unlike(Integer userId, Integer productId) {
        return null;
    }
}
