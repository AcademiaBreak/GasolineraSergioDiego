package com.academiabreak.principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

public class Gasolinera {
	private static Surtidor[] surtidores;
	private static Hashtable<String, Socio> socios;
	private static BufferedReader in;

	public static void main(String args[]) {
		in = new BufferedReader(new InputStreamReader(System.in));
		surtidores = new Surtidor[pedirSurtidor()];
		socios = new Hashtable<String, Socio>();

		menuPrincipal();
	}

	private static void menuPrincipal() {
		String opcion = "";
		boolean salir = false;

		while(!salir) {
			Utilidades.limpiarPantalla();
			System.out.println("1. Gestion Clientes.");
			System.out.println("2. Atencion Clientes.");
			System.out.println("3. Salir.");
			System.out.print("\tOpcion: ");
			try {
				opcion = in.readLine();
				if(!Utilidades.esOpcionValida(opcion, 1, 3)) {
					System.out.print("Opcion invalida.");
					Utilidades.pulsaIntro();
				} else if(Integer.parseInt(opcion) == 3) {
					salir = true;
				} else {
					realizarAccionMenuPrincipal(Integer.parseInt(opcion));
				}
			} catch(IOException ioe) {
				System.out.println("Error al leer de teclado...");
			}
		}
	}

	private static void realizarAccionMenuPrincipal(int opc) {
		switch(opc) {
		case 1:
			gestionClientes();
			break;
		case 2:
			// TODO: Atencion Clientes
			break;
		}
	}

	private static void gestionClientes() {
		String opcion = "";
		boolean salir = false;

		while(!salir) {
			Utilidades.limpiarPantalla();
			System.out.println("1. Alta Cliente.");
			System.out.println("2. Baja Cliente.");
			System.out.println("3. Ingreso Saldo Cliente.");
			System.out.println("4. Alta Vehiculo Cliente.");
			System.out.println("5. Baja Vehiculo Cliente.");
			System.out.println("6. Salir.");
			System.out.print("\tOpcion: ");
			try {
				opcion = in.readLine();
				if(!Utilidades.esOpcionValida(opcion, 1, 6)) {
					System.out.print("Opcion invalida. ");
					Utilidades.pulsaIntro();
				} else if(Integer.parseInt(opcion) == 6) {
					salir = true;
				} else {
					realizarAccionGestionClientes(Integer.parseInt(opcion));
				}
			} catch(IOException ioe) {
				System.out.println("Error al leer de teclado...");
			}
		}
	}

	private static void realizarAccionGestionClientes(int opc) {
		switch(opc) {
		case 1:
			altaCliente();
			break;
		case 2:
			bajaCliente();
			break;
		case 3:
			saldoCliente();
			break;
		case 4:
			altaVehiculo();
			break;
		case 5:
			bajaVehiculo();
			break;
		}
	}

	private static void altaCliente() {
		Socio soc = new Socio();
		String cad = "";

		try {
			Utilidades.limpiarPantalla();
			System.out.print("Introduce DNI: ");
			cad = in.readLine();
			if(Utilidades.esDni(cad)) {
				soc.setDni(cad);
				System.out.print("Introduce nombre: ");
				soc.setNombre(in.readLine());
				System.out.print("Introduce apellido: ");
				soc.setApellidos(in.readLine());
				System.out.print("Introduce direccion: ");
				soc.setDireccion(in.readLine());
				// TODO: Revisar aqui no pide saldo
				System.out.print("Introduce saldo: ");
				cad = in.readLine();
				if(Utilidades.esDecimal(cad)) {
					soc.setSaldo(Double.parseDouble(cad));
					socios.put(soc.getDni(), soc);
					System.out.println("*** Se procede a dar de alta al cliente:");
					System.out.println("\t" + soc.getNombre() + " con DNI: " + soc.getDni());
					System.out.println();
				} else {
					System.out.print("Saldo invalido. ");
				}
			} else {
				System.out.print("DNI invalido. ");
			}
			Utilidades.pulsaIntro();
		} catch(IOException ioe) {
			System.out.println("Error al leer de teclado...");
		}

	}

	private static void bajaCliente() {

	}

	private static void saldoCliente() {
		String cad = "";
		String dni = "";
		Socio soc = null;

		Utilidades.limpiarPantalla();
		try {
			System.out.print("Introduzca DNI de socio para meter saldo: ");
			dni = in.readLine();
			if(Utilidades.esDni(dni)) {
				if(socios.containsKey(dni)) {
					System.out.print("Introduzca el saldo a ingresar: ");
					cad = in.readLine();
					if(Utilidades.esDecimal(cad)) {
						soc = socios.get(dni);
						soc.ingresarSaldo(Double.parseDouble(cad));
						System.out.println("*** Se procede a incrementar el saldo del cliente:");
						System.out.println(soc.getNombre() + " con DNI: " + dni + " en " + cad);
						System.out.println();
					} else {
						System.out.print("Saldo introducido no valido. ");
					}
				} else {
					System.out.print("No existe el socio introducido. ");
				}
			} else {
				System.out.print("DNI introducido no valido. ");
			}
			Utilidades.pulsaIntro();
		} catch(IOException ioe) {
			System.out.print("Error al leer de teclado");

		}

	}

	private static void altaVehiculo() {
		// TODO: implementar altaVehiculo
	}

	private static void bajaVehiculo() {
		// TODO: implementar bajaVehiculo
	}

	private static int pedirSurtidor() {
		String cad = "";

		// TODO: poner logo
		while(!Utilidades.esEntero(cad)) {
			System.out.print("Introduce el surtidor: ");
			try {
				cad = in.readLine();
			} catch(IOException ioe) {
				System.out.println("Error al leer de teclado...");
			}
		}

		return Integer.parseInt(cad);
	}

}
