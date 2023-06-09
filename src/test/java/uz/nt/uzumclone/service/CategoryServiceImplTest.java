package uz.nt.uzumclone.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.nt.uzumclone.additional.AppStatusCodes;
import uz.nt.uzumclone.additional.AppStatusMessages;
import uz.nt.uzumclone.dto.CategoryDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.Category;
import uz.nt.uzumclone.repository.CategoryRepository;
import uz.nt.uzumclone.repository.ProductRepository;
import uz.nt.uzumclone.service.Impl.CategoryServiceImpl;
import uz.nt.uzumclone.service.mapper.BrandMapper;
import uz.nt.uzumclone.service.mapper.CategoryMapper;
import uz.nt.uzumclone.service.mapper.ProductMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void addCategory_ShouldReturnAddedCategory() {
        CategoryDto categoryDto = new CategoryDto();
        Category category = new Category();
        when(categoryMapper.toEntity(categoryDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);


        ResponseDto<CategoryDto> response = categoryService.addCategory(categoryDto);

        assertEquals(categoryDto, response.getData());
        assertEquals(true, response.isSuccess());
        assertEquals(AppStatusMessages.OK, response.getMessage());
    }
    @Test
    void addCategory_ShouldReturnErrorResponse_WhenExceptionIsThrown() {
        CategoryDto categoryDto = new CategoryDto();
        when(categoryMapper.toEntity(categoryDto)).thenThrow(new RuntimeException("Database error"));

        ResponseDto<CategoryDto> response = categoryService.addCategory(categoryDto);

        assertEquals(categoryDto, response.getData());
//        assertEquals(AppStatusMessages.DATABASE_ERROR, response.getMessage());
        assertEquals(AppStatusCodes.DATABASE_ERROR_CODE, response.getCode());
    }


}
