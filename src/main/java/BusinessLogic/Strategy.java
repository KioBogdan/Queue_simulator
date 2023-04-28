package BusinessLogic;

import Model.Server;
import Model.Client;

import java.util.List;

public interface Strategy {
    void addTask(List<Server> servers, Client t);
}

