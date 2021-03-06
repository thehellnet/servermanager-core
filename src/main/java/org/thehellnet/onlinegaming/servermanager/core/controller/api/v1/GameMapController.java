package org.thehellnet.onlinegaming.servermanager.core.controller.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thehellnet.onlinegaming.servermanager.core.controller.api.v1.aspect.CheckRoles;
import org.thehellnet.onlinegaming.servermanager.core.controller.api.v1.aspect.CheckToken;
import org.thehellnet.onlinegaming.servermanager.core.model.constant.Role;
import org.thehellnet.onlinegaming.servermanager.core.model.dto.JsonResponse;
import org.thehellnet.onlinegaming.servermanager.core.model.dto.request.token.GameMapListDTO;
import org.thehellnet.onlinegaming.servermanager.core.model.persistence.AppUser;
import org.thehellnet.onlinegaming.servermanager.core.model.persistence.Game;
import org.thehellnet.onlinegaming.servermanager.core.model.persistence.GameMap;
import org.thehellnet.onlinegaming.servermanager.core.repository.GameMapRepository;
import org.thehellnet.onlinegaming.servermanager.core.repository.GameRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/api/v1/public/gameMap")
public class GameMapController {

    private final GameRepository gameRepository;
    private final GameMapRepository gameMapRepository;

    @Autowired
    public GameMapController(GameRepository gameRepository, GameMapRepository gameMapRepository) {
        this.gameRepository = gameRepository;
        this.gameMapRepository = gameMapRepository;
    }

    @RequestMapping(
            path = "/list",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CheckToken
    @CheckRoles(Role.READ_PUBLIC)
    @ResponseBody
    public JsonResponse list(AppUser appUser, @RequestBody GameMapListDTO dto) {
        List<GameMap> gameMaps = new ArrayList<>();

        if (dto.gameTag != null) {
            Game game = gameRepository.findByTag(dto.gameTag);
            if (game == null) {
                return JsonResponse.getErrorInstance("Game tag not found");
            }
            gameMaps.addAll(gameMapRepository.findByGame(game));
        } else {
            gameMaps.addAll(gameMapRepository.findAll());
        }

        List<Map<String, String>> data = new ArrayList<>();
        for (GameMap gameMap : gameMaps) {
            Map<String, String> mapData = new HashMap<>();
            mapData.put("tag", gameMap.getTag());
            mapData.put("name", gameMap.getName());
            mapData.put("gameTag", gameMap.getGame().getTag());
            data.add(mapData);
        }

        return JsonResponse.getInstance(data);
    }
}
