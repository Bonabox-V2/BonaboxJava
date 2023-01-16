package br.com.bonabox.auth.api.controller;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping({"/manager/api/v1/auth"})
public class AuthenticateController {

    @GetMapping(value = {"/authenticate"}, produces = {"application/json"})
    public ResponseEntity<Object> authenticate() {
        try {
            String token = RandomStringUtils.randomAlphanumeric(60);
            String sha256hex = Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString();
            return new ResponseEntity(new DataAuthenticate(token, sha256hex), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
