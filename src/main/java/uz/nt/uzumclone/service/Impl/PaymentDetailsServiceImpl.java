package uz.nt.uzumclone.service.Impl;

import org.springframework.data.domain.Page;
import uz.nt.uzumclone.dto.PaymentDetailsDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.PaymentDetailsService;

public class PaymentDetailsServiceImpl implements PaymentDetailsService {
    @Override
    public ResponseDto<PaymentDetailsDto> add(PaymentDetailsDto paymentDetailsDto) {
        return null;
    }

    @Override
    public ResponseDto<Page<PaymentDetailsDto>> getAll(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseDto<PaymentDetailsDto> getById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto<Void> delete(Integer id) {
        return null;
    }

    @Override
    public ResponseDto<PaymentDetailsDto> update(PaymentDetailsDto paymentDetailsDto) {
        return null;
    }
}
