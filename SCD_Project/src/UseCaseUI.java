//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ComponentAdapter;
//import java.awt.event.ComponentEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class UseCaseUI extends JPanel {
//    private static Point initialClick;
//
//    public static class Actor extends JComponent {
//        private String name;
//
//        public Actor(String name) {
//            this.name = name;
//            setPreferredSize(new Dimension(100, 120));
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2d = (Graphics2D) g;
//            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//            int x = getWidth() / 2;
//            g2d.drawOval(x - 15, 5, 30, 30);  // Head
//            g2d.drawLine(x, 35, x, 65);       // Body
//            g2d.drawLine(x - 20, 50, x + 20, 50);  // Arms
//            g2d.drawLine(x, 65, x - 20, 95);  // Left leg
//            g2d.drawLine(x, 65, x + 20, 95);  // Right leg
//
//            FontMetrics fm = g2d.getFontMetrics();
//            int textWidth = fm.stringWidth(name);
//            g2d.drawString(name, x - textWidth / 2, 115);
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//            repaint();
//        }
//    }
//
//    public static class UseCase extends JComponent {
//        private String name;
//
//        public UseCase(String name) {
//            this.name = name;
//            setPreferredSize(new Dimension(150, 80));
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2d = (Graphics2D) g;
//            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//            g2d.drawOval(10, 10, getWidth() - 20, getHeight() - 20);
//
//            FontMetrics fm = g2d.getFontMetrics();
//            int textWidth = fm.stringWidth(name);
//            int textHeight = fm.getHeight();
//            g2d.drawString(name, (getWidth() - textWidth) / 2, (getHeight() + textHeight / 2) / 2);
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//            repaint();
//        }
//    }
//
//    public static class Arrow extends JComponent {
//        private final String type;
//        private int width = 100;
//        private int height = 50;
//        private int startX = 10;
//        private int startY = 25;
//
//        public Arrow(String type) {
//            this.type = type;
//            setPreferredSize(new Dimension(width, height));
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2d = (Graphics2D) g;
//            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//            // Dotted line for the arrow
//            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
//            g2d.drawLine(startX, startY, width - 20, startY);
//            g2d.drawLine(width - 20, startY, width - 30, startY - 10);
//            g2d.drawLine(width - 20, startY, width - 30, startY + 10);
//
//            FontMetrics fm = g2d.getFontMetrics();
//            int textWidth = fm.stringWidth(type);
//            if(!type.isEmpty())
//                g2d.drawString("<<" + type + ">>", (getWidth() - textWidth) / 2, startY - 5);
//            else
//                g2d.drawString("", (getWidth() - textWidth) / 2, startY - 5);
//
//        }
//
//        // Getter and Setter for resizable arrow properties
//        public void setArrowWidth(int width) {
//            this.width = width;
//            setPreferredSize(new Dimension(width, height));
//            revalidate();
//            repaint();
//        }
//
//        public void setArrowHeight(int height) {
//            this.height = height;
//            setPreferredSize(new Dimension(width, height));
//            revalidate();
//            repaint();
//        }
//    }
//    private static class Line extends JComponent{
//        @Override
//        protected void paintComponent(Graphics g){
//            super.paintComponent(g);
//            g.setColor(Color.gray);
//            g.fillRect(0,0,getWidth(),getHeight());
//
//        }
//    }
//    public static class UseCaseDiagram extends JPanel {
//        private final UseCaseUI.Actor sampleActor;
//        private final UseCaseUI.UseCase sampleUseCase;
//        private final UseCaseUI.Arrow includeArrow;
//        private final UseCaseUI.Arrow extendArrow;
//        private final UseCaseUI.Arrow simpleArrow;
//        private final UseCaseUI.Line line;
//        public UseCaseDiagram() {
//            setLayout(null);
//            setBackground(Color.WHITE);
//            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//            // Place sampleActor and sampleUseCase on the right, non-draggable
//            line=new UseCaseUI.Line();
//            line.setBounds(50,0,10,500);
//            add(line);
//
//            sampleActor = new UseCaseUI.Actor("Sample Actor");
//            sampleActor.setBounds(650, 50, 100, 120);
//            add(sampleActor);
//
//            sampleUseCase = new UseCaseUI.UseCase("Sample Use Case");
//            sampleUseCase.setBounds(650, 200, 150, 80);
//            add(sampleUseCase);
//
//            // Add sample arrows with fixed positions
//            includeArrow = new UseCaseUI.Arrow("include");
//            includeArrow.setBounds(650, 300, 100, 50);
//            add(includeArrow);
//
//            extendArrow = new UseCaseUI.Arrow("extend");
//            extendArrow.setBounds(650, 370, 100, 50);
//            add(extendArrow);
//
//            simpleArrow=new UseCaseUI.Arrow("");
//            simpleArrow.setBounds(650,440,100,50);
//            add(simpleArrow);
//
//            // Listeners to create copies of sample components
//            sampleActor.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    createActorCopy("Actor", 50, 50);
//                }
//            });
//
//            sampleUseCase.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    createUseCaseCopy("Use Case", 300, 100);
//                }
//            });
//
//            includeArrow.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    createArrowCopy("include", 200, 300);
//                }
//            });
//
//            extendArrow.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    createArrowCopy("extend", 200, 400);
//                }
//            });
//
//            simpleArrow.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    createArrowCopy("", 200, 400);
//                }
//            });
//
//            //to ensure selectable sample figures are on the RHS of window
//            JPanel tempPanel=this;
//            this.addComponentListener(new ComponentAdapter() {
//                @Override
//                public void componentResized(ComponentEvent e) {
//                    super.componentResized(e);
//                    int frameWidth= tempPanel.getWidth();
//                    int frameHeight = tempPanel.getHeight();
//
//                    line.setBounds(frameWidth-170,0,5,frameHeight);
//                    Dimension actorSize=sampleActor.getPreferredSize();
//                    sampleActor.setBounds(frameWidth-actorSize.width,50, actorSize.width, actorSize.height);
//                    Dimension useCaseSize=sampleUseCase.getPreferredSize();
//                    sampleUseCase.setBounds(frameWidth-useCaseSize.width,200,useCaseSize.width,useCaseSize.height);
//                    Dimension includeArrowSize=includeArrow.getPreferredSize();
//                    includeArrow.setBounds(frameWidth-includeArrowSize.width,300,includeArrowSize.width,includeArrowSize.height);
//                    Dimension extendArrowSize=extendArrow.getPreferredSize();
//                    extendArrow.setBounds(frameWidth-extendArrowSize.width,370,extendArrowSize.width,extendArrowSize.height);
//                    Dimension simpleArrowSize=simpleArrow.getPreferredSize();
//                    simpleArrow.setBounds(frameWidth-simpleArrowSize.width,440,simpleArrowSize.width,simpleArrowSize.height);
//
//                    line.repaint();
//                }
//            });
//        }
//
//        private void createActorCopy(String name, int x, int y) {
//            UseCaseUI.Actor actorCopy = new UseCaseUI.Actor(name);
//            actorCopy.setBounds(x, y, 100, 120);
//            actorCopy.addMouseListener(new UseCaseUI.UseCaseDiagram.DeleteEditAndDragAdapter(actorCopy));
//            actorCopy.addMouseMotionListener(new UseCaseUI.UseCaseDiagram.DeleteEditAndDragAdapter(actorCopy));
//            add(actorCopy);
//            repaint();
//        }
//
//        private void createUseCaseCopy(String name, int x, int y) {
//            UseCaseUI.UseCase useCaseCopy = new UseCaseUI.UseCase(name);
//            useCaseCopy.setBounds(x, y, 150, 80);
//            useCaseCopy.addMouseListener(new UseCaseUI.UseCaseDiagram.DeleteEditAndDragAdapter(useCaseCopy));
//            useCaseCopy.addMouseMotionListener(new UseCaseUI.UseCaseDiagram.DeleteEditAndDragAdapter(useCaseCopy));
//            add(useCaseCopy);
//            repaint();
//        }
//
//        private void createArrowCopy(String type, int x, int y) {
//            UseCaseUI.Arrow arrowCopy = new UseCaseUI.Arrow(type);
//            arrowCopy.setBounds(x, y, 100, 50);
//            arrowCopy.addMouseListener(new UseCaseUI.UseCaseDiagram.DeleteEditAndDragAdapter(arrowCopy));
//            arrowCopy.addMouseMotionListener(new UseCaseUI.UseCaseDiagram.DeleteEditAndDragAdapter(arrowCopy));
//            add(arrowCopy);
//            repaint();
//        }
//
//        private static class DeleteEditAndDragAdapter extends MouseAdapter {
//            private final JComponent component;
//
//            public DeleteEditAndDragAdapter(JComponent component) {
//                this.component = component;
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                initialClick = e.getPoint();
//                component.getComponentAt(initialClick);
//            }
//
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                int thisX = component.getLocation().x;
//                int thisY = component.getLocation().y;
//                int xMoved = e.getX() - initialClick.x;
//                int yMoved = e.getY() - initialClick.y;
//                component.setLocation(thisX + xMoved, thisY + yMoved);
//            }
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (SwingUtilities.isRightMouseButton(e)) {
//                    Container parent = component.getParent();
//                    if (parent != null) {
//                        int result = JOptionPane.showConfirmDialog(parent, "Are you sure you want to delete this item?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
//
//                        // Handle the user's choice
//                        if (result == JOptionPane.YES_OPTION) {
//                            parent.remove(component);
//                            parent.repaint();
//                        }
//                        //else do nothing
//                    }
//                } else if (e.getClickCount() == 2) {
//                    String newName = JOptionPane.showInputDialog(
//                            component, "Enter new name:", "Edit Name", JOptionPane.PLAIN_MESSAGE);
//                    if (newName != null && !newName.trim().isEmpty()) {
//                        if (component instanceof UseCaseUI.Actor) {
//                            ((UseCaseUI.Actor) component).setName(newName);
//                        } else if (component instanceof UseCaseUI.UseCase) {
//                            ((UseCaseUI.UseCase) component).setName(newName);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    public class UseCaseUIMouseAdapter extends MouseAdapter {
//        public UseCaseUIMouseAdapter(UseCaseUI.UseCase useCase) {
//        }
//    }
//    public class UseCaseUIActorMouseAdapter extends MouseAdapter {
//        public UseCaseUIActorMouseAdapter(UseCaseUI.Actor actor) {
//        }
//    }
//    public static void main(String[] args) {
//
//        UseCaseUIFrame frame=new UseCaseUIFrame();
//    }
//}
