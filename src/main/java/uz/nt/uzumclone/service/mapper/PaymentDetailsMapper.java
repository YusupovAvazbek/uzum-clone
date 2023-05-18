package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.uzumclone.dto.PaymentDetailsDto;
import uz.nt.uzumclone.model.PaymentDetails;

@Mapper(componentModel = "spring")
public interface PaymentDetailsMapper extends CommonMapper<PaymentDetailsDto, PaymentDetails> {

}
