package uz.nt.uzumclone.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.PaymentDetailsDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.PaymentDetailsServiceImpl;

@RestController
@RequestMapping("payment-details")
@RequiredArgsConstructor
public class PaymentDetailsResources {

    private final PaymentDetailsServiceImpl paymentDetailsService;

    @PostMapping("add")
    public ResponseDto<PaymentDetailsDto> add(@RequestBody @Valid PaymentDetailsDto paymentDetailsDto){
        return paymentDetailsService.add(paymentDetailsDto);
    }

    @GetMapping()
    public ResponseDto<Page<PaymentDetailsDto>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size){
        return paymentDetailsService.getAll(page, size);
    }

    @GetMapping("{id}")
    public ResponseDto<PaymentDetailsDto> getById(@PathVariable Integer id){
        return paymentDetailsService.getById(id);
    }

    @PatchMapping("update")
    public ResponseDto<PaymentDetailsDto> update(@RequestBody PaymentDetailsDto paymentDetailsDto){
        return paymentDetailsService.update(paymentDetailsDto);
    }

    @DeleteMapping()
    public ResponseDto<Void> delete(@RequestParam Integer id){
        return paymentDetailsService.delete(id);
    }
}
