package lotto;

import java.util.List;

import lotto.controller.LottoController;
import lotto.model.LottoMachine;
import lotto.model.generator.LottoTicketManualGenerator;
import lotto.model.generator.LottoTicketRandomGenerator;

public class LottoApplication {

	public static void main(String[] args) {
		LottoTicketRandomGenerator lottoTicketRandomGenerator = new LottoTicketRandomGenerator();
		LottoTicketManualGenerator lottoTicketManualGenerator = new LottoTicketManualGenerator();
		LottoMachine lottoMachine = new LottoMachine(List.of(lottoTicketRandomGenerator, lottoTicketManualGenerator));
		LottoController lottoController = new LottoController(lottoMachine);

		lottoController.run();
	}
}
