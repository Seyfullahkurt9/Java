package com.chatapp;

import com.chatapp.process.UserProcess;
import com.chatapp.communication.MessageQueue;
import java.util.Scanner;

public class ChatApplication {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue();

        UserProcess user1 = new UserProcess("User1", messageQueue);
        UserProcess user2 = new UserProcess("User2", messageQueue);
        UserProcess user3 = new UserProcess("User3", messageQueue);

        user1.start();
        user2.start();
        user3.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("Gönderici Gir (User1/User2/User3): ");
            String sender = scanner.nextLine();
            if ("q".equalsIgnoreCase(sender)) {
                break;
            }

            System.out.println("Alıcı Gir (User1/User2/User3): ");
            String recipient = scanner.nextLine();
            if ("q".equalsIgnoreCase(recipient)) {
                break;
            }

            System.out.println("Mesajı Gir: ");
            String message = scanner.nextLine();
            if ("q".equalsIgnoreCase(message)) {
                break;
            }
            if ("User1".equalsIgnoreCase(sender)) {
                user1.sendMessage(recipient, message);
            } else if ("User2".equalsIgnoreCase(sender)) {
                user2.sendMessage(recipient, message);
            } else if ("User3".equalsIgnoreCase(sender)) {
                user3.sendMessage(recipient, message);
            } else {
                System.out.println("Geçersiz gönerici veya alıcı.");
            }
        }

        // Interrupt user threads to stop them gracefully
        user1.interrupt();
        user2.interrupt();
        user3.interrupt();
        scanner.close();
    }
}
