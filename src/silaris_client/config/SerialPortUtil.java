/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package silaris_client.config;

import com.fazecast.jSerialComm.SerialPort;
import javax.swing.JComboBox;

/**
 *
 * @author ASUS
 */
public class SerialPortUtil {
    public static void loadPorts(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            comboBox.addItem(port.getSystemPortName());
        }
    }
}
