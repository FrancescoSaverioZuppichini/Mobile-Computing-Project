package com.example.vaevictis.myapplication;

import com.example.vaevictis.myapplication.APIProvider.APIProvider;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by vaevictis on 05.12.17.
 */


final public class SocketClient {


    static Socket socket;

    static public void start() {
        try {
            socket = IO.socket(APIProvider.BASE_URL);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    socket.emit("foo", "hi");
                }

            }).on("event", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                }

            });
            socket.connect();
        } catch (Exception e){
            System.out.println(e.getCause());
        }

    }
}
