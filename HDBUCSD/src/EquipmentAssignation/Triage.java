/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EquipmentAssignation;

/**
 *
 * @author Karen
 */
import java.util.Scanner;

public class Triage {
	public static void main(String[] args) {
		int prior = 0;
		Scanner reader = new Scanner(System.in);
		System.out.print("Bienvenido al sistema de evaluaciÃ³n parcial de urgencias.\n"
				+ "Por favor conteste con un 'si' o 'no' las siguientes preguntas,\n\t despuÃ©s oprima "
				+ "la tecla <enter> para ingresar su respuesta al sistema.\n"
				+ "Se le pide que conteste con la mayor exactitud posible para brindarle un mejor servicio.\n\n");
		System.out.print("Â¿Requiere de atenciÃ³n inmediata para salvar su vida? <enter>: ");
		String ans1 = reader.nextLine();
		
		switch(ans1) {
			case "si":
				prior = 1;
				break;
				
			case "no":
				System.out.print("\nÂ¿Se encuentra en una situaciÃ³n de alto riesgo donde se sienta "
						+ "desorientado, confundido, \n\t angustiado o con un dolor insoportable? <enter>: ");
				String ans2 = reader.nextLine();
				
				switch(ans2) {
					case "si":
						System.out.print("\nEn una escala del 1 al 10, Â¿CuÃ¡l es el nivel de dolor que siente? <enter>: ");
						int pain = reader.nextInt();
						
						if(pain >= 7) {
							prior = 2;
							break;
							
						} else {
							ans2 = "no";
						}
						
					case "no":
						System.out.print("\nÂ¿Considera que requiere alguno de los siguientes procedimientos?\n "
								+ "\t IntervenciÃ³n quirÃºrgica (quirofano)\n"
								+ "\t Prueba de sangre u orina\n"
								+ "\t Rayos X\n"
								+ "\t Alguno o varios medicamentos <enter>: ");
						String ans3 = reader.nextLine();
						
						switch(ans3) {
							case "si":
								prior = 3;
								break;
							case "no":
								prior = 4;
								break;
						}
				}
				
		}
		
		System.out.print(prior);
	}
}

