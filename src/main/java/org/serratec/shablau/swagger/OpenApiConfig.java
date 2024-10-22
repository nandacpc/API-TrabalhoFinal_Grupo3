package org.serratec.shablau.swagger;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(
	title = "API Shablau",
	version = "1.0",
	description = "Documentação da API de Projeto Final"))
public class OpenApiConfig {
	

}
