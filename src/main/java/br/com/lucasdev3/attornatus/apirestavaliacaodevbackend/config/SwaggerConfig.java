package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {


  @Bean
  public Docket api() {

    Contact contact = new Contact("Lucas Souza", "https://www.github.com/lucasdev3",
        "lucasobpro@gmail.com");

    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(
            "br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.controllers"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(new ApiInfoBuilder().title("Avaliação API Rest Desenvolvedor Back-end Attornatus")
//            .description("")
            .contact(contact)
            .version("0.0.1-SNAPSHOT")
            .build());
  }


}
