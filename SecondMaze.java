import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

class SecondMaze extends FirstMaze {
    public static boolean up = true;
    public static boolean down = true;
    public static boolean left = true;
    public static boolean right = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SecondMaze secondMaze = new SecondMaze();
            secondMaze.loadBackgroundImage("background.png");

            Cub player = new Cub("player.png", 100, 100);

            Cub stone1 = new Cub("stone.png", 450, 150);
            Cub stone2 = new Cub("stone.png", 450, 100);
            Cub stone3 = new Cub("stone.png", 500, 100);

            Cub finish1 = new Cub("finish.png", 450, 500);
            Cub finish2 = new Cub("finish.png", 500, 450);
            Cub finish3 = new Cub("finish.png", 500, 500);

            Cub[] stones = {stone1, stone2, stone3};
            Cub[] finishes = {finish1, finish2, finish3};

            secondMaze.p = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (secondMaze.backgroundImage != null) {
                        g.drawImage(secondMaze.backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    }
                }
            };

            secondMaze.p.add(player);
            secondMaze.p.add(stone1);
            secondMaze.p.add(stone2);
            secondMaze.p.add(stone3);
            secondMaze.p.add(finish1);
            secondMaze.p.add(finish2);
            secondMaze.p.add(finish3);

            secondMaze.p.setBackground(Color.WHITE);

            secondMaze.add(secondMaze.p);
            secondMaze.setSize(650, 650);
            secondMaze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            secondMaze.setFocusable(true);

            addAction(player, "UP", () -> {
                checkMove(player, stones, finishes, "up");
            });
            addAction(player, "DOWN", () -> {
                checkMove(player, stones, finishes, "down");
            });
            addAction(player, "LEFT", () -> {
                checkMove(player, stones, finishes, "left");
            });
            addAction(player, "RIGHT", () -> {
                checkMove(player, stones, finishes, "right");
            });

            secondMaze.setVisible(true);
        });
    }

    public static void checkMove(Cub player, Cub[] stones, Cub[] finishes, String direction) {
        boolean up = true;
        boolean down = true;
        boolean left = true;
        boolean right = true;
        boolean freeway;

        for (int i = 0; i < stones.length; i++) {
            if ((stones[i].getX() == player.getX()) && (stones[i].getY() == player.getY() + 50)) {
                down = false;
            } else if ((stones[i].getX() == player.getX()) && (stones[i].getY() == player.getY() - 50)) {
                up = false;
            } else if ((stones[i].getX() == player.getX() + 50) && (stones[i].getY() == player.getY())) {
                right = false;
            } else if ((stones[i].getX() == player.getX() - 50) && (stones[i].getY() == player.getY())) {
                left = false;
            }
        }

        System.out.println("up:" + up);
        System.out.println("down:" + down);
        System.out.println("left:" + left);
        System.out.println("right:" + right);

        if (up && down && left && right) {
            freeway = true;
        } else {
            freeway = false;
        }

        if (freeway) {
            if (direction == "up") {
                player.up();
            } else if (direction == "down") {
                player.down();
            } else if (direction == "left") {
                player.left();
            } else if (direction == "right") {
                player.right();
            }
        }
    }

    public static void 
}
