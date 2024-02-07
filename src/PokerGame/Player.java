package PokerGame;

import CardGame.Card;
import CardGame.Deck;


public class Player {
    Card[] hand = new Card[5];
    int score = 0;
    int comboS = 0;
    int cardS = 0;
    public boolean getCard(Deck deck) {
        if (hand.length >= 6) return false;
        else
            for (int i = 0; i < 5; i++) {
                if (hand[i] == null) {
                    hand[i] = deck.popCard();
                    break;
                }
            }
        return true;
    }
    public boolean putCard(int index, Deck deck) {
        if (deck.pushCard(hand[index])) {
            hand[index] = null;
            return true;
        } else return false;
    }
    public Card[] getHand() {
        Card[] temp = hand;
        return temp;
    }
    public void setCardS(int cardS) {
        this.cardS = cardS;
    }
    public void setComboS(int comboS) {
        this.comboS = comboS;
    }
    public int getComboS() {
        return comboS;
    }
    public int getCardS() {
        return cardS;
    }
    public void addPoint() {
        score++;
    }
    public Card putCard(int index) {
        Card temp = hand[index];
        hand[index] = null;
        return temp;
    }
}

