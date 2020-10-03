package com.nokia.test.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig  extends WebMvcConfigurationSupport {

    /**
     * Master service Version
     */
    private static final String MASTER_SERVICE_VERSION = "1.0";
    /**
     * Application Title
     */
    private static final String TITLE = "File store Service";
    /**
     * Master Data Service
     */
    private static final String DISCRIPTION = "File store service";
    @Bean
    public Docket api(){

        Docket docket= new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("(?!/(error).*).*")).build();

        docket.apiInfo(apiInfo());
        return docket;
    }

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/file-store/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
        registry.addRedirectViewController("/file-store/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/file-store/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/file-store/swagger-resources", "/swagger-resources");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file-store/**").addResourceLocations("classpath:/META-INF/resources/");
    }
    /**
     * Produces {@link ApiInfo}
     *
     * @return {@link ApiInfo}
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(TITLE).description(DISCRIPTION).version(MASTER_SERVICE_VERSION).build();
    }

}


