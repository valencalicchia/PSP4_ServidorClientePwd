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
        // Establece una conexión con el servidor en localhost y puerto 2000.
        try (Socket socket = new Socket("localhost", 2000); DataInputStream input = new DataInputStream(socket.getInputStream()); DataOutputStream output = new DataOutputStream(socket.getOutputStream()); Scanner scanner = new Scanner(System.in)) {  // Utiliza try-with-resources para cerrar recursos automáticamente.

            boolean salir = false;  // Bandera para controlar el ciclo de ejecución.

            while (!salir) {  // El ciclo continuará hasta que el servidor indique que se debe salir.
                // Lee el mensaje enviado por el servidor.
                String mensajeServidor = input.readUTF();
                System.out.println(mensajeServidor);  // Muestra el mensaje del servidor en consola.

                int numero;
                while (true) {  // Bucle para solicitar un número válido entre 0 y 100.
                    output.writeUTF("Escribe un número entre 0 y 100:");  // Solicita al cliente un número.

                    // Verifica si el usuario ha ingresado un número entero.
                    if (scanner.hasNextInt()) {
                        numero = scanner.nextInt();  // Lee el número ingresado.
                        scanner.nextLine();  // Limpia el buffer del scanner.

                        // Verifica que el número esté en el rango adecuado (0 a 100).
                        if (numero >= 0 && numero <= 100) {
                            break;  // Si es válido, sale del ciclo.
                        } else {
                            System.out.println("Número fuera del rango. Por favor, ingresa un número entre 0 y 100.");
                        }
                    } else {
                        System.out.println("Entrada no válida. Por favor, ingresa un número entero.");
                        scanner.nextLine();  // Limpia el buffer del scanner si la entrada no es válida.
                    }
                }

                output.writeInt(numero);  // Envía el número al servidor.

                // Lee el mensaje del servidor tras recibir el número.
                mensajeServidor = input.readUTF();
                System.out.println(mensajeServidor);  // Muestra el mensaje recibido del servidor.

                // Lee la respuesta del servidor para saber si debe continuar o salir.
                salir = input.readBoolean();
            }

        } catch (IOException ex) {
            // Si ocurre un error de conexión, lo captura y lo imprime.
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error en la conexión con el servidor", ex);
        }
    }
}
