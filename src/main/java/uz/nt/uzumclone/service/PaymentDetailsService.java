package uz.nt.uzumclone.service;

import org.springframework.data.domain.Page;
import uz.nt.uzumclone.dto.OrderDetailsDto;
import uz.nt.uzumclone.dto.PaymentDetailsDto;
import uz.nt.uzumclone.dto.ResponseDto;

public interface PaymentDetailsService {
    ResponseDto<PaymentDetailsDto> add(PaymentDetailsDto paymentDetailsDto);
    ResponseDto<Page<PaymentDetailsDto>> getAll(Integer page, Integer size);
    ResponseDto<PaymentDetailsDto> getById(Integer id);
    ResponseDto<Void> delete(Integer id);
    ResponseDto<PaymentDetailsDto> update(PaymentDetailsDto paymentDetailsDto);
}
