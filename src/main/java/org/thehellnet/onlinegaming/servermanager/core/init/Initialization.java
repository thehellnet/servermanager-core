package org.thehellnet.onlinegaming.servermanager.core.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.thehellnet.onlinegaming.servermanager.core.model.persistence.Game;
import org.thehellnet.onlinegaming.servermanager.core.repository.GameRepository;

import java.util.HashMap;
import java.util.Map;

@Component
public class Initialization {

    private static final Logger logger = LoggerFactory.getLogger(Initialization.class);

    private boolean alreadyRun = false;

    private final GameRepository gameRepository;

    @Autowired
    public Initialization(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshed() {
        if (alreadyRun) {
            return;
        }

        alreadyRun = true;

        logger.info("Initializing database data");

        createGames();

        logger.info("Database data initialization complete");
    }

    private void createGames() {
        Map<String, String> gameMap = new HashMap<>();
        gameMap.put("q3a", "Quake III Arena");
        gameMap.put("q3ut4", "Urban Terror");
        gameMap.put("cod", "Call of Duty");
        gameMap.put("cod2", "Call of Duty 2");
        gameMap.put("cod4", "Call of Duty 4: Modern Warfare");
        gameMap.put("codwaw", "Call of Duty: World at War");

        for (String tag : gameMap.keySet()) {
            Game game = gameRepository.findByTag(tag);
            if (game == null) {
                game = new Game(tag, gameMap.get(tag));
                gameRepository.save(game);
            }
        }
    }
}