package zhenyuyang.ucsb.edu.cashier;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.zxing.client.android.BeepManager;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Zhenyu on 2017-06-30.
 */

public class Client extends AsyncTask<Void, Void, Void> {
    private onServerRespondedListener onServerRespondedListener;

    public interface onServerRespondedListener{
        void onServerResponded(String response);

    }

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    String queryID;

    Client(String addr, int port, String queryID) {
        dstAddress = addr;
        dstPort = port;
        this.queryID = queryID;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);
            Log.i("Client","socket1 = "+socket);
            String toServerSentence;




            toServerSentence = queryID+"\n";
            System.out.println("Received: " + toServerSentence);
            DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
            outToClient.writeBytes(toServerSentence);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();

			/*
			 * notice: inputStream.read() will block if no data return
			 */

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
                Log.i("Client", "response = " + response);
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            Log.i("Client","socket2 = "+socket);
            if (socket != null) {
                try {
                    socket.close();
                    Log.i("Client","socket.close();");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //textResponse.setText(response);
        onServerRespondedListener.onServerResponded(response);
        Log.i("Client","onPostExecute");
        super.onPostExecute(result);
    }


    public void setOnServerRespondedListener(onServerRespondedListener onServerRespondedListener){
        this.onServerRespondedListener = onServerRespondedListener;
    }

}
