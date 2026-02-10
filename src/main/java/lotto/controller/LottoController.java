package lotto.controller;

import java.util.List;

import lotto.LottoConfig;
import lotto.model.*;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

	private final InputView inputView;
	private final OutputView outputView;
	private final LottoRandomGenerator lottoRandomGenerator;

	public LottoController(LottoRandomGenerator lottoRandomGenerator) {
		inputView = new InputView();
		outputView = new OutputView();
		this.lottoRandomGenerator = lottoRandomGenerator;
	}

	public void run() {
		Integer purchasePrice = inputView.readPurchasePrice();
		Integer ticketCount = purchasePrice / LottoConfig.LOTTO_TICKET_PRICE;
		outputView.printPurchasedTicketCount(ticketCount);
		List<LottoNumbers> lottoTickets = lottoRandomGenerator.generate(ticketCount);
		outputView.printLottoTickets(lottoTickets);

		List<Integer> winningNormalIntegerNumbers = inputView.readWinningNormalNumbers();
		LottoNumbers winningNormalNumbers =
				new LottoNumbers(winningNormalIntegerNumbers.stream().map(LottoNumber::new).toList());

		Integer bonusIntegerNumber = inputView.readBonusNumber();
		LottoNumber bonusNumber = new LottoNumber(bonusIntegerNumber);

		WinningLottoNumbers winningLottoNumbers = new WinningLottoNumbers(winningNormalNumbers, bonusNumber);

		List<Rank> ranks = lottoTickets.stream().map(winningLottoNumbers::match).toList();
		LottoResult lottoResult = new LottoResult(ranks);

		outputView.printLottoResult(lottoResult);
	}
}
