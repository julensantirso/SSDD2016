package ssdd.trackerTracker;

public class Master {


		/*Siempre que tenga una peticion llama a GestionarPeticionSwarm(String IdSwarm, String IpPeer, String Port)
		 y si la petición es de guardar, llama a GestionarGuardado(String IdSwarm, String IpPeer, String Port)
		*/

		
		public void GestionarPeticionSwarm(String IdSwarm, String IpPeer, String Port){
		/*Recibe la peticion de algo extrae los datos de la peticion y Llama a la funcion 
		  ObtenerSwarms(String IdSwarm, String IpPeer, String Port) de la Clase GestorBD.
		  Crea una lista de Peers y a traves de TCP Se los envia.
		 */
	}
		public void GestionarGuardado(String IdSwarm, String IpPeer, String Port){
		/*Recibe la peticion de Guardar
		Llama a la funcion Estaislistos?() A traves de JMS a la entidad Slaves
		Se Queda escuchando las respuestas y si es positiva llama a 
		Guardar(String IdSwarm, String IpPeer, String Port), sino sigue escuchando
		*/
	}

}
