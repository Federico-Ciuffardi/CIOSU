/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright (C) 2019  Federico Ciuffardi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Please contact me (federico.ciuffardi@outlook.com) if you need 
 * additional information or have any questions.
 */

package serial;

import java.util.HashMap;
import java.util.Map;
import com.fazecast.jSerialComm.SerialPort;

/*
 * Serial Port handler to provide access to an already open port if needed
 */

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
	//opens port identified with id or obtains it and reopens it
	public Port open(String id) {
		if(!ports.containsKey(id) || ports.get(id)== null) {
			ports.put(id, Port.open(id));
		}else {
			ports.get(id).reOpen();
		}
		return ports.get(id); 
	}
	//gets the available ports system name and description
	public String[] getAvailablePortsDescription() {
		SerialPort[] ps = SerialPort.getCommPorts();
		String[] psNames = new String[ps.length];
		for(int i = 0; i< ps.length;i++) {
			psNames[i] ="System name: "+ps[i].getSystemPortName() +" | Description: " + ps[i].getPortDescription();
		}
		return psNames;
	}
}
