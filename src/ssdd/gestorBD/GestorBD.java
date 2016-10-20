package ssdd.gestorBD;



import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	
public class GestorBD {
	

		private Connection con;
		
		public GestorBD(String nombrebd) {
			con = null;
			
			try {
				Class.forName("org.sqlite.JDBC");
				con = DriverManager.getConnection("jdbc:sqlite:" + nombrebd);
				con.setAutoCommit(false);
				
				System.out.println(" - La conexion se ha estalecido :)");
			} catch (Exception ex) {
				System.err.println(" # Nose puede crear SQLiteDBManager: " + ex.getMessage());
			}
		}
		
		public void closeConnection() {
			try {
				con.close();
				System.out.println("\n - Cerrada conexion con Bd :)");
			} catch (Exception ex) {
				System.err.println("\n # Error en el cierre de Bd: " + ex.getMessage());
			}
		}
		
		public void insertSwarm(String IdSwarm, String IpPeer, String Port) {	
			
		}
		
		public String[] ObtenerSwarm(String IdSwarm, String IpPeer, String Port) {	
			String[] a = null;
			return a;
		}
		
		public void deletePeer(String IdSwarm, String IpPeer, String Port) {	
			
		}
		
		public static void main(String[] args) {
			
		
			
		}
	}
