package BusinessLogic;

import Model.Client;

import java.util.Comparator;

public class SortByArrival implements Comparator<Client> {
    @Override
    public int compare(Client o1, Client o2) {
        return o1.getArrivalTime()-o2.getArrivalTime();
    }
}
