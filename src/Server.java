import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server {



    List<Player> elencoGiocatori = new ArrayList<Player>() ; //contiene l'elenco dei partecipanti.
    Standings classifica = new Standings() ;

    private int port;
    private int balance;
    ServerSocket server;
    int nAcceptedRequest;

        public Server(int port){
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
            String message ;
            int elo ;
            String nome ;
            String cognome ;
            String invio ; //la uso per leggere gli invio
            int scelta ;


            @Override
            public void run() {
                this.name = Thread.currentThread().getName();
                System.out.println("Starting thread ClientManager: "+name);

                try {
                    while (true){
                    // var br = new BufferedReader(new InputStreamReader(assigned_client.getInputStream()));

                    Scanner in = new Scanner(assigned_client.getInputStream());
                    PrintWriter out = new PrintWriter(assigned_client.getOutputStream());

                    message = "Torneo di scacchi\n\n[1] Inserisci partecipanti\n[2] Visualizza partecipanti\n";
                    message=message+"[3] Visualizza classifica\n[4] Genera turno\n[5] Esci dal programma\n" ;
                    message=message+"end" ; // utilizzata per segnalare la fine dell'invio di un flusso di dati.

                    out.println(message);
                    out.flush(); //invio il MENU A TENDINA AL CLIENT


                        scelta = Integer.parseInt(in.nextLine());
                        System.out.println("scelta="+scelta);

                        if(scelta==1) {
                            Player player=new Player() ; //creo un nuovo Oggetto di tipo Player.

                            message="Nome:" ;
                            out.println(message);
                            out.flush() ;
                            nome=in.nextLine() ;
                            player.setName(nome) ;
                            System.out.println("Ho ricevuto: "+nome);

                            message="Cognome:" ;
                            out.println(message);
                            out.flush();
                            cognome=in.nextLine() ;
                            player.setSurname(cognome);
                            System.out.println("Ho ricevuto: "+cognome);

                            message="Elo:" ;
                            out.println(message);
                            out.flush();
                            elo=Integer.parseInt(in.nextLine()) ;
                            player.setElo(elo);
                            System.out.println("Ho ricevuto: "+elo);

                            player.setTournPoints(0); //inizialmente il giocatore ha 0 punti classifica
                            elencoGiocatori.add(player) ;
                            classifica.addPlayer(player);
                            System.out.println("\nHo aggiunto "+nome+" "+cognome+" all'elenco partecipanti!");

                        }


                        if (scelta==2) {
                            //Player player=new Player() ;
                            //Iterator iterator=elencoGiocatori.iterator() ;
                            message="---- ELENCO GIOCATORI ----\n\n" ;

                            for(Player player1 : elencoGiocatori){
                                message=message+"Nome: "+player1.getName()+"\n" ;
                                message=message+"Cognome: "+player1.getSurname()+"\n";
                                message=message+"Elo: "+player1.getElo()+"\n\n" ;
                            }
                            message=message+"end" ;
                            out.println(message);
                            out.flush();

                            //invio=in.nextLine() ;
                        }


                            if(scelta==3){
                                message=classifica.printClassifica()+"\n";
                                message=message+"end" ;
                                out.println(message);
                                out.flush();

                                invio=in.nextLine() ;
                            }


                    }








                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public InnerClientManager(Socket s){
                this.assigned_client = s;
            }

        }
    }


