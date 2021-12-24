package com.kingsware.kdev.core.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class Swagger3Config {


    private List<RequestParameter> globalRequestParameters() {
        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder()
                .in(ParameterType.HEADER)
                .name("Authorization")
                .required(false)
                .query(param -> param.model(model -> model.scalarModel(ScalarType.STRING)));
        return Collections.singletonList(parameterBuilder.build());
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName("金智维易开发平台")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(globalRequestParameters());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("金智维易开发平台")
                .description("更多请咨询服务开发者 ")
                .contact(new Contact("金智维 ", "http://www.kingsware.com", "*@kingsware.com"))
                .version("1.0")
                .build();
    }
}
