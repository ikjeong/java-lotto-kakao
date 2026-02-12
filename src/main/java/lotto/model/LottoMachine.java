package lotto.model;

import java.util.List;

public class LottoMachine {

	public static final Integer LOTTO_TICKET_PRICE = 1_000;

	private final LottoTicketRandomGenerator lottoTicketRandomGenerator;

	public LottoMachine(LottoTicketRandomGenerator lottoTicketRandomGenerator) {
		this.lottoTicketRandomGenerator = lottoTicketRandomGenerator;
	}

	public List<LottoTicket> generate(Integer purchasePrice) {
		Integer ticketCount = purchasePrice / LOTTO_TICKET_PRICE;
		if (ticketCount.equals(0)){
			throw new IllegalArgumentException("티켓 주문 금액은 최소 " + LOTTO_TICKET_PRICE + "원 이상 입력해야 합니다.");
		}

		return lottoTicketRandomGenerator.generate(ticketCount);
	}
}
