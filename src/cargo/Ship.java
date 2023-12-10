package cargo;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
public class Ship {
private int currentCargo;
public Ship()
{
	this.currentCargo=0;
}
public void loadCargo(Order order)
{
	currentCargo =currentCargo + order.getCargoweight();
}
public void deliverCargo(int a) {
	//System.out.println("order deliver");
}

public int getCurrentCargo() {
    return currentCargo;
}
}


