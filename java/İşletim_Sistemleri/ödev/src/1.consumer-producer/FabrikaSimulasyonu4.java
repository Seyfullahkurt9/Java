import java.util.concurrent.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

// Ürün sınıfı
class Product {
    private int id;

    public Product(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Ürün-" + id;
    }
}

// Performans metriklerinin hazırlandığı kısım
class PerformanceMetrics {
    AtomicInteger totalProduced = new AtomicInteger(0);
    AtomicInteger totalConsumed = new AtomicInteger(0);
    ConcurrentHashMap<String, Integer> producerMetrics = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, Integer> consumerMetrics = new ConcurrentHashMap<>();
    AtomicInteger maxOccupancy = new AtomicInteger(0);
    long startTime;

    public PerformanceMetrics() {
        this.startTime = System.currentTimeMillis();
    }

    public void incrementProduced(String producerName) {
        totalProduced.incrementAndGet();
        producerMetrics.merge(producerName, 1, Integer::sum);
    }

    public void incrementConsumed(String consumerName) {
        totalConsumed.incrementAndGet();
        consumerMetrics.merge(consumerName, 1, Integer::sum);
    }

    public void updateMaxOccupancy(int currentOccupancy) {
        maxOccupancy.updateAndGet(max -> Math.max(max, currentOccupancy));
    }

    public void printMetrics() {
        long duration = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("\nPerformans Metrikleri:");
        System.out.println("Toplam süre: " + duration + " saniye");
        System.out.println("Toplam üretilen: " + totalProduced);
        System.out.println("Toplam tüketilen: " + totalConsumed);
        System.out.println("Üretim oranı: " + (totalProduced.get() / (float)duration) + " ürün/saniye");
        System.out.println("Tüketim oranı: " + (totalConsumed.get() / (float)duration) + " ürün/saniye");
        System.out.println("Maksimum depo doluluğu: " + maxOccupancy + " ürün");

        System.out.println("\nÜretici Metrikleri:");
        producerMetrics.forEach((name, count) -> 
            System.out.println(name + ": " + count + " ürün (" + (count / (float)duration) + " ürün/saniye)"));

        System.out.println("\nTüketici Metrikleri:");
        consumerMetrics.forEach((name, count) -> 
            System.out.println(name + ": " + count + " ürün (" + (count / (float)duration) + " ürün/saniye)"));
    }
}

class Factory {
    private final BlockingQueue<Product> storage;
    private final int maxCapacity;
    private final Semaphore producerSemaphore;
    private final Semaphore consumerSemaphore;
    private volatile boolean isRunning = true;
    private int productCounter = 0;
    private final PerformanceMetrics metrics;

    public Factory(int capacity) {
        this.maxCapacity = capacity;
        this.storage = new LinkedBlockingQueue<>(capacity);
        this.producerSemaphore = new Semaphore(capacity);
        this.consumerSemaphore = new Semaphore(0);
        this.metrics = new PerformanceMetrics();
    }

    public void produce(String producerName) throws InterruptedException {
        while (isRunning) {
            producerSemaphore.acquire();
            if (!isRunning) break;

            Product product = new Product(++productCounter);
            storage.put(product);
            int currentOccupancy = storage.size();
            metrics.updateMaxOccupancy(currentOccupancy);
            System.out.println(producerName + " " + product + " üretti. Depo: " + currentOccupancy + "/" + maxCapacity);
            metrics.incrementProduced(producerName);

            consumerSemaphore.release();
            Thread.sleep(new Random().nextInt(200));
        }
    }

    public void consume(String consumerName) throws InterruptedException {
        while (isRunning) {
            consumerSemaphore.acquire();
            if (!isRunning) break;

            Product product = storage.take();
            int currentOccupancy = storage.size();
            System.out.println(consumerName + " " + product + " tüketti. Depo: " + currentOccupancy + "/" + maxCapacity);
            metrics.incrementConsumed(consumerName);

            producerSemaphore.release();
            Thread.sleep(new Random().nextInt(2000));
        }
    }

    public void shutdown() {
        isRunning = false;
        producerSemaphore.release(maxCapacity);
        consumerSemaphore.release(maxCapacity);
    }

    public void printPerformanceMetrics() {
        metrics.printMetrics();
    }
}

class Producer implements Runnable {
    private final Factory factory;
    private final String name;

    public Producer(Factory factory, String name) {
        this.factory = factory;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            factory.produce(name);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private final Factory factory;
    private final String name;

    public Consumer(Factory factory, String name) {
        this.factory = factory;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            factory.consume(name);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class FabrikaSimulasyonu4 {
    public static void main(String[] args) throws InterruptedException {
        Factory factory = new Factory(15); // Depo kapasitesi ayarlandı
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        // Üreticileri başlat
        for (int i = 1; i <= 3; i++) {
            executorService.submit(new Producer(factory, "Üretici-" + i));
        }

        // Tüketicileri başlat
        for (int i = 1; i <= 5; i++) {
            executorService.submit(new Consumer(factory, "Tüketici-" + i));
        }

        // Simülasyonun çalışacağı süre
        Thread.sleep(5000);

        // Fabrikayı ve executor service'yi kapat
        factory.shutdown();
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        // Performans metriklerini yazdır
        factory.printPerformanceMetrics();

        System.out.println("Simülasyon tamamlandı.");
    }
}