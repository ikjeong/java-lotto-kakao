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
		PurchasedTickets allPurchasedTickets = purchaseTickets();
		WinningLottoNumbers winningLottoNumbers = readWinningLottoNumbers();

		LottoResult lottoResult = createLottoResult(allPurchasedTickets, winningLottoNumbers);
		outputView.printLottoResult(lottoResult);
	}

	private PurchasedTickets purchaseTickets() {
		Money purchasePrice = inputView.readPurchasePrice();
		List<List<LottoNumber>> manualLottoNumbersList = readManualLottoNumbersList();

		PurchasedTicketsGroup purchasedTicketsGroup = lottoMachine.purchaseTickets(purchasePrice, manualLottoNumbersList);
		PurchasedTickets manualPurchasedTickets = purchasedTicketsGroup.manualPurchasedTickets();
		PurchasedTickets autoPurchasedTickets = purchasedTicketsGroup.autoPurchasedTickets();
		outputView.printPurchasedTicketCount(manualPurchasedTickets.lottoTickets().size(), autoPurchasedTickets.lottoTickets().size());
		outputView.printLottoTickets(manualPurchasedTickets.lottoTickets());
		outputView.printLottoTickets(autoPurchasedTickets.lottoTickets());

		return purchasedTicketsGroup.getMergedPurchasedTickets();
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

	private LottoResult createLottoResult(PurchasedTickets purchasedTickets, WinningLottoNumbers winningLottoNumbers) {
		Money totalPrice = purchasedTickets.totalPrice();
		List<Rank> ranks = purchasedTickets.lottoTickets().stream()
				.map(winningLottoNumbers::match).toList();
		return new LottoResult(totalPrice, ranks);
	}
}
