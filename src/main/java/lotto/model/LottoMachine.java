package lotto.model;

import java.util.List;

public class LottoMachine {

	private final Money lottoTicketPrice;
	private final LottoTicketRandomGenerator lottoTicketRandomGenerator;

	private Money balance;

	public LottoMachine(Money lottoTicketPrice, LottoTicketRandomGenerator lottoTicketRandomGenerator) {
		this.lottoTicketPrice = lottoTicketPrice;
		this.lottoTicketRandomGenerator = lottoTicketRandomGenerator;
		this.balance = Money.zero();
	}

	public void deposit(Money money) {
		balance = balance.add(money);
	}

	public Money getBalance() {
		return balance;
	}

	public PurchasedTickets purchaseAutoTickets() {
		int ticketCount = Math.toIntExact(balance.divideBy(lottoTicketPrice));
		Money totalPrice = lottoTicketPrice.multiply(ticketCount);
		List<LottoTicket> lottoTickets = lottoTicketRandomGenerator.generate(ticketCount);
		balance = balance.subtract(totalPrice);
		return new PurchasedTickets(totalPrice, lottoTickets);
	}

	public PurchasedTickets purchaseManualTicket(List<LottoNumber> targetLottoNumbers) {
		if (balance.isLessThan(lottoTicketPrice)) {
			throw new IllegalArgumentException("금액이 부족하여 수동으로 로또를 구매할 수 없습니다.");
		}

		Money totalPrice = lottoTicketPrice;
		List<LottoTicket> lottoTickets = List.of(new LottoTicket(targetLottoNumbers));
		balance = balance.subtract(totalPrice);
		return new PurchasedTickets(totalPrice, lottoTickets);
	}
}
