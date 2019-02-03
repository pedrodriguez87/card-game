const urlParams = new URLSearchParams(window.location.search);
const gameId = urlParams.get('gameId');

const gamePromise = axios.get("http://localhost:8080/api/games/" + gameId);

gamePromise

.then(function(response) {

    let gameContainer = document.getElementById("game-container");

    const game = response.data;

    const g = document.createElement("p");
    g.textContent = "Game ID : " + game.id;
    gameContainer.appendChild(g);
    const s = document.createElement("p");
    s.textContent = "Game State: " + game.state;
    gameContainer.appendChild(s);
    const p = document.createElement("p");
    p.textContent = "Players: " + game.playerNames;
    gameContainer.appendChild(p);

    const a = document.createElement('a');
    const linkText = document.createTextNode("Turn back to games");
    a.appendChild(linkText);
    a.href = "http://localhost:8080/games.html";
    document.body.appendChild(a);


});