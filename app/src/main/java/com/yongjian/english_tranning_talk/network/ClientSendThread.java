package com.yongjian.english_tranning_talk.network;

import com.yongjian.english_tranning_talk.bean.TranObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by YONGJIAN on 2016/6/7 0007.
 */
public class ClientSendThread {
    private Socket mSocket = null;
    private ObjectOutputStream oos =null;
    public ClientSendThread(Socket socket){
        this.mSocket =socket;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(TranObject tran)throws IOException{
        oos.writeObject(tran);
        oos.flush();
    }
}
