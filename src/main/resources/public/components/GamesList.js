
class GamesList extends React.Component {

    componentDidMount() {

        // Component is mounted, now I can call an API to get data

        const gamesPromise = axios.get("http://localhost:8080/api/games");

        gamesPromise
        //.then(x => x.json()) // this is necessary when using fetch
            .then(function(response) {

                const games = response.data; // In axios you get a response object with the data inside
                console.log(games);

            });
    }

    render(){
        return (
            <div> I will display the games here</div>
        )
    }
}