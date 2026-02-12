package lotto.model;

import java.util.List;

public class LottoMachine {

	private final Money lottoTicketPrice;
	private final LottoTicketRandomGenerator lottoTicketRandomGenerator;

	public LottoMachine(Money lottoTicketPrice, LottoTicketRandomGenerator lottoTicketRandomGenerator) {
		this.lottoTicketPrice = lottoTicketPrice;
		this.lottoTicketRandomGenerator = lottoTicketRandomGenerator;
	}

	public LottoMachineGeneratedResult generate(Money purchasePrice) {
		int price = purchasePrice.amount();
		if (price < lottoTicketPrice.amount()){
			throw new IllegalArgumentException("티켓 주문 금액은 최소 " + lottoTicketPrice.amount() + "원 이상 입력해야 합니다.");
		}

		int ticketCount = price / lottoTicketPrice.amount();
		Money totalPrice = new Money(lottoTicketPrice.amount() * ticketCount);
		List<LottoTicket> lottoTickets = lottoTicketRandomGenerator.generate(ticketCount);
		return new LottoMachineGeneratedResult(totalPrice, lottoTickets);
	}
}
