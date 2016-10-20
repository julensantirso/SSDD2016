package ssdd.trackerTracker;

public class Slave {

	public int EstaisListos() {
		return 0;
		
		// Recibe la peticion de saber si esta listo por JMS por parte del Master 
		// Le responde por JMS
	}
	
	public void Guardar(String IdSwarm, String IpPeer, String Port){
		// Recibe la orden de guardar de guardar por medio de JMS asi que llama a la funcion 
		//insertSwarm(String IdSwarm, String IpPeer, String Port) en la clase GestorBD
	}

}
