package uz.nt.uzumclone.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
            title = "Uzum market uchun Swagger API",
            description = "Uzum market online do'konidan tovarlar xarid qilishingiz mumkin.",
            termsOfService = "https://uzum.uz/",
            contact = @Contact(name = "Uzum market", email = "support@uzum.com", url = "https://t.me//Uzum_Support_Bot")
))
public class SwaggerConfig {
}