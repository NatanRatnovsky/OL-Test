package com.natran.OLTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


import java.util.Collections;

@SpringBootApplication
@EnableSwagger2WebMvc
@Import(SpringDataRestConfiguration.class)
public class OlTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OlTestApplication.class, args);
    }

    @Bean
    public Docket swaggerConfig() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/*"))
                .apis(RequestHandlerSelectors.basePackage("com.natran.OLTest"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Item Api",
                "Test Work",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Natan Ratnovsky",
                        "https://www.linkedin.com/in/natan-ratnovsky-58990a27/?locale=en_US",
                        "nratnovsky@gmail.com"),
                "API License",
                "",
                Collections.emptyList());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    }
}
