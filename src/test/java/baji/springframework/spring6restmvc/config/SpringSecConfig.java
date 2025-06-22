package baji.springframework.spring6restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        http.csrf().ignoringRequestMatchers("/api/**");//deprecated
//        return http.build();
        httpSecurity.csrf(csrf -> {
            csrf.ignoringRequestMatchers("/api/**");
        });

        return httpSecurity.build();

    }
}
