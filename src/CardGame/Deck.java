package CardGame;


import java.util.Collections;
import java.util.LinkedList;


public class Deck {
    LinkedList<Card> deck = new LinkedList<>();

    public Deck() {
        for (Card.Color color : Card.Color.values()) {
            for (int i = 2; i <= 14; i++) {
                deck.push(new Card(color, i));
            }
        }
    }
    public void shuffle() {
        Collections.shuffle(deck);
    }
    public Card popCard() {
        return deck.pop();
    }
    public boolean pushCard(Card card) {
        if (deck.contains(card) || card == null) return false;
        else {
            deck.push(card);
            return true;
        }
    }
    public boolean addLast(Card card) {
        if (deck.contains(card) || card == null) return false;
        else {
            deck.addLast(card);
            return true;
        }
    }
}
