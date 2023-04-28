package Model;

public class Client { // client class
    private int ID;
    private int arrivalTime;
    private int serviceTime;
    private int originalServiceTime; //original service time

    public Client(int id, int arrTime, int serTime){
        this.ID = id;
        this.arrivalTime = arrTime;
        this.serviceTime = serTime;
        this.originalServiceTime = serTime;
    }

    public void setServiceTime(int serviceTime) { this.serviceTime = serviceTime; }

    public int getID() { return this.ID; }
    public int getArrivalTime() { //getter pentru arrivalTime
        return this.arrivalTime;
    }
    public int getServiceTime(){ //getter pentru serviceTime
        return this.serviceTime;
    }
    public int getOriginalServiceTime(){ //getter pentru serviceTime
        return this.originalServiceTime;
    }
}
