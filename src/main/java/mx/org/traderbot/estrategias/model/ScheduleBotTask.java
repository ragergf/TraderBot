package mx.org.traderbot.estrategias.model;

import java.util.TimerTask;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ScheduleBotTask extends TimerTask {
	
	EstrategiaBot estrategiaBot;

	@Override
	public void run() {
		estrategiaBot.run();
	}

	public EstrategiaBot getEstrategiaBot() {
		return estrategiaBot;
	}

	public void setEstrategiaBot(EstrategiaBot estrategiaBot) {
		this.estrategiaBot = estrategiaBot;
	}

	
}
