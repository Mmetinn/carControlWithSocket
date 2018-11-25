/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author metin
 */
public class Serversocket {
    static ObjectInputStream okuyucu = null;
    static ObjectOutputStream yazici = null;
    static ServerSocket srvrSocket=null;
    static Socket soket=null;
    static JSONObject jobj = new JSONObject();
    static JSONObject aOBJ = new JSONObject();
    static JSONObject rOBJ = new JSONObject();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        srvrSocket = new ServerSocket(8000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        soket=srvrSocket.accept();
                        System.out.println("sunucu çalışıyor...");
                        okuyucu=new ObjectInputStream(soket.getInputStream());
                        yazici=new ObjectOutputStream(soket.getOutputStream());
                        System.out.println("2222");
                        String gelen=(String)okuyucu.readObject();
                        jobj=new JSONObject(gelen);
                        if (jobj.get("kimlik").equals("android")) {
                            System.out.println("----");
                            aOBJ = jobj;
                            yazici.writeObject(rOBJ.toString());
                        }
                        if (jobj.get("kimlik").equals("rasbperry")) {
                            System.out.println("++++");
                            rOBJ = jobj;
                            yazici.writeObject(aOBJ.toString());

                        }

                        System.out.println(jobj);
                        yazici.close();
                        okuyucu.close();
                        soket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Serversocket.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Serversocket.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(Serversocket.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }
    
}