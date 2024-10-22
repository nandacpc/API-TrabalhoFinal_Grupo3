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
		.requestMatchers(HttpMethod.POST, "/pedidos").hasRole("ADM")
		.requestMatchers(HttpMethod.PUT, "/pedidos").hasRole("ADM")
		.requestMatchers(HttpMethod.DELETE, "/pedidos").hasRole("ADM")
		.requestMatchers(HttpMethod.GET, "/clientes").permitAll()
		.requestMatchers(HttpMethod.POST, "/clientes").hasRole("ADM")
		.requestMatchers(HttpMethod.PUT, "/clientes").hasRole("ADM")
		.requestMatchers(HttpMethod.DELETE, "/clientes").hasRole("ADM")
		.requestMatchers(HttpMethod.GET, "/produtos").permitAll()
		.requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADM")
		.requestMatchers(HttpMethod.PUT, "/produtos").hasRole("ADM")
		.requestMatchers(HttpMethod.DELETE, "/produtos").hasRole("ADM")
		.requestMatchers(HttpMethod.GET, "/categorias").permitAll()
		.requestMatchers(HttpMethod.POST, "/categorias").hasRole("ADM")
		.requestMatchers(HttpMethod.PUT, "/categorias").hasRole("ADM")
		.requestMatchers(HttpMethod.DELETE, "/categorias").hasRole("ADM"))
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