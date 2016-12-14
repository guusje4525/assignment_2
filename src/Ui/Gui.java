package Ui;

import Controllers.Controller;
import Controllers.Translator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Guus on 12/12/2016.
 */
public class Gui {

    private static JFrame frame;
    private static JPanel panel;
    private static JButton executeButton;
    private static JTextArea console, dsl;
    private static JTextField input;
    static Action submitInput = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Hier moet de observer zeggen tegen Controller van jo ik heb een bericht voor je
            new Translator(input.getText());
            refreshDSL();
            input.setText("");
        }
    };

    //setbounds(x, y, w, h)
    //1ste hoever naar rechts
    private static JScrollPane consoleField, dslField;

    public static void createFrame() {

        //Creating frame
        frame = new JFrame("Richrail");

        //Creating the mainPanel
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(true);
        panel.setBackground(Color.white);

        //Creating elements for inside the frames
        //Creating the console output screen
        console = new JTextArea();
        console.setMargin(new Insets(5, 5, 5, 5));
        console.setEditable(false);
        consoleField = new JScrollPane (console, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        consoleField.setBounds(10, 10, 560, 400);

        //Creating the input field for the console
        input = new JTextField();
        input.setBounds(10, 430, 400, 30);
        input.addActionListener(submitInput);

        //Creating the execute button to execute the command stored int he input field
        executeButton = new JButton();
        executeButton.setText("Execute");
        executeButton.setBounds(430, 430, 140, 30);
        executeButton.addActionListener(submitInput);

        //Creating the textArea where the DSL statements are stored
        dsl = new JTextArea("DSL output");
        dsl.setMargin(new Insets(5, 5, 5, 5));
        dsl.setEditable(false);
        dslField = new JScrollPane (dsl, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        dslField.setBounds(10, 470, 560, 400);

        //Adding elements to panel
        panel.add(consoleField);
        panel.add(input);
        panel.add(executeButton);
        panel.add(dslField);

        //Adding panel to frame and configuring the frame
        frame.add(panel);
        frame.setSize(590, 940);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //refreshes te DSL screen
        refreshDSL();
    }

    public static void refreshDSL(){
        dsl.setText(Controller.createDSL());
    }

    public static void setConsoleOutput(String text){
        console.append(text + "\n");
    }

}
