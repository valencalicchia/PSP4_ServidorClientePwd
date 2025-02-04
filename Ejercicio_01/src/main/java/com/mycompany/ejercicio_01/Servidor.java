/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio_01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;

/**
 *
 * @author valen
 */
public class Servidor {
    private static final int PUERTO = 2000;  // Define el puerto en el que el servidor escuchará.

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {  
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            while (true) {  
                Socket clienteSocket = servidor.accept();  // Acepta conexiones de clientes.
                
                //Creacion e inicio del hilo
                Ejercicio_01 manejador = new Ejercicio_01(clienteSocket);  
                manejador.start();
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, "Error al iniciar el servidor", ex);
        }
    }
}