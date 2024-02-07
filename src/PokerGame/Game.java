package PokerGame;

import CardGame.Deck;

import java.util.Random;
import java.util.concurrent.Semaphore;


public class Game implements Runnable {
    int n;
    Player[] players = {new Player(), new Player(), new Player(), new Player()};
    Deck deck;
    int[] move;
    Semaphore sync = new Semaphore(0);
    public Game(int n) {
        this.n = n;
        deck = new Deck();
        move = setMove(n);

    }
    public static int[] setMove(int n) {
        int[] move = new int[n];
        for (int i = 0; i < move.length; i++) {
            move[i] = -1;
        }
        return move;
    }
    public void startRound() {


        deck.shuffle();

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < players.length; j++) {

                players[j].getCard(deck);

            }
        }


    }
    int[] givePoints() {
        int[] winners = new int[4];
        int maxCombo = 1;
        int maxCard = 2;
        for (int i = 0; i < players.length; i++) {
            if (maxCombo < players[i].getComboS()) maxCombo = players[i].getComboS();
        }
        for (int i = 0; i < players.length; i++) {
            if (maxCombo == players[i].getComboS())
                if (maxCard < players[i].getCardS()) maxCard = players[i].getCardS();
        }
        for (int i = 0; i < players.length; i++) {
            if (players[i].getComboS() == maxCombo && players[i].getCardS() == maxCard) {
                players[i].addPoint();
                winners[i] = 1;
            }

        }

        return winners;
    }
    int[] endRound() {
        for (int i = 0; i < players.length; i++) {
            Combo.setScore(players[i]);
        }
        int[] winners = givePoints();


        for (int i = 0; i < players.length; i++) {


            for (int j = 0; j < 5; j++) {

                players[i].putCard(j, deck);


            }

        }
        return winners;
    }
    void release() {
        sync.release();
    }
    public Player getPlayer(int i) {
        return players[i];
    }
    void changeCard(int playerIndex) {
        for (int i = 0; i < move.length; i++) {
            if (move[i] != -1) {

                deck.addLast(players[playerIndex].putCard(move[i]));
                players[playerIndex].getCard(deck);


            }
        }
    }
    void addMove(int[] move) {
        this.move = move;
    }
    void defaultMoveArray() {
        for (int i = 0; i < move.length; i++) {
            move[i] = -1;

        }
    }
    boolean contain(int number) {
        for (int i = 0; i < move.length; i++) {
            if (move[i] == number) return true;
        }
        return false;
    }
    void AImove() {
        Random random = new Random();
        int c = random.nextInt(n);
        int temp = random.nextInt(n);
        for (int i = 0; i < c; i++) {
            while (contain(temp)) temp = random.nextInt(5);
            move[i] = temp;

        }
    }
    @Override
    public void run() {
        while (true) {
            try {
                sync.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            changeCard(0);
            defaultMoveArray();


            AImove();
            changeCard(1);
            defaultMoveArray();


            AImove();
            changeCard(2);
            defaultMoveArray();


            AImove();
            changeCard(3);
            defaultMoveArray();


        }
    }

}
