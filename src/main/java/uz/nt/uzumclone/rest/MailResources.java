package uz.nt.uzumclone.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.MailService;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class MailResources {
    private final MailService mailService;
    @PostMapping
    public ResponseDto<Boolean> sendEmail(@RequestParam String toEmail,
                                          @RequestParam String message){
        return mailService.sendEmail(toEmail,message,false,false);
    }
}
