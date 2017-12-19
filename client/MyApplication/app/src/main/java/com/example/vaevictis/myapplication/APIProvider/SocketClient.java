package com.example.vaevictis.myapplication.APIProvider;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by vaevictis on 05.12.17.
 */


final public class SocketClient {

    public static Socket socket;

    static public void start() {
        try {
            socket = IO.socket(APIProvider.BASE_URL);
            socket.connect();
        } catch (Exception e){
            System.out.println(e.getCause());
        }

    }
}
