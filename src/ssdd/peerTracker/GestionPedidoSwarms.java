package ssdd.peerTracker;

public class GestionPedidoSwarms {

		
		public void escuchar()
		{
		/* El Tracker crea un datagram Socket  y especifica el puerto donde quiere escuchar
	 	Luego crea el Datagram Packet y monta un buffer para almacernar las peticiones
		Si el Tracker esta seleccionando maestro este hilo estaria Paralizado
		Siempre que tenga una peticion llama a GestionarPeticionSwarm() y si la peticion es
		de guardar, llama a GestionarGuardado
		*/
		}
		
		public void GestionarPeticionSwarm(String IdSwarm, String IpPeer, String Port){
		/*Recibe la peticion de algo
		 extrae los datos de la peticion y Llama a la funcion ObtenerSwarms(String IdSwarm, String IpPeer, String Port) de la base de datos.
		 Crea una lista de Peers y a traves de TCP Se los envia.
		 */
	}
		public void GestionarGuardado(String IdSwarm, String IpPeer, String Port){
		/*Recibe la peticion de Guardar
		Llama a la funcion Estais listos? A traves de JMS
		Se Queda escuchando las respuestas y si es positiva manda guardar llamando
		a insertSwarm(String IdSwarm, String IpPeer, String Port), sino, sigue escuchando
		(todo por JMS)
		*/
		}
}
