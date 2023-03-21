/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crear.chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Set<String> usernames = new HashSet<>();
    private static Set<PrintWriter> writers = new HashSet<>();

    public static void main(String[] args) throws Exception {
        System.out.println("El servidor está funcionando...");
        ServerSocket listener = new ServerSocket(9001);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                // Configurar las entradas y salidas del cliente
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                // Solicitar un nombre de usuario único
                while (true) {
                    writer.println("USERNAME");
                    name = reader.readLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (usernames) {
                        if (!name.isEmpty() && !usernames.contains(name)) {
                            usernames.add(name);
                            break;
                        }
                    }
                }

                // Informar a los demás usuarios de la llegada de un nuevo usuario
                for (PrintWriter writer : writers) {
                    writer.println("MESSAGE " + name + " se ha unido al chat.");
                }
                writers.add(writer);

                // Leer mensajes del cliente
                while (true) {
                    String message = reader.readLine();
                    if (message == null) {
                        return;
                    }
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + name + ": " + message);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // Cuando un cliente se desconecta, informar a los demás usuarios
                if (name != null) {
                    usernames.remove(name);
                }
                if (writer != null) {
                    writers.remove(writer);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
                for (PrintWriter writer : writers) {
                    writer.println("MESSAGE " + name + " ha abandonado el chat.");
                }
            }
        }
    }
}

