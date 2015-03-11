package com.academiabreak.principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utilidades {

	public static boolean esOpcionValida(String opc, int min, int max) {
		boolean esValida = false;

		if(esEntero(opc) && Integer.parseInt(opc) >= min && Integer.parseInt(opc) <= max) {
			esValida = true;
		}

		return esValida;
	}

	public static void imprimirCabecera() {
		System.out.println("***********************************************");
		System.out.println("*                                             *");
		System.out.println("*                 GASODAM                     *");
		System.out.println("*           Versi�n Alpha 1.0.0               *");
		System.out.println("*                                             *");
		System.out.println("*                                  (c) 1� DAM *");
		System.out.println("***********************************************");
		System.out.println();
	}

	public static boolean esEntero(String cad) {
		boolean esEntero = !cad.isEmpty();
		int i = 0;

		while(i < cad.length() && esEntero) {
			if(Character.isDigit(cad.charAt(i))) {
				i++;
			} else {
				esEntero = false;
			}
		}

		return esEntero;
	}

	public static boolean esDecimal(String cad) {
		boolean esDecimal = !cad.isEmpty();
		int i = 0;
		int puntos = 0;

		while(i < cad.length() && esDecimal) {
			if(Character.isDigit(cad.charAt(i))) {
				i++;
			} else if(cad.charAt(i) == '.' && i != 0 && i != cad.length() && puntos < 1) {
				puntos++;
				i++;
			} else {
				esDecimal = false;
			}
		}

		return esDecimal;
	}

	public static boolean esDni(String cad) {
		String letrasDni = "TRWAGMYFPDXBNJZSQVHLCKE";
		boolean esDni = true;
		String cadDni = "";
		int i;

		if(cad.length() != 9 || !Character.isLetter(cad.charAt(cad.length() - 1))) {
			esDni = false;
		} else {
			i = 0;
			while(i < cad.length() - 1 && esDni) {
				if(Character.isDigit(cad.charAt(i))) {
					cadDni += cad.charAt(i);
					i++;
				} else {
					esDni = false;
				}
			}

			if(letrasDni.charAt(Integer.parseInt(cadDni) % 23) != Character.toUpperCase(cad.charAt(cad.length() - 1))) {
				esDni = false;
			}
		}

		return esDni;
	}

	public static void limpiarPantalla() {
		for(int i = 0; i < 30; i++) {
			System.out.println("");
		}
	}

	public static void pulsaIntro() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("\n *** Pulsa intro para continuar. ");
		in.readLine();
	}
}
