import java.awt.*;
import javax.swing.*;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class FirstMaze extends JFrame {
    public BufferedImage backgroundImage; // Imaginea de fundal
    public JPanel p;  // Facem p o variabilă de instanță

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FirstMaze firstMaze = new FirstMaze();
            firstMaze.loadBackgroundImage("background.png"); // Încarcă imaginea de fundal

            Cub player = new Cub("player.png", 100, 300);
            Cub stone = new Cub("stone.png", 300, 300);
            Cub finish = new Cub("finish.png", 500, 300);

            firstMaze.p = new JPanel(null) {  // Inițializăm p
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (firstMaze.backgroundImage != null) {
                        g.drawImage(firstMaze.backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    }
                }
            };

            firstMaze.p.add(player);
            firstMaze.p.add(stone);
            firstMaze.p.add(finish);
            firstMaze.p.setBackground(Color.WHITE);

            firstMaze.add(firstMaze.p);
            firstMaze.setSize(650, 650);
            firstMaze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            firstMaze.setFocusable(true);

            // Adaugăm acțiuni pentru tastele sageti
            addAction(player, "UP", () -> {
                player.up();
                checkMove(player, stone, finish, "up");
            });
            addAction(player, "DOWN", () -> {
                player.down();
                checkMove(player, stone, finish, "down");
            });
            addAction(player, "LEFT", () -> {
                player.left();
                checkMove(player, stone, finish, "left");
            });
            addAction(player, "RIGHT", () -> {
                player.right();
                checkMove(player, stone, finish, "right");
            });

            firstMaze.setVisible(true);
        });
    }

    public void loadBackgroundImage(String imagePath) {
        try {
            this.backgroundImage = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addAction(Cub cub, String key, Runnable action) {
        Action pressedAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        };

        cub.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
        cub.getActionMap().put(key, pressedAction);
    }

    public static void checkMove(Cub player, Cub stone, Cub finish, String direction) {
        if ("up".equals(direction) && (player.getX() == stone.getX()) && (player.getY() == stone.getY())) {
            stone.up();
        } else if ("down".equals(direction) && (player.getX() == stone.getX()) && (player.getY() == stone.getY())) {
            stone.down();
        } else if ("left".equals(direction) && (player.getY() == stone.getY()) && (player.getX() == stone.getX())) {
            stone.left();
        } else if ("right".equals(direction) && (player.getY() == stone.getY()) && (player.getX() == stone.getX())) {
            stone.right();
        }
        checkWin(stone, finish);
    }

    public static void checkWin(Cub stone, Cub finish) {
        if (stone.getX() == finish.getX() && stone.getY() == finish.getY()) {
            JLabel winLabel = new JLabel("You Win!");

            winLabel.setBounds(225, 100, 200, 50);
            winLabel.setFont(new Font("Arial", Font.BOLD, 25));
            winLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
            stone.getParent().add(winLabel);
            stone.getParent().revalidate();
            stone.getParent().repaint();
        }
    }
}
