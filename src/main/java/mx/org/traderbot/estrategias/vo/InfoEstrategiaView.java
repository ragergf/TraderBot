package mx.org.traderbot.estrategias.vo;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "singleton")
@Component("InfoEstrategiaView")
public class InfoEstrategiaView {
	
	
	
	JTextField textField_precio;
	JTextField textField_usd;
	JTextField textField_cripto;
	JTextField textField_usdAprox;
	JTextField textField_criptoAprox;
	JTextField textField_nombreLog;
	
	JTextArea textArea_info;
	
	JTextArea jTextArea1;
	
	JComboBox comboBox_estrategia;
	
	
	public InfoEstrategiaView()
	{
		textField_precio = new JTextField();
		textField_usd = new JTextField();
		textField_cripto = new JTextField();
		textField_precio = new JTextField();
		textField_usdAprox = new JTextField();
		textField_criptoAprox = new JTextField();
		textField_nombreLog = new JTextField();
		
		textArea_info = new JTextArea();
		
		comboBox_estrategia = new JComboBox();	
		jTextArea1 = new JTextArea();
	}

	public JTextField getTextField_precio() {
		return textField_precio;
	}

	public void setTextField_precio(JTextField textField_precio) {
		this.textField_precio = textField_precio;
	}

	public JTextField getTextField_usd() {
		return textField_usd;
	}

	public void setTextField_usd(JTextField textField_usd) {
		this.textField_usd = textField_usd;
	}

	public JTextField getTextField_cripto() {
		return textField_cripto;
	}

	public void setTextField_cripto(JTextField textField_cripto) {
		this.textField_cripto = textField_cripto;
	}

	public JTextField getTextField_usdAprox() {
		return textField_usdAprox;
	}

	public void setTextField_usdAprox(JTextField textField_usdAprox) {
		this.textField_usdAprox = textField_usdAprox;
	}

	public JTextField getTextField_criptoAprox() {
		return textField_criptoAprox;
	}

	public void setTextField_criptoAprox(JTextField textField_criptoAprox) {
		this.textField_criptoAprox = textField_criptoAprox;
	}

	public JTextField getTextField_nombreLog() {
		return textField_nombreLog;
	}

	public void setTextField_nombreLog(JTextField textField_nombreLog) {
		this.textField_nombreLog = textField_nombreLog;
	}

	public JTextArea getTextArea_info() {
		return textArea_info;
	}

	public void setTextArea_info(JTextArea textArea_info) {
		this.textArea_info = textArea_info;
	}

	public JComboBox getComboBox_estrategia() {
		return comboBox_estrategia;
	}

	public void setComboBox_estrategia(JComboBox comboBox_estrategia) {
		this.comboBox_estrategia = comboBox_estrategia;
	}

	public JTextArea getjTextArea1() {
		return jTextArea1;
	}

	public void setjTextArea1(JTextArea jTextArea1) {
		this.jTextArea1 = jTextArea1;
	}
	
	
}
