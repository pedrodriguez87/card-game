import tech.bts.cardgame.model.Deck;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.repository.DataSourceUtil;
import tech.bts.cardgame.repository.GameRepository;
import tech.bts.cardgame.repository.GameRepositoryJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class jdbcExample {

    public static void main(String[] args) throws SQLException {

        DataSource dataSource = DataSourceUtil.getDataSourceInPath();

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from games");

        List<Game> games = new ArrayList<>();

        while (rs.next()) {

            int id = rs.getInt("id");
            String state = rs.getString("state");
            String players = rs.getString("players");
            System.out.println(id + ", " + state + ", " + players);

            Game game = new Game(null);
            game.setId(id);

            if (players != null) {
                String[] names = players.split(",");
                for (String name : names) {
                    game.join(name);
                }
            }

            games.add(game);
        }

        rs.close();
        statement.close();
        connection.close();

        System.out.println("Games: ");
        for (Game game : games) {
            System.out.println(game.getId() + " " + game.getState() + " " + game.getPlayerNames());
        }






    }
}
