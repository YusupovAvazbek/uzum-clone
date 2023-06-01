package uz.nt.uzumclone.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.CartDto;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.CartServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartResources {
    private final CartServiceImpl cartService;
    @GetMapping("/{id}")
    public ResponseDto<List<ProductDto>> getCartUser(@PathVariable Integer id){
        return cartService.getUserCart(id);
    }
    @PostMapping
    public ResponseDto<CartDto> addToCart(@RequestParam Integer cartId,
                                          @RequestParam Integer productId){
        return cartService.addToCart(cartId, productId);
    }
}
