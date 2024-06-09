import java.util.HashMap;
import java.util.Map;

public class TrafficController {
    private static TrafficController instance;
    private final Map<String, Road> roads;

    private TrafficController(){
        roads = new HashMap<>();
    }

    public static synchronized TrafficController getInstance(){
        if (instance==null){
            instance=new TrafficController();
        }
        return instance;
    }

    public void addRoad(Road road){
        roads.put(road.getId(),road);
    }

    public void removeRoad(String roadId){
        roads.remove(roadId);
    }

    public void startTrafficControl(){
        for (Road road : roads.values()){
            TrafficLight trafficLight = road.getTrafficLight();
            new Thread(()->{
                while(true){
                    try {
                        System.out.println("Please note that the traffic signal is: " + trafficLight.getCurrentSignal());
                        Thread.sleep(trafficLight.getRedDuration());
                        trafficLight.changeSignal(Signal.YELLOW);
                        System.out.println("Please note that the traffic signal is: " + trafficLight.getCurrentSignal());
                        Thread.sleep(trafficLight.getGreenDuration());
                        trafficLight.changeSignal(Signal.GREEN);
                        System.out.println("Please note that the traffic signal is: " + trafficLight.getCurrentSignal());
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void handleEmergency(String roadId){
        Road road = roads.get(roadId);
        if (road!=null){
            TrafficLight trafficLight = road.getTrafficLight();
            trafficLight.changeSignal(Signal.GREEN);
            //perform emergency handling logic
        }
    }
}
