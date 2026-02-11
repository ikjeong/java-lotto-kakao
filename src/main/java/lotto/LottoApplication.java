package lotto;

import lotto.controller.LottoController;
import lotto.model.LottoNumbersRandomGenerator;

public class LottoApplication {

	public static void main(String[] args) {
		LottoNumbersRandomGenerator lottoNumbersRandomGenerator = new LottoNumbersRandomGenerator();
		LottoController lottoController = new LottoController(lottoNumbersRandomGenerator);

		lottoController.run();
	}
}
