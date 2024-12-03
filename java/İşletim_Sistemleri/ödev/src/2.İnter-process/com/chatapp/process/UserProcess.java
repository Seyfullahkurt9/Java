package com.chatapp.process;

import com.chatapp.communication.MessageQueue;

public class UserProcess extends Thread {
    private String username;
    private MessageQueue messageQueue;

    public UserProcess(String username, MessageQueue messageQueue) {
        this.username = username;
        this.messageQueue = messageQueue;
    }

    public void sendMessage(String recipient, String message) {
        messageQueue.sendMessage(username, recipient, message);
    }

    public void receiveMessage() {
        String message = messageQueue.receiveMessage(username);
        if (message != null) {
            System.out.println(username +" "+ message + " Mesajı aldı ");
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                receiveMessage();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
