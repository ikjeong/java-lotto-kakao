package lotto.model;

import java.util.List;

public record LottoMachineGeneratedResult(
		Money totalPrice,
		List<LottoTicket> lottoTickets
) {
}
