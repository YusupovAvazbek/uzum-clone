package uz.nt.uzumclone.repository;

import uz.nt.uzumclone.projections.ProductProjection;

import java.util.List;

public interface FavoriteCustomRepository {
    boolean like(Integer userId, Integer productId);
    List<ProductProjection> getFavouriteByUser(Integer userId);
    boolean unlike(Integer userId, Integer productId);
}
