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

	public LottoMachineGeneratedResult generate() {
		int price = balance.amount();
		int ticketCount = price / lottoTicketPrice.amount();
		Money totalPrice = new Money(lottoTicketPrice.amount() * ticketCount);
		List<LottoTicket> lottoTickets = lottoTicketRandomGenerator.generate(ticketCount);
		balance = new Money(balance.amount() - totalPrice.amount());
		return new LottoMachineGeneratedResult(totalPrice, lottoTickets);
	}
}
