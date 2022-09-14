package com.game.service;

import com.game.controller.dto.*;
import com.game.entity.Player;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PlayerMapperImpl implements PlayerMapper {

    public Player playerToCreateRequest(PlayerCreateRequest request) {
        Player player = new Player();
        player.setName(request.getName());
        player.setTitle(request.getTitle());
        player.setRace(request.getRace());
        player.setProfession(request.getProfession());
        Date birthday = Date.from(Instant.ofEpochMilli(request.getBirthday()));
        player.setBirthday(birthday);
        player.setBanned(request.getBanned() != null ? request.getBanned() : false);
        player.setExperience(request.getExperience() != null ? request.getExperience() : 0);
        return player;
    }

    public PlayerCreateResponse playerToCreateResponse(Player player) {
        PlayerCreateResponse response = new PlayerCreateResponse();
        response.setId(player.getId());
        response.setName(player.getName());
        response.setTitle(player.getTitle());
        response.setRace(player.getRace());
        response.setProfession(player.getProfession());
        response.setBirthday(player.getBirthday().getTime());
        response.setBanned(player.getBanned());
        response.setExperience(player.getExperience());
        response.setLevel(player.getLevel());
        response.setUntilNextLevel(player.getUntilNextLevel());
        return response;
    }

    public void playerToUpdateRequest(Player player, PlayerUpdateRequest request) {
        if (request.getName() != null) {
            player.setName(request.getName());
        }
        if (request.getTitle() != null) {
            player.setTitle(request.getTitle());
        }
        if (request.getRace() != null) {
            player.setRace(request.getRace());
        }
        if (request.getProfession() != null) {
            player.setProfession(request.getProfession());
        }
        if (request.getBirthday() != null) {
            Date birthday = Date.from(Instant.ofEpochMilli(request.getBirthday()));
            player.setBirthday(birthday);
        }
        if (request.getBanned() != null) {
            player.setBanned(request.getBanned());
        }
        if (request.getExperience() != null) {
            player.setExperience(request.getExperience());
        }
    }

    public PlayerUpdateResponse playerToUpdateResponse(Player player) {
        PlayerUpdateResponse response = new PlayerUpdateResponse();
        response.setId(player.getId());
        response.setName(player.getName());
        response.setTitle(player.getTitle());
        response.setRace(player.getRace());
        response.setProfession(player.getProfession());
        response.setBirthday(player.getBirthday().getTime());
        response.setBanned(player.getBanned());
        response.setExperience(player.getExperience());
        response.setLevel(player.getLevel());
        response.setUntilNextLevel(player.getUntilNextLevel());
        return response;
    }

    public List<PlayerGetResponse> playerToGetResponse(List<Player> players) {
        List<PlayerGetResponse> list = new ArrayList<>();
        for (Player player : players) {
            PlayerGetResponse response = new PlayerGetResponse();
            response.setId(player.getId());
            response.setName(player.getName());
            response.setTitle(player.getTitle());
            response.setRace(player.getRace());
            response.setProfession(player.getProfession());
            response.setBirthday(player.getBirthday().getTime());
            response.setBanned(player.getBanned());
            response.setExperience(player.getExperience());
            response.setLevel(player.getLevel());
            response.setUntilNextLevel(player.getUntilNextLevel());
            list.add(response);
        }
        return list;
    }
}
