import javax.swing.*;
import java.awt.*;

public class UseCaseUI extends JPanel {

    public static class Actor extends JComponent {
        private String name;

        public Actor(String name) {
            this.name = name;
            setPreferredSize(new Dimension(100, 120));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int x = getWidth() / 2;
            g2d.drawOval(x - 15, 5, 30, 30);  // Head
            g2d.drawLine(x, 35, x, 65);      // Body
            g2d.drawLine(x - 20, 50, x + 20, 50);  // Arms
            g2d.drawLine(x, 65, x - 20, 95);  // Left leg
            g2d.drawLine(x, 65, x + 20, 95);  // Right leg

            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(name);
            g2d.drawString(name, x - textWidth / 2, 115);
        }
    }

    public static class UseCase extends JComponent {
        private String name;

        public UseCase(String name) {
            this.name = name;
            setPreferredSize(new Dimension(150, 80));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.drawOval(10, 10, getWidth() - 20, getHeight() - 20);

            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(name);
            int textHeight = fm.getHeight();
            g2d.drawString(name,
                    (getWidth() - textWidth) / 2,
                    (getHeight() + textHeight / 2) / 2);
        }
    }

    public static class Association extends JComponent {
        protected Point start;
        protected Point end;

        public Association(Point start, Point end) {
            this.start = start;
            this.end = end;
            int width = Math.abs(end.x - start.x) + 40;
            int height = Math.abs(end.y - start.y) + 40;
            setBounds(Math.min(start.x, end.x) - 20, Math.min(start.y, end.y) - 20, width, height);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(1.5f));

            g2d.drawLine(start.x - getX(), start.y - getY(), end.x - getX(), end.y - getY());
            drawArrowHead(g2d, new Point(start.x - getX(), start.y - getY()), new Point(end.x - getX(), end.y - getY()));
        }

        protected void drawArrowHead(Graphics2D g2d, Point start, Point end) {
            int arrowSize = 12;
            double angle = Math.atan2(end.y - start.y, end.x - start.x);
            int[] xPoints = new int[3];
            int[] yPoints = new int[3];

            xPoints[0] = end.x;
            yPoints[0] = end.y;
            xPoints[1] = end.x - (int) (arrowSize * Math.cos(angle - Math.PI / 6));
            yPoints[1] = end.y - (int) (arrowSize * Math.sin(angle - Math.PI / 6));
            xPoints[2] = end.x - (int) (arrowSize * Math.cos(angle + Math.PI / 6));
            yPoints[2] = end.y - (int) (arrowSize * Math.sin(angle + Math.PI / 6));

            g2d.fillPolygon(xPoints, yPoints, 3);
        }
    }

    public static class DottedArrow extends Association {
        private String stereotype;
        private static final float STEREOTYPE_FONT_SIZE = 7.5f;  // Smaller font size for stereotypes

        public DottedArrow(Point start, Point end, String stereotype) {
            super(start, end);
            this.stereotype = stereotype;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            float[] dash = {6.0f, 4.0f};
            g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));

            g2d.drawLine(start.x - getX(), start.y - getY(), end.x - getX(), end.y - getY());

            // Save the original font
            Font originalFont = g2d.getFont();

            // Create and set smaller font for stereotype
            Font stereotypeFont = originalFont.deriveFont(STEREOTYPE_FONT_SIZE);
            g2d.setFont(stereotypeFont);

            // Draw stereotype with smaller font
            Point mid = new Point((start.x + end.x) / 2 - getX(), (start.y + end.y) / 2 - getY());
            FontMetrics fm = g2d.getFontMetrics(stereotypeFont);

            String stereotypeText = "<<" + stereotype + ">>";
            int textWidth = fm.stringWidth(stereotypeText);

            // Draw white background for better visibility
            g2d.setColor(Color.WHITE);
            g2d.fillRect(mid.x - textWidth / 2 - 2, mid.y - fm.getAscent() - 2,
                    textWidth + 4, fm.getHeight() + 4);

            // Draw the text
            g2d.setColor(Color.BLACK);
            g2d.drawString(stereotypeText, mid.x - textWidth / 2, mid.y - 5);

            // Restore original font
            g2d.setFont(originalFont);

            drawArrowHead(g2d, new Point(start.x - getX(), start.y - getY()), new Point(end.x - getX(), end.y - getY()));
        }
    }

    public static class UseCaseDiagram extends JPanel {
        public UseCaseDiagram() {
            setLayout(null);
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        }

        public void addActor(String name, int x, int y) {
            Actor actor = new Actor(name);
            actor.setBounds(x, y, 100, 120);
            add(actor);
            repaint();
        }

        public void addUseCase(String name, int x, int y) {
            UseCase useCase = new UseCase(name);
            useCase.setBounds(x, y, 150, 80);
            add(useCase);
            repaint();
        }

        public void addAssociation(int x1, int y1, int x2, int y2) {
            Association assoc = new Association(new Point(x1, y1), new Point(x2, y2));
            add(assoc);
            repaint();
        }

        public void addDottedArrow(int x1, int y1, int x2, int y2, String stereotype) {
            DottedArrow arrow = new DottedArrow(new Point(x1, y1), new Point(x2, y2), stereotype);
            add(arrow);
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Use Case Diagram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        UseCaseDiagram diagram = new UseCaseDiagram();
        frame.add(diagram);

        diagram.addActor("User", 50, 100);
        diagram.addUseCase("Use Case 1", 300, 100);
        diagram.addUseCase("Use Case 2", 300, 300);

        diagram.addAssociation(150, 130, 300, 130);
        diagram.addDottedArrow(375, 180, 375, 300, "include");
        diagram.addDottedArrow(325, 180, 325, 300, "extend");

        JPanel keyboardPanel = new JPanel(new BorderLayout());
        JTextField keyboardInput = new JTextField();
        keyboardPanel.add(keyboardInput, BorderLayout.CENTER);
        keyboardPanel.setBounds(550, 500, 200, 30);
        diagram.add(keyboardPanel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
