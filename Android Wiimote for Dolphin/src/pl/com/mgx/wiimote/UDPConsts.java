package pl.com.mgx.wiimote;

public class UDPConsts {
	/* BUTTONS */
	public final static int  UDPWM_B1 = (1<<0);
	public final static int  UDPWM_B2 = (1<<1);
	public final static int  UDPWM_BA = (1<<2);
	public final static int  UDPWM_BB = (1<<3);
	public final static int  UDPWM_BP = (1<<4);
	public final static int  UDPWM_BM = (1<<5);
	public final static int  UDPWM_BH = (1<<6);
	public final static int  UDPWM_BU = (1<<7);
	public final static int  UDPWM_BD = (1<<8);
	public final static int  UDPWM_BL = (1<<9);
	public final static int  UDPWM_BR = (1<<10);
	public final static int  UDPWM_SK = (1<<11);
	public final static int  UDPWM_NC = (1<<0);
	public final static int  UDPWM_NZ = (1<<1);
	
	/* MSG TYPES */
	public final static int PACKET_ACCEL = (1<<0);
	public final static int PACKET_BUTTONS = (1<<1);
	public final static int PACKET_IR = (1<<2);
	public final static int PACKET_NUN = (1<<3);
	public final static int PACKET_NUNACCEL = (1<<4);

}
