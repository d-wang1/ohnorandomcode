package communication;

import java.util.Scanner;

import jssc.*;

public class SerialComm {

	SerialPort port;
	static String portName = "/dev/cu.usbserial-141230";
	private boolean debug;  // Indicator of "debugging mode"

	// This function can be called to enable or disable "debugging mode"
	void setDebug(boolean mode) {
		debug = mode;
	}	


	// Constructor for the SerialComm class
	public SerialComm(String name) throws SerialPortException {
		port = new SerialPort(name);		
		port.openPort();
		port.setParams(SerialPort.BAUDRATE_9600,
				SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);

		debug = false; // Default is to NOT be in debug mode
	}

	// TODO: Add writeByte() method to write data to serial port
	public void writeByte(byte b) throws SerialPortException {
		port.writeByte(b);
		if(debug) {
			System.out.printf("<0x%d>",b);
		}

	}

	public boolean available() throws SerialPortException {
		return (port.getInputBufferBytesCount() > 0);
	}

	public byte readByte() throws SerialPortException {
		byte b = port.readBytes(1)[0];
		if(debug) {
			System.out.print("[0x"+String.format("%02x", b)+"]");
		}
		return b;
	}
	
	public static void sendData(SerialComm port) throws SerialPortException 
	{
		Scanner sys = new Scanner(System.in);
		System.out.println("Please enter a line to encode:");
		String nextinput = sys.nextLine();
		for (char c : nextinput.toCharArray()){
			port.writeByte((byte)c);
		}
		port.writeByte((byte)('\n'));
	}
//	public static void main(String[] args){	
//		
//		SerialComm port;
//		try {
//			port = new SerialComm(portName);
//			port.setDebug(true);
////			sendData(port);
//			while(true)
//			{
//				if(port.available())
//				{
//					byte input = port.readByte();
//					System.out.print((char)input);
////					if(input == (byte)'\n')
////					{
////						sendData(port);
////					}
//				}
//			}
//		
//		} catch (SerialPortException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
}
