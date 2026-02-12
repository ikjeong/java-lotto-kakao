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
		Money purchasePrice = inputView.readPurchasePrice();
		LottoMachineGeneratedResult machineGeneratedResult = lottoMachine.generate(purchasePrice);
		outputView.printPurchasedTicketCount(machineGeneratedResult.lottoTickets().size());
		outputView.printLottoTickets(machineGeneratedResult.lottoTickets());

		WinningLottoNumbers winningLottoNumbers = readWinningLottoNumbers();
		LottoResult lottoResult = createLottoResult(machineGeneratedResult, winningLottoNumbers);
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

	private LottoResult createLottoResult(LottoMachineGeneratedResult machineGeneratedResult, WinningLottoNumbers winningLottoNumbers) {
		Money totalPrice = machineGeneratedResult.totalPrice();
		List<Rank> ranks = machineGeneratedResult.lottoTickets().stream()
				.map(winningLottoNumbers::match).toList();
		return new LottoResult(totalPrice, ranks);
	}
}
