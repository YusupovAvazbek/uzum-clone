package uz.nt.uzumclone.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.ProductServiceImpl;
import java.util.List;
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchResources {
    private final ProductServiceImpl productService;
    @GetMapping()
    public ResponseDto<Page<ProductDto>> search(@RequestParam String query,
                                                @RequestParam List<String> filter,
                                                @RequestParam(required = false) String sorting,
                                                @RequestParam(required = false) String ordering,
                                                @RequestParam(required = false,defaultValue = "10") Integer size,
                                                @RequestParam(required = false, defaultValue = "0") Integer currentPage){
        return productService.universalSearch(query,filter, sorting, ordering,size,currentPage);
    }
}
