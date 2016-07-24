package com.yongjian.english_tranning_talk.network;

import android.content.Context;

import com.yongjian.english_tranning_talk.bean.TranObject;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by YONGJIAN on 2016/6/7 0007.
 */
public class NetService {
    private static NetService mService = null;
    private NetConnect connect = null;
    private Socket mSocket;
    private Context context;
    private ClientSendThread clientSendThread = null;
    private ClientListenThread clientListenThread = null;
    private boolean IsConnected = false;


    private NetService() {

    }

    public void onInit(Context context) {
        this.context = context;
    }
    public static NetService getInstance() {
        if (mService == null)
            mService = new NetService();
        return mService;
    }

    public void setupConnection() {
        connect = new NetConnect();
        connect.startConnect();
        if (connect.getIsConnected()) {
            IsConnected = true;
            mSocket = connect.getSocket();
            startListen(mSocket);
        } else {
            IsConnected = false;
        }
    }
    public boolean isConnected() {
        return IsConnected;
    }

    public void startListen(Socket socket) {
        clientSendThread = new ClientSendThread(socket);
        clientListenThread = new ClientListenThread(context, socket);
        clientListenThread.start();
    }

    public void send(TranObject t) throws IOException {
        clientSendThread.sendMessage(t);
    }

    public void closeConnection() {
        if (clientListenThread != null) {
            clientListenThread.close();
        }
        try {
            if (mSocket != null)
               mSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
         IsConnected = false;
         mSocket = null;
    }
}
