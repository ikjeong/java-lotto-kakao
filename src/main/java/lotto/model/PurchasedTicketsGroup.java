package lotto.model;

import java.util.List;

public record PurchasedTicketsGroup(
		PurchasedTickets manualPurchasedTickets,
		PurchasedTickets autoPurchasedTickets
) {

	public PurchasedTickets getMergedPurchasedTickets() {
		return PurchasedTickets.mergePurchasedTicketsList(List.of(manualPurchasedTickets, autoPurchasedTickets));
	}
}
