public class Main {

    public static void main(String[] args) {

        Simulation simulation = new Simulation(10, 5, 40);
        simulation.init();
        simulation.start(20, 20);

    }
}
