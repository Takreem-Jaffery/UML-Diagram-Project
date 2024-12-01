import javax.swing.*;
import java.awt.*;

public class UseCaseUIFrame extends JFrame {
    JPanel pageTitlePanel;
    JPanel diagramNotesPanel;
    JPanel bottomPanel;
    UseCaseUI.UseCaseDiagram UIPanel;

    JLabel pageTitle;
    JTextArea diagramNotes;

    public UseCaseUIFrame(){
        pageTitlePanel = new JPanel();
        diagramNotesPanel = new JPanel();
        bottomPanel=new JPanel();
        UIPanel= new UseCaseUI.UseCaseDiagram();

        pageTitle=new JLabel("Use Case Diagram");
        pageTitle.setForeground(Color.white);
        pageTitlePanel.add(pageTitle);
        pageTitlePanel.setBackground(new Color(333333));


        diagramNotes=new JTextArea();
        diagramNotes.setSize(170, 250);
        diagramNotes.setLineWrap(true);
        JScrollPane diagramNotesScrollPane = new JScrollPane(diagramNotes);
        diagramNotesPanel.setLayout(new BorderLayout());
        diagramNotesPanel.add(diagramNotesScrollPane);

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(0,150));
        bottomPanel.add(diagramNotesPanel,BorderLayout.EAST);

        this.setTitle("Use Case Diagram UML Editor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700,700);
        SpringLayout springLayout=new SpringLayout();

        //setLayout(springLayout);

        this.setLayout(new BorderLayout());

        this.add(UIPanel,BorderLayout.CENTER);
        this.add(pageTitlePanel,BorderLayout.NORTH);
        //this.add(diagramNotesPanel,BorderLayout.EAST);
        this.add(bottomPanel,BorderLayout.SOUTH);

        this.setVisible(true);
    }

}
