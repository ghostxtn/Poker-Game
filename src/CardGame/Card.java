package CardGame;


public class Card {
    public static final String[] Figures = {null, null, "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    public static final String[] FiguresN = {null, null, "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    public enum Color {
        DIAMOND,
        HEART,
        CLUB,
        SPADES

    }
    Color color;
    int figure;
    public Card(Color color, int figure) {
        this.color = color;
        this.figure = figure;
    }
    public int getFigure() {
        return figure;
    }
    public Color getColor() {
        return color;
    }
    public String getName() {
        String col = "P";
        if (color.equals(Color.CLUB)) col = "C";
        if (color.equals(Color.SPADES)) col = "S";
        if (color.equals(Color.HEART)) col = "H";
        if (color.equals(Color.DIAMOND)) col = "D";
        return "Textures/Card/PNG/" + FiguresN[figure] + col + ".png";
    }
}
