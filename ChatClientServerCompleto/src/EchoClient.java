
import java.net.*;
import java.io.*;
import java.util.ArrayList;

    public class EchoClient {
    
    public ArrayList<String> user = new ArrayList<>();
    String indirizzo;
        
    @SuppressWarnings("empty-statement")
    
    public EchoClient(){}
    
    public EchoClient(String str) throws IOException {
        /* Lanciando il programma senza argomenti si ottiene il local loopback IP address, per testarlo in locale (client e server
sulla stessa macchina), altrimenti si possono passare da linea di comando l’indirizzo IP o il nome della macchina
remota */

        indirizzo = "172.18.4.207";
        
        try {
// creazione socket
            Socket socket = new Socket(indirizzo, EchoServer.PORT);
            System.out.println("EchoClient: avviato");
            System.out.println("Socket del client: " + socket);
// creazione dell'utente
            String userInput;
            
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            // input str = stdIn.readLine();
            user.add(str);
// creazione stream di input da socket
            /*InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader in = new BufferedReader(isr);*/
            RunnablesClient r = new RunnablesClient(socket, user);
            Thread t1 = new Thread(r);
            t1.start();
// creazione stream di output su socket
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter out = new PrintWriter(bw, true);
            
// creazione stream di input da tastiera
            
// ciclo di lettura da tastiera, invio al server e stampa risposta
            while (true) {
                userInput = stdIn.readLine();
                if (userInput.equals("quit")) {
                    out.println(str + " ha abbandonato la chat ");
                    out.println("quit");
                    break;
                }
                out.println(str + ": " + userInput);
                //System.out.println("Echo: " + in.readLine());
            }
// chiudo gli oggetti stream e socket
            out.close();
            //in.close();
            stdIn.close();
            socket.close();
            t1.stop();
            user.remove(str);

        } catch (UnknownHostException e) {
            System.err.println("Host non riconosciuto... " + indirizzo);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Non riesco ad avere I/O per la connessione a: " + indirizzo);
            System.exit(1);
        }
        System.out.println("EchoClient: passo e chiudo...");
    }

    public String getUser(int number) {
        return user.get(number);
    }
    
    
}
