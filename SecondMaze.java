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
    public static int markedFinishes = 0;

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

        if (up && down && left && right) {
            moveCubs(player, stones, finishes, true, direction);
        } else {
            moveCubs(player, stones, finishes, false, direction);
        }

    }

    public static void moveCubs(Cub player, Cub[] stones, Cub[] finishes, boolean freeway, String direction) {
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
        } else {
            if (direction == "up") {
                boolean anotherStone = false;
                Cub colideStone = null;

                for (Cub stone : stones) {
                    if ((stone.getX() == player.getX()) && (stone.getY() == player.getY() - 50)) {
                        colideStone = stone;
                        break;
                    }
                }

                if (colideStone != null) {
                    for (Cub stone : stones) {
                        if ((colideStone.getX() == stone.getX()) && (colideStone.getY() - 50 == stone.getY())) {
                            anotherStone = true;
                            break;
                        }
                    }
                }

                if (!anotherStone) {
                    player.up();
                    colideStone.up();
                    checkWin(player,colideStone, finishes);
                }
            } else if (direction == "down") {
                boolean anotherStone = false;
                Cub colideStone = null;

                for (Cub stone : stones) {
                    if ((stone.getX() == player.getX()) && (stone.getY() == player.getY() + 50)) {
                        colideStone = stone;
                        break;
                    }
                }

                if (colideStone != null) {
                    for (Cub stone : stones) {
                        if ((colideStone.getX() == stone.getX()) && (colideStone.getY() + 50 == stone.getY())) {
                            anotherStone = true;
                            break;
                        }
                    }
                }

                if (!anotherStone) {
                    player.down();
                    colideStone.down();
                    checkWin(player,colideStone, finishes);
                }
            } else if (direction == "left") {
                boolean anotherStone = false;
                Cub colideStone = null;

                for (Cub stone : stones) {
                    if ((stone.getX() == player.getX() - 50) && (stone.getY() == player.getY())) {
                        colideStone = stone;
                        break;
                    }
                }

                if (colideStone != null) {
                    for (Cub stone : stones) {
                        if ((colideStone.getX() - 50 == stone.getX()) && (colideStone.getY() == stone.getY())) {
                            anotherStone = true;
                            break;
                        }
                    }
                }

                if (!anotherStone) {
                    player.left();
                    colideStone.left();
                    checkWin(player,colideStone, finishes);
                }
            } else if (direction.equals("right")) {
                boolean anotherStone = false;
                Cub colideStone = null;

                for (Cub stone : stones) {
                    if ((stone.getX() == player.getX() + 50) && (stone.getY() == player.getY())) {
                        colideStone = stone;
                        break;
                    }
                }

                if (colideStone != null) {
                    for (Cub stone : stones) {
                        if ((colideStone.getX() + 50 == stone.getX()) && (colideStone.getY() == stone.getY())) {
                            anotherStone = true;
                            break;
                        }
                    }
                }

                if (!anotherStone) {
                    player.right();
                    colideStone.right();
                    checkWin(player,colideStone, finishes);
                }
            }
        }
    }

    public static void checkWin(Cub player ,Cub colideStone, Cub[] finishes) {
        for (Cub finish : finishes) {
            if ((colideStone.getX() == finish.getX()) && (colideStone.getY() == finish.getY())) {
                markedFinishes++;
            }
        }

        if (markedFinishes == 3) {
            JLabel winLabel = new JLabel("You Win!");

            winLabel.setBounds(200, 100, 200, 50);
            winLabel.setFont(new Font("Arial", Font.BOLD, 25));
            winLabel.setHorizontalAlignment(SwingConstants.CENTER);

            player.getParent().add(winLabel);
            player.getParent().revalidate();
            player.getParent().repaint();
        }
    }
}
