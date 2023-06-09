package uz.nt.uzumclone.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.nt.uzumclone.additional.AppStatusMessages;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Brand;
import uz.nt.uzumclone.model.Product;
import uz.nt.uzumclone.projections.ProductProjection;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.service.Impl.BrandServiceImpl;
import uz.nt.uzumclone.service.Impl.ProductServiceImpl;
import uz.nt.uzumclone.service.mapper.CategoryMapper;
import uz.nt.uzumclone.service.mapper.ProductMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandServiceImpl brandServices;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getProductById_ProductExists_Success() {
        Integer productId = 1;
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(new ProductDto());

        ResponseDto<ProductDto> response = productService.getProductById(productId);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());

    }

    @Test
    void getProductById_ProductNotFound() {
        Integer productId = 1;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ResponseDto<ProductDto> response = productService.getProductById(productId);

        assertFalse(response.isSuccess());
        assertEquals("Data is not found", response.getMessage());
    }

    @Test
    void getProducts_Success() {
        Integer userId = 1;
        Integer currentPage = 1;
        Integer size = 10;
        List<ProductProjection> products = new ArrayList<>();
        when(productRepository.getProducts(userId, currentPage, size)).thenReturn(products);

        ResponseDto<List<ProductProjection>> response = productService.getProducts(userId, currentPage, size);

        assertTrue(response.isSuccess());
        assertNotNull(response.getData());
    }

    @Test
    void getViewedProduct_Success() {
        Integer userId = 2;
        List<ProductProjection> productViewed = new ArrayList<>();
        when(productRepository.getViewedProducts(userId)).thenReturn(productViewed);

        ResponseDto<List<ProductProjection>> response = productService.getViewedProduct(userId);
        System.out.println(response.getData().isEmpty());
        assertNotNull(response.getData());
    }

//    @Test
//    void addProduct_Success() {
//        ProductDto productDto = new ProductDto();
//        productDto.setName("Test Product");
//        productDto.setAmount(10);
//        productDto.setDescription("Test description");
//
//        Brand brand = new Brand();
//        brand.setName("Test Brand");
//        when(brandServices.addBrand("Test Brand")).thenReturn(brand);
//
//        Product product = new Product();
//        product.setName("Test Product");
//        product.setIsAvailable(true);
//        product.setAmount(10);
//        product.setDescription("Test description");
//        when(productMapper.toEntity(productDto)).thenReturn(product);
//
//        Product savedProduct = new Product();
//        savedProduct.setId(1);
//        savedProduct.setName("Test Product");
//        savedProduct.setIsAvailable(true);
//        savedProduct.setAmount(10);
//        savedProduct.setDescription("Test description");
//        when(productRepository.save(product)).thenReturn(savedProduct);
//
//
//        ResponseDto<ProductDto> response = productService.addProduct(productDto);
//
//        assertTrue(response.isSuccess());
//        assertNotNull(response.getData());
//        assertEquals("Test Product", response.getData().getName());
//    }

    @Test
    void updateProduct_ProductIdNull() {

        ProductDto productDto = new ProductDto();
        productDto.setId(null);

        ResponseDto<ProductDto> response = productService.updateProduct(productDto);

        assertFalse(response.isSuccess());
        assertEquals("Product ID is null", response.getMessage());
    }

    @Test
    void updateProduct_ProductNotFound() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1);

        when(productRepository.findById(1)).thenReturn(Optional.empty());

        ResponseDto<ProductDto> response = productService.updateProduct(productDto);

        assertFalse(response.isSuccess());
        assertEquals(AppStatusMessages.NOT_FOUND, response.getMessage());
    }
}
