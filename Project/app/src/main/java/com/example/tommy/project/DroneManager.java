package com.example.tommy.project;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Tommy on 17/05/2017.
 */

public class DroneManager {

    ScheduledExecutorService mScheduleTaskExecutor = Executors.newScheduledThreadPool(2);

    public DatagramPacket udp_packet;
    public DatagramSocket udp_socket;
    public InetAddress adresse;


    //ThreadsManager mThreads;
    //SensorData_Receiver NavRec;


    public byte[] buffer;



    String CommandeGenere = "";
    String sAdresse;
    String sTemps;


    static int iPitch = 0;
    static int iAltitude = 0;
    static int iBatterie = 0;
    static int iYaw = 0;
    static int iRoll = 0;
    static int iSpeed = 0;

    int iVal = 0;
    int iPortDrone;

    int iSecondeU = 0;
    int iSecondeD = 0;
    int iMinuteU = 0;
    int iMinuteD = 0;
    int icSecondeU = 0;
    int icSecondeD = 0;

    public DroneManager(){

    }

    /**

     *	This constructor receives as parameter the address and port of the drone to instantiate and a command to send.
     It instantiates GereThreads class that manages most of the necessary threads in the application and NavdataReceiver class that get drone status and	data.
     It also creates the socket and packet with CreationSocketPacket () function, and starts immediatly WatchDog thread to avoid losing the connection.

     */
    public DroneManager(String CommandeStart, String Adresse, int iPort){			// CONSTRUCTEUR RECOI l'objet de classe MainActivity en parametre

        sAdresse = Adresse;
        iPortDrone = iPort;
        CommandeGenere = CommandeStart;


//		mThreads = new ThreadsManager(this);

        udp_socket = CreationSocketPacket(CommandeStart, iPort, Adresse); // Socket creation

//		NavRec = new SensorData_Receiver(udp_socket);

//		mThreads.StartDonnee();
//		mThreads.StartWatchDog();

    }

    public void sendUDPTrame(String ComandeUDP){


        try {																// INSERTION TRAME
            buffer = ComandeUDP.concat("\r").getBytes("ASCII");

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        udp_packet.setData(buffer);							// Changement de la commande du packet

        try {
            udp_socket.send(udp_packet);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    /**

     *	This method created a socket and a packet. It is performed only once in the constructor of JoyActivity.
     Indeed, two objects are required for UDP communication, a socket and a packet as respective class DatagramSocket and DatagramPacket  (classes available in the Android libraries).

     @param ComandeUDP start command
     @param port The Port to use
     @param host The Address IP target

     */
    private DatagramSocket CreationSocketPacket(String ComandeUDP, int port, String host){
        buffer = new byte[512];

        try {											// TRY CREATION SOCKET
            udp_socket = new DatagramSocket();


        } catch (SocketException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();

        }



        try {													// INSERTION TRAME
            buffer = ComandeUDP.concat("\r").getBytes("ASCII");

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        try {												// INSERTION ADRESSE
            adresse = InetAddress.getByName(host);
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        udp_packet = new DatagramPacket(buffer, buffer.length, adresse, port);
        return udp_socket;

    }
}
