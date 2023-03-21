/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crear.chat;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class ChatClient {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private ChatGUI gui;

    public ChatClient(String serverAddress) throws Exception {
        // Conectar con el servidor
        socket = new Socket(serverAddress, 9001);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);

        // Crear la GUI
        gui = new ChatGUI();
        gui.setVisible(true);

        // Configurar el botón de enviar
        gui.addSendListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writer.println(gui.getInput());
                gui.clearInput();
            }
        });

        // Leer mensajes del servidor
        while (true) {
            String line = reader.readLine();
            if (line.startsWith("MESSAGE")) {
                gui.addMessage(line.substring(8));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String serverAddress = JOptionPane.showInputDialog("Introduzca la dirección IP del servidor:");
        ChatClient client = new ChatClient(serverAddress);
    }
}

