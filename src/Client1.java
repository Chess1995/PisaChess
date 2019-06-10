import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args){

        if (args.length<2){
            System.out.println("Please specify addresss and port as arguments!");
            System.exit(-1);
        }

        String address = args[0];
        int port = Integer.parseInt(args[1]);
        Socket socket = null;


        try {
            socket = new Socket(address,port);
        } catch (IOException e) {
            System.out.println("Unreachable host!");
            e.printStackTrace();
            System.exit(-1);
        }

        Scanner scanner = new Scanner(System.in); //PER LEGGERE DA TASTIERA
        String message=null ;
        int scelta ;

        while (true){


            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream()); //PER INVIARE AL SERVER
                Scanner in = new Scanner(socket.getInputStream()); // PER LEGGERE DAL SERVER
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); //PER LEGGERE DAL SERVER

                clearScreen();

                //------LETTURA DEL MENU A TENDINA------

                int ciclo=0 ;
                while (ciclo!=1){
                    message=in.nextLine() ;
                    if(message.startsWith("end"))
                        ciclo=1 ;
                    else
                    System.out.println(message);
            }



                    scelta = Integer.parseInt(scanner.nextLine());
                    out.println(scelta); //COMUNICO LA SCELTA AL SERVER
                    out.flush();


                    if (scelta==1) {
                        System.out.println("Hai digitato 1...");
                    }

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