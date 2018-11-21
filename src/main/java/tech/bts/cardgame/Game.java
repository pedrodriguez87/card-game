package tech.bts.cardgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {


    enum State { OPEN, PLAYING }

    private final Deck deck;
    private State state;
    private List<String> usernames;
    private Map<String, Card> pickedCardByUsername;
    private Map<String, Integer> discardsByUsername;

    public Game(Deck deck) {
        this.deck = deck;
        this.state = State.OPEN;
        this.usernames = new ArrayList<>();
        this.pickedCardByUsername = new HashMap<>();
        this.discardsByUsername = new HashMap<>();
    }

    public State getState() {
        return state;
    }

    public void join(String username) {

        if (state != State.OPEN) {
            throw new JoiningNotAllowedException();
        }

        usernames.add(username);
        discardsByUsername.put(username,0);


        if (usernames.size() == 2) {
            state = State.PLAYING;
        }
    }

    public List<String> getPlayerNames() {

        return usernames;
    }

    public Card pickCard(String username) {

        if(!usernames.contains(username)) {
            throw new PlayerNotInTheGameException();
        }

        if(state == State.OPEN) {
            throw new CannotPick2CardsWhenStatusIsOpenException();
        }

        Card pickedCard = pickedCardByUsername.get(username);
        if(pickedCard != null) {
            throw new CannotPick2CardsInARowException();
        }

        Card newPickedCard = deck.pickCard();
        pickedCardByUsername.put(username, newPickedCard);
        return newPickedCard;

    }

    public void discard(String username) {

        if(!pickedCardByUsername.containsKey(username)){
            throw new CannotDiscardIfPlayerDidntPickedCardException();
        }

        int discards = discardsByUsername.get(username);
        if (discards == 2) {
            throw new CannotDiscardMoreThanTwoCardsExcepction();
        }

        discardsByUsername.put(username, discards + 1);

        pickedCardByUsername.remove(username);

    }

    public Card keepCard(String username) {

        return pickedCardByUsername.get(username);
    }

    /**
     * Returns a negative integer, zero, or a positive integer
     * as the first hand is less than, equal to,
     * or greater than the second hand.
     *
    private int compare(Hand hand1, Hand hand2) {

        int points1 = 0;
        int points2 = 0;

        Card total1 = hand1.calculate();
        Card total2 = hand2.calculate();

        if (total1.getMagic() > total2.getMagic()) {
            points1++;
        } else if (total1.getMagic() < total2.getMagic()) {
            points2++;
        }

        // TODO: do the same with strength and intelligence

        return points1 - points2;
    }
     */
}
