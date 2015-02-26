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
				socios.put(soc.getDni(), soc);
				System.out.println("*** Se procede a dar de alta al cliente:");
				System.out.println("\t" + soc.getNombre() + " con DNI: " + soc.getDni());
				System.out.println();
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
		String dni = "";
		String cad = "";
		Socio soc = null;
		Vehiculo v;

		Utilidades.limpiarPantalla();
		try {
			System.out.print("Introduzca DNI de socio para meter saldo: ");
			dni = in.readLine();
			if(Utilidades.esDni(dni)) {
				if(socios.containsKey(dni)) {
					soc = socios.get(dni);
					v = crearVehiculo(soc);

					if(v != null) {
						soc.insertarVehiculo(v);
						System.out.println("***Se procede a dar de alta el veh�culo del cliente:");
						System.out.println(soc.getNombre() + " con DNI: " + soc.getDni() + " Veh�culo con matr�cula: "
								+ v.getMatricula());
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

	private static Vehiculo crearVehiculo(Socio socio) {
		Vehiculo vehiculo = null;
		String matricula = "";
		String marca = "";
		String opcion = "";

		try {
			System.out.print("Introduzca matricula: ");
			matricula = in.readLine();
			if(!socio.estaVehiculo(matricula)) {
				System.out.print("Introduce la marca: ");
				marca = in.readLine();

				System.out.println("");
				System.out.println("1.Coche ");
				System.out.println("2.Moto ");
				System.out.print("\tElija tipo de vehiculo: ");
				opcion = in.readLine();
				switch(opcion) {
				case "1":
					vehiculo = crearCoche(matricula, marca);
					break;
				case "2":
					vehiculo = crearMoto(matricula, marca);
					break;
				default:
					System.out.println("Opcion no valida... ");
					Utilidades.pulsaIntro();
				}
			} else {
				System.out.println("El vehiculo ya existe. ");
				Utilidades.pulsaIntro();
			}
		} catch(IOException ioe) {
			System.out.print("Error al leer de teclado");
		}

		return vehiculo;
	}

	private static Coche crearCoche(String matricula, String marca) throws IOException {
		String modelo = "";
		Coche coche = null;
		String opcion = "";
		Combustible com;

		System.out.println("Introduce modelo: ");
		modelo = in.readLine();
		System.out.println("");
		System.out.println("1. DIESEL ");
		System.out.println("2. GASOLINA ");
		System.out.println("3. ELECTRICO ");
		System.out.print("\tElija tipo de carburante: ");
		opcion = in.readLine();
		com = Combustible.getCombustibleByNum(opcion);
		if(com != null) {
			coche = new Coche(matricula, marca, modelo, com);
		} else {
			System.out.println("Combustible invalido. ");
			Utilidades.pulsaIntro();
		}

		return coche;
	}

	private static Moto crearMoto(String matricula, String marca) throws IOException {
		Moto moto = null;
		String cc = "";
		int cilindrada = 0;

		System.out.println("Introduce la cilindrada: ");
		cc = in.readLine();
		if(Utilidades.esEntero(cc)) {
			cilindrada = Integer.parseInt(cc);
			moto = new Moto(matricula, marca, cilindrada);
		} else {
			System.out.println("Cilindrada no valida. ");
			Utilidades.pulsaIntro();
		}

		return moto;
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