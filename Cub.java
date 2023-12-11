import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class Cub extends JPanel {
    private BufferedImage image;
    protected int x;
    protected int y;

    public Cub(String imagePath, int x, int y) {
        this.x = x;
        this.y = y;

        try {
            this.image = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(50, 50));
        setBounds(this.x, this.y, 50, 50);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Desenarea imaginii
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Metodele pentru a schimba poziția
    void up() {
        this.y -= 50;
        setBounds(this.x, this.y, 50, 50);
        repaint();
    }

    void down() {
        this.y += 50;
        setBounds(this.x, this.y, 50, 50);
        repaint();
    }

    void left() {
        this.x -= 50;
        setBounds(this.x, this.y, 50, 50);
        repaint();
    }

    void right() {
        this.x += 50;
        setBounds(this.x, this.y, 50, 50);
        repaint();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
