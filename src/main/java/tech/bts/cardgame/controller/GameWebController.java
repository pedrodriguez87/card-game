package tech.bts.cardgame.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.service.GameService;
import tech.bts.cardgame.service.GameUser;
import tech.bts.cardgame.util.HandlebarsUtil;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/games")
public class GameWebController {

    private GameService gameService;
    private Handlebars handlebars;

    @Autowired
    public GameWebController(GameService gameService) {
        this.gameService = gameService;

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        new Handlebars(loader);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllGames() throws IOException {

        Template template = HandlebarsUtil.compile("game-list");

        Map<String, Object> values = new HashMap<>();
        values.put("games", gameService.getAllGames());

        return template.apply(values);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{gameId}")
    public String getGameById(@PathVariable long gameId) throws IOException {

        Game game = gameService.getGameById(gameId);

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        Template template = HandlebarsUtil.compile("game-detail");

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

    @RequestMapping(method = RequestMethod.POST, path = "/join")
    public void joinGame(HttpServletResponse response, GameUser gameUser) throws IOException {

        gameService.joinGame(gameUser);
        response.sendRedirect("/games/" + gameUser.getGameId());

    }

}









