package BusinessLogic;

import Model.Server;
import Model.Client;

import java.util.List;

public class ShortestQueueStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Client t) { //cea mai mica coada ca si numar de persoane ce asteapta la ea
        int smallestSize = 0;
        int specificServer = 0;
        for(int i=0; i< servers.size(); i++) {
            Server currentServer = servers.get(i);
            if(i==0)
                smallestSize = currentServer.getServerSize();
            else {
                if(currentServer.getServerSize() < smallestSize) {
                    smallestSize = currentServer.getServerSize();
                    specificServer = i;
                }
            }
        }

        servers.get(specificServer).addTask(t); //adaugarea task-ului la coada corespunzatoare strategiei
    }
}
