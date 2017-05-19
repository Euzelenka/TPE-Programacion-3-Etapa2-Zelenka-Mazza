
public class ListaArreglo {

	Usuario arr[];
	int cantidad;

	public ListaArreglo(){
		arr = new Usuario[100];
		cantidad = 0;
	}

	public boolean estaVacia(){
		return cantidad == 0;
	}

	public boolean existe(int id) {
		if(!estaVacia()) {
			for (int i = 0; i < cantidad; i++) {
				if(arr[i].getId() == id) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int getCantidad(){
		return cantidad;
	}

	public Usuario getElemento(int pos){
		if(pos < cantidad){
			return arr[pos];
		}
		return null;
	}
	
	public void merge(int izq, int medio, int der) {
		int i = 0, j = 0, k = 0;
		Usuario [] aux = new Usuario[cantidad];
		
		for (i = izq; i <= der; i++) {
			aux[i] = arr[i];
		}	
			i = izq;
			j = medio + 1;
			k = izq;
			
			while(i <= medio && j <= der) {
				if(aux[i].getId() <= aux[j].getId()) {   
					arr[k] = aux[i];
					i++;
				}	
				else {
					arr[k] = aux[j];
					j++;
					}
				k++;
			}
			while(i <= medio) {
				arr[k] = aux[i];
				k++;
				i++;
			}	
	}
	
	
	public void ordenar() {
		mergeSort(0,cantidad-1);
	}
	
	private void mergeSort(int izq, int der) {
		if(izq < der) {
			int medio = (izq + der) / 2;
			mergeSort(izq,medio);
			mergeSort(medio+1,der);
			merge(izq,medio,der);
		}
	}
	
	public void agregar(Usuario u){
		
		if(!hayEspacio()){
			incrementarEspacio();
		}
		arr[cantidad] = u;
		cantidad++;
	}
	
	private boolean hayEspacio(){
		return arr[arr.length-1] == null;
	}
	
	private void incrementarEspacio(){
			
			Usuario nuevoArreglo[] = new Usuario[cantidad*2];
			for (int i = 0; i < cantidad; i++) {
				nuevoArreglo[i] = arr[i];
			}
			arr = nuevoArreglo;	
	}
	
	public void imprimir(){

		for (int i = 0; i < cantidad; i++) {
			System.out.println(arr[i]);
		}

	}
	
	public void eliminarTodo(){
		arr = new Usuario[10];
		cantidad = 0;
	}
	
}
