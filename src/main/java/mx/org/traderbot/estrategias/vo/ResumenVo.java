package mx.org.traderbot.estrategias.vo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.estrategias.model.EstrategiaBot;

@Scope(value = "singleton")
@Component("ResumenVo")
public class ResumenVo {
	private List<EstrategiaBot> estrategias;
	private List<JCheckBox> checks;

	public List<EstrategiaBot> getEstrategias() {
		return estrategias;
	}

	public void setEstrategias(List<EstrategiaBot> estrategias) {
		this.estrategias = estrategias;
	}

	public List<JCheckBox> getChecks() {
		return checks;
	}

	public void setChecks(List<JCheckBox> checks) {
		this.checks = checks;
	}
	
	
	 	 
}
