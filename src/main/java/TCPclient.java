import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TCPclient {

	private static final int TEMPO_SONECA = 100;

	private static final int PARAMETRO_IP = 0;
	
	private static final int PARAMETRO_PORTA = 1;
	
	private static final int PARAMETRO_TIMEOUT = 2;
	
	private static final int PARAMETRO_ITERACOES = 3;
	
	private static final int NUMERO_PARAMETROS = 4;
	
	public static void main(String[] args) throws IOException {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss.SSSSSS");
		
		String hostname = args.length == NUMERO_PARAMETROS ? args[PARAMETRO_IP] : "192.168.118.44";
		int port = args.length == NUMERO_PARAMETROS ? Integer.parseInt(args[PARAMETRO_PORTA]) : 1555;
		int timeout = args.length == NUMERO_PARAMETROS ? Integer.parseInt(args[PARAMETRO_TIMEOUT]) : 100;
		int iteracoes = args.length == NUMERO_PARAMETROS ? Integer.parseInt(args[PARAMETRO_ITERACOES]) : 100; 
		
		File arquivo = new File("socket-troubleshooting.txt");
		FileOutputStream is = new FileOutputStream(arquivo);
		@SuppressWarnings("resource")
		OutputStreamWriter filew = new OutputStreamWriter(is);

		Date date = null;
		for (int i = 0; i < iteracoes; i++) {
			try (Socket socket = new Socket(hostname, port)) {
				
				date = new Date();
				socket.setSoTimeout(timeout);
				Writer file = new BufferedWriter(filew);
				file.write("Conectando no server " + hostname + ":" + port + " " + dateFormat.format(date) + "\n");
				file.flush();

				sleep(TEMPO_SONECA);

			} catch (UnknownHostException ex) {

				System.err.println("Server not found: " + ex.getMessage());

			} catch (IOException ex) {

				System.err.println("I/O error: " + ex.getMessage());
			}
		}
	}

	private static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			System.err.println("InterruptedException in Thread.sleep: " + e.getMessage());
		}
	}

}