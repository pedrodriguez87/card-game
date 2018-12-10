package tech.bts.cardgame.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
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
import java.util.HashMap;
import java.util.Map;

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

        String result = "<h1>List of games</h1>\n";

        result += "<ul><a href=\"/games/create\">Create Game</a>\n";

        for (Game game : gameService.getAllGames()) {

            result += "<li><a href=\"/games/" + game.getId() + "\">Game " + game.getId() + "</a> is " + game.getState() + "</li>";
        }

        result += "</ul>";

        return result;

    }

    @RequestMapping(method = RequestMethod.GET, path = "/{gameId}")
    public String getGameById(@PathVariable long gameId) throws IOException {

        Game game = gameService.getGameById(gameId);

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("game-detail");

        Map<String, Object> values = new HashMap<>();
        values.put("game", game);
        values.put("gameIsOpen", game.getState() == Game.State.OPEN);

       return template.apply(values);

        /*
        String result = "<a href=\"/games\">Turn to games</a>" +
                        "<h1>Game " + game.getId() + "</h1>" +
                        "<p>State: " + game.getState() + "</p>" +
                        "<p>Players " + game.getPlayerNames() + "</p>";

        if (game.getState() == Game.State.OPEN){
            result += "<a href=\"/games/" + game.getId() + "/join\">Join game</a>";

        }

        return result;
        */
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

}









