package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.additional.AppStatusCodes;
import uz.nt.uzumclone.additional.AppStatusMessages;
import uz.nt.uzumclone.dto.CartDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Cart;
import uz.nt.uzumclone.model.Product;
import uz.nt.uzumclone.model.Users;
import uz.nt.uzumclone.repository.CartRepository;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.repository.UsersRepository;
import uz.nt.uzumclone.service.CartService;
import uz.nt.uzumclone.service.mapper.CartMapper;
import uz.nt.uzumclone.service.mapper.ProductMapper;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final UsersRepository usersRepository;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    @Override
    public Boolean createCart(Users user) {
        try {
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public ResponseDto<CartDto> addToCart(Integer id, Integer productId) {
        try {
            Optional<Cart> byId = cartRepository.findById(id);
            Optional<Product> byId1 = productRepository.findById(productId);

            Cart cart = byId.get();
            cart.getProducts().add(byId1.get());
            cart.setTotalPrice((int) (cart.getTotalPrice() + byId1.get().getPrice()));
            Cart save = cartRepository.save(cart);
            return ResponseDto.<CartDto>builder()
                    .code(AppStatusCodes.OK_CODE)
                    .success(true)
                    .message(AppStatusMessages.OK)
                    .data(cartMapper.toDto(save))
                    .build();
        }catch (Exception e){
            return ResponseDto.<CartDto>builder()
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public void removeFromCart(Cart cart, Product product) {

    }

    @Override
    public void clearCart(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public ResponseDto<List<ProductDto>> getUserCart(Integer userId) {
        Optional<Users> byId = usersRepository.findById(userId);
        if(byId.isEmpty()){
            return ResponseDto.<List<ProductDto>>builder()
                    .build();
        }
        Optional<Cart> byUserId = cartRepository.findByUserId(userId);
        return ResponseDto.<List<ProductDto>>builder()
                .data(byUserId.get().getProducts().stream().map(pr->productMapper.toDto(pr)).toList())
                .build();
    }
}
