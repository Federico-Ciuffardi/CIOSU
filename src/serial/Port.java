package serial;
import com.fazecast.jSerialComm.*;


public class Port {
	private SerialPort ser;
	private boolean verbose;
	private int trafic;
	private long startingTime;
	private long currentSpeedB;
	private long limit;
	private boolean showSpeed;
	
	private Port(SerialPort ser){
		this.ser = ser;
		verbose = false;
		trafic = 0;
		startingTime = 0;
		currentSpeedB = 0;
		limit = 8000/8;
		showSpeed = false;
		reseter.start();
	}
	public static Port open(String id) {
		SerialPort ser = SerialPort.getCommPort(id);
		if(ser.openPort()) {
			return new Port(ser);
		}else {
			return null;
		}
	}
	public void reOpen() {
		ser.openPort();
	}

	private void resetStats() {
		trafic = 0;
		startingTime = 0;
		startingTime = System.currentTimeMillis();
	}
	private Thread reseter = new Thread() {
		@Override
		public void run() {
			while(true) {
				resetStats();
				tSleep(1000);
				currentSpeedB = ((trafic*1000)/(System.currentTimeMillis()-startingTime));
				if(showSpeed) {
					showSpeed();
				}
			}
		}
	};
	public void showSpeed() {
		System.out.println(currentSpeedB*8 + " b/s");
	}
	public int send(char c) {
		byte[] toSend = new byte[1];
		toSend[0] = (byte) c;
		trafic++;
		limitSpeed();
		if(verbose) {
			System.out.println((byte)c+",E->1B");
		}
		return ser.writeBytes(toSend,1);
	}
	
	public int send(int b) {
		byte[] toSend = new byte[1];
		toSend[0] = (byte)b;
		trafic++;
		limitSpeed();
		if(verbose) {
			System.out.println(b+",E->1B");
		}
		return ser.writeBytes(toSend,1);
	}
	public int send(String s) {
		byte[] bs = s.getBytes();
		trafic=+ bs.length;
		limitSpeed();
		if(verbose) {
			for(int i = 0;i< bs.length;i++) {
				System.out.print(bs[i]);
				System.out.println(",E->"+bs.length+"B");
			}
		}
		return ser.writeBytes(bs,bs.length);
	}
	
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
		trafic+= readBuffer.toString().length();
	    return readBuffer;
	}
	public void tSleep(long t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void limitSpeed() {
		if(System.currentTimeMillis()-startingTime!=0) {
			currentSpeedB = ((trafic*1000)/(System.currentTimeMillis()-startingTime)) ;
		}
		while(currentSpeedB>limit) {//limit
			tSleep((long)(((float)currentSpeedB/(float)limit)));
			if(System.currentTimeMillis()-startingTime!=0) {
				currentSpeedB = ((trafic*1000)/(System.currentTimeMillis()-startingTime)) ;
			}
		}
	}
	public void closePort() {
		ser.closePort();
	}
}
