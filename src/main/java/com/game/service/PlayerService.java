package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.controller.dto.*;
import com.game.entity.Player;

import java.util.List;

public interface PlayerService {

    List<PlayerGetResponse> findAll(PlayerFilterRequest playerFilterRequest, PlayerOrder order,
                                    int pageNumber, int pageSize);

    Integer count(PlayerFilterRequest playerFilterRequest);

    Player findOne(Long id);

    PlayerCreateResponse save(PlayerCreateRequest playerCreateRequest);

    PlayerUpdateResponse update(PlayerUpdateRequest playerUpdateRequest, long id);

    boolean delete(Long id);
}
