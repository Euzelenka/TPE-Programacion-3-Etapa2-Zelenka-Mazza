
import java.io.*;
import java.util.ArrayList;

public class CentroDeOperaciones {

	ListaArreglo usuarios;

	public CentroDeOperaciones(){
		usuarios = new ListaArreglo();
	}

	// Carga los usuarios en los cuales se va a trabajar
	public void precarga(String ruta) {
		String csvFile = ruta;
		String line = "";
		String cvsSplitBy = ";";
		String[] items = null;

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			Usuario u;
			int id;
			
			br.readLine(); //PARA EVITAR TOMAR LA PRIMER LINEA DEL ARCHIVO

			while ((line = br.readLine()) != null) {

				items = line.split(cvsSplitBy);
				id = Integer.parseInt(items[0]);
				u = new Usuario(id);

				for (int i = 1; i < items.length; i++) {
					u.agregarGusto(items[i]);
				}
				usuarios.agregar(u);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void insertar(String ruta, int cantPrecarga){

		// Estas variables son para registrar el tiempo
		long tiempoGeneralInicial = System.nanoTime();
		long tiempoGeneralFinal;
		long tiempoInicial;
		long tiempoFinal;
		long tiempoTotal;

		String csvFile = ruta; //LA RUTA DE LOS ARCHIVOS A INGRESAR SE RECIBE POR PARÁMETRO
		String line = "";
		String cvsSplitBy = ";";
		String[] items = null; 

		// Este arrayList de Arreglo de String se va usar para guardar el id del usuario y para registrar el tiempo
		// que se tardo en isertar ese usuario.
		ArrayList<String[]> listaSalida = new ArrayList<String[]>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			Usuario u;
			int id;
			

			while ((line = br.readLine()) != null) {
				

				String[] metricas = new String[2]; // Arreglo con 2 posiciones, una para id, otra para tiempo que se tardo

				items = line.split(cvsSplitBy);
				metricas[0] = items[0];  // Se le asigna el id en la primera posicion

				tiempoInicial = System.nanoTime();	            	 
				id = Integer.parseInt(items[0]);
				u = new Usuario(id);

				for (int i = 1; i < items.length; i++) {
					u.agregarGusto(items[i]);
				}
				usuarios.agregar(u);
				
				tiempoFinal = System.nanoTime();

				tiempoTotal =  tiempoFinal- tiempoInicial;	
				metricas[1] = Long.toString(tiempoTotal);
				listaSalida.add(metricas);
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		usuarios.ordenar();
		tiempoGeneralFinal = System.nanoTime() - tiempoGeneralInicial;
		salidaInsertar(listaSalida, cantPrecarga, tiempoGeneralFinal);			
	}

	public void salidaInsertar(ArrayList<String[] > listaSalida, int cantPrecarga, long tiempoGeneralFinal){


		BufferedWriter bw = null;
		try {
			File file = new File("C:/Users/Eloy/Desktop/TpEspecialProgra3Zelenka-Mazza/salida-app1/salidaInsertarEn"+ cantPrecarga + ".csv");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			String[]usuarioActual = new String[2];

			for (int i = 0; i < listaSalida.size(); i++) {
				usuarioActual = listaSalida.get(i);
				String contenidoLineal = usuarioActual[0] + ";" + usuarioActual[1];
				bw.write(contenidoLineal);
				bw.newLine();
			}
			
			bw.write("Tiempo total:" + ";"  + tiempoGeneralFinal);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (Exception ex) {
				System.out.println("Error cerrando el BufferedWriter" + ex);
			}
		}
	}


	public void busquedaUsuarios(String ruta, int cantPrecarga) {
		
		long tiempoGeneralInicial = System.nanoTime();
		long tiempoGeneralFinal;
		long tiempoInicial;
		long tiempoFinal;
		long tiempoTotal;
		
		String csvFile = ruta;
		String line = "";
		String cvsSplitBy = ";";
		String[] items = null;
						
		ArrayList<String[]> listaSalidaBusqueda = new ArrayList<String[]>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
				
			int id;

			while ((line = br.readLine()) != null) {


				String[] metricas = new String[3];

				items = line.split(cvsSplitBy);
				metricas[0] = items[0];

				tiempoInicial = System.nanoTime();	            	 
				id = Integer.parseInt(items[0]);

				String resultadoBusqueda = "No Existe";
				if(usuarios.existe(id))
					resultadoBusqueda = "Existe";

				tiempoFinal = System.nanoTime() - tiempoInicial;
				tiempoTotal =  tiempoFinal- tiempoInicial;	

				metricas[1] = Long.toString(tiempoTotal);
				metricas[2] = resultadoBusqueda;
				listaSalidaBusqueda.add(metricas);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		tiempoGeneralFinal = System.nanoTime() - tiempoGeneralInicial;
		salidaBusqueda(listaSalidaBusqueda, cantPrecarga, tiempoGeneralFinal);

	}

	public void salidaBusqueda(ArrayList<String[] > listaSalidaBusqueda, int cantPrecarga, long tiempoGeneralFinal) {
		BufferedWriter bw = null;
		try {
			File file = new File("C:/Users/Eloy/Desktop/TpEspecialProgra3Zelenka-Mazza/salida-app1/salidaBusquedaEn"+ cantPrecarga + ".csv");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			String[]usuarioActual = new String[3];

			for (int i = 0; i < listaSalidaBusqueda.size(); i++) {
				// Escribo la primer linea del archivo
				usuarioActual = listaSalidaBusqueda.get(i);
				String contenidoLineal = usuarioActual[0] + ";" + usuarioActual[1] + ";" + usuarioActual[2];
				bw.write(contenidoLineal);
				bw.newLine();
			}
			
			bw.write("Tiempo total:" + ";"  + tiempoGeneralFinal);


		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (Exception ex) {
				System.out.println("Error cerrando el BufferedWriter" + ex);
			}
		}
	}

	public static void main(String[] args) {

		CentroDeOperaciones centro = new CentroDeOperaciones();

		//centro.precarga("C:/Users/Eloy/Desktop/datasets/dataset_3000000.csv");
		//centro.insertar("C:/Users/Eloy/Desktop/datasets/dataset_insert_10000.csv", 2300000);
		//centro.busquedaUsuarios("C:/Users/Eloy/Desktop/datasets/dataset_busqueda_10000.csv", 2300000);

		//centro.precarga("C:/Users/Eloy/Desktop/datasets/dataset_1000000.csv");
		//centro.busquedaUsuarios("C:/Users/Eloy/Desktop/datasets/dataset_insert_10000.csv", 1000000);
		//centro.insertar("C:/Users/Eloy/Desktop/datasets/dataset_busqueda_10000.csv", 1000000);

		centro.precarga("C:/Users/Euyi/Desktop/datasets/dataset_500000.csv");
		centro.insertar("C:/Users/Euyi/Desktop/datasets/dataset_insert_10000.csv", 500000);
		//centro.busquedaUsuarios("C:/Users/Eloy/Desktop/datasets/dataset_busqueda_10000.csv", 500000);
		
		/*ListaArreglo usuarios = new ListaArreglo();
		Usuario u1 = new Usuario(1);
		Usuario u2 = new Usuario(16);
		Usuario u3 = new Usuario(12);
		Usuario u4 = new Usuario(19);
		Usuario u5 = new Usuario(10);
		Usuario u6 = new Usuario(11);
		Usuario u7 = new Usuario(13);
		usuarios.agregar(u7);
		usuarios.agregar(u6);
		usuarios.agregar(u5);
		usuarios.agregar(u4);
		usuarios.agregar(u3);
		usuarios.agregar(u2);
		usuarios.agregar(u1);
		usuarios.ordenar(); */
	}

}
