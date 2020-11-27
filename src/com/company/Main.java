package com.company;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import javax.crypto.BadPaddingException;

public class Main {

    public static void main(String[] args) throws IOException {
        ej5();
        ej6();
        ej8();
        ejercici2();

    }
    //---------------- Exercicio 1 -------------------
    //Apartados 1.5 , 1.6 , 1.7, 1.8
    public static void ej5(){
        int keySize = 128;

        SecretKey secretKey = Xifrar.keygenKeyGeneration(keySize);
        String text = "texto fanstasma";
        //Encriptar
        byte[] txtEncriptado = Xifrar.encryptData(secretKey, text.getBytes());

        //Desencriptar
        byte[] txtDesencriptado = Xifrar.decryptData(secretKey, txtEncriptado);
        System.out.println("------------------ EJ(1.5) ---------------------");
        System.out.println("Texto encriptado! : " + new String(txtEncriptado));
        System.out.println("Texto desencriptado! : " + new String(txtDesencriptado));
        ej7SecretKey(secretKey);
        System.out.println();
    }
    public static void ej6(){
        int keySize = 128;
        String txtKey = "openUP";
        SecretKey secretKey = Xifrar.passwordKeyGeneration(txtKey, keySize);
        String textoEncrypt = "Texto OCULTO";

        //Encriptar
        byte[] txtEncriptado = Xifrar.encryptData(secretKey, textoEncrypt.getBytes());

        //Desencriptar
        byte[] txtDesencriptado = Xifrar.decryptData(secretKey, txtEncriptado);
        System.out.println("------------------ EJ(1.6) ---------------------");
        System.out.println("Texto encriptado! : " + new String(txtEncriptado));
        System.out.println("Texto desencriptado! : " + new String(txtDesencriptado));
        ej7SecretKey(secretKey);
    }

    public static void ej7SecretKey(SecretKey secretKey){
        System.out.println("____________________________________________");
        System.out.println("1. Algorithm: " + secretKey.getAlgorithm());
        System.out.println("2. Format: " + secretKey.getFormat());
        System.out.println("3. HashCode: " + secretKey.hashCode());

        //Tambien existe el getEncoded
        byte encoded[] = secretKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(encoded);

        System.out.println("4. Encoded: " + encodedKey);
    }
    public static void ej8(){
        int sizeKey = 128;
        String txtKey = "openUP";
        System.out.println();
        System.out.println("------------------ EJ(1.8) ---------------------");
        SecretKey secretKey = Xifrar.passwordKeyGeneration(txtKey, sizeKey);
        String textoEncrypt = "Texto OCULTO";

        //Encriptar
        byte[] txtEncriptado = Xifrar.encryptData(secretKey, textoEncrypt.getBytes());

        //Cambiamos de secretKey ponemos la del ej6
        secretKey = Xifrar.keygenKeyGeneration(sizeKey);

        //Desencriptar
        byte[] txtDesencriptado = Xifrar.decryptData(secretKey, txtEncriptado);
        System.out.println("------------------------------------------------");

        //Si ha funcionado bien, nos saldrá un mensaje...
    }

    // --------------- ¡ NOTA ! -----------------
    //Debugeando el codigo de este ejercicio se podra comprender a la perfeción cual es su funcionamiento. (captura en mi documento)
    public static void ejercici2() throws IOException {

        //Primero de leeremos el fichero
        File fileKeys = new File("C:\\Users\\Elvis\\Desktop\\M03\\clausA4.txt");
        File fileTextAmagat = new File("C:\\Users\\Elvis\\Desktop\\M03\\textamagat");

        //Para poder leer un fichero binario (textamagat) utilizamos la clase Path
        //Y le indicamos el fichero binario -> (fileTextAmagat)
        Path path = Paths.get(String.valueOf(fileTextAmagat));

        // Contendrá el contenido del fichero que ha leido el path
        byte[] txtEncrypt = Files.readAllBytes(path);
        byte[] txtDecrypt = null;

        //Ahora LEEMOS el fichero donde se encuentran las claves -> (clausA4.txt)
        FileReader fileReader = new FileReader(fileKeys);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        //leer lineas del fichero
        String linea = bufferedReader.readLine();

        System.out.println();
        System.out.println("--------- EJERCICIO 2 OUTPUT -----------");

        //Mientras haya una linea...
        while (linea != null){
            try {
                SecretKey secretKey = Xifrar.passwordKeyGeneration(linea,128);
                txtDecrypt = Xifrar.decryptData(secretKey,txtEncrypt);

                //Como convertir de byte[] a String
                //UTF8: la codificación
                System.out.println(new String(txtDecrypt, "UTF8"));
                //------- OBSERVACIÓN! --------
                //Cuando encontramos la contraseña correcta nos genera un texto "No te despedirán del trabajo, si ..."
                //Este texto se ejecuta en bucle, con un break paramos el proceso y termina el programa.
                break;
                //En StackOverflow recomiendan utilizar el catch (utilizar la exepción para entonces, escribir por pantalla "Clave incorrecta" y leer una nueva linea del fichero.)
            } catch (Exception e) {
                System.out.println("La contraseña no es: "+ linea);
                linea = bufferedReader.readLine();
            }
        }
        //Hemos encontrado la contraseña
        System.out.println("La contraseña es: " + linea + "\n ¡ Felicidades lo has conseguido !" );
    }
}
