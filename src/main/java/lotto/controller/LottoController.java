package lotto.controller;

import java.util.List;

import lotto.model.*;
import lotto.model.generator.ManualGenerateType;
import lotto.model.generator.RandomGenerateType;
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
		PurchaseSession purchasedTickets = purchaseTickets();
		WinningLottoNumbers winningLottoNumbers = readWinningLottoNumbers();

		LottoResult lottoResult = createLottoResult(purchasedTickets, winningLottoNumbers);
		outputView.printLottoResult(lottoResult);
	}

	private PurchaseSession purchaseTickets() {
		Money purchasePrice = inputView.readPurchasePrice();
		PurchaseSession purchaseSession = new PurchaseSession(purchasePrice, lottoMachine);
		List<List<LottoNumber>> manualLottoNumbersList = readManualLottoNumbersList();

		purchaseSession.purchaseTickets(new ManualGenerateType(manualLottoNumbersList));
		int purchasableRandomTicketCount = purchaseSession.getPurchasableCount();
		purchaseSession.purchaseTickets(new RandomGenerateType(purchasableRandomTicketCount));
		outputView.printPurchasedTicketCount(manualLottoNumbersList.size(), purchasableRandomTicketCount);
		outputView.printLottoTickets(purchaseSession.getLottoTickets());
		return purchaseSession;
	}

	private List<List<LottoNumber>> readManualLottoNumbersList() {
		int manualLottoTicketNumber = inputView.readManualLottoTicketNumber();
		return inputView.readManualLottoNumbersList(manualLottoTicketNumber);
	}

	private WinningLottoNumbers readWinningLottoNumbers() {
		List<LottoNumber> winningNormalNumbers = inputView.readWinningNormalNumbers();
		LottoNumber bonusNumber = inputView.readBonusNumber();
		return new WinningLottoNumbers(winningNormalNumbers, bonusNumber);
	}

	private LottoResult createLottoResult(PurchaseSession purchasedTickets, WinningLottoNumbers winningLottoNumbers) {
		Money totalPrice = purchasedTickets.getTotalPrice();
		List<Rank> ranks = purchasedTickets.getLottoTickets().stream()
				.map(winningLottoNumbers::match).toList();
		return new LottoResult(totalPrice, ranks);
	}
}
