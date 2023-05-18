package uz.nt.uzumclone.service;

import uz.nt.uzumclone.dto.CartDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.dto.UsersDto;
import uz.nt.uzumclone.model.Cart;
import uz.nt.uzumclone.model.Product;
import uz.nt.uzumclone.model.Users;

import java.util.List;

public interface CartService {
    Boolean createCart(Users user);
    ResponseDto<CartDto> addToCart(Integer cartId, Integer productId);
    void removeFromCart(Cart cart, Product product);
    void clearCart(Cart cart);
    ResponseDto<List<ProductDto>> getUserCart(Integer userId);
}
