import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Please specify addresss and port as arguments!");
            System.exit(-1);
        }

        String address = args[0];
        int port = Integer.parseInt(args[1]);
        Socket socket = null;


        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println("Unreachable host!");
            e.printStackTrace();
            System.exit(-1);
        }

        Scanner scanner = new Scanner(System.in); //PER LEGGERE DA TASTIERA
        String message = null;
        int scelta;
        int nTurno=0 ; //indica a quale turno il torneo si trova
        int inizioTorneo=0 ; // serve per il controllo delle funzionalita ammesse.
        int nGiocatori=0 ; //tiene il conto del numero di giocatori inseriti
        int turnoInCorso=0 ; //indica, se posto a 1, che un turno e ancora in corso di svolgimento.
        int fineTorneo=0 ; //posto ad 1 indica che il torneo e terminato

        String nomeGiocatore ;
        String cognomeGiocatore ;
        String invio ; //la utilizzo per leggere gli invio
        int elo ;

        while (true) {


            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream()); //PER INVIARE AL SERVER
                Scanner in = new Scanner(socket.getInputStream()); // PER LEGGERE DAL SERVER
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); //PER LEGGERE DAL SERVER

                clearScreen();

                //------LETTURA DEL MENU A TENDINA------

                int ciclo = 0;
                while (ciclo != 1) {
                    message = in.nextLine();
                    if (message.startsWith("end"))
                        ciclo = 1;
                    else
                        System.out.println(message);
                }


                scelta = Integer.parseInt(scanner.nextLine());
                out.println(scelta); //COMUNICO LA SCELTA AL SERVER
                out.flush();

                if(scelta==1) { //dovro leggere nome cognome e punteggio elo
                    clearScreen();
                    inizioTorneo=Integer.parseInt(in.nextLine()) ;
                    if (inizioTorneo == 0) {
                        message = in.nextLine();
                        System.out.println(message);
                        nomeGiocatore = scanner.nextLine();
                        out.println(nomeGiocatore);
                        out.flush();

                        clearScreen();
                        message = in.nextLine();
                        System.out.println(message);
                        cognomeGiocatore = scanner.nextLine();
                        out.println(cognomeGiocatore);
                        out.flush();

                        clearScreen();
                        message = in.nextLine();
                        System.out.println(message);
                        elo = Integer.parseInt(scanner.nextLine());
                        out.println(elo);
                        out.flush();

                    }
                    else {
                        System.out.println("L'inserimento giocatori è terminato!\n\n");

                        System.out.println("Premere invio per continuare");
                        invio=scanner.nextLine() ;
                        out.println(invio);
                        out.flush();

                    }
                }



                    else
                    if(scelta==2) {
                        clearScreen();
                        nGiocatori=Integer.parseInt(in.nextLine()) ;
                        if (nGiocatori>=1) {
                            ciclo = 0;
                            while (ciclo != 1) {

                                message = in.nextLine();

                                if (message.startsWith("end"))
                                    ciclo = 1;
                                else
                                    System.out.println(message);
                            }

                            System.out.println("Premere invio per continuare");
                            invio = scanner.nextLine();
                            out.println(invio);
                            out.flush();
                        }
                        else {
                            System.out.println("Nessun giocatore inserito!\n\n");

                            System.out.println("Premere invio per continuare");
                            invio=scanner.nextLine() ;
                            out.println(invio);
                            out.flush();
                        }

                    }

                        else
                        if(scelta==3) {
                            clearScreen();
                            inizioTorneo=Integer.parseInt(in.nextLine()) ;
                            if (inizioTorneo==1) {

                                ciclo = 0;
                                while (ciclo != 1) {

                                    message = in.nextLine();
                                    if (message.startsWith("end"))
                                        ciclo = 1;
                                    else
                                        System.out.println(message);
                                }

                                System.out.println("Premere invio per continuare");
                                invio = scanner.nextLine();
                                out.println(invio);
                                out.flush();
                            }
                            else {

                                System.out.println("Il torneo non è ancora cominciato!\n\n");

                                System.out.println("Premere invio per continuare");
                                invio=scanner.nextLine() ;
                                out.println(invio);
                                out.flush();

                            }

                        }

                        else
                        if(scelta==4) {
                            clearScreen();
                            nGiocatori=Integer.parseInt(in.nextLine()) ;
                            turnoInCorso=Integer.parseInt(in.nextLine()) ;
                            fineTorneo=Integer.parseInt(in.nextLine()) ;

                            if (nGiocatori >= 3 && turnoInCorso!=1 && fineTorneo!=1) {

                                ciclo = 0;
                                while (ciclo != 1) {
                                    message = in.nextLine();
                                    if (message.startsWith("end"))
                                        ciclo = 1;
                                    else
                                        System.out.println(message);
                                }
                                System.out.println("\nPremere invio per continuare");
                                invio = scanner.nextLine();
                                out.println(invio);
                                out.flush();
                            }
                            else {
                                if (nGiocatori<3)
                                    System.out.println("Per generare il turno servono almeno 3 giocatori!\n\n");
                                else
                                if (turnoInCorso==1)
                                    System.out.println("Completare prima l'inserimento dei risultati!\n\n");
                                else
                                if(fineTorneo==1)
                                System.out.println("Il torneo è terminato!\n\n");

                                System.out.println("Premere invio per continuare");
                                invio=scanner.nextLine() ;
                                out.println(invio);
                                out.flush();

                            }
                        }

                        else
                        if(scelta==5){
                            inizioTorneo=Integer.parseInt(in.nextLine()) ;
                            clearScreen();

                            if(inizioTorneo==1) {
                                ciclo = 0;
                                while (ciclo != 1) {
                                    message = in.nextLine();
                                    if (message.startsWith("end"))
                                        ciclo = 1;
                                    else
                                        System.out.println(message);
                                }

                                System.out.println("\n\nPremere invio per continuare");
                                invio = scanner.nextLine();
                                out.println(invio);
                                out.flush();
                            }
                            else {
                                System.out.println("Devi prima generare il primo turno!\n\n");

                                System.out.println("Premere invio per continuare");
                                invio=scanner.nextLine() ;
                                out.println(invio);
                                out.flush();
                            }

                        }
                        else
                        if(scelta==6) {
                            clearScreen();
                            //int controlloOpzione

                            turnoInCorso = Integer.parseInt(in.nextLine());
                            if (turnoInCorso==1) {
                                ciclo = 0;
                                while (ciclo != 1) {
                                    message = in.nextLine();
                                    if (message.startsWith("end"))
                                        ciclo = 1;
                                    else
                                        System.out.println(message);
                                }
                                scelta = Integer.parseInt(scanner.nextLine());
                                out.println(scelta);
                                out.flush(); //invio la scelta

                                //controlloOpzione=Integer.parseInt(in.nextLine()) ;

                                //if (controlloOpzione==1){
                                int letturaMosse = 0;
                                while (letturaMosse != 1) {
                                    message = scanner.nextLine();
                                    if (message.equals("#1") || message.equals("#0.5") || message.equals("#0"))
                                        letturaMosse = 1;
                                    out.println(message);
                                    out.flush();
                                }

                                //else
                                //System.out.println("Match inesistente o già seguito!");
                                System.out.println("Premere invio per continuare");
                                invio = scanner.nextLine();
                                out.println(invio);
                                out.flush(); }
                            else {
                                System.out.println("Nessun turno è in corso in questo momento!\n\n");
                                System.out.println("Premere invio per continuare");
                                invio=scanner.nextLine() ;
                                out.println(invio);
                                out.flush();
                            }


                        }

                        else
                        if (scelta==7) {
                            clearScreen();
                            socket.close();
                            break ;
                        }


                        //}







            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    public static void clearScreen() { // la utilizzo per pulire lo schermo
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}