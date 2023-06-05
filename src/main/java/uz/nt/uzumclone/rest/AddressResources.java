package uz.nt.uzumclone.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.AddressDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.AddressService;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressResources {
    private final AddressService addressService;

    @PostMapping
    public ResponseDto<AddressDto> addAddress(@RequestBody AddressDto addressDto) {
        return addressService.add(addressDto);
    }

    @PatchMapping
    public ResponseDto<AddressDto> updateAddress(@RequestBody AddressDto addressDto){
        return addressService.update(addressDto);
    }
    @DeleteMapping("/{id}")
    public ResponseDto<Void> deleteAddress(@PathVariable Integer id){
        return  addressService.delete(id);
    }
    @GetMapping("/{id}")
    public ResponseDto<AddressDto> getById(@PathVariable Integer id){
        return addressService.getById(id);
    }
}
