package app;

import java.util.Scanner;
import modelo.*;
import excepciones.*;
public class Main {
    public static void main(string[] args){
        Citylink citylink = new CityLink();
        citylink.precargardatos();

        Scanner scanner = new Scanner (System.in);
        boolean salir = false;

        System.out.println("--------------------------------");
        System.out.println("-----Bienvenido a Citylink-----");
        System.out.println("--------------------------------");

        ///Menu principal
         while(!salir){
            mostrarMenu();
            int opcion = leerEntero(scanner, "Seleccione una opcion (1-10):");
            switch(opcion){
                case 1:
                    System.out.println("\n--- 1. Registrar cliente---");
                    break;
                case 2:
                    System.out.println("\n--- 2. Registrar repartidor---");
                    break;
                case 3:
                    System.out.println("\n--- 3. Registrar pedido---");
                    break;
                case 4:
                    System.out.println("\n--- 4. Asignar pedido---");
                    break;
                case 5:
                    System.out.println("\n--- 5. Cambiar estado del pedido---");
                    break;
                case 6:
                    System.out.println("\n--- 6. Buscar cliente---");
                    break;
                case 7:
                    System.out.println("\n--- 7. Listar Pedidos (En construcción) ---");
                    break;
                case 8:
                    System.out.println("\n--- 8. Consultar Estado Rastreable (En construcción) ---");
                    break;
                case 9:
                    System.out.println("\n--- 9. Facturacion y Comisiones (En construcción) ---");
                    break;
                case 10:
                    System.out.println("\nGracias por utilizar CityLink. Hasta luego!");
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida. Debe ingresar un numero del 1 al 10.\n");
                    break;
            }
        }
        scanner.close();
            }
         }

    

