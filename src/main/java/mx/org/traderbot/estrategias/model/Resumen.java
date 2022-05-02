package mx.org.traderbot.estrategias.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JCheckBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.org.traderbot.estrategias.vo.InfoBalance;
import mx.org.traderbot.estrategias.vo.ResumenVo;


@Component("Resumen")
@Scope("prototype")
public class Resumen extends EstrategiaBotImpl{
	
	@Autowired
	ResumenVo resumenVo;

	
	public void run() {
		// TODO Auto-generated method stub
		resumen();
	}
	
	public void resumen()
	{
		String resumen="Resumen\n\n";
		List<InfoBalance> list = new ArrayList<InfoBalance>();
		int i=0;
		for(JCheckBox aux : resumenVo.getChecks())
		{
						
			if(aux.isSelected() && !aux.getText().contains("Resumen"))
			{
//				resumen += aux.getText() + "\n"
//						+ resumenVo.getEstrategias().get(i).getBalance().getEma_rapida() + "\n\n";
				
				list.add(resumenVo.getEstrategias().get(i).getBalance());
			}
			i++;
		}
		
		Collections.sort(list);
		
		for (InfoBalance balance : list)
		{
			resumen += balance.getNombreEstrategia() + "\n"
					+ "Aprox USD: " + balance.getAprox_final() + "\n"
					+ "No Transacciones: " + balance.getNumTransacciones() + "\n\n";
		}
		
		infoEstrategiaView.getjTextArea1().setText(resumen);
	}

}
