
public class Usuario {
	
	int id;
	String[] gustos;
	int contGustos;
	
	public Usuario(int id){
		this.id = id;
		gustos = new String[5];
		contGustos = 0;
	}
	
	public String toString(){
		String datos = id + " ";
		for (int i = 0; i < gustos.length; i++) {
			if(gustos[i] != null)
				datos += gustos[i] + " "; 
		}
		return datos;
	}
	
	public void agregarGusto(String gusto){
		
		if(!existe(gusto)){
			gustos[contGustos] = gusto;
			contGustos++;
		}
	}
	private boolean existe(String gusto) {
		
		for (int i = 0; i < gustos.length; i++) {
			if(gustos[i] != null){
				if(gustos[i].equals(gusto)){
					return true;
				}
			}
		}
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String[] getGustos() {
		return gustos;
	}

	public void setGustos(String[] gustos) {
		this.gustos = gustos;
	}

}
