/**
 * @author: Gabriela Lissette Rodriguez Contreras
 *
 *
 *
 * Tema: Proyecto Consumir API NASA
*/
package uce.edu.ec.ProyectoAPI;

import uce.edu.ec.ProyectoAPI.View.Window;

import javax.swing.*;


public class ProyectoApiApplication {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Window();
			}
		});
	}

}
