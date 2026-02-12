package lotto;

import lotto.config.LottoPolicy;
import lotto.controller.LottoController;
import lotto.model.LottoMachine;
import lotto.model.LottoTicketRandomGenerator;
import lotto.model.Money;

public class LottoApplication {

	public static void main(String[] args) {
		LottoTicketRandomGenerator lottoTicketRandomGenerator = new LottoTicketRandomGenerator();
		LottoMachine lottoMachine = new LottoMachine(new Money(LottoPolicy.LOTTO_TICKET_PRICE), lottoTicketRandomGenerator);
		LottoController lottoController = new LottoController(lottoMachine);

		lottoController.run();
	}
}
