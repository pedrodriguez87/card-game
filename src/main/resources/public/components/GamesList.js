class GamesList extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            games: [],
            loadingText: "Loading Games"
        };
    }

    componentDidMount() {
        // Component is mounted, now I can call an API to get data
        this.refreshGames();
    }

    refreshGames() {
        axios.get("http://localhost:8080/api/games")
            .then(response => {

                const games = response.data;
                console.log(games);

                // this.state.games = games;
                this.setState({
                    games: games,
                });
            });
    }

    addGame() {

        axios.post("/api/games").then(response => {

            this.refreshGames()

        });
    }

    render(){
        return (
            <div>
                <Title text = {this.props.title} />
                <p>{this.state.loadingText}</p>
                <p><button onClick={() => this.addGame()}>Add game</button> </p>
                <ul>
                    {this.state.games.map( game => (
                        <li key={game.id}><a href={"/games/" + game.id}>Game {game.id}</a> is {game.state}</li>
                        //<p>Game {game.id} is {game.state}</p>
                    ))}
                </ul>
            </div>
        );
    }
}