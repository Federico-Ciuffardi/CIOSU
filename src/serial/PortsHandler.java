package serial;

import java.util.HashMap;
import java.util.Map;

import com.fazecast.jSerialComm.SerialPort;

public class PortsHandler {
	private static PortsHandler instance = null;
	private Map<String,Port> ports;
	
	public static PortsHandler getInstance(){
		if(instance == null) {
			instance = new PortsHandler();
		}
		return instance;
	}
	
	private PortsHandler() {
		ports = new HashMap<String,Port>();
	}
	
	public Port open(String id) {
		if(!ports.containsKey(id) || ports.get(id)== null) {
			ports.put(id, Port.open(id));
		}else {
			ports.get(id).reOpen();
		}
		return ports.get(id); 
	}
	public String[] getAvailablePortsDescription() {
		SerialPort[] ps = SerialPort.getCommPorts();
		String[] psNames = new String[ps.length];
		for(int i = 0; i< ps.length;i++) {
			psNames[i] ="System name: "+ps[i].getSystemPortName() +" | Description: " + ps[i].getPortDescription();
		}
		return psNames;
	}
}
