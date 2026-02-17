package lotto.model;

import java.util.List;

public class LottoMachine {

	private final Money lottoTicketPrice;
	private final LottoTicketRandomGenerator lottoTicketRandomGenerator;

	private Money balance;

	public LottoMachine(Money lottoTicketPrice, LottoTicketRandomGenerator lottoTicketRandomGenerator) {
		this.lottoTicketPrice = lottoTicketPrice;
		this.lottoTicketRandomGenerator = lottoTicketRandomGenerator;
		this.balance = new Money(0);
	}

	public void deposit(Money money) {
		balance = new Money(balance.amount() + money.amount());
	}

	public Money getBalance() {
		return balance;
	}

	public PurchasedTickets purchaseAutoTickets() {
		int ticketCount = Math.toIntExact(balance.amount() / lottoTicketPrice.amount());
		Money totalPrice = new Money(lottoTicketPrice.amount() * ticketCount);
		List<LottoTicket> lottoTickets = lottoTicketRandomGenerator.generate(ticketCount);
		balance = new Money(balance.amount() - totalPrice.amount());
		return new PurchasedTickets(totalPrice, lottoTickets);
	}

	public PurchasedTickets purchaseManualTicket(List<LottoNumber> targetLottoNumbers) {
		if (balance.amount() < lottoTicketPrice.amount()) {
			throw new IllegalArgumentException("금액이 부족하여 수동으로 로또를 구매할 수 없습니다.");
		}

		Money totalPrice = new Money(lottoTicketPrice.amount());
		List<LottoTicket> lottoTickets = List.of(new LottoTicket(targetLottoNumbers));
		balance = new Money(balance.amount() - totalPrice.amount());
		return new PurchasedTickets(totalPrice, lottoTickets);
	}
}
