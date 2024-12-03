package com.chatapp.communication;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MessageQueue {
    private Map<String, Queue<String>> messageQueues;
    private final Object lock = new Object();

    public MessageQueue() {
        messageQueues = new HashMap<>();
    }

    public void sendMessage(String sender, String recipient, String message) {
        synchronized (lock) {
            messageQueues.putIfAbsent(recipient, new LinkedList<>());
            messageQueues.get(recipient).offer(sender + " tarafından " + message);
            lock.notifyAll();
        }
    }

    public String receiveMessage(String recipient) {
        synchronized (lock) {
            while (!messageQueues.containsKey(recipient) || messageQueues.get(recipient).isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return messageQueues.get(recipient).poll();
        }
    }
}
