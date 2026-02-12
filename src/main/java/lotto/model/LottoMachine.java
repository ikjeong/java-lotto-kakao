package lotto.model;

import java.util.List;

public class LottoMachine {

	public static final Money LOTTO_TICKET_PRICE = new Money(1_000);

	private final LottoTicketRandomGenerator lottoTicketRandomGenerator;

	public LottoMachine(LottoTicketRandomGenerator lottoTicketRandomGenerator) {
		this.lottoTicketRandomGenerator = lottoTicketRandomGenerator;
	}

	public List<LottoTicket> generate(Money purchasePrice) {
		int price = purchasePrice.amount();
		if (price < LOTTO_TICKET_PRICE.amount()){
			throw new IllegalArgumentException("티켓 주문 금액은 최소 " + LOTTO_TICKET_PRICE.amount() + "원 이상 입력해야 합니다.");
		}

		int ticketCount = price / LOTTO_TICKET_PRICE.amount();
		return lottoTicketRandomGenerator.generate(ticketCount);
	}
}
