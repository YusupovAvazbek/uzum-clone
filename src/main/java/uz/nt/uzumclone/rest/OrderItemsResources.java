package uz.nt.uzumclone.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.OrderItemsDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.OrderItemsService;

@RestController
@RequestMapping("order-items")
@RequiredArgsConstructor
public class OrderItemsResources {
    private final OrderItemsService orderItemsService;

    @PostMapping("add")
    public ResponseDto<OrderItemsDto> add(@RequestBody @Valid OrderItemsDto orderItemsDto){
        return orderItemsService.add(orderItemsDto);
    }

    @GetMapping()
    public ResponseDto<Page<OrderItemsDto>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size){
        return orderItemsService.getAll(page, size);
    }

    @GetMapping("{id}")
    public ResponseDto<OrderItemsDto> getById(@PathVariable Integer id){
        return orderItemsService.getById(id);
    }

    @PatchMapping("update")
    public ResponseDto<OrderItemsDto> update(@RequestBody OrderItemsDto orderItemsDto){
        return orderItemsService.update(orderItemsDto);
    }

    @DeleteMapping()
    public ResponseDto<Void> delete(@RequestParam Integer id){
        return orderItemsService.delete(id);
    }
}
