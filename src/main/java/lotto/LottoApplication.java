package lotto;

import lotto.controller.LottoController;
import lotto.model.LottoTicketRandomGenerator;

public class LottoApplication {

	public static void main(String[] args) {
		LottoTicketRandomGenerator lottoTicketRandomGenerator = new LottoTicketRandomGenerator();
		LottoController lottoController = new LottoController(lottoTicketRandomGenerator);

		lottoController.run();
	}
}
