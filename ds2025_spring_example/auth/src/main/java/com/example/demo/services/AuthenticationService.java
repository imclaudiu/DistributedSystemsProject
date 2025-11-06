package com.example.demo.services;

import com.example.demo.dtos.AuthenticationDTO;
import com.example.demo.dtos.AuthenticationDetailsDTO;
import com.example.demo.dtos.LoginDTO;
import com.example.demo.dtos.builders.AuthenticationBuilder;
import com.example.demo.entities.Authentication;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.AuthenticationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final AuthenticationRepository authenticationRepository;
    private final JwtEncoder jwtEncoder;

    public AuthenticationService(AuthenticationRepository authenticationRepository, JwtEncoder jwtEncoder){
        this.authenticationRepository = authenticationRepository;
        this.jwtEncoder = jwtEncoder;
    }

    public Authentication insertAuth(AuthenticationDetailsDTO authenticationDetailsDTO){
        Authentication authentication = AuthenticationBuilder.toEntity(authenticationDetailsDTO);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String encryptedPass = encoder.encode(authentication.getPassword());
        authentication.setPassword(encryptedPass);
        return authenticationRepository.save(authentication);
    }

    public List<AuthenticationDTO> getAllAuths(){
        List<Authentication> authenticationList = authenticationRepository.findAll();
        return authenticationList.stream().map(AuthenticationBuilder::toAuthenticationDTO).toList();
    }

    public AuthenticationDetailsDTO getAuth(UUID id)
    {
        Authentication authentication = this.authenticationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Couldn't find details about " + id));
        return AuthenticationBuilder.toAuthenticationDetailsDTO(authentication);
    }

    public Boolean checkPassword(LoginDTO loginDTO) {
          Authentication authentication = authenticationRepository.findByUsername(loginDTO.getUsername()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        Authentication authentication = authenticationRepository.findById(loginDTO.getId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return BCrypt.checkpw(loginDTO.getPassword(), authentication.getPassword());
    }

    public Authentication patchDevice(UUID id, AuthenticationDetailsDTO authenticationDetailsDTO){
        Authentication authentication = authenticationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Authentication details not found"));

        if(authenticationDetailsDTO.getUsername() != null)
            authentication.setUsername(authenticationDetailsDTO.getUsername());

        if(authenticationDetailsDTO.getPassword() != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
            String encryptedPass = encoder.encode(authenticationDetailsDTO.getPassword());
            authentication.setPassword(encryptedPass);
        }

        if(authenticationDetailsDTO.getRole() != null)
            authentication.setRole(authenticationDetailsDTO.getRole());

        return authenticationRepository.save(authentication);
    }

    public void delete(UUID id){
        Authentication authentication = authenticationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Authentication details not found."));
        authenticationRepository.delete(authentication);
    }

    public String generateToken(String username) {
        Authentication authentication = authenticationRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://localhost/api/auth")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getUsername())
                .claim("roles", authentication.getRole())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String login(LoginDTO loginDTO){
        if(!checkPassword(loginDTO)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials!");
        }
        return generateToken(loginDTO.getUsername());
    }

//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
//    String result = encoder.encode("myPassword");
//    assertTrue(encoder.matches("myPassword", result));

}
