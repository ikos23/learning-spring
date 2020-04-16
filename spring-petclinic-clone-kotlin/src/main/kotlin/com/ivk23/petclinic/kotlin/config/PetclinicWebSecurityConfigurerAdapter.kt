package com.ivk23.petclinic.kotlin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository

/**
 * * Just adding appropriate starter is not enough, because
 * there are two things that need configured that Spring
 * Boot cannot guess for us: **users and their credentials**,
 * and the customized security settings for **app URLs**.
 *
 * * So, a **configuration class that extends WebSecurityConfigurerAdapter**
 * is needed :)
 */
@Configuration
@EnableWebSecurity
class PetclinicWebSecurityConfigurerAdapter :
        WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity) {
        web.ignoring()
                .mvcMatchers(
                        "/",
                        "/styles/**",
                        "/assets/**",
                        "/api/**",
                        "/index/**",
                        "/vets/**",
                        "/pets/**",
                        "/owners/**",
                        "/visits/**")
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/admin")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .csrf()
                .csrfTokenRepository(csrfRepository())

    }

    @Bean
    override fun userDetailsServiceBean(): UserDetailsService {
        val noRolesUser = User
                .withUsername("test")
                .password(encoder().encode("test123"))
                .roles()
                .build()

        val vet = User
                .withUsername("foo")
                .password(encoder().encode("pwd123"))
                .roles("VET")
                .build()
        val admin = User
                .withUsername("admin")
                .password(encoder().encode("admin123"))
                .roles("ADMIN")
                .build()

        return InMemoryUserDetailsManager(noRolesUser, admin, vet)
    }

    /**
     * We need `PasswordEncoder` bean, because Spring Boot
     * will use it to encrypt passwords provided by the users
     * to compare them with the credentials stored in memory.
     */
    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun csrfRepository(): CsrfTokenRepository {
        val repo = HttpSessionCsrfTokenRepository()
        repo.setParameterName("_csrf")
        repo.setHeaderName("X-CSRF-TOKEN")
        return repo
    }
}