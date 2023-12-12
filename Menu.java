import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Menu {
    private ImageIcon backgroundImage;
    private JFrame frame;
    private JPanel panel;

    public Menu() {
        try {
            backgroundImage = new ImageIcon(getClass().getResource("game.gif"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("Meniu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);

        panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        addButton("Level I", 150, 100, 100, 50, "FirstMaze.java");
        addButton("Level II", 300, 100, 100, 50, "SecondMaze.java");
        addButton("Level III", 450, 100, 100, 50, "SecondMaze.java");

        frame.add(panel);

        frame.setVisible(true);
    }

    private void addButton(String buttonText, int x, int y, int width, int height, final String mazeClassFile) {
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBounds(x, y, width, height);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String command = "javac " + mazeClassFile;
                    Process compileProcess = Runtime.getRuntime().exec(command);
                    compileProcess.waitFor();

                    command = "java " + mazeClassFile.replace(".java", "");
                    Process runProcess = Runtime.getRuntime().exec(command);

                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(button);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu();
            }
        });
    }
}
