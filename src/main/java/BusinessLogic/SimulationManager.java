package BusinessLogic;

import GUI.SimulationFrame;
import Model.Client;
import Model.Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {
    public static int timeLimit1 = 60;
    public int maxProcessingTime1 = 4;
    public int minProcessingTime1 = 2;
    public int numberOfServers1 = 2;
    public int numberOfClients1 = 8;
    public int minArrivalTime1 = 2;
    public int maxArrivalTime1 = 30;
    private int timeLimit;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int numberOfServers;
    private int numberOfClients;
    private int minArrivalTime;
    private int maxArrivalTime;

    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME; //default strategy

    private AtomicInteger currentTime = new AtomicInteger(0);

    private Scheduler scheduler; // specific Scheduler
    private List<Client> generatedTasks = new ArrayList<>(); //randomly-generated clients
    private SimulationFrame simGUI; //specific GUI

    public SimulationManager(SimulationFrame gui, int timeL, int nrClients, int nrServers, int minArrTime, int maxArrTime, int minProcTime, int maxProcTime){

        this.simGUI = gui;
        this.timeLimit = timeL;
        this.numberOfClients = nrClients;
        this.numberOfServers = nrServers;
        this.minProcessingTime = minProcTime;
        this.maxProcessingTime = maxProcTime;
        this.minArrivalTime = minArrTime;
        this.maxArrivalTime = maxArrTime;

        scheduler = new Scheduler(numberOfServers, numberOfClients, selectionPolicy); //initialize scheduler with retrieved data from the interface
        generateNRandomTasks(numberOfClients, minProcessingTime, maxProcessingTime, minArrivalTime, maxArrivalTime); //generate randomly numberofClients1 clients
    }

    //default strategy: time strategy
    public void generateNRandomTasks(int numberOfClients, int minProcessingTime, int maxProcessingTime, int minArrivalTime, int maxArrivalTime){

        for(int i=0; i<numberOfClients; i++){
            int randWaitingTime = (int)Math.floor(Math.random()*(maxProcessingTime-minProcessingTime+1)+minProcessingTime); //random waiting time
            int randArrivalTime = (int)Math.floor(Math.random()*(maxArrivalTime-minArrivalTime+1)+minArrivalTime);
            Client newClient = new Client(i + 1, randArrivalTime, randWaitingTime); // new client
            generatedTasks.add(newClient);
        }

        Collections.sort(generatedTasks, new SortByArrival()); //sort clients according to arrivalTime
    }

    @Override
    public void run(){
        List<Client> clientsOfCurrTime = new ArrayList<>();
        boolean cond = true;
        float avgWaitingTime=0, avgServiceTime=0;
        int peakHour=0, maxQueuesClients = 0;

        while(currentTime.get() <= timeLimit && cond) {

            List<Server> currServers = new ArrayList<>(scheduler.getServers());
            int nr = 0, maxCurrQueuesClients = 0;

            simGUI.flushTextArea();
            for (int i = 0; i < generatedTasks.size(); i++) {
                if (generatedTasks.get(i).getArrivalTime() <= currentTime.get()) {
                    scheduler.dispatchTask(generatedTasks.get(i)); // add task to the corresponding queue
                    clientsOfCurrTime.add(generatedTasks.get(i));
                }
            }

            generatedTasks.removeAll(clientsOfCurrTime); //deletes all clients that arrived at the currentTime

            System.out.println("Time " + currentTime);
            simGUI.setTextArea("Time " + currentTime + "\n"); //print in console

            System.out.println("Waiting clients: ");
            simGUI.setTextArea("Waiting clients: "); //print in console

            for (int j = 0; j < generatedTasks.size(); j++) {
                simGUI.setTextArea(toString(generatedTasks.get(j)));
                System.out.print(toString(generatedTasks.get(j)));
            }

            System.out.println("\n");
            simGUI.setTextArea("\n");

            boolean condServer = true;
            for(Server i : currServers) {  // print servers status
                System.out.print("Queue number " + nr + ":" + "\n");
                simGUI.setTextArea("Queue number " + nr + ":"); //print in swing

                if(i.getServerSize() != 0)
                    condServer = false;

                for(Client j : i.getClients()) {
                    ++maxCurrQueuesClients;
                    System.out.println(toString(j));
                    simGUI.setTextArea(toString(j)); //print in swing
                }
                ++nr;
                simGUI.setTextArea("\n");
            }

            if(maxCurrQueuesClients > maxQueuesClients) { // actualize peak hour value
                maxQueuesClients = maxCurrQueuesClients;
                peakHour = currentTime.get();
            }

            for(Server s: scheduler.getServers()) { //a second has passed for all servers
                if(s.getServerSize() != 0) {
                    s.getWaitingPeriod().addAndGet(-1);
                }
            }

            for(Server s: scheduler.getServers()) { //check first client for every server and decrement a second of each s service time
                Client processingClient = s.getClients().peek();
                if(processingClient != null) {
                    if(processingClient.getServiceTime() >= 1)
                        processingClient.setServiceTime(processingClient.getServiceTime() - 1);
                    else {
                        if(processingClient.getServiceTime() == 0) {
                            avgServiceTime += processingClient.getOriginalServiceTime();
                            s.getClients().remove(processingClient);
                            Client followingClient = s.getClients().peek();
                            if(followingClient != null)
                                followingClient.setServiceTime(followingClient.getServiceTime()-1);

                            simGUI.setTextArea(showClient(processingClient));
                            System.out.println(showClient(processingClient));
                        }
                    }
                }
                if(s.getServerSize() > 1)
                    avgWaitingTime += s.getServerSize() - 1;
            }

            simGUI.setTextArea("\n");

            if(!Thread.interrupted()) { //check if thread not interrupted already
                try {
                    Thread.sleep(1000); // wait a second (a second passed in the simulation)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(condServer && generatedTasks.isEmpty()) // stopping condition before simulation time
                cond = false;

            currentTime.incrementAndGet();
        }

        //write simulation results: avg waiting time, avg. service time, peak hour;
        simGUI.setTextArea("Average waiting time: " + (avgWaitingTime/numberOfClients) + "\n");
        simGUI.setTextArea("Average service time: " + (avgServiceTime/numberOfClients) + "\n");
        simGUI.setTextArea("Peak hour: " + peakHour);

        try {
            File newFile = new File("C:\\Users\\Marius\\Desktop\\PT_2023_30227_Chiorean_Bogdan_Assignment_2\\probe.txt");
            if(newFile.createNewFile()) {
                System.out.println("File created:" + newFile.getName());
            }
            else System.out.println("File already exists");

            FileWriter writer = new FileWriter("C:\\Users\\Marius\\Desktop\\PT_2023_30227_Chiorean_Bogdan_Assignment_2\\probe.txt");
            writer.write(simGUI.getTextArea());
            writer.close();
            System.out.println("Successfully wrote to the file.");

        } catch(IOException e) {
            System.out.println("An error ocurred");
            e.printStackTrace();
        }
    }

    public String toString (Client e) {
        return "(" + e.getID() + "," + e.getArrivalTime() + "," + e.getServiceTime() + ")" + ";" ;
    }

    public String showClient(Client e1) {
        return "Service time finished for client with ID " + e1.getID() + " who arrived at time " + e1.getArrivalTime() + " with a service time of " + e1.getOriginalServiceTime() + "\n";
    }
}
