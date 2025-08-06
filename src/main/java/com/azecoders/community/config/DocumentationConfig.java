package com.azecoders.community.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class DocumentationConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Azecoders – Azərbaycan Developer Cəmiyyəti Platforması")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Riyad Rahimov")
                        .email("riyad_rahimov.dev@mail.ru")
                        )
                .description("""
                        **Azecoders** — Azərbaycanın developer cəmiyyətinə həsr olunmuş sosial platformadır. 
                        İstifadəçilər qeydiyyatdan keçə, profil yarada, post paylaşa, şərh yaza və bəyənə bilərlər. 
                        Admin panel, JWT ilə autentifikasiya, Redis üzərindən Refresh Token idarəsi və Swagger ilə API sənədləşməsi mövcuddur.
                        """));
    }
}