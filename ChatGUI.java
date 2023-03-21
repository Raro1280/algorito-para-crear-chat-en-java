/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crear.chat;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ChatGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public ChatGUI() {
        // Configurar la ventana
        setTitle("Chat");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear los componentes
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        inputField = new JTextField();
        sendButton = new JButton("Enviar");
        
        // Añadir los componentes a la ventana
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);
    }

    public void addMessage(String message) {
        chatArea.append(message + "\n");
    }

    public String getInput() {
        return inputField.getText();
    }

    public void clearInput() {
        inputField.setText("");
    }

    public void addSendListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }
}


