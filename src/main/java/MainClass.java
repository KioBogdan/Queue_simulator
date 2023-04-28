import BusinessLogic.Controller;
import GUI.SimulationFrame;

public class MainClass {
    public static void main(String[] args) {
        new Controller(new SimulationFrame()); //controller for creating a new SimulationFrame and create an instance of the class SimulationManager
    }
}

