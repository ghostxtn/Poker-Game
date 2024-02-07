package PokerGame;

import CardGame.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Window extends JPanel implements ActionListener {
    Image background = new ImageIcon("Textures/background.png").getImage();
    final int width = 1000;
    final int height = 1000;
    int n = 5;
    int[] winner = new int[4];
    Game game = new Game(n);
    int[] move = Game.setMove(n);
    int swapCount = 0;
    JButton start;
    JButton swap;
    JButton next;
    JButton exit;
    JButton close;
    boolean GameInProgress;
    ExecutorService exS = Executors.newFixedThreadPool(1);
    JLabel[] card = {new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel()};



    public Window() {


        setLayout(null);
        setBounds(0, 0, width, height);
        exS.execute(game);


        startWindow();


    }

    void startWindow() {

        start = new JButton("Play");
        start.setBounds(width / 2 - 50, height / 2 - 25, 100, 50);
        start.addActionListener(this);
        add(Logo());


        add(start);
    }

    void exitWindow() {

        boolean next = false;
        game = new Game(n);
        start = new JButton("Start new game");
        close = new JButton("Exit");


        start.setBounds(width / 2 - 210, height / 2 - 25, 200, 50);
        close.setBounds(width / 2 + 10, height / 2 - 25, 200, 50);
        start.addActionListener(this);
        close.addActionListener(this);
        JTextArea win = new JTextArea();

        for (int i = 0; i < winner.length; i++) {
            if (winner[i] == 1) {
                if (next) win.setText(win.getText() + ", ");
                if (i == 0) win.setText(win.getText() + "You");
                else win.setText(win.getText() + "Player " + (i + 1));
                next = true;

            }

        }
        win.setText("Are you sure u wanna leave the game ?");
        win.setOpaque(false);
        win.setEditable(false);


        Font text = new Font("Cooper Black", Font.BOLD, 35);


        win.setFont(text);

        win.setForeground(Color.WHITE);


        win.setSize(win.getPreferredSize());
        win.setBounds(width / 2 - win.getWidth() / 2, height / 2 - 200, win.getWidth(), win.getHeight());

        add(win);


        exS = Executors.newFixedThreadPool(1);
        exS.execute(game);
        add(close);
        add(start);

    }



    void startGame() {
        removeAll();
        revalidate();
        repaint();
        GameInProgress = true;
        swapCount = 0;
        swap = new JButton();


        swap.setBounds(width / 2 - 50, height / 2 - 75, 100, 150);


        swapUpdate();
        swap.addActionListener(this);


        game.startRound();

        addCard(0, game.getPlayer(0).getHand(), true);
        addCard(1, game.getPlayer(1).getHand(), false);
        addCard(2, game.getPlayer(2).getHand(), false);
        addCard(3, game.getPlayer(3).getHand(), false);

        add(swap);

        repaint();


    }



    void endGame() {

        GameInProgress = false;
        game.addMove(move);
        game.release();
        addCard(0, game.getPlayer(0).getHand(), true);
        addCard(1, game.getPlayer(1).getHand(), true);
        addCard(2, game.getPlayer(2).getHand(), true);
        addCard(3, game.getPlayer(3).getHand(), true);
        next = new JButton("Play Next Round");
        next.setBounds(350, height - 310, 140, 50);
        next.addActionListener(this);
        exit = new JButton("End game");
        exit.setBounds(350 + 150, height - 310, 140, 50);
        exit.addActionListener(this);

        JLabel deck = new JLabel();
        deck.setIcon(scale(100, 150, new ImageIcon("Textures/Card/PNG/back.png")));
        deck.setBounds(width / 2 - 50, height / 2 - 75, 100, 150);

        int[] winners = game.endRound();
        showWinners(winners);
        add(next);
        add(exit);
        add(deck);

        move = Game.setMove(n);
    }


    void addCard(int pos, Card[] hand, boolean vis) {

        JLabel[] card = {new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel()};

        switch (pos) {
            case 0:
                if (vis)
                    for (int i = 0; i < hand.length; i++) {


                        card[i].setIcon(scale(100, 150, new ImageIcon(hand[i].getName())));
                        card[i].setBounds(240 + i * 110, height - 200, 110, 150);

                        card[i].setText(String.valueOf(i));
                        card[i].setName(hand[i].getName());
                        this.card[i] = card[i];
                        Mouse_Handler(this.card[i]);

                        add(this.card[i]);

                    }
                else
                    for (int i = 0; i < hand.length; i++) {
                        card[i].setIcon(scale(100, 150, new ImageIcon("Textures/Card/PNG/back.png")));
                        card[i].setBounds(250 + i * 100, height - 200, 100, 150);
                        add(card[i]);

                    }

                break;
            case 1:
                if (vis)
                    for (int i = 0; i < hand.length; i++) {

                        card[i].setIcon(rotateBy(90, scale(100, 150, new ImageIcon(hand[i].getName())).getImage()));

                        card[i].setBounds(10, 250 + i * 100, 150, 100);
                        add(card[i]);

                    }
                else
                    for (int i = 0; i < hand.length; i++) {

                        card[i].setIcon(rotateBy(90, scale(100, 150, new ImageIcon("Textures/Card/PNG/back.png")).getImage()));
                        card[i].setBounds(10, 250 + i * 100, 150, 100);
                        add(card[i]);

                    }

                break;
            case 2:
                if (vis)
                    for (int i = 0; i < hand.length; i++) {


                        card[i].setIcon(scale(100, 150, new ImageIcon(hand[i].getName())));
                        card[i].setBounds(250 + i * 100, 10, 100, 150);
                        add(card[i]);

                    }
                else
                    for (int i = 0; i < hand.length; i++) {
                        card[i].setIcon(scale(100, 150, new ImageIcon("Textures/Card/PNG/back.png")));
                        card[i].setBounds(250 + i * 100, 10, 100, 150);
                        add(card[i]);

                    }
                break;
            case 3:
                if (vis)
                    for (int i = 0; i < hand.length; i++) {


                        card[i].setIcon(rotateBy(90, scale(100, 150, new ImageIcon(hand[i].getName())).getImage()));

                        card[i].setBounds(width - 180, 250 + i * 100, 150, 100);
                        add(card[i]);

                    }
                else
                    for (int i = 0; i < hand.length; i++) {
                        card[i].setIcon(rotateBy(270, scale(100, 150, new ImageIcon("Textures/Card/PNG/back.png")).getImage()));
                        card[i].setBounds(width - 180, 250 + i * 100, 150, 100);
                        add(card[i]);

                    }

                break;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {

        g.drawImage(background, 0, 0, null);

    }

    JLabel Logo() {
        JLabel logo = new JLabel();
        ImageIcon icon = new ImageIcon("Textures/Logo.png");
        logo.setIcon(scale(500, 300, icon));
        logo.setBounds(width / 2 - logo.getIcon().getIconWidth() / 2, 1000 / 2 - 300, logo.getIcon().getIconWidth(), logo.getIcon().getIconHeight());
        logo.setVisible(true);
        return logo;

    }


    ImageIcon scale(int width, int height, ImageIcon imageIcon) {
        ImageIcon temp = new ImageIcon();
        Image modIm = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        temp.setImage(modIm);
        return temp;
    }


    public ImageIcon rotateBy(double degrees, Image source) {
        ImageIcon temp = new ImageIcon();

        int w = source.getWidth(null);
        int h = source.getHeight(null);

        double rads = Math.toRadians(degrees);

        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);


        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();

        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);


        int x = w / 2;
        int y = h / 2;
        at.rotate(rads, x, y);
        g2d.setTransform(at);

        g2d.drawImage(source, 0, 0, null);
        g2d.dispose();

        temp.setImage(rotated);

        return temp;
    }

    public ImageIcon combineImage(int width, int height, ImageIcon image, ImageIcon overlay) {
        ImageIcon temp = new ImageIcon();
        BufferedImage combine = new BufferedImage(overlay.getIconWidth(), overlay.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics g = combine.getGraphics();
        g.drawImage(image.getImage(), 0, 0, null);
        g.drawImage(overlay.getImage(), 0, 0, null);

        g.dispose();

        temp.setImage(combine);
        temp = scale(width, height, temp);

        return temp;


    }

    int contain(int number) {
        for (int i = 0; i < move.length; i++) {
            if (move[i] == number) return i;
        }
        return -1;
    }

    void addMove(int pos) {
        int index = contain(pos);
        if (index == -1) {
            for (int i = 0; i < move.length; i++) {
                if (move[i] == -1) {
                    move[i] = pos;
                    return;
                }

            }
        } else move[index] = -1;


    }

    void swapUpdate() {
        ImageIcon swp = combineImage(100, 150, new ImageIcon("Textures/Card/PNG/back.png"), new ImageIcon("Textures/Swap/Done/Swap.png"));
        ImageIcon number = scale(100, 150, new ImageIcon("Textures/Swap/Done/" + swapCount + ".png"));


        swap.setIcon(combineImage(100, 150, swp, number));
        swap.setContentAreaFilled(false);
        swap.setBorderPainted(false);
    }


    void showWinners(int[] winners) {
        JTextArea win = new JTextArea();
        JTextArea comb = new JTextArea();
        boolean next = false;
        int cardS = 2;
        int combS = 1;

        for (int i = 0; i < winners.length; i++) {
            if (winners[i] == 1) {
                if (next) win.setText(win.getText() + ", ");
                if (i == 0) win.setText(win.getText() + "You");
                else win.setText(win.getText() + "Player " + (i + 1));
                if (!next) {
                    cardS = game.getPlayer(i).getCardS();
                    combS = game.getPlayer(i).getComboS();
                }
                next = true;

            }

        }
        win.setText(win.getText() + " wins");
        comb.setText("with " + Combo.combo[combS] + ", Higher card : " + Card.Figures[cardS]);

        win.setOpaque(false);
        win.setEditable(false);

        comb.setOpaque(false);
        comb.setEditable(false);

        Font text = new Font("Cooper Black", Font.BOLD, 26);


        win.setFont(text);
        comb.setFont(text);
        comb.setForeground(Color.WHITE);
        win.setForeground(Color.WHITE);

        comb.setSize(comb.getPreferredSize());
        win.setSize(win.getPreferredSize());
        win.setBounds(width / 2 - win.getWidth() / 2, height / 2 - 200, win.getWidth(), win.getHeight());
        comb.setBounds(width / 2 - comb.getWidth() / 2, height / 2 - 200 + win.getHeight(), comb.getWidth(), comb.getHeight());
        add(win);
        add(comb);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object button = e.getSource();
        if (button == start) {
            removeAll();
            revalidate();
            repaint();

            startGame();

        }
        if (button == swap) {
            removeAll();
            revalidate();
            repaint();

            endGame();

        }
        if (button == next) {
            removeAll();
            revalidate();
            repaint();


            startGame();

        }
        if (button == exit) {
            removeAll();
            revalidate();
            repaint();
            exitWindow();

        }
        if (button == close) {
            System.exit(1);
        }

    }


    private void Mouse_Handler(JLabel label) {

        label.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {


            }

            @Override
            public void mousePressed(MouseEvent e) {
                String position = label.getText();
                int index = Integer.parseInt(position);
                if (GameInProgress)
                    if (contain(index) == -1 && contain(-1) != -1) {

                        addMove(index);




                        ImageIcon im = new ImageIcon("Textures/Card/PNG/SEL1.png");
                        ImageIcon im1 = new ImageIcon(label.getName());
                        ImageIcon combined = combineImage(100, 150, im1, im);

                        swapCount++;

                        swapUpdate();


                        card[index].setIcon(combined);


                    } else {

                        addMove(index);
                        card[index].setIcon(scale(100, 150, new ImageIcon(label.getName())));
                        if (contain(-1) != -1) swapCount--;

                        swapUpdate();


                    }


                revalidate();
                repaint();


            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }
}
