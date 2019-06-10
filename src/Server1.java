import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Server1 {
    @Override
    public String toString() {
        return "Server{" +
                "port=" + port +
                ", balance=" + balance +
                ", server=" + server +
                ", nAcceptedRequest=" + nAcceptedRequest +
                '}';
    }

    private int port;
    private int balance;

    ServerSocket server;
    int nAcceptedRequest;

    public Server1(int port){
        this.port = port;
    }

    public void go(){

        try {
            server = new ServerSocket(port);
            System.out.println("Starting server on port "+port);
        } catch (IOException e) {
            System.out.println("Cannot start server on port "+port);
            e.printStackTrace();
            System.exit(-1);

        }

        while (true){
            try {
                System.out.println("Ready to accept connections...");
                Socket client = server.accept();
                nAcceptedRequest++;
                System.out.println("Accepted connection request n."+nAcceptedRequest+ " from:"+client.getRemoteSocketAddress());

                InnerClientManager cm = new InnerClientManager(client);

                Thread t = new Thread(cm,"Thread"+String.valueOf(nAcceptedRequest));
                t.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    public class InnerClientManager implements Runnable {
        Socket assigned_client;
        String name;
        String message=null ;

        @Override
        public void run() {
            this.name = Thread.currentThread().getName();
            System.out.println("Starting thread ClientManager: "+name);

            while (true){
            try {
                // var br = new BufferedReader(new InputStreamReader(assigned_client.getInputStream()));

                Scanner in = new Scanner(assigned_client.getInputStream()); //LEGGE DAL CLIENT
                PrintWriter out = new PrintWriter(assigned_client.getOutputStream()); // INVIA AL CLIENT

                message="Torneo di scacchi\n\n[1] Inserisci partecipanti\n[2] Genera turno\n[5] Esci dal programma\nend";
                out.println(message);
                out.flush(); //invio il MENU A TENDINA AL CLIENT

                int scelta = Integer.parseInt(in.nextLine()) ;



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public InnerClientManager(Socket s){
            this.assigned_client = s;
        }

    }






}