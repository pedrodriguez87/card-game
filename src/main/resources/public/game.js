const urlParams = new URLSearchParams(window.location.search);
const gameId = urlParams.get('gameId');

// if the url you want to call is yours you can call it like this "/api/games/"
axios.get("http://localhost:8080/api/games/" + gameId)
    .then(function(response) {
    const game = response.data;
    displayGame(game);
    })
    .catch(function(error) {
        console.log("There was an error!", error);
    } );


function displayGame(game) {

    let gameContainer = document.getElementById("game-container");

    const g = document.createElement("p");
    g.textContent = "Game ID : " + game.id;
    gameContainer.appendChild(g);
    const s = document.createElement("p");
    s.textContent = "Game State: " + game.state;
    gameContainer.appendChild(s);
    const p = document.createElement("p");
    p.textContent = "Players: " + game.playerNames;
    gameContainer.appendChild(p);


}