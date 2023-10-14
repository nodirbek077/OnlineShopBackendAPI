package uz.supersite.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import uz.supersite.entity.*;
import uz.supersite.event.RegistrationCompleteEvent;
import uz.supersite.jwt.JwtTokenUtil;
import uz.supersite.repository.VerificationTokenRepository;
import uz.supersite.reqAndRes.LoginRequest;
import uz.supersite.reqAndRes.RegisterRequest;
import uz.supersite.service.AuthService;

import java.text.ParseException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//    @PostMapping("/auth/login")
//    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request){
//        try {
//            Authentication  authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//            );
//
//            User user = (User) authentication.getPrincipal();
//
//            String accessToken = jwtTokenUtil.generateAccessToken(user);
//            AuthResponse response = new AuthResponse(user.getEmail(),accessToken);
//            return ResponseEntity.ok(response);
//        }catch (BadCredentialsException e){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest registerRequest, final HttpServletRequest httpServletRequest){
        Customer customer = authService.register(registerRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(customer,applicationUrl(httpServletRequest)));
        return "Success! Please, check your email to complete your registration";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            Optional<Customer> customer = authService.findByEmail(loginRequest.getEmail());

            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword()));

            if (customer.isPresent()){
                Customer existingCustomer = (Customer) authenticate.getPrincipal();
                String accessToken = jwtTokenUtil.generateAccessToken(existingCustomer);
                AuthResponse response = new AuthResponse(existingCustomer.getEmail(),accessToken);
                return ResponseEntity.ok(response);
            }else
                throw new UsernameNotFoundException("Customer not found. Please, you should register first");
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/register/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getCustomer().isEnabled())
            return "This account has already been verified. Please login!";
        String verificationResult = authService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid"))
            return "Email verified successfully. Now you can login to your account";
        return "Invalid verification token";
    }
    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
