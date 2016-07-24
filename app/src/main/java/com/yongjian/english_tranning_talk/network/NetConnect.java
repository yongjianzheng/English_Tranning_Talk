package com.yongjian.english_tranning_talk.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by YONGJIAN on 2016/6/7 0007.
 */
public class NetConnect {
    private Socket mClientSocket = null;
    private final String SERVER_IP = "192.168.139.1";
    private final int SERVER_PORT = 8399;
    private boolean mIsconnected = false;
    public NetConnect(){

    }
    public void startConnect(){
        mClientSocket = new Socket();
        try {
            mClientSocket.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT),3000);
            if (mClientSocket.isConnected())
                mIsconnected =true;
            else
                mIsconnected =false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean getIsConnected(){
        return mIsconnected;
    }
    public Socket getSocket(){
        return mClientSocket;
    }

}
