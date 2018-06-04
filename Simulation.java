import java.util.ArrayList;
import java.util.Random;

public class Simulation {

    private int numOfCars;
    private ArrayList<Integer> line1;
    private int lineCapacity;
    private int maxSpeed;


    public Simulation(int numOfCars, int maxSpeed, int lineCapacity){

        this.numOfCars = numOfCars;
        this.maxSpeed = maxSpeed;
        this.lineCapacity = lineCapacity;
        line1 = new ArrayList<Integer>();

        initRoad(this.line1);
    }

    private void initRoad(ArrayList<Integer> road){
        for(int i = 0; i < this.lineCapacity ; i++){
            road.add(-1);
        }
    }

    public void init(){
        Random rand = new Random();
        int i = numOfCars;

        while(i != 0){
            int raNum = rand.nextInt(line1.size()) + 1;
            try{
                if(line1.get(raNum) == -1){
                    int raSpeed = rand.nextInt(this.maxSpeed) + 1;
                    line1.set(raNum, raSpeed);
                    i--;
                }
            } catch(IndexOutOfBoundsException e){}

        }
    }

    // Checks if there's good distance from car A to car B
    private boolean isAbleToMove(int start, int times){
        boolean isEmpty = true;
        for(int i = 1; i <= times; i++){
            if(line1.get((start + i) % line1.size()) != -1){
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    private void applyRules(int randomLimit){

        Random rand = new Random();
        int currentSpeed = 0;
        boolean  isFree;

        // Change speed of cars depending on its free space in front of them.
        for(int i = 0; i < line1.size(); i++){

            currentSpeed = line1.get(i);

            if(currentSpeed >= 0){
                isFree = isAbleToMove(i, currentSpeed);

                // Accelerate
                if(isFree && currentSpeed < this.maxSpeed){
                    line1.set(i, currentSpeed+1);
                }

                // Slow Down
                else if(!isFree && currentSpeed >= 1){
                    line1.set(i, currentSpeed-1);
                }

                // Randomization
                if(rand.nextInt(randomLimit) + 1 < 3 && currentSpeed > 0){
                    line1.set(i, currentSpeed-1);
                }
                i += currentSpeed-1;
            }

        }

        // Move cars
        // Speed + index
        ArrayList<Integer> newLine = new ArrayList<Integer>();
        initRoad(newLine);
        for(int j = 0; j < this.line1.size(); j++){
            if(line1.get(j) != -1){
                newLine.set((j + Integer.valueOf(line1.get(j))) % newLine.size(), line1.get(j));
            }
        }

        line1 = newLine;

    }

    // Main function
    public void start(int times, int randomLimit){
        while(times != 0){
            printList();
            applyRules(randomLimit);
            times--;
        }
    }

    // Print the road
    private void printList(){
        for(int i = 0; i < line1.size(); i++){
            if(line1.get(i) == -1){
                System.out.print(".");
            }
            else{
                System.out.print(line1.get(i));
            }

        }
        System.out.println();
    }


}
