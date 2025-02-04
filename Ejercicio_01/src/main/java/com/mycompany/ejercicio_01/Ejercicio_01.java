/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio_01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.*;

/**
 *
 * @author valen
 */
public class Ejercicio_01 extends Thread {

    private final Socket clienteSocket;

    public Ejercicio_01(Socket clienteSocket) {
        this.clienteSocket = clienteSocket;
    }

    @Override
    public void run() {
        System.out.println("Cliente conectado"); 

        try (DataInputStream input = new DataInputStream(clienteSocket.getInputStream());
             DataOutputStream output = new DataOutputStream(clienteSocket.getOutputStream())) {  

            // Genera un número secreto aleatorio entre 0 y 100.
            int numeroSecreto = generarNumeroAleatorio(0, 100);
            System.out.println("Número secreto: " + numeroSecreto); 

            boolean acertado = false; 

            while (!acertado) {  
                output.writeUTF("Escribe un número entre 0 y 100:"); 

                int numeroCliente = input.readInt();  
                System.out.println("Número recibido del cliente: " + numeroCliente);  

                // Compara el número del cliente con el número secreto y envía un mensaje correspondiente.
                if (numeroCliente == numeroSecreto) {
                    output.writeUTF("¡Ganaste!"); 
                    acertado = true;  
                } else if (numeroCliente < numeroSecreto) {
                    output.writeUTF("El número secreto es mayor");  
                } else {
                    output.writeUTF("El número secreto es menor"); 
                }

                output.writeBoolean(acertado);  // Envía al cliente si ha acertado o no.
            }

            System.out.println("Cliente desconectado");  

        } catch (IOException ex) {
            Logger.getLogger(Ejercicio_01.class.getName()).log(Level.SEVERE, "Error en la comunicación con el cliente", ex);
        }
    }

    // Método que genera un número aleatorio entre el rango [minimo, maximo].
    private int generarNumeroAleatorio(int minimo, int maximo) {
        return (int) (Math.random() * (maximo - minimo + 1)) + minimo;
    }
}
