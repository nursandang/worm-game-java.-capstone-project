//nur sandang 51240052

package com.mycompany.wormgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

// Enum bentuk kepala
enum HeadShape {
    CIRCLE, RECTANGLE, TRIANGLE, PARALLELOGRAM
}

public class WormGame extends JPanel implements ActionListener, KeyListener {
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private final int UNIT_SIZE = 20;
    private final int DELAY = 150;

    private final LinkedList<Point> worm = new LinkedList<>();
    private Point food;
    private Color[] wormColors;
    private Color currentFoodColor;
    private Color backgroundColor;
    private HeadShape currentHeadShape = HeadShape.CIRCLE;

    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private Random random;
    private int score = 0;

    // Tambahan untuk bunga dan daun jatuh
    private final int NUM_FLOWERS = 30;
    private Point[] flowerPositions;
    private int[] flowerSpeeds;

    private final int NUM_TREES = 5;
    private Point[] treePositions;

    private Random rand = new Random();

    public WormGame() {
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(this);
        initializeColors();

        // Inisialisasi bunga jatuh
        flowerPositions = new Point[NUM_FLOWERS];
        flowerSpeeds = new int[NUM_FLOWERS];
        for (int i = 0; i < NUM_FLOWERS; i++) {
            flowerPositions[i] = new Point(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
            flowerSpeeds[i] = 1 + rand.nextInt(3); // kecepatan jatuh bunga 1-3 pixel/frame
        }

        // Inisialisasi posisi pohon tetap di bawah
        treePositions = new Point[NUM_TREES];
        for (int i = 0; i < NUM_TREES; i++) {
            int x = i * (WIDTH / NUM_TREES) + rand.nextInt(UNIT_SIZE * 3);
            int y = HEIGHT - UNIT_SIZE * 4;
            treePositions[i] = new Point(x, y);
        }

        startGame();
    }

   private void initializeColors() {
        wormColors = new Color[]{
            Color.decode("#ADD8E6"),
            Color.decode("#90EE90"),
            Color.decode("#FFB6C1"),
            Color.decode("#DDA0DD"),
            Color.decode("#FFDAB9")
        };
        // Generasi warna makanan gelap tetap, tetapi sekarang akan memastikan tidak terlalu terbentang saat dijadikan warna aktif (akan diimplementasikan dalam metode generateFoodColor)
        currentFoodColor = generateFoodColor(); // Makanan gelap tetap maupun terang tetap berada dalam batas yang mesun
        backgroundColor = generateSoftColor();    // Latar lembut
}

// Metode untuk generasi warna makanan, ada gunakan perbandingan untuk memastikan warna tidak terlalu terbentang terhadap latar belakang saat ini.
private Color generateFoodColor() {
    Color foodColor = new Color(generateRandomNumber(0xFF), generateRandomNumber(0xFF), generateRandomNumber(0xFF));
    
    // Cari perbandingan dengan latar belakang saat ini
    Color bgColor = backgroundColor; 
    int rDiff = Math.abs(foodColor.getRed() - bgColor.getRed());
    int gDiff = Math.abs(foodColor.getGreen() - bgColor.getGreen());
    int bDiff = Math.abs(foodColor.getBlue() - bgColor.getBlue());
    
    // Jika keterbentuan dengan latar belakang adalah terlebih lanjut (di atas 150), pemihak warna
    if (rDiff > 150 || gDiff > 150 || bDiff > 150) {
        float contrast = (0.8f + (0.2f * Math.random())) / 2.0f; // Kemampuan untuk jarak kontras yang lebih lemah atau lebih ketat
        foodColor = adjustContrast(foodColor, contrast);
    }

    return foodColor;
}

// Metode untuk memuliakan kontras
private Color adjustContrast(Color c, float factor) {
    int h = (int) c.getHue();
    int s = (int) ((c.getRed() + c.getGreen() + c.getBlue()) / 3f / 255.0f);
    int v = (int) (c.getRed() * factor + (c.getGreen() * (1 - factor)) + c.getBlue() * (1 - factor));

    return new Color(h, s, v);
}

// Metode untuk membuat angka acak di lawan dari jSUPERIORity 255
private int generateRandomNumber(int max) {
    return (int) (Math.random() * max);
}


    private Color generateSoftColor() {
     int r = 150 + random.nextInt(105);
     int g = 150 + random.nextInt(105);
     int b = 150 + random.nextInt(105);
     return new Color(r, g, b);
 }

 private Color generateDarkBrightColor() {
     int r = 50 + random.nextInt(80);
     int g = 50 + random.nextInt(80);
     int b = 50 + random.nextInt(80);
     float[] hsb = new java.awt.Color(r, g, b).getHSBColor() ;
     float brightness = Math.max(0.6f, hsb[2]);
     return new Color(new float[]{hsb[0], hsb[1], brightness});
 }

    public void startGame() {
        worm.clear();
        worm.add(new Point(WIDTH / (2 * UNIT_SIZE), HEIGHT / (2 * UNIT_SIZE)));
        worm.add(new Point(WIDTH / (2 * UNIT_SIZE) - 1, HEIGHT / (2 * UNIT_SIZE)));
        worm.add(new Point(WIDTH / (2 * UNIT_SIZE) - 2, HEIGHT / (2 * UNIT_SIZE)));
        direction = 'R';
        score = 0;
        placeFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void placeFood() {
        int x, y;
        do {
            x = random.nextInt(WIDTH / UNIT_SIZE);
            y = random.nextInt(HEIGHT / UNIT_SIZE);
            food = new Point(x, y);
        } while (worm.contains(food));

        currentFoodColor = generateDarkBrightColor(); // Warna makanan gelap tapi terang
        currentHeadShape = HeadShape.values()[random.nextInt(HeadShape.values().length)];
        backgroundColor = generateSoftColor();    // Warna latar lembut
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Gambar pohon di bawah
        for (Point treePos : treePositions) {
            drawTree(g, treePos.x, treePos.y);
        }
        // Gambar bunga jatuh dan update posisi jatuh
        for (int i = 0; i < NUM_FLOWERS; i++) {
            drawFlower(g, flowerPositions[i].x, flowerPositions[i].y);
            flowerPositions[i].y += flowerSpeeds[i];
            if (flowerPositions[i].y > HEIGHT) {
                flowerPositions[i].y = 0;
                flowerPositions[i].x = rand.nextInt(WIDTH);
                flowerSpeeds[i] = 1 + rand.nextInt(3);
            }
        }

        setBackground(backgroundColor);
        draw(g);

        // Tambahkan garis pagar
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
    }

    private void drawTree(Graphics g, int x, int y) {
        // batang pohon coklat
        g.setColor(new Color(101, 67, 33));
        g.fillRect(x + 8, y + 30, 10, 30);
        // daun hijau bundar
        g.setColor(new Color(34, 139, 34));
        g.fillOval(x, y, 30, 40);
    }

    private void drawFlower(Graphics g, int x, int y) {
        // batang bunga hijau
        g.setColor(new Color(34, 139, 34));
        g.fillRect(x + 4, y + 10, 2, 10);
        // kelopak bunga berwarna-warni
        Color[] petalColors = {Color.PINK, Color.RED, Color.MAGENTA, Color.ORANGE, Color.YELLOW};
        g.setColor(petalColors[rand.nextInt(petalColors.length)]);
        g.fillOval(x, y, 10, 10);
        // tengah bunga kuning
        g.setColor(Color.YELLOW);
        g.fillOval(x + 3, y + 3, 4, 4);
    }

    public void draw(Graphics g) {
        if (running) {
            // Gambar makanan
            g.setColor(currentFoodColor);
            g.fillRoundRect(food.x * UNIT_SIZE, food.y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, 10, 10);
            g.setColor(Color.white);
            g.fillOval(food.x * UNIT_SIZE + 5, food.y * UNIT_SIZE + 5, 5, 5);

            // Gambar worm
            for (int i = 0; i < worm.size(); i++) {
                Point p = worm.get(i);
                Color color = (i == 0)
                        ? wormColors[score % wormColors.length].brighter()
                        : new Color(
                        (int) (wormColors[score % wormColors.length].getRed() * (1 - (float) i / worm.size() * 0.3)),
                        (int) (wormColors[score % wormColors.length].getGreen() * (1 - (float) i / worm.size() * 0.3)),
                        (int) (wormColors[score % wormColors.length].getBlue() * (1 - (float) i / worm.size() * 0.3))
                );

                g.setColor(color);
                int px = p.x * UNIT_SIZE;
                int py = p.y * UNIT_SIZE;

                if (i == 0) {
                    // Kepala dengan efek mata
                    switch (currentHeadShape) {
                        case CIRCLE:
                            g.fillOval(px, py, UNIT_SIZE, UNIT_SIZE);
                            // Mata putih
                            g.setColor(Color.white);
                            g.fillOval(px + UNIT_SIZE / 4, py + UNIT_SIZE / 3, UNIT_SIZE / 5, UNIT_SIZE / 5);
                            g.fillOval(px + UNIT_SIZE / 2, py + UNIT_SIZE / 3, UNIT_SIZE / 5, UNIT_SIZE / 5);
                            // Pupil hitam
                            g.setColor(Color.black);
                            g.fillOval(px + UNIT_SIZE / 4 + 2, py + UNIT_SIZE / 3 + 2, UNIT_SIZE / 10, UNIT_SIZE / 10);
                            g.fillOval(px + UNIT_SIZE / 2 + 2, py + UNIT_SIZE / 3 + 2, UNIT_SIZE / 10, UNIT_SIZE / 10);
                            break;
                        case RECTANGLE:
                            g.fillRect(px, py, UNIT_SIZE, UNIT_SIZE);
                            // Mata putih
                            g.setColor(Color.white);
                            g.fillRect(px + UNIT_SIZE / 4, py + UNIT_SIZE / 3, UNIT_SIZE / 5, UNIT_SIZE / 5);
                            g.fillRect(px + UNIT_SIZE / 2, py + UNIT_SIZE / 3, UNIT_SIZE / 5, UNIT_SIZE / 5);
                            // Pupil hitam
                            g.setColor(Color.black);
                            g.fillRect(px + UNIT_SIZE / 4 + 2, py + UNIT_SIZE / 3 + 2, UNIT_SIZE / 10, UNIT_SIZE / 10);
                            g.fillRect(px + UNIT_SIZE / 2 + 2, py + UNIT_SIZE / 3 + 2, UNIT_SIZE / 10, UNIT_SIZE / 10);
                            break;
                        case TRIANGLE:
                            int[] xPoints = {px + UNIT_SIZE / 2, px, px + UNIT_SIZE};
                            int[] yPoints = {py, py + UNIT_SIZE, py + UNIT_SIZE};
                            g.fillPolygon(xPoints, yPoints, 3);
                            // Mata putih
                            g.setColor(Color.white);
                            g.fillOval(px + UNIT_SIZE / 3, py + UNIT_SIZE / 3, UNIT_SIZE / 6, UNIT_SIZE / 6);
                            g.fillOval(px + UNIT_SIZE / 2, py + UNIT_SIZE / 3, UNIT_SIZE / 6, UNIT_SIZE / 6);
                            // Pupil hitam
                            g.setColor(Color.black);
                            g.fillOval(px + UNIT_SIZE / 3 + 1, py + UNIT_SIZE / 3 + 1, UNIT_SIZE / 12, UNIT_SIZE / 12);
                            g.fillOval(px + UNIT_SIZE / 2 + 1, py + UNIT_SIZE / 3 + 1, UNIT_SIZE / 12, UNIT_SIZE / 12);
                            break;
                        case PARALLELOGRAM:
                            int[] xPoly = {px + 5, px + UNIT_SIZE, px + UNIT_SIZE - 5, px};
                            int[] yPoly = {py, py, py + UNIT_SIZE, py + UNIT_SIZE};
                            g.fillPolygon(xPoly, yPoly, 4);
                            // Mata putih
                            g.setColor(Color.white);
                            g.fillOval(px + UNIT_SIZE / 4, py + UNIT_SIZE / 4, UNIT_SIZE / 6, UNIT_SIZE / 6);
                            g.fillOval(px + UNIT_SIZE / 2, py + UNIT_SIZE / 4, UNIT_SIZE / 6, UNIT_SIZE / 6);
                            // Pupil hitam
                            g.setColor(Color.black);
                            g.fillOval(px + UNIT_SIZE / 4 + 1, py + UNIT_SIZE / 4 + 1, UNIT_SIZE / 12, UNIT_SIZE / 12);
                            g.fillOval(px + UNIT_SIZE / 2 + 1, py + UNIT_SIZE / 4 + 1, UNIT_SIZE / 12, UNIT_SIZE / 12);
                            break;
                    }
                } else {
                    g.fillRoundRect(px, py, UNIT_SIZE, UNIT_SIZE, 15, 15);
                }
            }

            // Tampilkan skor
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, 20, 30);
        } else {
            gameOver(g);
        }
    }

    public void move() {
        Point head = worm.getFirst();
        Point newPoint = new Point(head.x, head.y);

        switch (direction) {
            case 'U': newPoint.y -= 1; break;
            case 'D': newPoint.y += 1; break;
            case 'L': newPoint.x -= 1; break;
            case 'R': newPoint.x += 1; break;
        }

        // Wrap-around layar
        if (newPoint.x < 0) newPoint.x = WIDTH / UNIT_SIZE - 1;
        else if (newPoint.x >= WIDTH / UNIT_SIZE) newPoint.x = 0;
        if (newPoint.y < 0) newPoint.y = HEIGHT / UNIT_SIZE - 1;
        else if (newPoint.y >= HEIGHT / UNIT_SIZE) newPoint.y = 0;

        if (worm.contains(newPoint)) {
            running = false;
            timer.stop();
        } else {
            worm.addFirst(newPoint);

            if (newPoint.equals(food)) {
                score++;
                placeFood();
            } else {
                worm.removeLast();
            }
        }
    }

    public void gameOver(Graphics g) {
        String message = "Game Over";
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(message, (WIDTH - metrics.stringWidth(message)) / 2, HEIGHT / 2);

        String scoreMsg = "Score: " + score;
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics scoreMetrics = getFontMetrics(g.getFont());
        g.drawString(scoreMsg, (WIDTH - scoreMetrics.stringWidth(scoreMsg)) / 2, HEIGHT / 2 + 50);

        String restartMsg = "Press R to Restart";
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics restartMetrics = getFontMetrics(g.getFont());
        g.drawString(restartMsg, (WIDTH - restartMetrics.stringWidth(restartMsg)) / 2, HEIGHT / 2 + 90);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') direction = 'D';
                break;
            case KeyEvent.VK_R:
                if (!running) startGame();
                break;
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Worm Game with Falling Flowers and Trees");
        WormGame gamePanel = new WormGame();
        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}