package project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import project.service.PasswordEncrypter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userService;

    private final PasswordEncrypter passwordEncoder;
    public WebSecurityConfig(UserDetailsServiceImpl userService, PasswordEncrypter passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().ignoringAntMatchers("/**").and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/", "home",
                        "/categories/*","/items/*","/registration").permitAll()


                .antMatchers("/categories/add","/categoryGroups/add",
               "/categories/delete/**","/categoryGroups/delete/**","/users" ).access("hasRole('ROLE_ADMIN')")
                .antMatchers("/**").access("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
                .and().authorizeRequests().and().exceptionHandling().accessDeniedPage("/403").and()
                .authenticationProvider(daoAuthenticationProvider())
                .sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());

    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
}
