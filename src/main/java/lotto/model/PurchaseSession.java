package lotto.model;

import java.util.ArrayList;
import java.util.List;

import lotto.model.generator.GenerateType;

public class PurchaseSession {

	private final Money ticketPrice;
	private final LottoMachine lottoMachine;
	private final Money depositPrice;

	private Money totalPrice;
	private List<LottoTicket> lottoTickets;

	public PurchaseSession(Money ticketPrice, Money depositPrice, LottoMachine lottoMachine) {
		if (depositPrice.isLessThan(ticketPrice)) {
			throw new IllegalArgumentException("최소 " + ticketPrice.amount() + "원 이상 입력해야 합니다.");
		}

		this.ticketPrice = ticketPrice;
		this.depositPrice = depositPrice;
		this.lottoMachine = lottoMachine;
		totalPrice = Money.zero();
		lottoTickets = new ArrayList<>();
	}

	public void purchaseTickets(GenerateType generateType) {
		validatePurchasable(generateType);
		List<LottoTicket> issuedTickets = lottoMachine.issueTickets(generateType);
		totalPrice = totalPrice.add(ticketPrice.multiply(issuedTickets.size()));
		lottoTickets.addAll(issuedTickets);
	}

	public int getPurchasableCount() {
		Money remain = depositPrice.subtract(totalPrice);
		return Math.toIntExact(remain.calculateQuotientDivideBy(ticketPrice));
	}

	public Money getTotalPrice() {
		return totalPrice;
	}

	public List<LottoTicket> getLottoTickets() {
		return List.copyOf(lottoTickets);
	}

	private void validatePurchasable(GenerateType generateTypes) {
		if (getPurchasableCount() < generateTypes.getCount()) {
			throw new IllegalArgumentException("티켓 구매 금액이 부족합니다.");
		}
	}
}
