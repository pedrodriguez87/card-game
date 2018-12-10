package tech.bts.cardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.service.GameService;
import tech.bts.cardgame.service.GameUser;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping(path = "/games")
public class GameWebController {

    private GameService gameService;

    @Autowired
    public GameWebController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllGames() {

        return buildGameList();

    }

    @RequestMapping(method = RequestMethod.GET, path = "/{gameId}")
    public String getGameById(@PathVariable long gameId) {

        Game game = gameService.getGameById(gameId);

        String result = "<a href=\"/games\">Turn to games</a>";


        result += "<h1>Game " + game.getId() + "</h1>";
        result += "<p>State: " + game.getState() + "</p>";
        result += "<p>Players " + game.getPlayerNames() + "</p>";

        if (game.getState() == Game.State.OPEN){
            result += "<a href=\"/games/" + game.getId() + "/join\">Join game</a>";

        }

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/create")
    public void createGame(HttpServletResponse response) throws IOException {

        gameService.createGame();
        response.sendRedirect("/games");

    }

    @RequestMapping(method = RequestMethod.GET, path = "/{gameId}/join")
    public void joinGame(HttpServletResponse response, @PathVariable long gameId) throws IOException {

        gameService.joinGame(new GameUser(gameId,"Pedro"));
        response.sendRedirect("/games/" + gameId);

    }

    private String buildGameList() {

        String result = "<h1>List of games</h1>\n";

        result += "<ul><a href=\"/games/create\">Create Game</a>\n";

        for (Game game : gameService.getAllGames()) {

            result += "<li><a href=\"/games/" + game.getId() + "\">Game " + game.getId() + "</a> is " + game.getState() + "</li>";
        }

        result += "</ul>";

        return result;
    }
}









