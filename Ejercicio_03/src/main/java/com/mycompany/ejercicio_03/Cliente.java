/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio_03;

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
        try (Socket socket = new Socket("localhost", 1500);
             DataInputStream entrada = new DataInputStream(socket.getInputStream());
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            scanner.useDelimiter("\n"); 

            // Lee mensajes del servidor, los imprime y envía las respuestas
            System.out.println(entrada.readUTF().trim());  
            salida.writeUTF(scanner.next()); 

            System.out.println(entrada.readUTF().trim()); 
            salida.writeUTF(scanner.next()); 

            if (entrada.readBoolean()) {  // Verifica si el servidor confirmó las credenciales
                ejecutarComandos(entrada, salida, scanner);
            } else {
                System.out.println("Credenciales incorrectas.");
            }
        } catch (IOException e) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error en la comunicación", e); 
        }
    }

    private static void ejecutarComandos(DataInputStream entrada, DataOutputStream salida, Scanner scanner) throws IOException {
        boolean activo = true;
        while (activo) {
            System.out.println(entrada.readUTF().trim());  
            String comando = scanner.next();  // Lee el comando ingresado por el usuario.
            salida.writeUTF(comando);  

            switch (comando) {
                case "ls": 
                    listarArchivos(entrada);
                    break;
                case "get":
                    obtenerArchivo(entrada, salida, scanner);
                    break;
                case "exit":
                    activo = false;
                    break;
                default:
                    System.out.println("Comando no reconocido.");
                    break;
            }
        }
    }

    private static void listarArchivos(DataInputStream entrada) throws IOException {
        int cantidad = entrada.readInt();  // Lee la cantidad de archivos.
        for (int i = 0; i < cantidad; i++) {
            System.out.println(entrada.readUTF().trim());  // Muestra los nombres de los archivos recibidos.
        }
    }

    private static void obtenerArchivo(DataInputStream entrada, DataOutputStream salida, Scanner scanner) throws IOException {
        System.out.println("Introduce la ruta del archivo a mostrar:");
        salida.writeUTF(scanner.next());  

        if (entrada.readBoolean()) {  // Verifica si el archivo existe en el servidor.
            int tamaño = entrada.readInt();  // Lee el tamaño del archivo.
            byte[] datos = new byte[tamaño];
            entrada.readFully(datos); 
            System.out.println(new String(datos));  // Muestra el contenido del archivo.
        } else {
            System.out.println("Error, el archivo no existe.");
        }
    }
}