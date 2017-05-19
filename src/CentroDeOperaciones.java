
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
			//int x = 0;
			//boolean limiteAlcanzado = false;
			
			br.readLine(); //PARA EVITAR TOMAR LA PRIMER LINEA DEL ARCHIVO

			while ((line = br.readLine()) != null ) {

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
		long tiempoInicial;
		long tiempoFinal;
		long tiempoTotal;

		String csvFile = ruta; // La ruta de los archivos a insertar se recibe por parametro
		String line = "";
		String cvsSplitBy = ";";
		String[] items = null; 

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			Usuario u;
			int id;
			
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
		tiempoInicial = System.nanoTime();
		
		usuarios.ordenar();
		
		tiempoFinal = System.nanoTime();
		tiempoTotal =  tiempoFinal - tiempoInicial;

		salidaOrdenar("Tiempo total en ordenar:"+tiempoTotal, cantPrecarga);			
	}

	public void salidaOrdenar(String tiempoTotal, int cantPrecarga){


		BufferedWriter bw = null;
		try {
			File file = new File("C:/Users/Euyi/Desktop/Datasets/salida-app1/salidaOrdenarEn"+ cantPrecarga + ".csv");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			bw.write(tiempoTotal);
			bw.newLine();			

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
			int x=0;
			while ((line = br.readLine()) != null) {
				x++;
				
					System.out.println(x);
				String[] metricas = new String[3];

				items = line.split(cvsSplitBy);
				metricas[0] = items[0];

				id = Integer.parseInt(items[0]);
				tiempoInicial = System.nanoTime();	            	 

				String resultadoBusqueda = "No Existe";
				if(usuarios.busquedaDyC(id))
					resultadoBusqueda = "Existe";

				tiempoFinal = System.nanoTime();
				tiempoTotal =  tiempoFinal- tiempoInicial;	

				metricas[1] = Long.toString(tiempoTotal);
				metricas[2] = resultadoBusqueda;
				listaSalidaBusqueda.add(metricas);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		salidaBusqueda(listaSalidaBusqueda, cantPrecarga);

	}

	public void salidaBusqueda(ArrayList<String[] > listaSalidaBusqueda, int cantPrecarga) {
		BufferedWriter bw = null;
		try {
			File file = new File("C:/Users/Euyi/Desktop/Datasets/salida-app1/salidaBusquedaEn"+ cantPrecarga + ".csv");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			String[]usuarioActual = new String[3];

			for (int i = 0; i < listaSalidaBusqueda.size(); i++) {
				
				//ESCRIBO LA PRIMER LINEA DEL ARCHIVO
				
				usuarioActual = listaSalidaBusqueda.get(i);
				String contenidoLineal = usuarioActual[0] + ";" + usuarioActual[1] + ";" + usuarioActual[2];
				bw.write(contenidoLineal);
				bw.newLine();
			}

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
		//centro.insertar("C:/Users/Eloy/Desktop/datasets/dataset_insert_10000.csv", 2000000);
		//centro.busquedaUsuarios("C:/Users/Eloy/Desktop/datasets/dataset_busqueda_10000.csv", 2000000);

		//centro.precarga("C:/Users/Eloy/Desktop/datasets/dataset_1000000.csv");
		//centro.busquedaUsuarios("C:/Users/Eloy/Desktop/datasets/dataset_insert_10000.csv", 1000000);
		//centro.insertar("C:/Users/Eloy/Desktop/datasets/dataset_busqueda_10000.csv", 1000000);

		centro.precarga("C:/Users/Euyi/Desktop/datasets/dataset_500000.csv");
		centro.insertar("C:/Users/Euyi/Desktop/datasets/dataset_insert_10000.csv", 500000);
		centro.busquedaUsuarios("C:/Users/Euyi/Desktop/datasets/dataset_busqueda_10000.csv", 500000);

		/*ListaArreglo usuarios = new ListaArreglo();
		

		
		for (int i = 0; i < 100; i++) {
			Usuario u = new Usuario(i); 
			usuarios.agregar(u);
		}
		//usuarios.imprimir();
		for (int i = 101; i < 200; i++) {
			System.out.println(usuarios.busquedaDyC(i)+"numero"+i);
		} */
	}

}
