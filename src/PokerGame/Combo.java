package PokerGame;

import CardGame.Card;




public class Combo {
    public static final String[] combo = {null, "High End", "Pair", "Two Pair", "Three of a Kind", "Straight", "Flush", "Full", "For of a Kind", "Poker", "Royal Poker"};
    int index = -1;
    int index1 = -1;
    int comboScore = 1;
    int cardScore = 0;
    void HighCard(Card[] hand) {
        int max = 1;
        for (int i = 0; i < hand.length; i++) {
            if (hand[i].getFigure() > max) max = hand[i].getFigure();
        }
        cardScore = max;
    }
    boolean Pair(Card[] hand) {

        for (int i = 0; i < hand.length; i++) {

            for (int j = 0; j < hand.length; j++) {
                if (j != i)
                    if (hand[j].getFigure() == hand[i].getFigure()) {
                        if (comboScore < 2) {
                            comboScore = 2;
                            cardScore = hand[i].getFigure();
                        }
                        index = i;
                        index1 = j;
                        return true;
                    }


            }
        }
        return false;
    }
    boolean TwoPair(Card[] hand) {
        for (int i = 0; i < hand.length; i++) {
            if (i != index && i != index1)
                for (int j = 0; j < hand.length; j++) {
                    if (j != i && j != index & j != index1)
                        if (hand[j].getFigure() == hand[i].getFigure()) {
                            if (comboScore < 3) {
                                comboScore = 3;
                                if (cardScore < hand[i].getFigure()) cardScore = hand[i].getFigure();
                            }
                            return true;
                        }

                }
        }
        return false;
    }
    boolean TreeOfKind(Card[] hand) {
        for (int i = 0; i < hand.length; i++) {
            for (int j = 0; j < hand.length; j++) {
                if (j != i) {
                    if (hand[j].getFigure() == hand[i].getFigure()) {
                        for (int k = 0; k < hand.length; k++) {
                            if (hand[k].getFigure() == hand[i].getFigure() && k != j && k != i) {
                                if (comboScore < 4) {
                                    comboScore = 4;
                                    cardScore = hand[k].getFigure();
                                }
                                return true;
                            }

                        }
                    }

                }

            }

        }
        return false;
    }
    boolean Straight(Card[] hand) {
        int min = hand[0].getFigure();
        int temp = 1;
        for (int i = 1; i < hand.length; i++) {
            if (hand[i].getFigure() < min) min = hand[i].getFigure();
        }

        for (int i = 0; i < hand.length; i++) {
            if (hand[i].getFigure() == min + 1) {
                temp++;
                min = hand[i].getFigure();
                if (temp == 5) {

                    if (comboScore < 5) {
                        comboScore = 5;
                        cardScore = hand[i].getFigure();
                    }
                    return true;
                }
                i = -1;
            }
        }
        return false;
    }
    boolean Flush(Card[] hand) {
        Card.Color color = hand[0].getColor();
        for (int i = 1; i < hand.length; i++) {
            if (hand[i].getColor() != color) return false;
        }
        if (comboScore < 6) {
            comboScore = 6;
            this.HighCard(hand);
        }
        return true;
    }
    boolean Full(Card[] hand) {
        if (this.TreeOfKind(hand)) {
            for (int i = 0; i < hand.length; i++) {
                for (int j = 0; j < hand.length; j++) {
                    if (hand[i].getFigure() == hand[j].getFigure() && hand[i].getFigure() != cardScore && i != j) {
                        if (comboScore < 7) {
                            comboScore = 7;
                            this.HighCard(hand);
                        }

                        return true;
                    }

                }

            }

        }
        return false;
    }
    boolean FourOfKind(Card[] hand) {
        if (this.TreeOfKind(hand)) {
            int temp = 0;
            for (int i = 0; i < hand.length; i++) {
                if (hand[i].getFigure() == cardScore) temp++;
            }
            if (temp == 4) {
                if (comboScore < 8) comboScore = 8;
                return true;
            }
        }
        return false;
    }
    boolean Poker(Card[] hand) {
        if (this.Flush(hand))
            if (this.Straight(hand)) {
                if (comboScore < 9) comboScore = 9;
                return true;
            }
        return false;
    }
    boolean RoyalPoker(Card[] hand) {
        if (this.Poker(hand))
            if (cardScore == 14) {
                comboScore = 10;
                return true;
            }
        return false;
    }
    public static void setScore(Player player) {
        Combo combo = new Combo();
        combo.HighCard(player.getHand());
        combo.Poker(player.getHand());
        combo.RoyalPoker(player.getHand());
        combo.FourOfKind(player.getHand());
        combo.Full(player.getHand());
        combo.Flush(player.getHand());
        combo.TreeOfKind(player.getHand());
        combo.Straight(player.getHand());
        if (combo.Pair(player.getHand())) {
            combo.TwoPair(player.getHand());
        }
        player.setComboS(combo.comboScore);
        player.setCardS(combo.cardScore);
    }

}
