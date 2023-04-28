package BusinessLogic;

import Model.Server;
import Model.Client;

import java.util.ArrayList;
import java.util.List;

//Scheduler class
public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxClientsPerServer;
    private Strategy strategy; //Strategy of adding a client to a queue


    public Scheduler(int maxNumber, int maxClients, SelectionPolicy sel){
        this.maxNoServers = maxNumber;
        this.maxClientsPerServer = maxClients;
        servers = new ArrayList<>(maxNoServers);

        for(int i=0; i<maxNoServers; i++){  // create maxNoServers servers, having maxClientsPerServer clients
            Server s = new Server(maxClientsPerServer);
            servers.add(s);
            Thread thrServer = new Thread(s);
            thrServer.start(); // start the thread of the server
        }

        changeStrategy(sel); //schimbam strategia corespunzator
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        }

        if(policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new TimeStrategy();
        }
    }

    public void dispatchTask(Client t) { strategy.addTask(servers, t); } // adds the client t to a queue, based on the strategy

    public List<Server> getServers() { return this.servers; }
}
