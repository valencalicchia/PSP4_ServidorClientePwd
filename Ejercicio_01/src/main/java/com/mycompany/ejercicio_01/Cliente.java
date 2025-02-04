/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio_01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.*;

/**
 *
 * @author valen
 */
public class Cliente {
 public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 2000);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            boolean salir = false;

            while (!salir) {
                String mensajeServidor = input.readUTF();
                System.out.println(mensajeServidor);

                int numero;
                while (true) {
                output.writeUTF("Escribe un número entre 0 y 100:");
                    if (scanner.hasNextInt()) {
                        numero = scanner.nextInt();
                        scanner.nextLine();

                        if (numero >= 0 && numero <= 100) {
                            break;
                        } else {
                            System.out.println("Número fuera del rango. Por favor, ingresa un número entre 0 y 100.");
                        }
                    } else {
                        System.out.println("Entrada no válida. Por favor, ingresa un número entero.");
                        scanner.nextLine();
                    }
                }
                output.writeInt(numero);
                mensajeServidor = input.readUTF();
                System.out.println(mensajeServidor);

                salir = input.readBoolean();
            }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error en la conexión con el servidor", ex);
        }
    }
}
