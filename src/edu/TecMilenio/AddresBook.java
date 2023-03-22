package edu.TecMilenio;
import javax.management.InvalidApplicationException;
import java.io.*;
import java.util.*;
public class AddresBook {
    private static Map<String, String> AgendaTelefonica = null;
    private static BufferedReader Captura = new BufferedReader(new InputStreamReader(System.in));

    private static String ArchivoProcesar = "src/AgendaTelefonica.txt";

    public static void MenuAgenda() {
        String Opcion = "";
        boolean z = true;
        try {
            Cargar();
            do{

                System.out.println("1: Contactos");
                System.out.println("2: Anair un contacto"); //anair = añadir - lo pongo asi para que no salten los errores
                System.out.println("3: Elimina un contacto");
                System.out.println("esc: Regresar Menu Principal.");
                System.out.print("Elige una opcion: ");

                Opcion = Captura.readLine().toString().trim().toUpperCase();

                switch (Opcion) {
                    case "1":
                        Contactos();
                        break;
                    case "2":
                        CrearContacto();
                        break;
                    case "3":
                        EliminarContacto();
                        break;
                    case "esc":
                        z = false;
                        break;
                    default:
                        System.out.println("");
                        System.out.println("Error, elige una opcion correcta");
                        System.out.println("");
                        MenuAgenda();
                        break;
                }
                Opcion = "";
            } while (z);
        } catch (Exception ex) {
            System.out.println(" ¡¡ ERROR !! " + ex.getMessage());
        } finally {
        }
    }

    private static void Contactos(){
        try {
            System.out.println("\nContactos:");
            for (Map.Entry<String, String> item : AgendaTelefonica.entrySet()) {
                System.out.println("          " + item.getKey() + ", " + item.getValue());
            }
            System.out.print("\n\nPreciona ENTER para continuar. ");
            Captura.readLine();
        }
        catch (Exception ex){
            System.out.println(" ¡¡ ERROR !! " + ex.getMessage());
        }
        finally { }
    }
    private static void CrearContacto(){
        String Numero = "";
        String Nombre = "";
        try {
            System.out.println("\nAnair un contacto"); //anair = añadir lo pongo asi para que no salten los errores

            Numero = SolicitaNumero();
            System.out.print("Nombre:");
            Nombre = Captura.readLine().toString().trim();

            AgendaTelefonica.put(Numero, Nombre);

            Guardar();
            System.out.print("\nGuardado correctamente. \nPreciona ENTER");
            Captura.readLine();
        }
        catch (Exception ex){
            System.out.println(" ¡¡ ERROR !! " + ex.getMessage());
        }
        finally {
        }
    }
    private static void EliminarContacto(){
        String eliminarContacto = "";
        String opc = "";
        try {
            System.out.println("\nEliminar un contacto.");
            eliminarContacto = SolicitaNumero();

            if (ValidaNumero(eliminarContacto)) {
                AgendaTelefonica.remove(eliminarContacto);
                System.out.print("\nEliminado   \nPreciona ENTER para continuar. ");
                Captura.readLine();
            }
            else {
                System.out.print("\n ¡¡ ERROR !! \nEl telefono ingresado no existe, precione esc para salir o intente de nuevo ");
                opc = Captura.readLine().toString().trim().toUpperCase();
                switch (opc) {
                    case "esc":
                        break;
                    default:
                        EliminarContacto();
                        break;
                }
            }
        }
        catch (Exception ex){
            System.out.println(" ¡¡ ERROR !! " + ex.getMessage());
        }
        finally {
        }
    }

    private static void Cargar(){
        BufferedReader Agenda = null;
        String Linea;
        try {
            AgendaTelefonica = new HashMap<>();
            Agenda = new BufferedReader(new FileReader(ArchivoProcesar));

            while ((Linea = Agenda.readLine()) != null) {
                String[] Dato = null;
                if (Linea != ""){
                    Dato = Linea.split(",");
                    AgendaTelefonica.put(Dato[0].trim(), Dato[1].trim());
                }
            }
        }
        catch (Exception ex){
            System.out.println(" ¡¡ ERROR !! " + ex.getMessage());
        }
        finally {
            try {
                if (Agenda != null) Agenda.close();
            }
            catch (IOException ex) { System.out.println(" ¡¡ ERROR !! " + ex.getMessage()); }
        }
    }
    private static void Guardar(){
        BufferedWriter Agenda = null;
        String Linea;
        try {
            Agenda = new BufferedWriter(new FileWriter(ArchivoProcesar));

            for (Map.Entry<String, String> item : AgendaTelefonica.entrySet()) {
                Agenda.write(item.getKey() + "," + item.getValue() + "\n");
            }
        }
        catch (Exception ex){
            System.out.println(" ¡¡ ERROR !! " + ex.getMessage());
        }
        finally {
            try {
                if (Agenda != null) Agenda.close();
            }
            catch (IOException ex) { System.out.println(" ¡¡ ERROR !! " + ex.getMessage()); }
        }
    }

    private  static String SolicitaNumero() {
        String Resultado = "";
        try {
            System.out.print("Numero: ");
            Resultado = Captura.readLine().toString().trim();

            Long.parseLong(Resultado);
            if (Resultado.length() != 10) throw new InvalidApplicationException("Teléfono incorrecto");

        }
        catch (NumberFormatException nfe){
            System.out.println("El telefono ingresado es incorrecto, solo se pueden ingresar 10 numeros");
            Resultado = SolicitaNumero();
        }
        catch (InvalidApplicationException ex){
            System.out.println("El telefono ingresado es incorrecto, solo se pueden ingresar 10 numeros");
            Resultado = SolicitaNumero();
        }
        catch (Exception ex){
            System.out.println(" ¡¡ ERROR !! " + ex.getMessage());
        }
        finally { }
        return Resultado;
    }
    private static Boolean ValidaNumero(String sTelefono){
        Boolean Resultado = false;
        for (Map.Entry<String, String> item : AgendaTelefonica.entrySet()) {
            if (item.getKey().equals(sTelefono)) {
                Resultado = true;
                break;
            }
        }
        return Resultado;
    }
}
