package Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

//Server class
public class Server implements Runnable{
    private BlockingQueue<Client> clients; // list of servers clients
    private AtomicInteger waitingPeriod; //waiting period for the server(queue)

    public Server(int serverCapacity) {
        this.clients = new ArrayBlockingQueue<>(serverCapacity); //queue with maximum number of clients serverCapacity
        this.waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Client newClient) { // adds a client to server
        clients.add(newClient);
        waitingPeriod.addAndGet(newClient.getServiceTime());
    }

    @Override
    public void run(){
        boolean valid = true;
        if(clients.isEmpty()) //check if specific queue is empty
            valid = false;

        while(valid){
            Client processingTask = new Client(0,0,0);

            try { processingTask = clients.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!Thread.interrupted()) { //check to see if thread is not already interrupted
                try {
                    Thread.sleep(processingTask.getServiceTime()* 1000L); //interrupting thread with serviceTime seconds for the current client
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            waitingPeriod.addAndGet(-processingTask.getServiceTime()); //client has finished his serving, decrement waiting time of the queue
        }
    }

    public BlockingQueue<Client> getClients() { return clients; }
    public int getServerSize() { return clients.size(); }
    public AtomicInteger getWaitingPeriod(){ //returnam timpul de asteptare de pe coada
        return this.waitingPeriod;
    }
}
