package uz.nt.uzumclone.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.projections.ProductProjection;
import uz.nt.uzumclone.service.FavouriteService;

import java.util.List;

@RestController
@RequestMapping("/wishes")
@RequiredArgsConstructor
public class FavoriteResources {
    private final FavouriteService favouriteService;

    @PostMapping("/like")
    public ResponseDto<Boolean> like(@RequestParam Integer userId,
                                     @RequestParam Integer productId){
        return favouriteService.like(userId,productId);
    }
    @DeleteMapping("/like")
    public ResponseDto<Boolean> unlike(@RequestParam Integer userId,
                                     @RequestParam Integer productId){
        return favouriteService.unlike(userId,productId);
    }
    @GetMapping
    public ResponseDto<List<ProductProjection>> getFavoriteProducts(@RequestParam Integer userId){
        return favouriteService.getFavouriteByUser(userId);
    }
}
