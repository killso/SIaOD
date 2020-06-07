import java.awt.geom.Rectangle2D;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
public  class FractalExplorer {
    public int size; //Целое число «размер экрана», которое является шириной и высотой
    //  отображения в пикселях.
    public JImageDisplay pic; //Ссылка JImageDisplay, для обновления отображения в разных
    // методах в процессе вычисления фрактала.
    public FractalGenerator gen; //Объект FractalGenerator. Будет использоваться ссылка на базовый
    // класс для отображения других видов фракталов в будущем.
    public Rectangle2D.Double diapason; //Объект Rectangle2D.Double, указывающий диапазона комплексной
    // плоскости, которая выводится на экран.
    public int rowsRemaining; //rows remaining
    public JButton reset;
    public JButton save;
    public JComboBox comboBox;
    public FractalExplorer(int size1) {
        size = size1;
        diapason = new Rectangle2D.Double();
        gen = new Mandelbrot();
        gen.getInitialRange(diapason);
        pic = new JImageDisplay(size, size);
        return;
    }
    public void createAndShowGUI() {
        Reset a = new Reset();
        //центр
        JFrame jfr = new JFrame("Фракталы");
        jfr.add(pic, BorderLayout.CENTER);
        Mouse click = new Mouse();
        pic.addMouseListener(click);
        //(выход)
        jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // choise of fractal
        JComboBox comboBox = new JComboBox();
        comboBox.addItem(new Mandelbrot());
        comboBox.addItem(new Tricorn());
        comboBox.addItem(new BurningShip());
        comboBox.addActionListener(a);
        JLabel labelDescription = new JLabel("Фракталы:");
        JPanel panel = new JPanel();
        panel.add(labelDescription);
        panel.add(comboBox);
        jfr.add(panel, BorderLayout.NORTH);
        // reset
        JButton resetImageButton = new JButton("Reset");
        resetImageButton.addActionListener(a);
        // save
        JButton saveImageButton = new JButton("Save Image");
        saveImageButton.addActionListener(a);
        //down
        JPanel panelDowner = new JPanel();
        panelDowner.add(resetImageButton);
        panelDowner.add(saveImageButton);
        jfr.add(panelDowner, BorderLayout.SOUTH);
        pic.setLayout(new BorderLayout());
        jfr.pack();
        jfr.setVisible(true);
        jfr.setResizable(false);
        return;
    }
    public void enableUI(boolean val) { //включать или отключать кнопки с выпадающим списком в пользовательском
        // интерфейсе на основе указанного параметра
        comboBox.setEnabled(val); //ипсользуем метод setEnabled
        reset.setEnabled(val);
        save.setEnabled(val);
    }
        public void drawFractal() {
            enableUI(false);
            rowsRemaining = size;
            for (int i=0; i<size;i++) {
                FractalWorker draw = new FractalWorker(i);
                draw.execute();
            }
        }
    public class FractalWorker extends SwingWorker<Object, Object> {
        int[] RGB;
        public int y1;
        public FractalWorker(int yCoord) {
            y1 = yCoord;
        }
        public Object doInBackground() {
            RGB = new int[size];
            for (int x = 0; x<size; x++) {
                double xCoord = FractalGenerator.getCoord(diapason.x, diapason.x + diapason.width,
                        size, x);
                double yCoord = FractalGenerator.getCoord(diapason.y, diapason.y + diapason.height,
                        size, y1);
                int numIters = gen.numIterations(xCoord, yCoord);

                if (numIters == -1) {
                    RGB[x] = 0;
                } else {
                    float hue = 0.7f + (float) numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    RGB[x] = rgbColor;
                }
            }
            return null;
        }
        public void done() {
            for (int i = 0;i<size; i++) {
                pic.drawPixel(i, y1, RGB[i]);
            }
            pic.repaint(0, 0, y1, size, 1);
            rowsRemaining--;
            if (rowsRemaining==0) {
                enableUI(true);
            }
        }
    }
    public class Reset implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Save")) {
                JFileChooser finder = new JFileChooser();
                JFileChooser chooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Image", "png");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);

                int result = finder.showSaveDialog(pic);
                if (result == finder.APPROVE_OPTION) {
                    File file = finder.getSelectedFile();
                    try {
                        ImageIO.write(pic.getImage(), "png", file);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(pic, exception.getMessage(),
                                "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
                return;
            }
            if (e.getSource() instanceof JComboBox) {
                JComboBox mySource = (JComboBox) e.getSource();
                gen = (FractalGenerator) mySource.getSelectedItem();
            }
            gen.getInitialRange(diapason);
            drawFractal();
        }
    }
    public class Mouse extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (rowsRemaining!= 0) return; //измените реализацию mouse-listener
            int x = e.getX();
            double xCoord = FractalGenerator.getCoord(diapason.x, diapason.x + diapason.width,
                    size, x);
            int y = e.getY();
            double yCoord = FractalGenerator.getCoord(diapason.y, diapason.y + diapason.height,
                    size, y);
            gen.recenterAndZoomRange(diapason, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }
    public static void main(String args[]) {
        FractalExplorer start = new FractalExplorer(800);
        start.createAndShowGUI();
        start.drawFractal(); //отображение начального представления
    }
}
