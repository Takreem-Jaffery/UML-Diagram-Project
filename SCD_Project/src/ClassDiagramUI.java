//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionAdapter;
//
//public class ClassDiagramUI extends JFrame {
//    JPanel pageTitlePanel;
//    JPanel elementsPanel;
//    JPanel diagramNotesPanel;
//    JPanel canvasPanel;
//    JPanel leftPanel;
//    JPanel bottomPanel;
//
//    JLabel pageTitle;
//    JTextArea diagramNotes;
//
//    UMLCanvas canvas;
//    JLabel mouseCoords;
//    UMLClass umlClassPanel;
//    public ClassDiagramUI() {
//        pageTitlePanel = new JPanel();
//        elementsPanel = new JPanel();
//        diagramNotesPanel = new JPanel();
//        canvasPanel = new JPanel();
//        leftPanel = new JPanel();
//        bottomPanel=new JPanel();
//        umlClassPanel=new UMLClass();
//
//        pageTitle = new JLabel("UML Class Diagram");
//        pageTitle.setForeground(Color.white);
//        diagramNotes = new JTextArea();
//        diagramNotes.setSize(100, 250);
//        diagramNotes.setLineWrap(true);
//        //canvas=new Canvas();
//        canvas=new UMLCanvas();
//
//        canvasPanel.add(canvas);
//        canvasPanel.setBackground(new Color(149, 184, 209));
//        diagramNotesPanel.setLayout(new GridLayout(1, 1, 10, 10));
//        diagramNotesPanel.add(diagramNotes);
//
//        leftPanel.setLayout(new GridLayout(2, 1,10,10));
//        leftPanel.setBackground(new Color(149, 184, 209));
//        leftPanel.add(elementsPanel);
//        leftPanel.add(diagramNotesPanel);
//
//        pageTitlePanel.add(pageTitle);
//        pageTitlePanel.setBackground(new Color(333333));
//
//
//        mouseCoords=new JLabel();
//        canvas.addMouseMotionListener(new MouseMotionAdapter() {
//            @Override
//            public void mouseMoved(MouseEvent e) {
//                super.mouseMoved(e);
//                mouseCoords.setText("("+e.getX()+","+e.getY()+")");
//                repaint();
//                revalidate();
//
//            }
//        });
//        bottomPanel.add(mouseCoords);
//        bottomPanel.setBackground(new Color(149, 184, 209));
//
//        this.setLayout(new BorderLayout());
//        this.add(pageTitlePanel, BorderLayout.NORTH);
//        this.add(canvas, BorderLayout.CENTER);
//        this.add(leftPanel, BorderLayout.EAST);
//        this.add(bottomPanel, BorderLayout.SOUTH);
//        this.setSize(500, 500);
//        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        this.setVisible(true);
//
//    }
//
//    public class UMLClass extends JPanel{
//        String className;
//        boolean isAbstract;
//        //if isAbstract is true then we can set the text to be italic?
//        final int WIDTH=100;
//        final int HEIGHT=50;
//        Point imageCorner;
//        Point prevPoint;
//        public UMLClass(){
//            className="Class";
//            isAbstract=false;
//            int x=5,y=5;
//            imageCorner=new Point(x,y);
//            ClickListener clickListener=new ClickListener();
//            DragListener dragListener=new DragListener();
//            this.addMouseListener(clickListener);
//            this.addMouseMotionListener(dragListener);
//        }
//        public void paintClass(Graphics g,int x, int y){
//            g.drawRect(x,y,WIDTH,HEIGHT);
//            g.drawLine(x,y+15,x+WIDTH,y+15);
//            className="Class";
//            g.drawString(className,x+(WIDTH/3),y+10);
//        }
//
//        @Override
//        public void paintComponents(Graphics g) {
//            super.paintComponents(g);
//            paintClass(g, (int) imageCorner.getX(), (int) imageCorner.getY());
//        }
//
//        private class ClickListener extends MouseAdapter {
//            public void mousePressed(MouseEvent e){
//                prevPoint=e.getPoint();
//            }
//        }
//        private class DragListener extends MouseMotionAdapter {
//            //actually moves image as we move our mouse
//            public void mouseDragged(MouseEvent e){
//                Point currentPoint=e.getPoint();
//                imageCorner.translate(
//                        (int)(currentPoint.getX()-prevPoint.getX()),
//                        (int) (currentPoint.getY()-prevPoint.getY())
//                );
//                prevPoint=currentPoint;
//                repaint();
//            }
//        }
//    }
//    public class UMLCanvas extends Canvas{
//        String className;
//        String associationName;
//        String multiplicity1;
//        String multiplicity2;
//        public void paintClass(Graphics g){
//            int x=5,y=5;
//            int width=100,height=50;
//            g.drawRect(x,y,width,height);
//            g.drawLine(x,y+15,x+width,y+15);
//            className="Class";
//            g.drawString(className,x+(width/3),y+10);
//        }
//        public void paintAssociation(Graphics g){
//            int x=120,y=15;
//            int length=100;
//            associationName="association";
//            multiplicity1="*";
//            multiplicity2="1";
//
//            g.drawLine(x,y,x+length,y);
//            g.drawString(associationName,x+(length/4),y-5);
//            g.drawString(multiplicity1,x,y-5);
//            g.drawString(multiplicity2,x+length-5,y-5);
//        }
//        public void paintInheritance(Graphics g){
//            int x=120,y=35;
//            int length=100;
//            int[] triangleX={x+length,x+length+5,x+length};
//            int[] triangleY={y-5,y,y+5};
//            g.drawLine(x,y,x+length,y);
//            g.drawPolygon(triangleX,triangleY,3);
//
//        }
//        public void paintAggregation(Graphics g){
//            int x=120,y=55;
//            int length=100;
//            int[] diamondX={x+length,x+length+5,x+length+10,x+length+5};
//            int[] diamondY={y,y-5,y,y+5};
//            g.drawLine(x,y,x+length,y);
//            g.drawPolygon(diamondX,diamondY,4);
//
//        }
//        public void paintComposition(Graphics g){
//            int x=120,y=75;
//            int length=100;
//            int[] diamondX={x+length,x+length+5,x+length+10,x+length+5};
//            int[] diamondY={y,y-5,y,y+5};
//            g.drawLine(x,y,x+length,y);
//            g.fillPolygon(diamondX,diamondY,4);
//
//        }
//        public void paintComment(Graphics g){
//            int x=5, y=65;
//            int width=100, height=50;
//            String commentText="Comment...";
//            int[] commentX={x,width-5,width,width,x};
//            int[] commentY={y,y,y+5,y+height,y+height};
//
//            int[] triangleX={width-5,width-5,width};
//            int[] triangleY={y,y+5,y+5};
//            g.drawPolygon(commentX,commentY,5);
//            g.drawPolygon(triangleX,triangleY,3);
//            g.drawString(commentText,x+5,y+15);
//        }
//        public void paint(Graphics g){
//            paintClass(g);
//            paintAssociation(g);
//            paintInheritance(g);
//            paintAggregation(g);
//            paintComposition(g);
//            paintComment(g);
//        }
//    }
//
//    public static void main(String[] args) {
//        ClassDiagramUI form = new ClassDiagramUI();
//
//    }
//}
