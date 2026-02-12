package lotto;

import lotto.controller.LottoController;
import lotto.model.LottoMachine;
import lotto.model.LottoTicketRandomGenerator;

public class LottoApplication {

	public static void main(String[] args) {
		LottoTicketRandomGenerator lottoTicketRandomGenerator = new LottoTicketRandomGenerator();
		LottoMachine lottoMachine = new LottoMachine(lottoTicketRandomGenerator);
		LottoController lottoController = new LottoController(lottoMachine);

		lottoController.run();
	}
}
