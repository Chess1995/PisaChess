import java.io.*;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server {



    List<Player> elencoGiocatori = new ArrayList<Player>() ; //contiene l'elenco dei partecipanti.
    Standings classifica = new Standings() ;
    Round round ; // è l'oggetto turno fatto dagli abbinamenti correnti;
    int nTurno=1 ; //inizialmente il numero del turno da generare è il primo.
    int idPlayer=0 ; // è un identificativo crescente che viene assegnato ai giocatori
    int inizioTorneo=0 ; //serve per il controllo delle operazioni possibili ; //0 indica che il torneo deve ancora
    //iniziare, 1 che è in corso,
    int fineTorneo=0 ; //quando è posto ad 1 il torneo è terminato
    int turnoInCorso=0 ; //identifica, se posto a 1, che un turno è in corso di svolgimento e non completato.
    int nRisultatiInseriti=0 ;
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
                    message=message+"[3] Visualizza classifica\n[4] Genera turno\n[5] Visualizza turno\n" ;
                    message=message+"[6] Segui match\n[7] Esci dal programma\n" ;
                    message=message+"end" ; // utilizzata per segnalare la fine dell'invio di un flusso di dati.

                    out.println(message);
                    out.flush(); //invio il MENU A TENDINA AL CLIENT


                        scelta = Integer.parseInt(in.nextLine());
                        System.out.println("scelta="+scelta);

                        if(scelta==1) {
                            out.println(inizioTorneo);
                            out.flush();
                            if (inizioTorneo == 0) { //se il torneo non è ancora iniziato...
                                Player player = new Player(); //creo un nuovo Oggetto di tipo Player.

                                message = "Nome:";
                                out.println(message);
                                out.flush();
                                nome = in.nextLine();
                                player.setName(nome);
                                System.out.println("Ho ricevuto: " + nome);

                                message = "Cognome:";
                                out.println(message);
                                out.flush();
                                cognome = in.nextLine();
                                player.setSurname(cognome);
                                System.out.println("Ho ricevuto: " + cognome);

                                message = "Elo:";
                                out.println(message);
                                out.flush();
                                elo = Integer.parseInt(in.nextLine());
                                player.setElo(elo);
                                System.out.println("Ho ricevuto: " + elo);

                                player.setId(idPlayer + 1);
                                idPlayer++;

                                elencoGiocatori.add(player);
                                classifica.addPlayer(player);
                                System.out.println("\nHo aggiunto " + nome + " " + cognome + " all'elenco partecipanti!");
                            }
                            else {
                                invio=in.nextLine() ;
                            }

                        }

                        else
                        if (scelta==2) {
                            out.println(elencoGiocatori.size());
                            out.flush();
                            if (elencoGiocatori.size()>=1) {
                                //Player player=new Player() ;
                                //Iterator iterator=elencoGiocatori.iterator() ;
                                message = "---- ELENCO GIOCATORI ----\n\n";

                                for (Player player1 : elencoGiocatori) {
                                    message = message + "Nome: " + player1.getName() + "\n";
                                    message = message + "Cognome: " + player1.getSurname() + "\n";
                                    message = message + "Elo: " + player1.getElo() + "\n\n";
                                }
                                message = message + "end";
                                out.println(message);
                                out.flush();

                                invio = in.nextLine();
                            }
                            else{
                                invio=in.nextLine() ;
                            }
                        }

                            else
                            if(scelta==3){
                                out.println(inizioTorneo);
                                out.flush();
                                if (inizioTorneo==1) {
                                    classifica.sortClassifica();
                                    message = classifica.printClassifica() + "\n";
                                    message = message + "end";
                                    out.println(message);
                                    out.flush();

                                    invio = in.nextLine();
                                }
                                else {
                                    invio=in.nextLine() ;
                                }
                            }
                            else
                            if(scelta==4) {
                                out.println(elencoGiocatori.size());
                                out.flush();
                                out.println(turnoInCorso);
                                out.flush();
                                out.println(fineTorneo);
                                out.flush();
                                if(elencoGiocatori.size()>=3 && turnoInCorso!=1 && fineTorneo!=1){
                                int matchId=0 ;
                                int turno=0 ;
                                int board=0 ; //indica il numero di scacchiera dell'incontro.
                                int ricercaGiocatore=0 ;
                                int nturniNecessari=0 ; //indica il numero di turni necessari per completare il torneo.
                                round=new Round() ;
                                round.setnRound(nTurno);
                                String tabellaAbbinamento=null ;
                                String nomeFile ;

                                if(elencoGiocatori.size()%2!=0)
                                    nomeFile=Integer.toString(elencoGiocatori.size()+1) ;
                                else
                                    nomeFile=Integer.toString(elencoGiocatori.size()) ;

                                nturniNecessari=Integer.parseInt(nomeFile)-1 ; //mi serve per capire quando il torneo è completato.
                                nomeFile=nomeFile+".txt" ;
                                System.out.println("cerco nel file "+nomeFile);

                                File file =new File(nomeFile) ;
                                Scanner scanner = new Scanner(file) ;


                                while(turno!=nTurno){
                                    turno=Integer.parseInt(scanner.nextLine()) ;
                                    tabellaAbbinamento=scanner.nextLine() ;
                                }
                                Player player1= new Player() ; //sarà il bianco dell incontro
                                Player player2= new Player() ; //sarà il nero dell incontro
                                for(String number : tabellaAbbinamento.split(" ") ) {
                                    for (Player player : elencoGiocatori) {
                                        if (ricercaGiocatore == 0) { //devo ancora trovare il primo giocatore
                                            if (Integer.toString(player.getId()).equals(number)) {
                                                player1 = player; //ho trovato il bianco
                                                ricercaGiocatore = 1;
                                            }
                                        } else //devo trovare il secondo giocatore
                                            if (Integer.toString(player.getId()).equals(number)) {
                                                player2 = player; //ho trovato il nero
                                                ricercaGiocatore = 0;
                                                board = board + 1;
                                                matchId=matchId+1 ;
                                                Pairing pairing = new Pairing(); //creo un abbinamento
                                                pairing.setPlayer1(player1);
                                                pairing.setPlayer2(player2);
                                                pairing.setBoard(board);
                                                pairing.setMatchId(matchId);
                                                round.addPairing(pairing);


                                            }


                                    }


                                }

                                nTurno++ ;
                                turnoInCorso=1 ; // un turno è già in corso di svolgimento!
                                nRisultatiInseriti=0 ; // non ho ancora inserito risultati per questo turno


                                //System.out.println("la size è: "+ round.pairingList.size());
                                for(Pairing pairing : round.getPairingList()){
                                    System.out.println(pairing.getPlayer1().getSurname()+" "+pairing.getPlayer2().getSurname()+"\n");
                                }

                                message="Turno "+round.getnRound()+" generato con successo!\n" ;
                                message=message+"end" ;
                                out.println(message);
                                out.flush();
                                inizioTorneo=1 ;
                                if(nTurno>nturniNecessari)
                                    fineTorneo=1 ;



                                invio=in.nextLine() ; }

                                else {
                                    invio=in.nextLine() ;
                                }

                            }

                            else
                            if(scelta==5) {
                                out.println(inizioTorneo);
                                out.flush();
                                if (inizioTorneo==1) {
                                    message = round.printRound();
                                    message = message + "end";
                                    out.println(message);
                                    out.flush();

                                    invio = in.nextLine();
                                }
                                else{
                                    invio=in.nextLine() ;
                                }
                            }

                            else
                            if (scelta==6) {
                                File file=null ; //lo utilizzo per salvare le statistiche del turno su file
                                //int controlloOpzione=0 ;
                                out.println(turnoInCorso);
                                out.flush();
                                if (turnoInCorso==1)
                                {
                                int ciclo=0 ;
                                message="Quale dei seguenti match vuoi seguire?\n\n" ;
                                for (Pairing pairing : round.getPairingList()){
                                    message=message+"["+pairing.getMatchId()+"] "+pairing.getPlayer1().getSurname()+" " ;
                                    message=message+pairing.getPlayer1().getName() ;
                                    message=message+" vs "+pairing.getPlayer2().getSurname()+" " ;
                                    message=message+pairing.getPlayer2().getName()+"\n\n" ;
                                }
                                message=message+"end" ;
                                out.println(message);
                                out.flush();

                                scelta = Integer.parseInt(in.nextLine()); //ricevo la scelta

                                for (Pairing pairing : round.getPairingList()) {
                                    if(pairing.getMatchId()==scelta && pairing.getSeguito()==0) {
                                        //controlloOpzione=1 ;
                                        //out.println(controlloOpzione);
                                        //out.flush();
                                        pairing.setSeguito(1);


                                        while (ciclo != 1) {
                                            message = in.nextLine();

                                            if (message.equals("#1")) {
                                                ciclo = 1;
                                                pairing.getPlayer1().addTournPoints(1);
                                                pairing.setResults(1);
                                            } else if (message.equals("#0.5")) {
                                                ciclo = 1;
                                                pairing.getPlayer1().addTournPoints(0.5);
                                                pairing.getPlayer2().addTournPoints(0.5);
                                                pairing.setResults(0.5);
                                            } else if (message.equals("#0")) {
                                                ciclo = 1;
                                                pairing.getPlayer2().addTournPoints(1);
                                                pairing.setResults(0);
                                            } else {
                                                pairing.addMoves(message);
                                                System.out.println(message);
                                            }
                                        }

                                    }

                                    }
                                    nRisultatiInseriti++;
                                    invio=in.nextLine() ;

                                    if (nRisultatiInseriti == round.getPairingList().size()) {
                                        turnoInCorso = 0; //il turno è terminato
                                        //STAMPO SU FILE...
                                        String nomeFile="turno"+(nTurno-1)+".txt" ;
                                        file = new File(nomeFile) ;
                                        FileWriter writer = new FileWriter(nomeFile) ;

                                        for(Pairing pairing1 : round.getPairingList()) {
                                            message=pairing1.getPlayer1().getSurname() ;
                                            message=message+" "+pairing1.getPlayer1().getName() ;
                                            message=message+" vs "+pairing1.getPlayer2().getSurname() ;
                                            message=message+" "+pairing1.getPlayer2().getName() ;
                                            message=message+" ris: "+pairing1.getFormatOfResults()+"\n\n" ;
                                            message=message+"MOSSE\n" ;
                                            for (String s: pairing1.getMoves()) {
                                                message = message+s+" ";
                                            }
                                            message=message+"\n"+"----------"+"\n\n" ;
                                            writer.write(message) ;
                                        }
                                        writer.close();
                                        classifica.setnTurno(nTurno-1);
                                    }
                                }
                                else {
                                    invio=in.nextLine() ;
                                }





                                //if (controlloOpzione==0) {
                                    //out.println(controlloOpzione);
                                //}
                            }

                            else
                                if(scelta==7) {
                                    assigned_client.close();
                                    break ;
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


