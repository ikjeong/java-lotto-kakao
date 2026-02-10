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
		try {
			executeLotto();
		} catch (RuntimeException runtimeException) {
			outputView.printError(runtimeException.getMessage());
		}
	}

	private void executeLotto() {
		Integer ticketCount = calculateTicketCount();
		List<LottoNumbers> lottoTickets = issueLottoTickets(ticketCount);
		WinningLottoNumbers winningLottoNumbers = readWinningLottoNumbers();
		LottoResult lottoResult = createLottoResult(lottoTickets, winningLottoNumbers);
		outputView.printLottoResult(lottoResult);
	}

	private Integer calculateTicketCount() {
		Integer purchasePrice = inputView.readPurchasePrice();
		validatePurchasePrice(purchasePrice);
		Integer ticketCount = purchasePrice / LottoConfig.LOTTO_TICKET_PRICE;
		outputView.printPurchasedTicketCount(ticketCount);
		return ticketCount;
	}

	private void validatePurchasePrice(Integer purchasePrice) {
		if (purchasePrice >= LottoConfig.LOTTO_TICKET_PRICE) {
			return;
		}
		throw new IllegalArgumentException(LottoConfig.LOTTO_TICKET_PRICE + "원 이상 입력해야 합니다.");
	}

	private List<LottoNumbers> issueLottoTickets(Integer ticketCount) {
		List<LottoNumbers> lottoTickets = lottoRandomGenerator.generate(ticketCount);
		outputView.printLottoTickets(lottoTickets);
		return lottoTickets;
	}

	private WinningLottoNumbers readWinningLottoNumbers() {
		LottoNumbers winningNormalNumbers = readWinningNormalNumbers();
		LottoNumber bonusNumber = new LottoNumber(inputView.readBonusNumber());
		return new WinningLottoNumbers(winningNormalNumbers, bonusNumber);
	}

	private LottoNumbers readWinningNormalNumbers() {
		List<Integer> winningNormalIntegerNumbers = inputView.readWinningNormalNumbers();
		return new LottoNumbers(winningNormalIntegerNumbers.stream().map(LottoNumber::new).toList());
	}

	private LottoResult createLottoResult(List<LottoNumbers> lottoTickets, WinningLottoNumbers winningLottoNumbers) {
		List<Rank> ranks = lottoTickets.stream().map(winningLottoNumbers::match).toList();
		return new LottoResult(ranks);
	}
}
