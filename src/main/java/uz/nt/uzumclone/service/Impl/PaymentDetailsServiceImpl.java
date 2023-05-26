package uz.nt.uzumclone.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.nt.uzumclone.dto.PaymentDetailsDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.model.PaymentDetails;
import uz.nt.uzumclone.repository.PaymentDetailsRepository;
import uz.nt.uzumclone.service.PaymentDetailsService;
import uz.nt.uzumclone.service.mapper.PaymentDetailsMapper;

import java.util.Optional;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    private final PaymentDetailsMapper paymentDetailsMapper;
    private final PaymentDetailsRepository paymentDetailsRepository;

    @Override
    public ResponseDto<PaymentDetailsDto> add(PaymentDetailsDto paymentDetailsDto) {
        try {
            PaymentDetails paymentDetails = paymentDetailsMapper.toEntity(paymentDetailsDto);
            paymentDetailsRepository.save(paymentDetails);

            return ResponseDto.<PaymentDetailsDto>builder()
                    .success(true)
                    .message(OK)
                    .data(paymentDetailsMapper.toDto(paymentDetails))
                    .build();
        } catch (Exception e){
            return ResponseDto.<PaymentDetailsDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " -> " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<PaymentDetailsDto>> getAll(Integer page, Integer size) {
        Long count = paymentDetailsRepository.count();

        PageRequest pageRequest = PageRequest.of(
                (count / size) <= page ?
                        (count % size == 0 ? (int) (count / size) - 1
                                : (int) (count / size))
                        : page,
        size);

        Page<PaymentDetailsDto> paymentDetailsDtos = paymentDetailsRepository.findAll(pageRequest)
                .map(paymentDetailsMapper::toDto);

        return ResponseDto.<Page<PaymentDetailsDto>>builder()
                .success(true)
                .message(OK)
                .data(paymentDetailsDtos)
                .build();
    }

    @Override
    public ResponseDto<PaymentDetailsDto> getById(Integer id) {
        if(id == null){
            return ResponseDto.<PaymentDetailsDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(NULL_VALUE)
                    .build();
        }

        return paymentDetailsRepository.findById(id)
                .map(p -> ResponseDto.<PaymentDetailsDto>builder()
                        .success(true)
                        .message(OK)
                        .data(paymentDetailsMapper.toDto(p))
                        .build())
                .orElse(ResponseDto.<PaymentDetailsDto>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .build());
    }

    @Override
    public ResponseDto<Void> delete(Integer id) {
        if(id == null){
            return ResponseDto.<Void>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(NULL_VALUE)
                    .build();
        }

        paymentDetailsRepository.deleteById(id);

        return ResponseDto.<Void>builder()
                .success(true)
                .message(OK)
                .build();
    }

    @Override
    public ResponseDto<PaymentDetailsDto> update(PaymentDetailsDto paymentDetailsDto) {
        if(paymentDetailsDto.getId() == null){
            return ResponseDto.<PaymentDetailsDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(NULL_VALUE)
                    .build();
        }

        Optional<PaymentDetails> optional = paymentDetailsRepository.findById(paymentDetailsDto.getId());

        if(optional.isEmpty()){
            return ResponseDto.<PaymentDetailsDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        PaymentDetails paymentDetails = optional.get();

        if (paymentDetailsDto.getOrderId() != null){
            paymentDetails.setOrderId(paymentDetailsDto.getOrderId());
        }
        if (paymentDetailsDto.getAmount() != null){
            paymentDetails.setAmount(paymentDetailsDto.getAmount());
        }
        if (paymentDetailsDto.getProvider() != null){
            paymentDetails.setProvider(paymentDetailsDto.getProvider());
        }
        if (paymentDetailsDto.getStatus() != null){
            paymentDetails.setStatus(paymentDetailsDto.getStatus());
        }
        if (paymentDetailsDto.getCreatedAt() != null){
            paymentDetails.setCreatedAt(paymentDetailsDto.getCreatedAt());
        }
        if (paymentDetailsDto.getModifiedAt() != null){
            paymentDetails.setModifiedAt(paymentDetailsDto.getModifiedAt());
        }

        paymentDetailsRepository.save(paymentDetails);

        return ResponseDto.<PaymentDetailsDto>builder()
                .success(true)
                .message(OK)
                .data(paymentDetailsMapper.toDto(paymentDetails))
                .build();
    }
}
