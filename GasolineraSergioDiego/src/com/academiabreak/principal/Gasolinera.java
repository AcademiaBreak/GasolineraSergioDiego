package com.academiabreak.principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;

public class Gasolinera {
	private static Surtidor[] surtidores;
	private static Hashtable<String, Socio> socios;
	private static BufferedReader in;

	public static void main(String args[]) {
		in = new BufferedReader(new InputStreamReader(System.in));
		surtidores = new Surtidor[pedirSurtidor()];
		socios = new Hashtable<String, Socio>();

		crearSurtidores();
		menuPrincipal();
	}

	private static void crearSurtidores() {
		for(int i = 0; i < surtidores.length; i++) {
			surtidores[i] = new Surtidor(i+1);
		}
	}

	private static void menuPrincipal() {
		String opcion = "";
		boolean salir = false;

		while(!salir) {
			Utilidades.limpiarPantalla();
			Utilidades.imprimirCabecera();
			System.out.println("1. Gestion Clientes.");
			System.out.println("2. Atencion Clientes.");
			System.out.println("3. Salir.");
			System.out.print("\tOpcion: ");
			try {
				opcion = in.readLine();
				if(!Utilidades.esOpcionValida(opcion, 1, 3)) {
					System.out.println("");
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

	private static void realizarAccionMenuPrincipal(int opc) throws IOException {
		switch(opc) {
		case 1:
			gestionClientes();
			break;
		case 2:
			atenderClientes();
			break;
		}
	}

	private static void atenderClientes() {
		String cad;
		int opc;
		boolean salir = false; 
		
		while(!salir) {
			Utilidades.limpiarPantalla();
			Utilidades.imprimirCabecera();
			System.out.println("1. Recibir vehiculo. ");
			System.out.println("2. Atender vehiculo. ");
			System.out.println("3. Ver Ocupacion Surtidores. ");
			System.out.println("4. Salir");
			System.out.print("\t OPCION: ");
			try {
				cad = in.readLine();
	
				if(Utilidades.esOpcionValida(cad, 1, 4)) {
					opc = Integer.parseInt(cad);
					if(opc == 4) {
						salir = true; 
					} else {
						realizarAccionAtencionCliente(opc);
					}
				} else {
					System.out.println();
					System.out.println("Opcion Invalida");
					Utilidades.pulsaIntro();
				}
			} catch(IOException ioe) {
				System.out.println("Error al leer de teclado...");
			}
		}
	}

	private static boolean hayVehiculosEnCola() {
		boolean hayVehiculos = false;
		int i = 0;

		while(!hayVehiculos && i < surtidores.length) {
			if(surtidores[i].getTamanio() > 0) {
				hayVehiculos = true;
			} else {
				i++;
			}
		}

		return hayVehiculos;
	}

	private static void atenderVehiculo() {
		String cantidad;
		double cant;
		Surtidor sur = obtenerMayorSurtidor();
		Vehiculo v = sur.atender();
		Socio soc = getDuenio(v.getMatricula());

		Utilidades.limpiarPantalla();
		Utilidades.imprimirCabecera();
		try {
			System.out.print("Introduzca cuanto quiera repostar: ");
			cantidad = in.readLine();
			if(Utilidades.esDecimal(cantidad)) {
				cant = Double.parseDouble(cantidad);
				if(cant <= soc.getSaldo()) {
					soc.retirarSaldo(cant);
					System.out.println("Gracias por elegirnos,esperamos volver a verle pronto. ");
				} else {
					System.out.println("Saldo insuficiente. Remueva el vehiculo del surtidor. ");
				}
			} else {
				System.out.println("Cantidad no valida. ");
			}
			Utilidades.pulsaIntro();
		} catch(IOException ioe) {
			System.out.println("Error al leer de teclado...");
		}
	}


	private static void realizarAccionAtencionCliente(int opc) throws IOException {
		switch(opc) {
		case 1:
			recibirVehiculo();
			break;
		case 2:
			if(hayVehiculosEnCola()) {
				atenderVehiculo();
			} else {
				System.out.println("");
				System.out.println("No hay vehiculos esperando... ");
				Utilidades.pulsaIntro();
			}
			break;
		case 3:
			verSurtidores();
		}
	}

	private static void verSurtidores() throws IOException {
		Utilidades.limpiarPantalla();
		Utilidades.imprimirCabecera();

		for(int i = 0; i < surtidores.length; i++) {
			System.out.println("Surtidores " + surtidores[i].getId() + ": " + surtidores[i].getTamanio());

		}
		Utilidades.pulsaIntro();

	}

	private static Socio getDuenio(String matricula) {
		Enumeration<String> claves = socios.keys();
		boolean encontrado = false;
		Socio soc = null;

		while(claves.hasMoreElements() && !encontrado) {
			soc = socios.get(claves.nextElement());
			if(soc.estaVehiculo(matricula)) {
				encontrado = true;
			}
		}

		return soc;
	}

	private static Surtidor obtenerMayorSurtidor() {
		Surtidor sur = null;

		if(surtidores.length > 0) {
			sur = surtidores[0];
			for(int i = 1; i < surtidores.length; i++) {
				if(sur.getTamanio() < surtidores[i].getTamanio()) {
					sur = surtidores[i];
				}
			}
		}
		
		return sur;
	}

	private static void recibirVehiculo() {
		String matricula = "";
		Surtidor surt = null;
		Vehiculo vc;

		try {
			Utilidades.limpiarPantalla(); 
			Utilidades.imprimirCabecera();
			System.out.print("Introduzca la matricula: ");
			matricula = in.readLine();
			surt = obtenerSurtidorMenosLleno();
			vc = obtenerVehiculo(matricula);
			if(vc != null) {
				if(!estaVehiculoCola(vc)) {
					surt.insertar(vc);
					System.out.println("Se procede a introducir al vehículo en el surtidor: ");
					System.out.println(matricula + " se introduce en la cola del surtidor: " + surt.getId());
				} else {
					System.out.println("Este vehiculo ya esta en un surtidor. ");
				}
			} else {
				System.out.println("Esa matricula no se corresponde con ningun vehiculo introducido. ");
			}
			Utilidades.pulsaIntro();
		} catch(IOException ioe) {
			System.out.println("Error al leer de teclado.. ");
		}
	}

	private static Vehiculo obtenerVehiculo(String matricula) {
		Vehiculo vc = null;
		Enumeration<String> dnis = socios.keys();
		String dni;
		Socio soc;
		boolean encontrado = false;

		while(dnis.hasMoreElements() && !encontrado) {
			dni = (String)dnis.nextElement();
			soc = socios.get(dni);

			if(soc.estaVehiculo(matricula)) {
				vc = soc.getVehiculo(matricula);
				encontrado = true;
			}
		}
		return vc;
	}

	private static Surtidor obtenerSurtidorMenosLleno() {
		Surtidor surt = surtidores[0];

		for(int i = 1; i < surtidores.length; i++) {
			if(surt.getTamanio() > surtidores[i].getTamanio()) {
				surt = surtidores[i];
			}
		}
		return surt;
	}

	private static boolean estaVehiculoCola(Vehiculo vc) {
		boolean encontrado = false;
		int i = 0;

		while(!encontrado && i < surtidores.length) {
			if(surtidores[i].estaVehiculo(vc)) {
				encontrado = true;
			} else {
				i++;
			}
		}

		return encontrado;
	}

	private static boolean estaVehiculoCola(Hashtable<String, Vehiculo> listaVehiculos) {
		boolean encontrado = false;
		int i = 0;
		Vehiculo vc;
		Enumeration<String> keys = listaVehiculos.keys();

		while(keys.hasMoreElements()) {
			vc = listaVehiculos.get(keys.nextElement());
			while(!encontrado && i < surtidores.length) {
				if(surtidores[i].estaVehiculo(vc)) {
					encontrado = true;
				} else {
					i++;
				}
			}
		}

		return encontrado;
	}

	private static void gestionClientes() {
		String opcion = "";
		boolean salir = false;

		while(!salir) {
			Utilidades.limpiarPantalla();
			Utilidades.imprimirCabecera();
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

	private static void realizarAccionGestionClientes(int opc) throws IOException {
		switch(opc) {
		case 1:
			altaCliente();
			break;
		case 2:
			if(!socios.isEmpty()) {
				bajaCliente();
			} else {
				System.out.println("No hay socios para dar de baja. ");
				Utilidades.pulsaIntro();
			}
			break;
		case 3:
			if(!socios.isEmpty()) {
				saldoCliente();
			} else {
				System.out.println("No hay socios para introducir saldo. ");
				Utilidades.pulsaIntro();
			}
			break;
		case 4:
			if(!socios.isEmpty()) {
				altaVehiculo();
			} else {
				System.out.println("No hay socios para introducir vehiculos. ");
				Utilidades.pulsaIntro();
			}
			break;
		case 5:
			if(!socios.isEmpty()) {
				bajaVehiculo();
			} else {
				System.out.println("No hay socios para dar de baja vehiculos. ");
				Utilidades.pulsaIntro();
			}
			break;
		}
	}

	private static void altaCliente() {
		Socio soc = new Socio();
		String cad = "";

		try {
			Utilidades.limpiarPantalla();
			Utilidades.imprimirCabecera();
			System.out.print("Introduce DNI: ");
			cad = in.readLine();
			if(Utilidades.esDni(cad)) {
				cad = cad.toUpperCase();
				if(!socios.containsKey(cad)) {
					soc.setDni(cad);
					System.out.print("Introduce nombre: ");
					soc.setNombre(in.readLine());
					System.out.print("Introduce apellido: ");
					soc.setApellidos(in.readLine());
					System.out.print("Introduce direccion: ");
					soc.setDireccion(in.readLine());
					socios.put(soc.getDni(), soc);
					System.out.println("");
					System.out.println("*** Se procede a dar de alta al cliente:");
					System.out.println("\t" + soc.getNombre() + " con DNI: " + soc.getDni());
				} else {
					System.out.println("Este DNI ya esta registrado. ");
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
		String cad = "";
		Socio soc;

		Utilidades.limpiarPantalla();
		Utilidades.imprimirCabecera();
		try {
			System.out.print("Introduzca el DNI del cliente que desea eliminar: ");
			cad = in.readLine();
			if(Utilidades.esDni(cad)) {
				cad = cad.toUpperCase(); 
				if(socios.containsKey(cad)) {
					soc = socios.get(cad);
					if(!estaVehiculoCola(soc.getVehiculos())) {
						socios.remove(cad);
						System.out.println("***Se procede a dar de baja al cliente:");
						System.out.println("\t" + soc.getNombre() + " con DNI: " + cad);
					} else {
						System.out.println("\t" + soc.getNombre() + " con DNI: " + cad + "tiene vehiculos en cola. ");
					}
				} else {
					System.out.print("\nIntroducido cliente no existente. ");
				}
			} else {
				System.out.print("\nDNI invalido.");
			}
			Utilidades.pulsaIntro();
		} catch(IOException ioe) {
			System.out.print("Error al leer por teclado: ");
		}

	}
	
	private static void saldoCliente() {
		String cad = "";
		String dni = "";
		Socio soc = null;

		Utilidades.limpiarPantalla();
		Utilidades.imprimirCabecera();
		try {
			System.out.print("Introduzca DNI de socio para meter saldo: ");
			dni = in.readLine();
			if(Utilidades.esDni(dni)) {
				dni = dni.toUpperCase();
				if(socios.containsKey(dni)) {
					System.out.print("Introduzca el saldo a ingresar: ");
					cad = in.readLine();
					if(Utilidades.esDecimal(cad)) {
						soc = socios.get(dni);
						soc.ingresarSaldo(Double.parseDouble(cad));
						System.out.println("");
						System.out.println("*** Se procede a incrementar el saldo del cliente:");
						System.out.println(soc.getNombre() + " con DNI: " + dni + " en " + cad);
					} else {
						System.out.print("\nSaldo introducido no valido. ");
					}
				} else {
					System.out.print("\nNo existe el socio introducido. ");
				}
			} else {
				System.out.print("\nDNI introducido no valido. ");
			}
			Utilidades.pulsaIntro();
		} catch(IOException ioe) {
			System.out.print("Error al leer de teclado");
		}
	}

	private static void altaVehiculo() {
		String dni = "";
		Socio soc = null;
		Vehiculo v;

		Utilidades.limpiarPantalla();
		Utilidades.imprimirCabecera();
		try {
			System.out.print("Introduzca DNI de socio: ");
			dni = in.readLine();
			if(Utilidades.esDni(dni)) {
				dni = dni.toUpperCase(); 
				if(socios.containsKey(dni)) {
					soc = socios.get(dni);
					v = crearVehiculo(soc);

					if(v != null) {
						soc.insertarVehiculo(v);
						System.out.println("");
						System.out.println("***Se procede a dar de alta el vehículo del cliente:");
						System.out.println(soc.getNombre() + " con DNI: " + soc.getDni() + " Vehículo con matrícula: "
								+ v.getMatricula());
					}
				} else {
					System.out.print("\nNo existe el socio introducido. ");
				}
			} else {
				System.out.print("\nDNI introducido no valido. ");
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
		Utilidades.limpiarPantalla();
		Utilidades.imprimirCabecera();
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
				System.out.println("");
				switch(opcion) {
				case "1":
					vehiculo = crearCoche(matricula, marca);
					break;
				case "2":
					vehiculo = crearMoto(matricula, marca);
					break;
				default:
					System.out.println("Opcion no valida... ");
				}
			} else {
				System.out.println("El vehiculo ya existe. ");
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

		System.out.print("Introduce modelo: ");
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
		
		System.out.print("Introduce la cilindrada: ");
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
		String dni = "";
		Socio soc;
		String mat = "";

		Utilidades.limpiarPantalla();
		Utilidades.imprimirCabecera();
		try {
			System.out.print("Introduzca el DNI del cliente: ");
			dni = in.readLine();
			if(Utilidades.esDni(dni)) {
				dni = dni.toUpperCase(); 
				if(socios.containsKey(dni)) {
					soc = socios.get(dni);
					System.out.print("Introduce la matricula: ");
					mat = in.readLine();
					if(soc.estaVehiculo(mat)) {
						if(!estaVehiculoCola(soc.getVehiculo(mat))) {
							socios.remove(dni);
							System.out.println("");
							System.out.println("***Se procede a dar de baja al vehiculo del cliente :");
							System.out.println("\t" + soc.getNombre() + " con DNI: " + dni + " matricula vehículo: "
									+ mat);
						} else {
							System.out.println("\t" + soc.getNombre() + " con DNI: " + dni
									+ " tiene este vehiculo en cola. ");
						}
					} else {
						System.out.print("La matricula " + mat + " no pertenecce al socio con DNI " + dni);
					}
				} else {
					System.out.print("Introducido cliente no existente. ");
				}
			} else {
				System.out.print("DNI invalido. ");
			}
			Utilidades.pulsaIntro();
		} catch(IOException ioe) {
			System.out.print("Error al leer por teclado: ");
		}

	}

	private static int pedirSurtidor() {
		String cad = "";

		Utilidades.limpiarPantalla();
		Utilidades.imprimirCabecera();
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
