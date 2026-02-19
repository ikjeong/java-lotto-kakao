package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchasedTicketsTest {

	@Test
	@DisplayName("PurchasedTickets merge 검증")
	void validateMergePurchasedTicketsList() {
		PurchasedTickets firstPurchasedTickets = generatePurchasedTickets(1_000L, 1);
		PurchasedTickets secondPurchasedTickets = generatePurchasedTickets(1_000L, 7);

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

	PurchasedTickets generatePurchasedTickets(long amount, int startLottoNumber) {
		Money money = new Money(amount);
		List<LottoNumber> lottoNumbers = new ArrayList<>();
		for (int i = startLottoNumber; i < startLottoNumber + LottoTicket.LOTTO_LENGTH; i++) {
			lottoNumbers.add(LottoNumber.of(i));
		}
		List<LottoTicket> lottoTickets = List.of(new LottoTicket(lottoNumbers));
		return new PurchasedTickets(money, lottoTickets);
	}
}
