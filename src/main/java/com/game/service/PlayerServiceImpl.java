package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.controller.dto.*;
import com.game.entity.Player;
import com.game.repository.PlayerFilterRepository;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerFilterRepository playerFilterRepository;
    private final PlayerMapperImpl playerMapperImpl;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerFilterRepository playerFilterRepository,
                             PlayerMapperImpl playerMapperImpl) {
        this.playerRepository = playerRepository;
        this.playerFilterRepository = playerFilterRepository;
        this.playerMapperImpl = playerMapperImpl;
    }

    @Override
    public List<PlayerGetResponse> findAll(PlayerFilterRequest request, PlayerOrder order, int pageNumber, int pageSize) {
        List<Player> players = playerFilterRepository.findAll(request, order, pageNumber, pageSize);
        return playerMapperImpl.playerToGetResponse(players);
    }

    @Override
    public Integer count(PlayerFilterRequest request) {
        return playerFilterRepository.count(request);
    }

    @Override
    public Player findOne(Long id) {
        Optional<Player> foundPlayer = playerRepository.findById(id);
        return foundPlayer.orElse(null);
    }

    @Override
    @Transactional
    public PlayerCreateResponse save(PlayerCreateRequest request) {
        Player player = playerMapperImpl.playerToCreateRequest(request);

        int level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        player.setLevel(level);

        int untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
        player.setUntilNextLevel(untilNextLevel);

        player = playerRepository.save(player);

        return playerMapperImpl.playerToCreateResponse(player);
    }

    @Override
    @Transactional
    public PlayerUpdateResponse update(PlayerUpdateRequest request, long id) {
        Player player = playerRepository.findById(id).orElse(null);
        if (player == null) {
            return null;
        }

        playerMapperImpl.playerToUpdateRequest(player, request);

        int level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        player.setLevel(level);

        int untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
        player.setUntilNextLevel(untilNextLevel);
        player = playerRepository.save(player);

        return playerMapperImpl.playerToUpdateResponse(player);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!playerRepository.findById(id).isPresent()) {
            return false;
        }
        playerRepository.deleteById(id);
        return true;
    }
}
