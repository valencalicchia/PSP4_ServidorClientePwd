/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio_02;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.*;

/**
 *
 * @author valen
 */
public class Ejercicio_02 extends Thread {

    private final Socket clienteSocket;

    public Ejercicio_02(Socket clienteSocket) {
        this.clienteSocket = clienteSocket;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(clienteSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clienteSocket.getOutputStream())) {

            String rutaArchivo = in.readUTF();  
            File archivo = new File(rutaArchivo);
            
            // Verifica si el archivo existe y es un archivo válido
            if (archivo.exists() && archivo.isFile()) {  
                out.writeBoolean(true);  // Informa al cliente que el archivo existe
                byte[] contenido = leerArchivo(archivo); 

                out.writeInt(contenido.length);  
                out.write(contenido);  // Envía el contenido del archivo al cliente
                System.out.println("Archivo enviado: " + archivo.getName()); 
            } else {
                out.writeBoolean(false); 
                System.out.println("Archivo no encontrado: " + rutaArchivo);  
            }

        } catch (IOException ex) {
            Logger.getLogger(Ejercicio_02.class.getName()).log(Level.SEVERE, "Error en la comunicación con el cliente", ex);
        } finally {
            try {
                clienteSocket.close(); 
            } catch (IOException ex) {
                Logger.getLogger(Ejercicio_02.class.getName()).log(Level.SEVERE, "Error al cerrar el socket del cliente", ex);
            }
        }
    }

    private byte[] leerArchivo(File archivo) throws IOException {
        try (FileInputStream fis = new FileInputStream(archivo);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesLeidos;

            while ((bytesLeidos = fis.read(buffer)) != -1) {  // Lee el archivo hasta el final
                bos.write(buffer, 0, bytesLeidos);  // Escribe los bytes leídos en el buffer de salida
            }

            return bos.toByteArray();
        }
    }
}
