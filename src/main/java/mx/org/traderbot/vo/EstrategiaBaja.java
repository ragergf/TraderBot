package mx.org.traderbot.vo;

import javax.swing.JTextField;

public class EstrategiaBaja {
	
	public JTextField camporMACDLine;
	public JTextField campoSignalLine;
	public JTextField campoEMA13;
	public JTextField campoEMA34;
	
	public EstrategiaBaja()
	{
		camporMACDLine = new JTextField();
		campoSignalLine = new JTextField();
		campoEMA13 = new JTextField();
		campoEMA34 = new JTextField();
	}
	
	public JTextField getCamporMACDLine() {
		return camporMACDLine;
	}
	public void setCamporMACDLine(JTextField camporMACDLine) {
		this.camporMACDLine = camporMACDLine;
	}
	public JTextField getCampoSignalLine() {
		return campoSignalLine;
	}
	public void setCampoSignalLine(JTextField campoSignalLine) {
		this.campoSignalLine = campoSignalLine;
	}
	public JTextField getCampoEMA13() {
		return campoEMA13;
	}
	public void setCampoEMA13(JTextField campoEMA13) {
		this.campoEMA13 = campoEMA13;
	}
	public JTextField getCampoEMA34() {
		return campoEMA34;
	}
	public void setCampoEMA34(JTextField campoEMA34) {
		this.campoEMA34 = campoEMA34;
	}
	
	
	
}
