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

import com.fazecast.jSerialComm.SerialPort;

/*
 * Serial Port with an abstract approach providing speed limit, and debugging functionalities 
 */

public class Port {
	private SerialPort ser;
	private boolean verbose;
	private int traffic;
	private long startingTime;
	private long currentSpeedB;
	private long limit;
	private boolean showSpeed;
	
	private Port(SerialPort ser){
		this.ser = ser;
		verbose = false;//set true to show debugging info
		traffic = 0;
		startingTime = 0;
		currentSpeedB = 0;
		limit = 8000/8;
		showSpeed = false;//set true to show speed info
		reseter.start();
	}
	
	public void setLimit(int l) {
		limit = l;
	}
	
	public void setVerbose(boolean b) {
		verbose = b;
	}
	
	public void setShowSpeed(boolean b) {
		showSpeed = b;
	}
	//tries to open the id port returns it on success otherwise returns null
	public static Port open(String id) {
		SerialPort ser = SerialPort.getCommPort(id);
		if(ser.openPort()) {
			return new Port(ser);
		}else {
			return null;
		}
	}
	//re opens the port
	public void reOpen() {
		ser.openPort();
	}
	//Traffic stats reset
	private void resetStats() {
		traffic = 0;
		startingTime = 0;
		startingTime = System.currentTimeMillis();
	}
	//thread to reset the stats periodically
	private Thread reseter = new Thread() {
		@Override
		public void run() {
			while(true) {
				resetStats();
				tSleep(1000);
				currentSpeedB = ((traffic*1000)/(System.currentTimeMillis()-startingTime));
				if(showSpeed) {
					showSpeed();
				}
			}
		}
	};
	//Shows speed on bits per second
	public void showSpeed() {
		System.out.println(currentSpeedB*8 + " b/s");
	}
	//sends the char casted to a byte
	public int send(char c) {
		byte[] toSend = new byte[1];
		toSend[0] = (byte) c;
		traffic++;
		limitSpeed();
		if(verbose) {
			System.out.println((byte)c+",E->1B");
		}
		return ser.writeBytes(toSend,1);
	}
	//sends the int casted to a byte
	public int send(int b) {
		byte[] toSend = new byte[1];
		toSend[0] = (byte)b;
		traffic++;
		limitSpeed();
		if(verbose) {
			System.out.println(b+",E->1B");
		}
		return ser.writeBytes(toSend,1);
	}
	//sends the string casted to a byte array
	public int send(String s) {
		byte[] bs = s.getBytes();
		traffic=+ bs.length;
		limitSpeed();
		if(verbose) {
			for(int i = 0;i< bs.length;i++) {
				System.out.print(bs[i]);
				System.out.println(",E->"+bs.length+"B");
			}
		}
		return ser.writeBytes(bs,bs.length);
	}
	//blocks until it receives a byte array
	public byte[] receive() {
		while (ser.bytesAvailable() <= 0) {
			tSleep(20);
	    }
	    byte[] readBuffer = new byte[ser.bytesAvailable()];
	    limitSpeed();
	    ser.readBytes(readBuffer, readBuffer.length);
	    if(verbose) {
		    for(int i = 0;i<readBuffer.length;i++) {
	 			System.out.print(readBuffer[i]+",");
	 	    }
	 	    System.out.println("R<-"+readBuffer.toString().length()+"B");
	    }
		traffic+= readBuffer.toString().length();
	    return readBuffer;
	}
	//sleep thread and consumes the exception (to simplify code)
	public void tSleep(long t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	//generates delay as needed to limit the speed
	public void limitSpeed() {
		if(System.currentTimeMillis()-startingTime!=0) {
			currentSpeedB = ((traffic*1000)/(System.currentTimeMillis()-startingTime)) ;
		}
		while(currentSpeedB>limit) {//limit
			tSleep((long)(((float)currentSpeedB/(float)limit)));
			if(System.currentTimeMillis()-startingTime!=0) {
				currentSpeedB = ((traffic*1000)/(System.currentTimeMillis()-startingTime)) ;
			}
		}
	}
	
	public void closePort() {
		ser.closePort();
	}
	
}
