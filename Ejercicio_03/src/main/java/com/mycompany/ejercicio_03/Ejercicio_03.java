/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejercicio_03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.*;

/**
 *
 * @author valen
 */

public class Ejercicio_03 extends Thread {
    private final Socket socket;

    public Ejercicio_03(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream entrada = new DataInputStream(socket.getInputStream());
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {

            //Lee usuario y contrasenia enviados
            salida.writeUTF("Introduce tu usuario");
            String usuario = entrada.readUTF().trim(); 
            
            salida.writeUTF("Introduce tu contrasenia");
            String pwd = entrada.readUTF().trim(); 

            // Verifica las credenciales del usuario
            if (usuario.equals("psp4") && pwd.equals("123456")) {
                salida.writeBoolean(true);  
                manejarComandos(entrada, salida);
            } else {
                salida.writeBoolean(false);  
            }
        } catch (IOException e) {
            Logger.getLogger(Ejercicio_03.class.getName()).log(Level.SEVERE, "Error en la comunicación", e);
        } finally {
            try {
                socket.close(); 
            } catch (IOException e) {
                Logger.getLogger(Ejercicio_03.class.getName()).log(Level.SEVERE, "Error cerrando el socket", e);
            }
        }
    }

    private void manejarComandos(DataInputStream entrada, DataOutputStream salida) throws IOException {
        boolean continuar = true;
        while (continuar) {
            // Pide al cliente un comando y lo lee
            salida.writeUTF("Introduce un comando (ls/get/exit)");  
            String comando = entrada.readUTF().trim();  

            switch (comando) {
                case "ls": 
                    listarArchivos(salida);
                    break;
                case "get":
                    enviarArchivo(entrada, salida);
                    break;
                case "exit": 
                    continuar = false;
                    break;
                default:
                    salida.writeUTF("Comando no reconocido");  // Responde si el comando no es válido.
                    break;
            }
        }
    }

    private void listarArchivos(DataOutputStream salida) throws IOException {
        File directorio = new File("./ejemplo");  // Directorio donde están los archivos a listar.
        File[] archivos = directorio.listFiles();  // Obtiene todos los archivos del directorio.
        
        if (archivos == null) {
            salida.writeInt(0);
            return;
        }
        
        salida.writeInt(archivos.length);  // Envía al cliente el número de archivos disponibles.
        for (File archivo : archivos) {
            if (archivo.isFile()) {  // Solo envía los archivos (no directorios).
                salida.writeUTF(archivo.getName());  // Envía el nombre del archivo al cliente.
            }
        }
    }

    private void enviarArchivo(DataInputStream entrada, DataOutputStream salida) throws IOException {
        String ruta = entrada.readUTF().trim();  // Lee la ruta del archivo que el cliente desea obtener
        File archivo = new File("./ejemplo/" + ruta); 

        if (archivo.exists() && archivo.isFile()) {
            salida.writeBoolean(true); 
            byte[] contenido = new String(java.nio.file.Files.readAllBytes(archivo.toPath())).getBytes();  // Lee el contenido del archivo.
            salida.writeInt(contenido.length);  // Envía el tamaño del archivo
            salida.write(contenido);  // Envía el contenido del archivo
        } else {
            salida.writeBoolean(false);
        }
    }
}