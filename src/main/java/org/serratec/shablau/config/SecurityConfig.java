package org.serratec.shablau.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests(r -> r.anyRequest().authenticated())
//		.httpBasic(Customizer.withDefaults());
		
		http.authorizeHttpRequests(authorize -> authorize
		.requestMatchers(HttpMethod.GET, "/pedidos").permitAll()
		.requestMatchers(HttpMethod.GET, "/pedidos/{id_pedido}").permitAll()
		.requestMatchers(HttpMethod.GET, "/pedidos/{status}").permitAll()
		.requestMatchers(HttpMethod.POST, "/pedidos").hasRole("ADM")
		.requestMatchers(HttpMethod.PUT, "/pedidos").hasRole("ADM")
		.requestMatchers(HttpMethod.PUT, "/pedidos/{id_pedido}").hasRole("ADM")
		.requestMatchers(HttpMethod.DELETE, "/pedidos/{id_pedido}").hasRole("ADM")
		.requestMatchers(HttpMethod.GET, "/clientes").permitAll()
		.requestMatchers(HttpMethod.GET, "/clientes/{id_cliente}").permitAll()
		.requestMatchers(HttpMethod.GET, "/clientes/{nome}").permitAll()
		.requestMatchers(HttpMethod.GET, "/clientes/{cpf}").permitAll()
		.requestMatchers(HttpMethod.POST, "/clientes").hasRole("ADM")
		.requestMatchers(HttpMethod.PUT, "/clientes/{id_cliente}").hasRole("ADM")
		.requestMatchers(HttpMethod.DELETE, "/clientes/{id_cliente}").hasRole("ADM")
		.requestMatchers(HttpMethod.GET, "/produtos").permitAll()
		.requestMatchers(HttpMethod.GET, "/produtos/{id_produto}").permitAll()
		.requestMatchers(HttpMethod.GET, "/produtos/nome/{nome}").permitAll()
		.requestMatchers(HttpMethod.GET, "/produtos/data/{data_inicio}/{data_final}").permitAll()
		.requestMatchers(HttpMethod.GET, "/produtos/estoque/{min}/{max}").permitAll()
		.requestMatchers(HttpMethod.GET, "/produtos/preco/{valor_min}/{valor_max}").permitAll()
		.requestMatchers(HttpMethod.GET, "/produtos/categoria/{id_categoria}").permitAll()
		.requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADM")
		.requestMatchers(HttpMethod.PUT, "/produtos/{id_produto}").hasRole("ADM")
		.requestMatchers(HttpMethod.DELETE, "/produtos/{id_produto}").hasRole("ADM")
		.requestMatchers(HttpMethod.GET, "/categorias").permitAll()
		.requestMatchers(HttpMethod.GET, "/categorias/{id_categoria}").permitAll()
		.requestMatchers(HttpMethod.GET, "/categorias/descricao/{palavra}").permitAll()
		.requestMatchers(HttpMethod.GET, "/categorias/nome/{nome}").permitAll()
		.requestMatchers(HttpMethod.POST, "/categorias").hasRole("ADM")
		.requestMatchers(HttpMethod.PUT, "/categorias").hasRole("ADM")
		.requestMatchers(HttpMethod.DELETE, "/categorias/{id_categoria}").hasRole("ADM"))
		.csrf(csrf -> csrf.disable())
		.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
	public InMemoryUserDetailsManager detalhesUsuario() {
		UserDetails usuario = User.builder()				
				.username("shablau")
				.password(encoder().encode("123456"))
				.roles("ADM").build();
		
		UserDetails usuario2 = User.builder()				
				.username("joao")
				.password(encoder().encode("123456"))
				.roles("RH").build();
		
		return new InMemoryUserDetailsManager(usuario, usuario2);
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}