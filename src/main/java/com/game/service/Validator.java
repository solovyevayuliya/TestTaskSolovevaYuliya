package com.game.service;

import com.game.controller.dto.PlayerCreateRequest;
import com.game.controller.dto.PlayerUpdateRequest;

public interface Validator {

    boolean validateUpdateRequest(PlayerUpdateRequest playerUpdateRequest);

    boolean validateCreateRequest(PlayerCreateRequest playerCreateRequest);
}
