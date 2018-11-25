/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspiclient;

import com.pi4j.gpio.extension.base.DacGpioProvider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogOutput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.SoftPwm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author metin
 */
public class RaspiClient {

    static ObjectInputStream okuyucu = null;
    static ObjectOutputStream yazici = null;
    static Socket soket = null;
    static JSONObject jobj = new JSONObject();
    static boolean sag = false, sol = false, geri = false;
    private static int rightSpeed = 23;
    private static int leftSpeed = 26;
    static boolean x = false;
    static int hiz = 100;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JSONException {
        // TODO code application logic here
        jobj.put("kimlik", "rasbperry");
        final GpioController gControl = GpioFactory.getInstance();
        final GpioPinDigitalOutput sag = gControl.provisionDigitalOutputPin(RaspiPin.GPIO_02, "sag", PinState.LOW);
        final GpioPinDigitalOutput sol = gControl.provisionDigitalOutputPin(RaspiPin.GPIO_03, "sol", PinState.LOW);
        final GpioPinDigitalOutput in1 = gControl.provisionDigitalOutputPin(RaspiPin.GPIO_04, "in1", PinState.LOW);
        final GpioPinDigitalOutput in2 = gControl.provisionDigitalOutputPin(RaspiPin.GPIO_05, "in2", PinState.LOW);
        final GpioPinDigitalOutput in3 = gControl.provisionDigitalOutputPin(RaspiPin.GPIO_06, "in3", PinState.LOW);
        final GpioPinDigitalOutput in4 = gControl.provisionDigitalOutputPin(RaspiPin.GPIO_07, "in4", PinState.LOW);       
        final Runtime runtime = Runtime.getRuntime();        
        SoftPwm.softPwmCreate(23, 0, 100);
        SoftPwm.softPwmCreate(26, 0, 100);

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        soket = new Socket("172.20.10.7", 8000);
                        yazici = new ObjectOutputStream(soket.getOutputStream());
                        okuyucu = new ObjectInputStream(soket.getInputStream());
                        yazici.writeObject(jobj.toString());
                        yazici.flush();
                        String gelen = (String) okuyucu.readObject();
                        yazici.flush();
                        long lStart = System.currentTimeMillis();

                        JSONObject jobj = new JSONObject(gelen);

                        System.out.println(jobj);
                        if (jobj.length() != 0) {
                            if (jobj.get("vites").equals("1")) {
                                hiz = 40;
                            } else if (jobj.get("vites").equals("2")) {
                                hiz = 50;
                            } else if (jobj.get("vites").equals("3")) {
                                hiz = 60;
                            } else if (jobj.get("vites").equals("4")) {
                                hiz = 70;
                            } else if (jobj.get("vites").equals("5")) {
                                hiz = 80;
                            } else if (jobj.get("vites").equals("6")) {
                                hiz = 90;
                            } else if (jobj.get("vites").equals("7")) {
                                hiz = 95;
                            } else if (jobj.get("vites").equals("8")) {
                                hiz = 98;
                            } else if (jobj.get("vites").equals("9")) {
                                hiz = 99;
                            }
                            if (jobj.get("sol").equals("1")) {
                                System.out.println("sag::");                                
                                in1.low();
                                in2.high();
                                in3.high();
                                in4.high();
                                SoftPwm.softPwmWrite(23, hiz);
                                SoftPwm.softPwmWrite(26, hiz);                      
                            } else if (jobj.get("sag").equals("1")) {
                                System.out.println("sol::");                                
                                in1.high();
                                in2.high();
                                in3.low();
                                in4.high();
                                SoftPwm.softPwmWrite(23, hiz);
                                SoftPwm.softPwmWrite(26, hiz);

                            } else if (jobj.get("ileri").equals("1")) {
                                System.out.println("ileri::");                                
                                in1.low();
                                in2.high();
                                in3.low();
                                in4.high();
                                SoftPwm.softPwmWrite(23, hiz);
                                SoftPwm.softPwmWrite(26, hiz);                 
                            } else if (jobj.get("geri").equals("1")) {
                                System.out.println("geri::");                                
                                in1.high();
                                in2.low();
                                in3.high();
                                in4.low();                               
                                SoftPwm.softPwmWrite(23, hiz);
                                SoftPwm.softPwmWrite(26, hiz);

                            } else if (jobj.get("dur").equals("1")) {
                                System.out.println("dur::");                                
                                in1.low();
                                in2.low();
                                in3.low();
                                in4.low();

                            }
                        }
                        yazici.close();
                        okuyucu.close();
                        soket.close();                        
                    } catch (IOException ex) {
                        Logger.getLogger(RaspiClient.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(RaspiClient.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(RaspiClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }
    static boolean ileri = false;
}
