package lotto.model;

import java.util.ArrayList;
import java.util.List;

public record PurchasedTickets(
		Money totalPrice,
		List<LottoTicket> lottoTickets
) {

	public static PurchasedTickets mergePurchasedTicketsList(List<PurchasedTickets> purchasedTicketsList) {
		Money totalPrice = purchasedTicketsList.stream()
				.map(PurchasedTickets::totalPrice)
				.reduce(Money.zero(), Money::add);

		List<LottoTicket> lottoTickets = new ArrayList<>();
		purchasedTicketsList.stream().map(PurchasedTickets::lottoTickets)
				.forEach(lottoTickets::addAll);
		return new PurchasedTickets(totalPrice, lottoTickets);
	}
}
