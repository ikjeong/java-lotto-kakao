package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchasedTicketsTest {

	@Test
	@DisplayName("PurchasedTickets merge 검증")
	void validateMergePurchasedTicketsList() {
		PurchasedTickets firstPurchasedTickets = generatePurchasedTickets(1_000L, 0);
		PurchasedTickets secondPurchasedTickets = generatePurchasedTickets(1_000L, 1);

		PurchasedTickets mergedPurchasedTickets = PurchasedTickets.mergePurchasedTicketsList(List.of(firstPurchasedTickets, secondPurchasedTickets));
		Money totalPrice = mergedPurchasedTickets.totalPrice();
		List<LottoTicket> lottoTickets = mergedPurchasedTickets.lottoTickets();

		assertThat(totalPrice).isEqualTo(new Money(2_000L));
		assertThat(lottoTickets).containsExactly(
				firstPurchasedTickets.lottoTickets().getFirst(),
				secondPurchasedTickets.lottoTickets().getFirst()
		);
		assertThat(lottoTickets.size()).isEqualTo(2);
	}

	PurchasedTickets generatePurchasedTickets(long amount, int startIndex) {
		Money money = new Money(amount);
		List<LottoNumber> lottoNumbers = LottoNumber.getLottoNumberCandidates()
				.subList(startIndex, startIndex + LottoTicket.LOTTO_LENGTH);
		List<LottoTicket> lottoTickets = List.of(new LottoTicket(lottoNumbers));
		return new PurchasedTickets(money, lottoTickets);
	}
}
