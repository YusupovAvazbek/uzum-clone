package uz.nt.uzumclone.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.OrderDetailsDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.OrderDetailsServiceImpl;

@RestController
@RequestMapping("order-details")
@RequiredArgsConstructor
public class OrderDetailsResources {

    private final OrderDetailsServiceImpl orderDetailsService;

    @PostMapping("add")
    public ResponseDto<OrderDetailsDto> add(@RequestBody @Valid OrderDetailsDto orderDetailsDto){
        return orderDetailsService.add(orderDetailsDto);
    }

    @GetMapping()
    public ResponseDto<Page<OrderDetailsDto>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size){
        return orderDetailsService.getAll(page, size);
    }

    @GetMapping("{id}")
    public ResponseDto<OrderDetailsDto> getById(@PathVariable Integer id){
        return orderDetailsService.getById(id);
    }

    @PatchMapping("update")
    public ResponseDto<OrderDetailsDto> update(@RequestBody OrderDetailsDto orderDetailsDto){
        return orderDetailsService.update(orderDetailsDto);
    }

    @DeleteMapping()
    public ResponseDto<Void> delete(@RequestParam Integer id){
        return orderDetailsService.delete(id);
    }

}
