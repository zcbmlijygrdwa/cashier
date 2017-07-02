package zhenyuyang.ucsb.edu.cashier;

/**
 * Created by Zhenyu on 2017-07-01.
 */

public class SettingsManager {
    private static SettingsManager instance = null;

    private String serverAddress;
    private int serverPort;

    public static SettingsManager getInstance(){
        if(instance==null){
            instance = new SettingsManager();
            return instance;
        }
        else{
            return instance;
        }
    }

    public SettingsManager(){
        serverAddress = "169.231.184.54";
        serverPort = 6789;
    }


    public void setServerAddress(String serverAddress){
        this.serverAddress = serverAddress;
    }

    public String getServerAddress(){
        return serverAddress;
    }

    public void setServerPort(int serverPort){
        this.serverPort = serverPort;
    }

    public int getServerPort(){
        return serverPort;
    }



}
