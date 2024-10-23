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

//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        //http.authorizeHttpRequests(r -> r.anyRequest().authenticated())
//        //.httpBasic(Customizer.withDefaults());
//
//        http.authorizeHttpRequests(authorize -> authorize
//        .requestMatchers(HttpMethod.GET, "**").permitAll()
//        .requestMatchers(HttpMethod.PUT, "**").permitAll()
//        .requestMatchers(HttpMethod.POST, "**").permitAll()
//        //.requestMatchers(HttpMethod.POST, "").hasRole("ADM")
//        //.requestMatchers(HttpMethod.DELETE, "").hasRole("ADM")
//        .requestMatchers(HttpMethod.PUT, "**").hasRole("ADM"))
//        .csrf(csrf -> csrf.disable())
//        .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET, "/").permitAll()
            .requestMatchers("/swagger-ui.html", "/swagger-ui/", "/v2/api-docs", "/webjars/","/swagger-resources/").permitAll()
            .requestMatchers(HttpMethod.POST, "/pedidos","/clientes","/produtos","/itenspedidos").hasRole("ADM")
            .requestMatchers(HttpMethod.DELETE, "/pedidos/","/clientes/","/produtos/","/itenspedidos/").hasRole("ADM")
            .requestMatchers(HttpMethod.PUT, "/pedidos/","/clientes/","/produtos/","/itenspedidos/").hasRole("ADM")
            .requestMatchers(HttpMethod.PATCH, "/pedidos/","/clientes/","/produtos/","/itenspedidos/").hasRole("ADM"))
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
	
	@Bean
	public InMemoryUserDetailsManager detalhesUsuario() {
		UserDetails usuario = User.builder().username("shablau").password(encoder().encode("123456")).roles("ADM")
				.build();

		UserDetails usuario2 = User.builder().username("joao").password(encoder().encode("123456")).roles("RH").build();

		return new InMemoryUserDetailsManager(usuario, usuario2);
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
