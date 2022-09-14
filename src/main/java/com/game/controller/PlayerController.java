package com.game.controller;

import com.game.controller.dto.*;
import com.game.entity.Player;
import com.game.service.PlayerService;
import com.game.service.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayerController {

    private final PlayerService playerService;
    private final ValidatorImpl validatorImpl;

    @Autowired
    public PlayerController(PlayerService playerService, ValidatorImpl validatorImpl) {
        this.playerService = playerService;
        this.validatorImpl = validatorImpl;
    }

    @GetMapping("/players")
    public ResponseEntity<List<PlayerGetResponse>> getAll(PlayerFilterRequest request,
                                                          @RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
                                                          @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                                          @RequestParam(required = false, defaultValue = "3") int pageSize) {
        try {
            List<PlayerGetResponse> players = playerService.findAll(request, order, pageNumber, pageSize);
            return new ResponseEntity<>(players, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/players/count")
    public ResponseEntity<Integer> count(PlayerFilterRequest request) {
        try {
            Integer playersCount = playerService.count(request);
            return new ResponseEntity<>(playersCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getOne(@PathVariable("id") long id) {
        if (id < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Player player = playerService.findOne(id);

        if (player != null) {
            return new ResponseEntity<>(player, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/players")
    public ResponseEntity<PlayerCreateResponse> create(@RequestBody PlayerCreateRequest request) {
        if (!validatorImpl.validateCreateRequest(request)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            PlayerCreateResponse response = playerService.save(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/players/{id}")
    public ResponseEntity<PlayerUpdateResponse> update(@RequestBody PlayerUpdateRequest request, @PathVariable long id) {
        if (!validatorImpl.validateUpdateRequest(request)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (id < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            PlayerUpdateResponse response = playerService.update(request, id);
            if (response == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        if (id < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            boolean isDeleted = playerService.delete(id);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
