package lotto.controller;

import java.util.List;

import lotto.model.*;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

	private final InputView inputView;
	private final OutputView outputView;
	private final LottoMachine lottoMachine;

	public LottoController(LottoMachine lottoMachine) {
		inputView = new InputView();
		outputView = new OutputView();
		this.lottoMachine = lottoMachine;
	}

	public void run() {
		try {
			executeLotto();
		} catch (RuntimeException runtimeException) {
			outputView.printError(runtimeException.getMessage());
		}
	}

	private void executeLotto() {
		int purchasePrice = inputView.readPurchasePrice();
		List<LottoTicket> lottoTickets = lottoMachine.generate(purchasePrice);
		outputView.printPurchasedTicketCount(lottoTickets.size());
		outputView.printLottoTickets(lottoTickets);

		WinningLottoNumbers winningLottoNumbers = readWinningLottoNumbers();
		LottoResult lottoResult = createLottoResult(lottoTickets, winningLottoNumbers);
		outputView.printLottoResult(lottoResult);
	}

	private WinningLottoNumbers readWinningLottoNumbers() {
		List<LottoNumber> winningNormalNumbers = readWinningNormalNumbers();
		LottoNumber bonusNumber = LottoNumber.of(inputView.readBonusNumber());
		return new WinningLottoNumbers(winningNormalNumbers, bonusNumber);
	}

	private List<LottoNumber> readWinningNormalNumbers() {
		List<Integer> winningNormalIntegerNumbers = inputView.readWinningNormalNumbers();
		return winningNormalIntegerNumbers.stream().map(LottoNumber::of).toList();
	}

	private LottoResult createLottoResult(List<LottoTicket> lottoTickets, WinningLottoNumbers winningLottoNumbers) {
		List<Rank> ranks = lottoTickets.stream().map(winningLottoNumbers::match).toList();
		return new LottoResult(ranks);
	}
}
