package lotto.model;

import java.util.List;

public class LottoMachine {

	private final Money lottoTicketPrice;
	private final LottoTicketRandomGenerator lottoTicketRandomGenerator;

	public LottoMachine(Money lottoTicketPrice, LottoTicketRandomGenerator lottoTicketRandomGenerator) {
		this.lottoTicketPrice = lottoTicketPrice;
		this.lottoTicketRandomGenerator = lottoTicketRandomGenerator;
	}

	public PurchasedTicketsGroup purchaseTickets(Money purchasePrice, List<List<LottoNumber>> targetLottoNumbersList) {
		if (purchasePrice.isLessThan(lottoTicketPrice)) {
			throw new IllegalArgumentException("최소 " + lottoTicketPrice.amount() + "원 이상을 입력해주세요.");
		}
		PurchasedTickets manualPurchasedTickets = purchaseManualTickets(purchasePrice, targetLottoNumbersList);
		PurchasedTickets autoPurchasedTickets = purchaseAutoTickets(purchasePrice.subtract(manualPurchasedTickets.totalPrice()));
		return new PurchasedTicketsGroup(manualPurchasedTickets, autoPurchasedTickets);
	}

	public PurchasedTickets purchaseManualTickets(Money purchasePrice, List<List<LottoNumber>> targetLottoNumbersList) {
		Money totalPrice = lottoTicketPrice.multiply(targetLottoNumbersList.size());
		if (purchasePrice.isLessThan(totalPrice)) {
			throw new IllegalArgumentException("수동 구매 금액이 부족합니다. 최소 " + totalPrice.amount() + "원이 필요합니다.");
		}
		List<LottoTicket> lottoTickets = targetLottoNumbersList.stream()
				.map(LottoTicket::new)
				.toList();
		return new PurchasedTickets(totalPrice, lottoTickets);
	}

	public PurchasedTickets purchaseAutoTickets(Money purchasePrice) {
		int ticketCount = Math.toIntExact(purchasePrice.calculateQuotientDivideBy(lottoTicketPrice));
		Money totalPrice = lottoTicketPrice.multiply(ticketCount);
		List<LottoTicket> lottoTickets = lottoTicketRandomGenerator.generate(ticketCount);
		return new PurchasedTickets(totalPrice, lottoTickets);
	}
}
