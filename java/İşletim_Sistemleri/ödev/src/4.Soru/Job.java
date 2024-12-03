import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Job {
    int jobId;
    int burstTime;
    int arrivalTime;
    int priority;
    int startTime;
    int completionTime;

    // Job sýnýfý oluþturucusu
    public Job(int jobId, int burstTime, int arrivalTime, int priority) {
        this.jobId = jobId;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.startTime = -1; // Baþlangýç zamaný henüz belirltilmemiþ
        this.completionTime = -1; // Tamamlanma zamaný henüz belirtlimemiþ
    }

    // Dönüþ süresi hesaplayan metod
    public int getTurnaroundTime() {
        return completionTime - arrivalTime;
    }

    // Bekleme süresi hesaplayan metod
    public int getWaitingTime() {
        return getTurnaroundTime() - burstTime;
    }

    @Override
    public String toString() {
        return "Job(ID=" + jobId + ", BurstTime=" + burstTime + ", ArrivalTime=" + arrivalTime + ", Priority=" + priority + ")";
    }
}