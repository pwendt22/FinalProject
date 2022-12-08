package com.Laundry.com.LaundryMyApp.security;


import com.Laundry.com.LaundryMyApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SucessLogin sucessLogin;

    @Bean
    public BCryptPasswordEncoder gerarCriptografia() {
        BCryptPasswordEncoder criptografia = new BCryptPasswordEncoder();
        return criptografia;
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        UserDetailServ detalheDoUsuario = new UserDetailServ(userRepository);
        return detalheDoUsuario;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/auth/user/*").hasAnyAuthority("USER","ADMIN","BIBLIOTECARIO")
                .antMatchers("/auth/admin/*").hasAnyAuthority("ADMIN")
                .antMatchers("/auth/biblio/*").hasAnyAuthority("BIBLIOTECARIO")
                .antMatchers("/usuario/admin/*").hasAnyAuthority("ADMIN")
                .and()
                .exceptionHandling().accessDeniedPage("/auth/auth-acesso-negado")
                .and()
                .formLogin().successHandler(sucessLogin)
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // O objeto que vai obter os detalhes do usuário
        UserDetailsService detalheDoUsuario = userDetailsServiceBean();
        // Objeto para criptografia
        BCryptPasswordEncoder criptografia = gerarCriptografia();

        auth.userDetailsService(detalheDoUsuario).passwordEncoder(criptografia);
    }
}
