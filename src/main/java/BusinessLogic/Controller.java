package BusinessLogic;

import GUI.SimulationFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private SimulationFrame simulationFrame;
    private int numberClients;
    private int numberServers;
    private int timeLimit;
    private int minArrTime;
    private int maxArrTime;
    private int minProcTime;
    private int maxProcTime;

    public Controller(SimulationFrame simGUI) {
        this.simulationFrame = simGUI;
        simulationFrame.addStartListener(new StartListener());
    }

    public class StartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try { numberClients = Integer.parseInt(simulationFrame.getTextNoClients());}
            catch (NumberFormatException e1) {
                e1.printStackTrace();
                System.out.println("Invalid number format for number of queues!");}

            try { numberServers = Integer.parseInt(simulationFrame.getTextNoQueues()); }
            catch (NumberFormatException e1) {
                e1.printStackTrace();
                System.out.println("Invalid number format for number of servers!");}

            try { timeLimit = Integer.parseInt(simulationFrame.getTextSim()); }
            catch (NumberFormatException e1) {
                e1.printStackTrace();
                System.out.println("Invalid number format for simulation time!");}

            try { minArrTime = Integer.parseInt(simulationFrame.getTextMinArr());}
            catch (NumberFormatException e1) {
                e1.printStackTrace();
                System.out.println("Invalid number format for minimum arrival time!");}

            try { maxArrTime = Integer.parseInt(simulationFrame.getTextMaxArr());}
            catch (NumberFormatException e1) {
                e1.printStackTrace();
                System.out.println("Invalid number format for maximum arrival time!");}

            try { minProcTime = Integer.parseInt(simulationFrame.getTextMinServ());}
            catch (NumberFormatException e1) {
                e1.printStackTrace();
                System.out.println("Invalid number format for minimum service time");}

            try { maxProcTime = Integer.parseInt(simulationFrame.getTextMaxServ());}
            catch (NumberFormatException e1) {
                e1.printStackTrace();
                System.out.println("Invalid number format for maximum service time");}

            SimulationManager simulationManager = new SimulationManager(simulationFrame, timeLimit, numberClients, numberServers, minArrTime, maxArrTime, minProcTime, maxProcTime);

            Thread t = new Thread(simulationManager); //we start the thread for SimulationMananger
            t.start();
        }
    }
}
