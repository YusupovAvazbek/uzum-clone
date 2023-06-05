package uz.nt.uzumclone.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.projections.ProductProjection;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.service.Impl.ProductServiceImpl;
import uz.nt.uzumclone.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResources {

    private final ProductServiceImpl productService;

    @PostMapping
    public ResponseDto<ProductDto> addProduct(@RequestBody @Valid ProductDto productDto){
        return productService.addProduct(productDto);
    }

    @PatchMapping
    public ResponseDto<ProductDto> updateProduct(@RequestBody ProductDto productDto){
        return productService.updateProduct(productDto);
    }

//    @GetMapping
//    public ResponseDto<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "10",required = false) Integer size,
//                                                        @RequestParam(defaultValue = "0",required = false) Integer page){
//        return productService.getAllProducts(page, size);
//    }
    @GetMapping
    public ResponseDto<List<ProductProjection>> getProducts(@RequestParam Integer userId){
        return productService.getProducts(userId);
    }
    @GetMapping("with-native")
    public ResponseDto<List<ProductProjection>> getProductsWithNative(@RequestParam Integer userId){
        return productService.getProductsWithNativeQuery(userId);
    }

    @GetMapping("with-jpql")
    public ResponseDto<List<ProductProjection>> getProductsWithJPQL(@RequestParam Integer userId){
        return productService.getProductsWithJPQL(userId);
    }
    @GetMapping("/{id}")
    public ResponseDto<ProductDto> getProductById(@PathVariable Integer id){
        return productService.getProductById(id);
    }
    @GetMapping("viewed")
    public ResponseDto<List<ProductProjection>> getViewedProduct(@RequestParam Integer userId){
        return productService.getViewedProduct(userId);
    }

}
