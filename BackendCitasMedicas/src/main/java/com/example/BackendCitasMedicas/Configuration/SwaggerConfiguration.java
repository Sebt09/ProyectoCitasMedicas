package com.example.BackendCitasMedicas.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Citas Medicas")
                        .version("1.0")
                        .description("Documentacion de la API para gestionar Citas Medicas de un Medico independiente")
                        .contact(new Contact()
                                .name("Soporte API")
                                .email("j.sn43@hotmail.com")));
    }
}
