//package com.alpha.user.conf;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.RestController;
//
//import static springfox.documentation.builders.PathSelectors.regex;
//
//import java.util.function.Predicate;
//import java.util.stream.Stream;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
////@Configuration
////@EnableSwagger2
//public class Swagger2Configuration {
//
//	@Bean
//	public Docket accessToken() {
//		return new Docket(DocumentationType.SWAGGER_2).groupName("api")
//				.select()
//				//.apis(RequestHandlerSelectors.basePackage("com.alpha.user.controller"))
//				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//				.paths(regex("/userxxx/.*"))
//				.build()
//				.apiInfo(apiInfo());
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("循证医学").description("阿尔法医生API")
//				.contact(new Contact("罗鸿云", "http://gmws.ebmsz.com", "32305819@qq.com"))
//				.version("1.0").build();
//	}
//
//	/*private Predicate<String> predicate(String... paths) {
//		return (input)->{
//			Stream.of(paths).anyMatch(regex(input));
//		};
//	}*/
//}
