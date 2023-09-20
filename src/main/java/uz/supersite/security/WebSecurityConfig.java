package uz.supersite.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.supersite.jwt.JwtTokenFilter;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired private JwtTokenFilter jwtTokenFilter;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }
    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }
    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(exceptionHandlingConfigurer ->exceptionHandlingConfigurer.authenticationEntryPoint(
                        ((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage()))
                ))

                .authorizeHttpRequests(reg -> reg
                        .requestMatchers("/auth/login")
                        .permitAll()
                        .requestMatchers("/products").hasAnyAuthority("ROLE_EDITOR","ROLE_CUSTOMER")
                        .requestMatchers("/products/add").hasAnyAuthority("ROLE_EDITOR")
                        .requestMatchers("/api/v1/users/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();




    }





















//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    @Bean
//    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//        http
//                .cors(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .formLogin(AbstractHttpConfigurer::disable)
//                .securityMatcher("/**")
//                .authorizeHttpRequests(registry -> registry
//                        .requestMatchers("/").permitAll()
//                        .requestMatchers("/auth/login").permitAll()
//                        .anyRequest().authenticated()
//                );
//
//        return http.build();
//
//
//
//
//    }




//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new MyUserDetailsService();
//    }
//
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authenticationProvider());
//    }

//    @SuppressWarnings("removal")
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeHttpRequests((auth) ->
//                        auth.requestMatchers("/v1/users/**").hasAuthority("Admin")
//                            .requestMatchers("/v1/categories/**", "/v1/brands/**").hasAnyAuthority("Admin", "Editor")
//                            .anyRequest().authenticated()
//                ) .formLogin()
//                .loginPage("/login")
//                .usernameParameter("email")
//                .permitAll()
//                .and()
//                .logout().permitAll() //bu keyni qoshilgani sababi application restart bo'lganda boshqatdan login qil deb so'ramaydi o'zi login qilmasdan o'tkazib yuboraveradi
//                .and().rememberMe().key("AbsdeFgHijklmnoPqrs_1234567890")
//                .tokenValiditySeconds(7 * 24 * 60 * 60);
//        return httpSecurity.build();
//    }
}
