package com.ivk23.petclinic.kotlin.config

import com.google.common.base.Predicates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors.regex
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


// https://springfox.github.io/springfox/docs/current/#introduction
// Swagger docs can be found here: http://localhost:8080/swagger-ui.html
@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun postsApi(): Docket? {
        return Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api")
                .apiInfo(apiInfo())
                .select()
                .paths(postPaths())
                .build()
    }

    private fun postPaths() = Predicates.or(regex("/api/.*"))

    private fun apiInfo(): ApiInfo? {
        return ApiInfoBuilder()
                .title("Petclinic Clone Kotlin API")
                .description("API reference for developers")
                .version("v1")
                .build()
    }

}