package com.game.service;

import com.game.controller.dto.PlayerCreateRequest;
import com.game.controller.dto.PlayerUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class ValidatorImpl implements Validator {

    public boolean validateUpdateRequest(PlayerUpdateRequest playerUpdateRequest) {
        if (playerUpdateRequest.getName() != null && playerUpdateRequest.getName().length() > 12) {
            return false;
        }
        if (playerUpdateRequest.getTitle() != null && playerUpdateRequest.getTitle().length() > 30) {
            return false;
        }
        if (playerUpdateRequest.getBirthday() != null && playerUpdateRequest.getBirthday() < 0) {
            return false;
        }
        return playerUpdateRequest.getExperience() == null
                || (playerUpdateRequest.getExperience() >= 0 && playerUpdateRequest.getExperience() <= 10000000);
    }

    public boolean validateCreateRequest(PlayerCreateRequest playerCreateRequest) {
        if (playerCreateRequest.getName() == null || playerCreateRequest.getName().length() > 12
                || playerCreateRequest.getName().isEmpty()) {
            return false;
        } else if (playerCreateRequest.getTitle() == null || playerCreateRequest.getTitle().length() > 30) {
            return false;
        } else if (playerCreateRequest.getRace() == null) {
            return false;
        } else if (playerCreateRequest.getProfession() == null) {
            return false;
        } else if (playerCreateRequest.getBirthday() == null || playerCreateRequest.getBirthday() < 0) {
            return false;
        } else if (playerCreateRequest.getBanned() == null) {
            return false;
        } else return playerCreateRequest.getExperience() != null && playerCreateRequest.getExperience() >= 0
                && playerCreateRequest.getExperience() <= 10000000;
    }
}
