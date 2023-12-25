import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThirdMaze extends FirstMaze {
    public static int markedFinishes = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThirdMaze thirdMaze = new ThirdMaze();
            ThirdMaze.loadBackgroundImage("background.png");

            Cub player = new Cub("player.png", 100, 100);

            Cub stone1 = new Cub("stone.png", 600, 100);
            Cub stone2 = new Cub("stone.png", 650, 100);
            Cub stone3 = new Cub("stone.png", 550, 150);
            Cub stone4 = new Cub("stone.png", 600, 150);

            Cub finish1 = new Cub("finish.png", 600, 600);
            Cub finish2 = new Cub("finish.png", 650, 600);
            Cub finish3 = new Cub("finish.png", 600, 650);
            Cub finish4 = new Cub("finish.png", 650, 650);

            Cub[] stones = {stone1, stone2, stone3, stone4};
            Cub[] finishes = {finish1, finish2, finish3, finish4};
            List<Cub> walls = new ArrayList<>();

            int[] xPosition = {450, 450, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450, 550, 600, 650, 700, 750, 250, 250, 250, 250, 250, 550, 550, 400, 350, 400, 450, 400, 600, 650, 700, 750, 0, 50, 100, 150, 200, 250, 300, 350, 400, 450};
            int[] yPosition = {150, 200, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 350, 400, 450, 500, 300, 350, 400, 450, 450, 450, 500, 500, 500, 500, 500, 550, 550, 550, 550, 550, 550, 600, 650, 700, 750};


            ThirdMaze.p = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (thirdMaze.backgroundImage != null) {
                        g.drawImage(thirdMaze.backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    }
                }
            };

            thirdMaze.p.add(player);
            thirdMaze.p.add(stone1);
            thirdMaze.p.add(stone2);
            thirdMaze.p.add(stone3);
            thirdMaze.p.add(stone4);
            thirdMaze.p.add(finish1);
            thirdMaze.p.add(finish2);
            thirdMaze.p.add(finish3);
            thirdMaze.p.add(finish4);

            for (int i = 0; i < xPosition.length; i++) {
                Cub wall = new Cub("wall.png", xPosition[i], yPosition[i]);
                thirdMaze.p.add(wall);
                walls.add(wall);
            }

            thirdMaze.p.setBackground(Color.WHITE);

            thirdMaze.add(thirdMaze.p);
            thirdMaze.setSize(800, 800);
            thirdMaze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            thirdMaze.setFocusable(true);

            addAction(player, "UP", () -> {
                checkMove(player, stones, walls, finishes, "up");
            });
            addAction(player, "DOWN", () -> {
                checkMove(player, stones, walls, finishes, "down");
            });
            addAction(player, "LEFT", () -> {
                checkMove(player, stones, walls, finishes, "left");
            });
            addAction(player, "RIGHT", () -> {
                checkMove(player, stones, walls, finishes, "right");
            });

            thirdMaze.setVisible(true);
        });
    }

    public static void checkMove(Cub player, Cub[] stones, List<Cub> walls, Cub[] finishes, String direction) {
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

        for (int i = 0; i < walls.size(); i++) {
            if ((walls.get(i).getX() == player.getX()) && (walls.get(i).getY() == player.getY() + 50)) {
                down = false;
            } else if ((walls.get(i).getX() == player.getX()) && (walls.get(i).getY() == player.getY() - 50)) {
                up = false;
            } else if ((walls.get(i).getX() == player.getX() + 50) && (walls.get(i).getY() == player.getY())) {
                right = false;
            } else if ((walls.get(i).getX() == player.getX() - 50) && (walls.get(i).getY() == player.getY())) {
                left = false;
            }
        }

        if (up && down && left && right) {
            moveCubs(player, stones, walls, finishes, true, direction);
        } else {
            moveCubs(player, stones, walls, finishes, false, direction);
        }
    }

    public static void moveCubs(Cub player, Cub[] stones, List<Cub> walls, Cub[] finishes, boolean freeway, String direction) {
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
                boolean thereIsAWall = false;
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

                    for (Cub wall : walls) {
                        if ((colideStone.getX() == wall.getX()) && (colideStone.getY() - 50 == wall.getY())) {
                            thereIsAWall = true;
                            break;
                        }
                    }
                }

                for (Cub wall : walls) {
                    if ((player.getX() == wall.getX()) && (player.getY() - 50 == wall.getY())) {
                        thereIsAWall = true;
                        break;
                    }
                }

                if (!anotherStone && !thereIsAWall) {
                    player.up();
                    colideStone.up();
                    checkWin(player, colideStone, finishes);
                }
            }  else if (direction == "down") {
                boolean anotherStone = false;
                boolean thereIsAWall = false;
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

                    for (Cub wall : walls) {
                        if ((colideStone.getX() == wall.getX()) && (colideStone.getY() + 50 == wall.getY())) {
                            thereIsAWall = true;
                            break;
                        }
                    }
                }

                for (Cub wall : walls) {
                    if ((player.getX() == wall.getX()) && (player.getY() +  50 == wall.getY())) {
                        thereIsAWall = true;
                        break;
                    }
                }

                if (!anotherStone && !thereIsAWall) {
                    player.down();
                    colideStone.down();
                    checkWin(player, colideStone, finishes);
                }
            } else if (direction == "left") {
                boolean anotherStone = false;
                boolean thereIsAWall = false;
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

                    for (Cub wall : walls) {
                        if ((colideStone.getX() - 50 == wall.getX()) && (colideStone.getY() == wall.getY())) {
                            thereIsAWall = true;
                            break;
                        }
                    }
                }

                for (Cub wall : walls) {
                    if ((player.getX() - 50 == wall.getX()) && (player.getY() == wall.getY())) {
                        thereIsAWall = true;
                        break;
                    }
                }

                if (!anotherStone && !thereIsAWall) {
                    player.left();
                    colideStone.left();
                    checkWin(player, colideStone, finishes);
                }
            } else if (direction.equals("right")) {
                boolean anotherStone = false;
                boolean thereIsAWall = false;
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

                    for (Cub wall : walls) {
                        if ((colideStone.getX() + 50 == wall.getX()) && (colideStone.getY() == wall.getY())) {
                            thereIsAWall = true;
                            break;
                        }
                    }
                }

                for (Cub wall : walls) {
                    if ((player.getX() + 50 == wall.getX()) && (player.getY() == wall.getY())) {
                        thereIsAWall = true;
                        break;
                    }
                }

                if (!anotherStone && !thereIsAWall) {
                    player.right();
                    colideStone.right();
                    checkWin(player, colideStone, finishes);
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

        if (markedFinishes == 4) {
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