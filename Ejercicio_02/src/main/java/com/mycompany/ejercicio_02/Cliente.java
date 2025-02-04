/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ejercicio_02;

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
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Introduce la ruta del archivo a solicitar (para prueba se puede usar 'mifichero.txt'");
            String rutaArchivo = scanner.nextLine();

            out.writeUTF(rutaArchivo);
            boolean archivoExiste = in.readBoolean();

            if (archivoExiste) {
                int longitud = in.readInt();
                byte[] contenido = new byte[longitud];

                in.readFully(contenido);

                String contenidoArchivo = new String(contenido);
                System.out.println("Contenido del archivo:");
                System.out.println(contenidoArchivo);

            } else {
                System.out.println("El archivo solicitado no existe en el servidor.");
            }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "Error en la comunicación con el servidor", ex);
        }
    }
}