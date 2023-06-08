package uz.nt.uzumclone.service.Impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import uz.nt.uzumclone.dto.ProductDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.MailService;
import uz.nt.uzumclone.service.ProductService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static uz.nt.uzumclone.additional.AppStatusCodes.*;
import static uz.nt.uzumclone.additional.AppStatusMessages.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final String getHtml;
    private final ProductServiceImpl productService;
    private final SpringTemplateEngine templateEngine;
    @Override
    public ResponseDto<Boolean> sendEmail(String to, String content, boolean isMultipart, boolean isHtml){
      log.debug("send e-mail[multipart '{}' and html '{}' ] to '{}' content={}",
              isMultipart,isHtml,to,content);


      MimeMessage mimeMessage = mailSender.createMimeMessage();
      try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setSubject("Uzum Market");
//            List<ProductDto> productList = productService.universalSearch("telefonlar", null, null, null, 1, 1).getData().get().toList();

//          Context context = new Context();
//          context.setVariable("productList",productList);
//          String main = templateEngine.process(getHtml, context);
//          message.setText(main,true);
            message.setText(getHtml);
            mailSender.send(mimeMessage);
            log.info("sent e-mail to User '{}'",to);
            return ResponseDto.<Boolean>builder()
                    .data(true)
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .build();


        } catch (MessagingException e) {
            log.warn("e-mail could not send to User '{}', exception is: {}",to,e.getMessage());
            return ResponseDto.<Boolean>builder()
                    .message("e-mail could not send to User '{}'"+to +"exception is: {}"+e.getMessage())
                    .success(false)
                    .data(false)
                    .build();
        }

    }
}
