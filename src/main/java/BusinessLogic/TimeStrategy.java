package BusinessLogic;

import Model.Server;
import Model.Client;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//Time strategy: add client to shortest waiting time queue
public class TimeStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Client t) {
        AtomicInteger waitPeriod = new AtomicInteger(0);
        int specificServer = 0;
        for(int i=0; i < servers.size(); i++) {
            Server currentServer = servers.get(i);
            if(i==0) waitPeriod = currentServer.getWaitingPeriod();
            else {
                if(currentServer.getWaitingPeriod().get() < waitPeriod.get()) {
                    waitPeriod = currentServer.getWaitingPeriod();
                    specificServer = i;
                }
            }
        }
        servers.get(specificServer).addTask(t);
    }
}
