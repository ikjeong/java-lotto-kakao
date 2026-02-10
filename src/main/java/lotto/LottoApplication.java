package lotto;

import lotto.controller.LottoController;
import lotto.model.LottoRandomGenerator;

public class LottoApplication {

	public static void main(String[] args) {
		LottoRandomGenerator lottoRandomGenerator = new LottoRandomGenerator();
		LottoController lottoController = new LottoController(lottoRandomGenerator);

		lottoController.run();
	}
}
