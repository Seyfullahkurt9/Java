import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SchedulingSimulation {

    // Round Robin Algoritmasý
    public static void roundRobin(List<Job> jobs, int quantum) {
        Queue<Job> jobQueue = new LinkedList<>(jobs); // Ýþleri saklayan bir kuyruk
        int currentTime = 0; // Mevcut zaman
        Map<Integer, Job> jobMap = new HashMap<>(); // Ýþlerin haritasý
        Map<Integer, Integer> remainingBurstTimes = new HashMap<>(); // Kalan burst time'lar

        // Ýþleri bir harita ile saklarýz ve kalan burst time'larý ayarlarýz
        for (Job job : jobs) {
            jobMap.put(job.jobId, job);
            remainingBurstTimes.put(job.jobId, job.burstTime);
        }

        // Kuyruk boþalana kadar iþleri sýrayla iþleme
        while (!jobQueue.isEmpty()) {
            Job currentJob = jobQueue.poll(); 
            int remainingTime = remainingBurstTimes.get(currentJob.jobId); //iþin kalan süresi

            
            if (currentJob.startTime == -1) {
                currentJob.startTime = currentTime;
            }

            
            if (currentJob.arrivalTime > currentTime) {
                currentTime = currentJob.arrivalTime;
            }

            int processingTime = Math.min(quantum, remainingTime); 
            System.out.println("Zaman " + currentTime + ": Ýþleniyor " + currentJob.jobId + " -- " + processingTime + " units");

            remainingBurstTimes.put(currentJob.jobId, remainingTime - processingTime); // Kalan zamaný güncelle
            currentTime += processingTime; // Mevcut zamaný güncelle

            // Eðer iþ tamamlanmýþsa, tamamlanma zamanýný belirle
            if (remainingBurstTimes.get(currentJob.jobId) == 0) {
                currentJob.completionTime = currentTime;
            } else {
                jobQueue.offer(currentJob); // Ýþ tamamlanmamýþsa kuyruða tekrar ekle
            }
        }

        printRoundRobinMetrics(new ArrayList<>(jobMap.values())); // RoundRobin Performans metriklerini yazdýr
    }

    // Priority Scheduling Algoritmasý
    public static void priorityScheduling(List<Job> jobs) {
        AtomicInteger currentTime = new AtomicInteger(0); 
        List<Job> pendingJobs = new ArrayList<>(jobs);  
        Map<Integer, Job> jobMap = new HashMap<>();

        // Ýþleri bir harita ile sakla
        for (Job job : jobs) {
            jobMap.put(job.jobId, job);
        }

        // Tüm iþler tamamlanana kadar döngü
        while (!pendingJobs.isEmpty()) {
            List<Job> availableJobs = pendingJobs.stream()
                .filter(job -> job.arrivalTime <= currentTime.get()) // Mevcut zamana kadar gelmiþ iþleri filtrele
                .collect(Collectors.toList());

            if (availableJobs.isEmpty()) {
                currentTime.incrementAndGet(); // Mevcut zamaný bir artýr
                continue;
            }

            Job currentJob = availableJobs.stream()
                .max(Comparator.comparingInt(job -> job.priority)) // En yüksek öncelikli iþi bul
                .orElse(null);

            if (currentJob != null) {
                pendingJobs.remove(currentJob); // Ýþleri bekleyen listeden çýkar
                if (currentJob.startTime == -1) {
                    currentJob.startTime = currentTime.get(); // Ýþin baþlangýç zamanýný belirle
                }
                System.out.println("Zaman " + currentTime + ": Ýþleniyor " + currentJob.jobId + " -- " + currentJob.burstTime + " units");
                currentTime.addAndGet(currentJob.burstTime); // Mevcut zamaný iþin burst time'ý kadar artýr
                currentJob.completionTime = currentTime.get(); // Ýþin tamamlanma zamanýný belirle
            }
        }

        printPrioritySchedulingMetrics(new ArrayList<>(jobMap.values())); //Priority Performans metriklerini yazdýr
    }

    // Multi-Level Queue Scheduling Algoritmasý
    public static void multiLevelQueueScheduling(List<Job> jobs) {
        Queue<Job> highPriorityQueue = new LinkedList<>(); // Yüksek öncelikli iþler kuyruðu
        Queue<Job> mediumPriorityQueue = new LinkedList<>(); // Orta öncelikli iþler kuyruðu
        Queue<Job> lowPriorityQueue = new LinkedList<>(); // Düþük öncelikli iþler kuyruðu
        Map<Integer, Job> jobMap = new HashMap<>(); // Ýþlerin haritasý

        // Ýþleri bir harita ile sakla
        for (Job job : jobs) {
            jobMap.put(job.jobId, job);
            switch (job.priority) {
                case 1:
                    highPriorityQueue.add(job); // Yüksek öncelikli kuyruða ekle
                    break;
                case 2:
                    mediumPriorityQueue.add(job); // Orta öncelikli kuyruða ekle
                    break;
                case 3:
                    lowPriorityQueue.add(job); // Düþük öncelikli kuyruða ekle
                    break;
                default:
                    System.out.println("Geçersiz önceliðe sahip Proses " + job.jobId); // Geçersiz öncelik
            }
        }

        System.out.println("Yüksek öncelikli Prosesler iþleniyor");
        processQueue(highPriorityQueue); // Yüksek öncelikli iþleri iþle

        System.out.println("Orta öncelikli Prosesler iþleniyor");
        processQueue(mediumPriorityQueue); // Orta öncelikli iþleri iþle

        System.out.println("Düþük öncelikli Prosesler iþleniyor");
        processQueue(lowPriorityQueue); // Düþük öncelikli iþleri iþle

        printMultiLevelQueueMetrics(new ArrayList<>(jobMap.values())); // Performans metriklerini yazdýr
    }

    private static void processQueue(Queue<Job> queue) {
        int currentTime = 0; // Mevcut zaman
        while (!queue.isEmpty()) {
            Job job = queue.poll(); // Kuyruktan bir iþ al

            if (job.startTime == -1) {
                job.startTime = currentTime; // Ýþin baþlangýç zamanýný belirle
            }

            if (job.arrivalTime > currentTime) {
                currentTime = job.arrivalTime; 
            }

            System.out.println("Zaman " + currentTime + ": Ýþleniyor " + job.jobId + " -- " + job.burstTime + " units");
            currentTime += job.burstTime; // Mevcut zamaný iþin burst time'ý kadar artýr
            job.completionTime = currentTime; // Ýþin tamamlanma zamanýný belirle
        }
    }

    private static void printRoundRobinMetrics(List<Job> jobs) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int jobCount = jobs.size();

        System.out.println("\nRound Robin Performans Metrikleri:");
        for (Job job : jobs) {
            int waitingTime = job.getWaitingTime(); // Ýþin bekleme süresi
            int turnaroundTime = job.getTurnaroundTime(); // Ýþin dönüþ süresi
            totalWaitingTime += waitingTime; // Toplam bekleme süresini güncelle
            totalTurnaroundTime += turnaroundTime; // Toplam dönüþ süresini güncelle
            System.out.println("Proses " + job.jobId + ": Bekleme Zamaný = " + waitingTime + ", Dönüþ Zamaný = " + turnaroundTime);
        }

        System.out.println("\nOrtalama Bekleme Zamaný: " + (totalWaitingTime / (double) jobCount)); // Ortalama bekleme süresi
        System.out.println("Ortalama Dönüþ Zamaný: " + (totalTurnaroundTime / (double) jobCount)); // Ortalama dönüþ süresi
    }

    private static void printPrioritySchedulingMetrics(List<Job> jobs) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int jobCount = jobs.size();

        System.out.println("\nPriority Scheduling Performans Metrikleri:");
        for (Job job : jobs) {
            int waitingTime = job.getWaitingTime(); // Ýþin bekleme süresi
            int turnaroundTime = job.getTurnaroundTime(); // Ýþin dönüþ süresi
            totalWaitingTime += waitingTime; // Toplam bekleme süresini güncelle
            totalTurnaroundTime += turnaroundTime; // Toplam dönüþ süresini güncelle
            System.out.println("Proses " + job.jobId + ": Bekleme Zamaný = " + waitingTime + ", Dönüþ Zamaný = " + turnaroundTime);
        }

        System.out.println("\nOrtalama Bekleme Zamaný: " + (totalWaitingTime / (double) jobCount)); // Ortalama bekleme süresi
        System.out.println("Ortalama Dönüþ Zamaný: " + (totalTurnaroundTime / (double) jobCount)); // Ortalama dönüþ süresi
    }

    private static void printMultiLevelQueueMetrics(List<Job> jobs) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int jobCount = jobs.size();

        System.out.println("\nMulti-Level Queue Scheduling Performans Metrikleri:");
        for (Job job : jobs) {
            int waitingTime = job.getWaitingTime(); // Ýþin bekleme süresi
            int turnaroundTime = job.getTurnaroundTime(); // Ýþin dönüþ süresi
            totalWaitingTime += waitingTime; // Toplam bekleme süresini güncelle
            totalTurnaroundTime += turnaroundTime; // Toplam dönüþ süresini güncelle
            System.out.println("Proses " + job.jobId + ": Bekleme Zamaný = " + waitingTime + ", Dönüþ Zamaný = " + turnaroundTime);
        }

        System.out.println("\nOrtalama Bekleme Zamaný: " + (totalWaitingTime / (double) jobCount)); // Ortalama bekleme süresi
        System.out.println("Ortalama Dönüþ Zamaný: " + (totalTurnaroundTime / (double) jobCount)); // Ortalama dönüþ süresi
    }

    // Ana program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Kullanýcýdan giriþ almak için Scanner
        List<Job> jobs = new ArrayList<>(); // Ýþ listesi

        System.out.print("Yapýlacak Proses Sayýsýný Girin: ");
        int numberOfJobs = scanner.nextInt(); // Kullanýcýdan iþ sayýsýný al

        // Kullanýcýdan iþ bilgilerini al
        for (int i = 0; i < numberOfJobs; i++) {
            System.out.print("Patlama Süresini girin Proses " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            System.out.print("Geliþ Süresini girin Proses " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Önceliði girin Proses " + (i + 1) + " (1 = Yüksek, 2 = Orta, 3 = Düþük): ");
            int priority = scanner.nextInt();

            jobs.add(new Job(i + 1, burstTime, arrivalTime, priority)); // Ýþleri listeye ekle
        }

        System.out.print("Round Robin için quantum deðerini girin: ");
        int quantum = scanner.nextInt(); // Round Robin için kuantum deðeri

        //algoritmalarýný çalýþtýr
        System.out.println("\nRound Robin Scheduling:");
        roundRobin(new ArrayList<>(jobs), quantum);

        System.out.println("\nPriority Scheduling:");
        priorityScheduling(new ArrayList<>(jobs));

        System.out.println("\nMulti-Level Queue Scheduling:");
        multiLevelQueueScheduling(new ArrayList<>(jobs));

        scanner.close(); 
    }
}