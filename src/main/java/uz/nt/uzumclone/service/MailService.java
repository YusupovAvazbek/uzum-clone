package uz.nt.uzumclone.service;

import uz.nt.uzumclone.dto.ResponseDto;

public interface MailService {
    ResponseDto<Boolean> sendEmail(String to, String content, boolean isMultipart, boolean isHtml);
}
