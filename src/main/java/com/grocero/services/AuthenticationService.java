package com.grocero.services;

import com.grocero.dtos.AuthenticationDto;

import java.util.Optional;

public interface AuthenticationService {

    Optional<AuthenticationDto> authenticate(String username, String authToken);

}
