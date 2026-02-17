package lotto.model;

import java.util.List;

public record PurchasedTickets(
		Money totalPrice,
		List<LottoTicket> lottoTickets
) {
}
