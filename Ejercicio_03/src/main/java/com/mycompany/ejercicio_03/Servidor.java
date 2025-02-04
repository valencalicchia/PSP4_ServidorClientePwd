/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio_03;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;

/**
 *
 * @author valen
 */
public class Servidor {
    private static final int PUERTO = 1500;  

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(PUERTO)) { 
            System.out.println("Servidor iniciado en el puerto " + PUERTO); 

            // Bucle infinito
            while (true) {
                Socket clienteSocket = servidor.accept();  
                System.out.println("Cliente conectado.");  // Mensaje que indica que un cliente se ha conectado

                // Crea un nuevo hilo y lo inicia
                Ejercicio_03 manejador = new Ejercicio_03(clienteSocket);
                manejador.start();  
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, "Error al iniciar el servidor", ex);  // Manejo de excepciones si ocurre un error al crear el socket del servidor.
        }
    }
}