package communication;

import jssc.*;

public class MsgReceiver {

	final private SerialComm port;

	public MsgReceiver(String portname) throws SerialPortException {
		port = new SerialComm(portname);
	}

	public void run() {
		// insert FSM code here to read msgs from port
		// and write to console
		while(true) {
			try {
				if(port.readByte()=='!') {
					byte header = port.readByte();
					switch(header) {
					case 0x30:
						while(true) {
							if(port.available()) {
								byte in = port.readByte();
								System.out.print((char)in);
								if(in == 0x00) break;
							}
						}
						System.out.println();
						break;
					case 0x32:
						long timestamp = 0;
						for(int i=0;i<32;i++) {
							byte in = port.readByte();
							//							String s = String.format("%8s",Integer.toBinaryString(in)).replace(' ', '0');
							//							System.out.println(i+" : "+s);
							timestamp += in;
							if(i<31) timestamp *= 16;
						}
						System.out.println("Time Elapsed: "+timestamp);
						break;
					case 0x33:
						long poten = 0;
						for(int i=0;i<16;i++) {
							byte in = port.readByte();
							poten += in;
							if(i<15) poten *= 16;
						}
						System.out.println("Poten Reading: "+poten);
						break;
					case 0x34:
						long rawTime = 0;
						for(int i=0;i<32;i++) {
							byte in = port.readByte();
							rawTime += in;
							if(i<31) rawTime *= 16;
						}
						long dist = 34300*rawTime/2000000;
						System.out.println("Distance: " +dist+"cm");
						break;
					case 0x31:
						while(true) {
							if(port.available()) {
								byte in = port.readByte();
								System.out.print((char)in);
								if(in == 0x00) break;
							}
						}
						System.out.println();
						break;
					default: System.out.println("       ERROR!   UNKNOWN KEY "+(int)header);
					}
				}

			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
	}


	public static void main(String[] args) throws SerialPortException {
		MsgReceiver msgr = new MsgReceiver("/dev/cu.usbserial-144230"); // Adjust this to be the right port for your machine
		msgr.run();
	}
}
