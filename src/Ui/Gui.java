package Ui;

import Controllers.Controller;
import Controllers.Translator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Guus on 12/12/2016.
 */
public class Gui {

    private JFrame frame;
    private JPanel panel;
    private JButton executeButton, newTrainButton, newWagonButton;
    private JTextArea console, dsl;
    private JTextField input, newTrainInput, newWagonInput;
    private JLabel createNewTrain, createNewWagon;
    private JComboBox selectTrain;
    private JToggleButton dslToggle, trainGuiToggle;
    private ArrayList<JLabel> drawingProperties;
    Action toggleDSL = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dslField.setVisible(true);
            dslToggle.setSelected(true);
            trainGuiToggle.setSelected(false);
            deleteTrainsOnScreen();
        }
    };
    private Controller controller;
    Action submitInput = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (input.getText() != null && !"".equals(input.getText())) {
                new Translator(input.getText(), controller);
                input.setText("");
            } else if (newTrainInput.getText() != null && !"".equals(newTrainInput.getText())) {
                controller.addTrain(newTrainInput.getText());
                newTrainInput.setText("");
            } else if (newWagonInput.getText() != null && !"".equals(newWagonInput.getText())) {
                controller.addWagon(String.valueOf(selectTrain.getSelectedItem()), newWagonInput.getText());
                newWagonInput.setText("");
            }
            refreshScreen();
            setItemsInSelectTrain();

        }
    };
    Action toggleGUI = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dslField.setVisible(false);
            dslToggle.setSelected(false);
            trainGuiToggle.setSelected(true);
            drawTrains();
        }
    };
    //setbounds(x, y, w, h)
    //1ste hoever naar rechts
    private JScrollPane consoleField, dslField;

    public Gui(Controller controller) {
        this.controller = controller;
    }

    public void createFrame() {

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

        //input field for new train
        newTrainInput = new JTextField();
        newTrainInput.setBounds(590, 40, 200, 30);

        //Label for creating new train
        createNewTrain = new JLabel("Create train");
        createNewTrain.setBounds(590, 10, 100, 30);

        //newTrainButton
        newTrainButton = new JButton();
        newTrainButton.setText("Add train");
        newTrainButton.setBounds(810, 40, 150, 30);
        newTrainButton.addActionListener(submitInput);

        //input field for new wagon
        newWagonInput = new JTextField();
        newWagonInput.setBounds(590, 110, 200, 30);

        //Label for creating new train
        createNewWagon = new JLabel("Create wagon");
        createNewWagon.setBounds(590, 80, 100, 30);

        //newTrainButton
        newWagonButton = new JButton();
        newWagonButton.setText("Add wagon");
        newWagonButton.setBounds(810, 150, 150, 30);
        newWagonButton.addActionListener(submitInput);

        //dropdownlist for selecting train
        selectTrain = new JComboBox();
        selectTrain.setBounds(810, 110, 150, 30);

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

        //Toggle button for dsl field or the train gui
        dslToggle = new JToggleButton("DSL");
        dslToggle.addActionListener(toggleDSL);
        dslToggle.setBounds(590, 200, 150, 30);
        dslToggle.setSelected(true);
        trainGuiToggle = new JToggleButton("GUI");
        trainGuiToggle.addActionListener(toggleGUI);
        trainGuiToggle.setBounds(590, 240, 150, 30);

        //Adding elements to panel
        panel.add(consoleField);
        panel.add(input);
        panel.add(executeButton);
        panel.add(dslField);
        panel.add(newTrainInput);
        panel.add(createNewTrain);
        panel.add(newTrainButton);
        panel.add(newWagonInput);
        panel.add(createNewWagon);
        panel.add(newWagonButton);
        panel.add(selectTrain);
        panel.add(dslToggle);
        panel.add(trainGuiToggle);

        //Adding panel to frame and configuring the frame
        frame.add(panel);
        //frame.setSize(590, 940);
        frame.setSize(1000, 1000);
        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                if (!dslField.isVisible()) drawTrains();
            }

            public void componentMoved(ComponentEvent arg0) {
            }

            public void componentHidden(ComponentEvent arg0) {
            }

            public void componentShown(ComponentEvent arg0) {
            }
        });

        //refreshes the screen
        refreshScreen();
    }

    public void refreshScreen() {
        dsl.setText(controller.createDSL());
        if (!dslField.isVisible()) drawTrains();
    }

    public void setConsoleOutput(String text) {
        console.append(text + "\n");
    }

    public void clearConsole() {
        console.setText("");
    }

    public void setItemsInSelectTrain() {
        selectTrain.removeAllItems();
        for (String trainName : controller.getTrainNames()) {
            selectTrain.addItem(trainName);
        }
    }

    public void deleteTrainsOnScreen() {
        if (drawingProperties != null) {
            for (JLabel j : drawingProperties) {
                panel.remove(j);
                panel.validate();
                panel.repaint();
            }
        }
    }

    public void drawTrains() {
        panel.validate();
        panel.repaint();
        deleteTrainsOnScreen();
        drawingProperties = new ArrayList<>();
        int offsetX = 10;
        int offsetY = 470;

        for (String trainName : controller.getTrainNames()) {
            drawingProperties.add(drawTrain(offsetX, offsetY, trainName));
            drawingProperties.add(drawName(offsetX, offsetY, trainName));

            int tempOffsetX = offsetX + 170;
            for (String wagonName : controller.getWagonNames(trainName)) {
                drawingProperties.add(drawWagon(tempOffsetX, offsetY, wagonName));
                drawingProperties.add(drawName(tempOffsetX, offsetY, wagonName));
                tempOffsetX += 125;
            }
            offsetY += 150;
        }
    }

    public JLabel drawTrain(int x, int y, String trainName) {
        ImageIcon img = new ImageIcon("train.png");
        ImageIcon imgX = new ImageIcon("trainX.png");
        JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(x, y, 154, 109);
        imgLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imgLabel.setIcon(imgX);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                imgLabel.setIcon(img);
            }
        });
        imgLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                controller.deleteTrain(trainName);
            }
        });
        panel.add(imgLabel);
        return imgLabel;
    }

    public JLabel drawWagon(int x, int y, String wagonname) {
        ImageIcon img = new ImageIcon("wagon.png");
        ImageIcon imgX = new ImageIcon("wagonX.png");
        JLabel imgLabel = new JLabel(img);
        imgLabel.setBounds(x, y, 117, 109);
        imgLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imgLabel.setIcon(imgX);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                imgLabel.setIcon(img);
            }
        });
        imgLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                controller.deleteWagon(wagonname);
            }
        });
        panel.add(imgLabel);
        return imgLabel;
    }

    public JLabel drawName(int x, int y, String name) {
        JLabel nameLabel = new JLabel(name);
        nameLabel.setBounds(x, y + 100, 140, 30);
        panel.add(nameLabel);
        return nameLabel;
    }

}
