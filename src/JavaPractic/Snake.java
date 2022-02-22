package JavaPractic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Snake extends JFrame {

    // Propiedades de la interfaz
    public String title = "Snake";
    public int width = 640, height = 480;

    // Propiedades de la serpiente
    public Point snake;
    public int snakewidth = 10, snakeheight = 10;
    public Imagensnake imagensnake;

    // Propiedades de la comida
    public Point Food;

    // Propiedades del juego
    public int direction = KeyEvent.VK_LEFT;
    public long frequency = 20;

    public Snake() {

        setTitle(title);
        setSize(width, height);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int dimensionwidth = dimension.width / 2 - width / 2;
        int dimensionheight = dimension.height / 2 - height / 2;
        this.setLocation(dimensionwidth, dimensionheight);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Comando comando = new Comando();
        this.addKeyListener(comando);

        snake = new Point(width / 2, height / 2);

        imagensnake = new Imagensnake();
        this.getContentPane().add(imagensnake);

        Movimiento movimiento = new Movimiento();
        Thread trid = new Thread(movimiento);
        trid.start();

        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        Snake snake = new Snake();
    }

    public void UpdateFrame() {
        imagensnake.repaint();
    }

    public class Imagensnake extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(0, 0, 255));
            g.fillRect(snake.x, snake.y, snakewidth, snakeheight);
        }
    }

    public class Comando extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (direction != KeyEvent.VK_LEFT) {
                    direction = KeyEvent.VK_RIGHT;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (direction != KeyEvent.VK_RIGHT) {
                    direction = KeyEvent.VK_LEFT;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (direction != KeyEvent.VK_DOWN) {
                    direction = KeyEvent.VK_UP;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (direction != KeyEvent.VK_UP) {
                    direction = KeyEvent.VK_DOWN;
                }
            }
        }
    }

    public class Movimiento extends Thread {

        private long last = 0;

        public Movimiento() {
        }

        public void run() {
            while (true) {
                if ((java.lang.System.currentTimeMillis() - last) > frequency) {

                    if (direction == KeyEvent.VK_RIGHT) {
                        snake.x = snake.x + snakewidth;
                        if (snake.x > width) {
                            snake.x = 0;
                        }
                    } else if (direction == KeyEvent.VK_LEFT) {
                        snake.x = snake.x - snakewidth;
                        if (snake.x < 0) {
                            snake.x = width - snakewidth;
                        }
                    } else if (direction == KeyEvent.VK_UP) {
                        snake.y = snake.y - snakeheight;
                        if (snake.y < 0) {
                            snake.y = height;
                        }
                    } else if (direction == KeyEvent.VK_DOWN) {
                        snake.y = snake.y + snakeheight;
                        if (snake.y > height) {
                            snake.y = 0;
                        }
                    }

                    UpdateFrame();
                    last = java.lang.System.currentTimeMillis();
                }
            }

        }
    }

}