package cargo;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Order {
	private static final Random random=new Random();
	private int cargoweight;
	private String destination;
	long ordertime;
	public Order()
	{
		this.cargoweight= random.nextInt(41)+10;
		this.ordertime=System.currentTimeMillis();
		this.destination=random.nextBoolean()? "gotham":"atlana";
		
	}
	public int getCargoweight() {
		return cargoweight;
	}
	public String getDestination()
	{
		return destination;
	}
	public Boolean isCancelled()
	{
		return System.currentTimeMillis() - ordertime > 60;
	}
	public void deliverCargo(int t)
	{}
}
