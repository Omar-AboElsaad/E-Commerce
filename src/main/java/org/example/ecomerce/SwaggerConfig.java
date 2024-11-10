//package org.example.ecomerce;
//
//import org.springdoc.core.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//    @Configuration
//    public class SwaggerConfig implements WebMvcConfigurer {
//
//        @Bean
//        public GroupedOpenApi api() {
//            return GroupedOpenApi.builder()
//                    .group("e-commerce-api")
//                    .pathsToMatch("/api/**")
//                    .build();
//        }
//
//        @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//            registry.addResourceHandler("/swagger-ui/**")
//                    .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/4.11.1/");
//        }
//    }
//
//
