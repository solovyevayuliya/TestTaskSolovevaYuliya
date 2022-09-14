package com.game.service;

import com.game.controller.dto.*;
import com.game.entity.Player;

import java.util.List;

public interface PlayerMapper {

    Player playerToCreateRequest(PlayerCreateRequest request);

    PlayerCreateResponse playerToCreateResponse(Player player);

    void playerToUpdateRequest(Player player, PlayerUpdateRequest request);

    PlayerUpdateResponse playerToUpdateResponse(Player player);

    List<PlayerGetResponse> playerToGetResponse(List<Player> players);
}
