package GUI;

import BusinessLogic.Controller;

import javax.swing.*;

public class SimulationFrame {
    private JLabel labelHead, labelNoClients, labelNoQueues, labelSim, labelMinArr, labelMaxArr, labelMinServ, labelMaxServ;
    private JTextField textNoClients, textNoQueues, textSim, textMinArr, textMaxArr, textMinServ, textMaxServ;
    private JButton buttonStart;
    private JTextArea textAreaRez;
    private JScrollPane scroll;

    public SimulationFrame() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(900,550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);

        panel.setLayout(null);

        labelHead = new JLabel("Queue simulator");
        labelHead.setBounds(150,15,150,50);
        panel.add(labelHead);

        //number of clients label
        labelNoClients = new JLabel("Number of clients: ");
        labelNoClients.setBounds(25, 70, 150, 30);
        panel.add(labelNoClients);

        //jtextField for nOClients
        textNoClients = new JTextField();
        textNoClients.setBounds(180, 70, 150, 30);
        panel.add(textNoClients);

        //textarea for showing
        textAreaRez = new JTextArea();
        textAreaRez.setBounds(340, 70, 500, 400);
        textAreaRez.setEditable(false);
        //textAreaRez.setVisible(true);

        scroll = new JScrollPane(textAreaRez);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(340, 70, 500, 400);
        panel.add(scroll);

        //number of queues label
        labelNoQueues = new JLabel("Number of queues: ");
        labelNoQueues.setBounds(25, 105, 150, 30);
        panel.add(labelNoQueues);

        //jtextField for noQueues
        textNoQueues = new JTextField();
        textNoQueues.setBounds(180, 105, 150, 30);
        panel.add(textNoQueues);

        //label simulation time
        labelSim = new JLabel("Simulation time: ");
        labelSim.setBounds(25, 140, 150, 30);
        panel.add(labelSim);

        //text for simulation time
        textSim = new JTextField();
        textSim.setBounds(180, 140, 150, 30);
        panel.add(textSim);

        //minimal arrival time
        labelMinArr = new JLabel("Mimimum arrival time: ");
        labelMinArr.setBounds(25, 175, 150, 30);
        panel.add(labelMinArr);

        //text for min arrival time
        textMinArr = new JTextField();
        textMinArr.setBounds(180, 175, 150, 30);
        panel.add(textMinArr);

        //maximal arrival time
        labelMaxArr = new JLabel("Maximum arrival time: ");
        labelMaxArr.setBounds(25, 210, 150, 30);
        panel.add(labelMaxArr);

        //text for max arrival time
        textMaxArr = new JTextField();
        textMaxArr.setBounds(180, 210, 150, 30);
        panel.add(textMaxArr);

        //minumim service time
        labelMinServ = new JLabel("Minimum service time: ");
        labelMinServ.setBounds(25, 245, 150, 30);
        panel.add(labelMinServ);

        //text for min service time
        textMinServ = new JTextField();
        textMinServ.setBounds(180, 245, 150, 30);
        panel.add(textMinServ);

        //maximal arrival time
        labelMaxServ = new JLabel("Maximum service time: ");
        labelMaxServ.setBounds(25, 280, 150, 30);
        panel.add(labelMaxServ);

        //text for max arrival time
        textMaxServ = new JTextField();
        textMaxServ.setBounds(180, 280, 150, 30);
        panel.add(textMaxServ);

        //button to start the simulation
        buttonStart = new JButton("Start");
        buttonStart.setBounds(25, 320, 100, 50);
        //buttonStart.addActionListener(new StartListener());
        panel.add(buttonStart);
    }

    public void addStartListener(Controller.StartListener st1) { buttonStart.addActionListener(st1); }

    public String getTextNoClients() { return textNoClients.getText(); }
    public String getTextNoQueues() { return textNoQueues.getText(); }
    public String getTextSim() { return textSim.getText(); }
    public String getTextMinArr() { return textMinArr.getText(); }
    public String getTextMaxArr() { return textMaxArr.getText(); }
    public String getTextMinServ() { return textMinServ.getText(); }
    public String getTextMaxServ() { return textMaxServ.getText(); }
    public String getTextArea() { return textAreaRez.getText(); }

    public void setTextArea(String s) { textAreaRez.append(s); }
    public void flushTextArea() { textAreaRez.setText(""); }
}



