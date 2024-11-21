package com.dgeniust.jwt_project.controller;

import com.dgeniust.jwt_project.dto.request.AuthenticationRequest;
import com.dgeniust.jwt_project.dto.request.IntrospectRequest;
import com.dgeniust.jwt_project.dto.request.LogoutRequest;
import com.dgeniust.jwt_project.dto.request.RefreshRequest;
import com.dgeniust.jwt_project.dto.response.ApiResponse;
import com.dgeniust.jwt_project.dto.response.AuthenticationResponse;
import com.dgeniust.jwt_project.dto.response.IntrospectResponse;
import com.dgeniust.jwt_project.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticated(request);
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(result);
        return apiResponse;
    }
    @PostMapping("/logout")
    ApiResponse<Void> authenticate(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> checkToken(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introSpect(request);
        ApiResponse<IntrospectResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(result);
        return apiResponse;
    }
    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(result);
        return apiResponse;
    }
}
