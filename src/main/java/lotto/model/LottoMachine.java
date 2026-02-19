package lotto.model;

import java.util.List;

public class LottoMachine {

	private final Money lottoTicketPrice;
	private final LottoTicketRandomGenerator lottoTicketRandomGenerator;

	public LottoMachine(Money lottoTicketPrice, LottoTicketRandomGenerator lottoTicketRandomGenerator) {
		this.lottoTicketPrice = lottoTicketPrice;
		this.lottoTicketRandomGenerator = lottoTicketRandomGenerator;
	}

	public PurchasedTickets purchaseAutoTickets(Money purchasePrice) {
		int ticketCount = Math.toIntExact(purchasePrice.calculateQuotientDivideBy(lottoTicketPrice));
		Money totalPrice = lottoTicketPrice.multiply(ticketCount);
		List<LottoTicket> lottoTickets = lottoTicketRandomGenerator.generate(ticketCount);
		return new PurchasedTickets(totalPrice, lottoTickets);
	}

	public PurchasedTickets purchaseManualTickets(Money purchasePrice, List<List<LottoNumber>> targetLottoNumbersList) {
		Money totalPrice = lottoTicketPrice.multiply(targetLottoNumbersList.size());
		if (purchasePrice.isLessThan(totalPrice)) {
			throw new IllegalArgumentException("수동 구매 금액이 부족합니다.");
		}
		List<LottoTicket> lottoTickets = targetLottoNumbersList.stream()
				.map(LottoTicket::new)
				.toList();
		return new PurchasedTickets(totalPrice, lottoTickets);
	}
}
