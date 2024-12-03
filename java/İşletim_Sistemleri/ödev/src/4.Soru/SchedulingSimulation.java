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

    // Round Robin Algoritmas�
    public static void roundRobin(List<Job> jobs, int quantum) {
        Queue<Job> jobQueue = new LinkedList<>(jobs); // ��leri saklayan bir kuyruk
        int currentTime = 0; // Mevcut zaman
        Map<Integer, Job> jobMap = new HashMap<>(); // ��lerin haritas�
        Map<Integer, Integer> remainingBurstTimes = new HashMap<>(); // Kalan burst time'lar

        // ��leri bir harita ile saklar�z ve kalan burst time'lar� ayarlar�z
        for (Job job : jobs) {
            jobMap.put(job.jobId, job);
            remainingBurstTimes.put(job.jobId, job.burstTime);
        }

        // Kuyruk bo�alana kadar i�leri s�rayla i�leme
        while (!jobQueue.isEmpty()) {
            Job currentJob = jobQueue.poll(); 
            int remainingTime = remainingBurstTimes.get(currentJob.jobId); //i�in kalan s�resi

            
            if (currentJob.startTime == -1) {
                currentJob.startTime = currentTime;
            }

            
            if (currentJob.arrivalTime > currentTime) {
                currentTime = currentJob.arrivalTime;
            }

            int processingTime = Math.min(quantum, remainingTime); 
            System.out.println("Zaman " + currentTime + ": ��leniyor " + currentJob.jobId + " -- " + processingTime + " units");

            remainingBurstTimes.put(currentJob.jobId, remainingTime - processingTime); // Kalan zaman� g�ncelle
            currentTime += processingTime; // Mevcut zaman� g�ncelle

            // E�er i� tamamlanm��sa, tamamlanma zaman�n� belirle
            if (remainingBurstTimes.get(currentJob.jobId) == 0) {
                currentJob.completionTime = currentTime;
            } else {
                jobQueue.offer(currentJob); // �� tamamlanmam��sa kuyru�a tekrar ekle
            }
        }

        printRoundRobinMetrics(new ArrayList<>(jobMap.values())); // RoundRobin Performans metriklerini yazd�r
    }

    // Priority Scheduling Algoritmas�
    public static void priorityScheduling(List<Job> jobs) {
        AtomicInteger currentTime = new AtomicInteger(0); 
        List<Job> pendingJobs = new ArrayList<>(jobs);  
        Map<Integer, Job> jobMap = new HashMap<>();

        // ��leri bir harita ile sakla
        for (Job job : jobs) {
            jobMap.put(job.jobId, job);
        }

        // T�m i�ler tamamlanana kadar d�ng�
        while (!pendingJobs.isEmpty()) {
            List<Job> availableJobs = pendingJobs.stream()
                .filter(job -> job.arrivalTime <= currentTime.get()) // Mevcut zamana kadar gelmi� i�leri filtrele
                .collect(Collectors.toList());

            if (availableJobs.isEmpty()) {
                currentTime.incrementAndGet(); // Mevcut zaman� bir art�r
                continue;
            }

            Job currentJob = availableJobs.stream()
                .max(Comparator.comparingInt(job -> job.priority)) // En y�ksek �ncelikli i�i bul
                .orElse(null);

            if (currentJob != null) {
                pendingJobs.remove(currentJob); // ��leri bekleyen listeden ��kar
                if (currentJob.startTime == -1) {
                    currentJob.startTime = currentTime.get(); // ��in ba�lang�� zaman�n� belirle
                }
                System.out.println("Zaman " + currentTime + ": ��leniyor " + currentJob.jobId + " -- " + currentJob.burstTime + " units");
                currentTime.addAndGet(currentJob.burstTime); // Mevcut zaman� i�in burst time'� kadar art�r
                currentJob.completionTime = currentTime.get(); // ��in tamamlanma zaman�n� belirle
            }
        }

        printPrioritySchedulingMetrics(new ArrayList<>(jobMap.values())); //Priority Performans metriklerini yazd�r
    }

    // Multi-Level Queue Scheduling Algoritmas�
    public static void multiLevelQueueScheduling(List<Job> jobs) {
        Queue<Job> highPriorityQueue = new LinkedList<>(); // Y�ksek �ncelikli i�ler kuyru�u
        Queue<Job> mediumPriorityQueue = new LinkedList<>(); // Orta �ncelikli i�ler kuyru�u
        Queue<Job> lowPriorityQueue = new LinkedList<>(); // D���k �ncelikli i�ler kuyru�u
        Map<Integer, Job> jobMap = new HashMap<>(); // ��lerin haritas�

        // ��leri bir harita ile sakla
        for (Job job : jobs) {
            jobMap.put(job.jobId, job);
            switch (job.priority) {
                case 1:
                    highPriorityQueue.add(job); // Y�ksek �ncelikli kuyru�a ekle
                    break;
                case 2:
                    mediumPriorityQueue.add(job); // Orta �ncelikli kuyru�a ekle
                    break;
                case 3:
                    lowPriorityQueue.add(job); // D���k �ncelikli kuyru�a ekle
                    break;
                default:
                    System.out.println("Ge�ersiz �nceli�e sahip Proses " + job.jobId); // Ge�ersiz �ncelik
            }
        }

        System.out.println("Y�ksek �ncelikli Prosesler i�leniyor");
        processQueue(highPriorityQueue); // Y�ksek �ncelikli i�leri i�le

        System.out.println("Orta �ncelikli Prosesler i�leniyor");
        processQueue(mediumPriorityQueue); // Orta �ncelikli i�leri i�le

        System.out.println("D���k �ncelikli Prosesler i�leniyor");
        processQueue(lowPriorityQueue); // D���k �ncelikli i�leri i�le

        printMultiLevelQueueMetrics(new ArrayList<>(jobMap.values())); // Performans metriklerini yazd�r
    }

    private static void processQueue(Queue<Job> queue) {
        int currentTime = 0; // Mevcut zaman
        while (!queue.isEmpty()) {
            Job job = queue.poll(); // Kuyruktan bir i� al

            if (job.startTime == -1) {
                job.startTime = currentTime; // ��in ba�lang�� zaman�n� belirle
            }

            if (job.arrivalTime > currentTime) {
                currentTime = job.arrivalTime; 
            }

            System.out.println("Zaman " + currentTime + ": ��leniyor " + job.jobId + " -- " + job.burstTime + " units");
            currentTime += job.burstTime; // Mevcut zaman� i�in burst time'� kadar art�r
            job.completionTime = currentTime; // ��in tamamlanma zaman�n� belirle
        }
    }

    private static void printRoundRobinMetrics(List<Job> jobs) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int jobCount = jobs.size();

        System.out.println("\nRound Robin Performans Metrikleri:");
        for (Job job : jobs) {
            int waitingTime = job.getWaitingTime(); // ��in bekleme s�resi
            int turnaroundTime = job.getTurnaroundTime(); // ��in d�n�� s�resi
            totalWaitingTime += waitingTime; // Toplam bekleme s�resini g�ncelle
            totalTurnaroundTime += turnaroundTime; // Toplam d�n�� s�resini g�ncelle
            System.out.println("Proses " + job.jobId + ": Bekleme Zaman� = " + waitingTime + ", D�n�� Zaman� = " + turnaroundTime);
        }

        System.out.println("\nOrtalama Bekleme Zaman�: " + (totalWaitingTime / (double) jobCount)); // Ortalama bekleme s�resi
        System.out.println("Ortalama D�n�� Zaman�: " + (totalTurnaroundTime / (double) jobCount)); // Ortalama d�n�� s�resi
    }

    private static void printPrioritySchedulingMetrics(List<Job> jobs) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int jobCount = jobs.size();

        System.out.println("\nPriority Scheduling Performans Metrikleri:");
        for (Job job : jobs) {
            int waitingTime = job.getWaitingTime(); // ��in bekleme s�resi
            int turnaroundTime = job.getTurnaroundTime(); // ��in d�n�� s�resi
            totalWaitingTime += waitingTime; // Toplam bekleme s�resini g�ncelle
            totalTurnaroundTime += turnaroundTime; // Toplam d�n�� s�resini g�ncelle
            System.out.println("Proses " + job.jobId + ": Bekleme Zaman� = " + waitingTime + ", D�n�� Zaman� = " + turnaroundTime);
        }

        System.out.println("\nOrtalama Bekleme Zaman�: " + (totalWaitingTime / (double) jobCount)); // Ortalama bekleme s�resi
        System.out.println("Ortalama D�n�� Zaman�: " + (totalTurnaroundTime / (double) jobCount)); // Ortalama d�n�� s�resi
    }

    private static void printMultiLevelQueueMetrics(List<Job> jobs) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int jobCount = jobs.size();

        System.out.println("\nMulti-Level Queue Scheduling Performans Metrikleri:");
        for (Job job : jobs) {
            int waitingTime = job.getWaitingTime(); // ��in bekleme s�resi
            int turnaroundTime = job.getTurnaroundTime(); // ��in d�n�� s�resi
            totalWaitingTime += waitingTime; // Toplam bekleme s�resini g�ncelle
            totalTurnaroundTime += turnaroundTime; // Toplam d�n�� s�resini g�ncelle
            System.out.println("Proses " + job.jobId + ": Bekleme Zaman� = " + waitingTime + ", D�n�� Zaman� = " + turnaroundTime);
        }

        System.out.println("\nOrtalama Bekleme Zaman�: " + (totalWaitingTime / (double) jobCount)); // Ortalama bekleme s�resi
        System.out.println("Ortalama D�n�� Zaman�: " + (totalTurnaroundTime / (double) jobCount)); // Ortalama d�n�� s�resi
    }

    // Ana program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Kullan�c�dan giri� almak i�in Scanner
        List<Job> jobs = new ArrayList<>(); // �� listesi

        System.out.print("Yap�lacak Proses Say�s�n� Girin: ");
        int numberOfJobs = scanner.nextInt(); // Kullan�c�dan i� say�s�n� al

        // Kullan�c�dan i� bilgilerini al
        for (int i = 0; i < numberOfJobs; i++) {
            System.out.print("Patlama S�resini girin Proses " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();
            System.out.print("Geli� S�resini girin Proses " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("�nceli�i girin Proses " + (i + 1) + " (1 = Y�ksek, 2 = Orta, 3 = D���k): ");
            int priority = scanner.nextInt();

            jobs.add(new Job(i + 1, burstTime, arrivalTime, priority)); // ��leri listeye ekle
        }

        System.out.print("Round Robin i�in quantum de�erini girin: ");
        int quantum = scanner.nextInt(); // Round Robin i�in kuantum de�eri

        //algoritmalar�n� �al��t�r
        System.out.println("\nRound Robin Scheduling:");
        roundRobin(new ArrayList<>(jobs), quantum);

        System.out.println("\nPriority Scheduling:");
        priorityScheduling(new ArrayList<>(jobs));

        System.out.println("\nMulti-Level Queue Scheduling:");
        multiLevelQueueScheduling(new ArrayList<>(jobs));

        scanner.close(); 
    }
}