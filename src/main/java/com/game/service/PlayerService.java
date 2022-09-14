package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    //можно выбросить исключение ,если чел не найден
    public Player findOne(Long id) {
        Optional<Player> foundPerson = playerRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Player player) {
        playerRepository.save(player);
    }

    @Transactional
    public void update(Long id, Player updatedPlayer) {
        updatedPlayer.setId(id);
        playerRepository.save(updatedPlayer);
    }

    @Transactional
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }

    //нужен метод возвращающий количество пользаков
}
