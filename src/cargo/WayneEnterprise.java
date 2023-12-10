package cargo;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class WayneEnterprise {
    private static final int ORDER_COST = 1000;
    private static final int CANCELED_ORDER_PENALTY = 250;
    private static final int TARGET_EARNINGS = 100000;

    private static int totalEarnings = 0;
    private static int totalOrdersDelivered = 0;
    private static int totalOrdersCanceled = 0;

    private static BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    private static BlockingQueue<Ship> availableShips = new LinkedBlockingQueue<>();

    private static final Object lock = new Object();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            availableShips.add(new Ship());
        }

        
        for (int i = 0; i < 5; i++) {
        	
         Thread t=   new Thread(() -> {
                while (true) {
                    try {
                        Ship ship = availableShips.take();
                        Order order = orderQueue.take();
                        ship.loadCargo(order);

                        
                        Thread.sleep(100);

                        ship.deliverCargo(ORDER_COST);
                        totalOrdersDelivered++;

                        synchronized (lock) {
                            totalEarnings += ORDER_COST;
                            if (totalEarnings >= TARGET_EARNINGS) {
                                System.out.println("Simulation completed!");
                                printResults();
                                System.exit(0);
                            }
                        }

                        availableShips.add(ship);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
         t.start();
        }

        
       
        Thread t2=    new Thread(() -> {
                while (true) {
                    try {
                        Order order = new Order();
                        orderQueue.put(order);

                        // Simulate order placement time
                        Thread.sleep(50);

                        synchronized (lock) {
                            if (totalEarnings >= TARGET_EARNINGS) {
                                System.out.println("Consumer thread terminating");
                                printResults();
                                System.exit(0);
                            }

                            if (order.isCancelled()) {
                                totalOrdersCanceled++;
                                totalEarnings -= CANCELED_ORDER_PENALTY;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        t2.start();
        }
   

    private static void printResults() {
        System.out.println("Total orders delivered: " + totalOrdersDelivered);
        System.out.println("Total orders canceled: " + totalOrdersCanceled);
        System.out.println("Total earnings: $" + totalEarnings);
    }
}
