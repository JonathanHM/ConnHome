package com.example.petersomersby.connhome.Network;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Peter Somersby on 01-06-2017.
 */

public class Networking {

    private int UDP_SERVER_PORT;
    private String UDP_SERVER_IP_ADDRESS;

    public Networking(int port, String address) {
        this.UDP_SERVER_PORT = port;
        this.UDP_SERVER_IP_ADDRESS = address;
    }

    public void send(String udpMsg)  {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            InetAddress serverAddr = InetAddress.getByName(UDP_SERVER_IP_ADDRESS);
            DatagramPacket dp;
            dp = new DatagramPacket(udpMsg.getBytes(), udpMsg.length(), serverAddr, UDP_SERVER_PORT);
            ds.send(dp);
        } catch (SocketException e) {
            e.printStackTrace();
        }catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }
}
