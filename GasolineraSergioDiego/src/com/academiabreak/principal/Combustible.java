package com.academiabreak.principal;

public enum Combustible {
	DIESEL,
	GASOLINA,
	ELECTRICO; 
	
	public static Combustible getCombustibleByNum(String num) {
		Combustible com; 
		com = null;
		
		switch(num) {
		case "1":
			com = DIESEL;
			break;
		case "2":
			com = GASOLINA;
			break;
		case "3":
			com = ELECTRICO;
			break;
		}
		
		return com; 
	}
}
