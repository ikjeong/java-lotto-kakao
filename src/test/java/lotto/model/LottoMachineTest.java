package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoMachineTest {

	Money ticketPrice = new Money(1_000L);
	LottoMachine lottoMachine;

	@BeforeEach
	void setup() {
		LottoTicketRandomGenerator lottoTicketRandomGenerator = new LottoTicketRandomGenerator();
		lottoMachine = new LottoMachine(ticketPrice, lottoTicketRandomGenerator);
	}

	@Test
	@DisplayName("구매 가격에 따른 자동 티켓 발행 확인")
	void validateLottoTicketCountByPurchasePrice() {
		Money purchasePrice = ticketPrice.multiply(14L).add(ticketPrice.subtract(new Money(1L)));

		PurchasedTickets purchasedTickets = lottoMachine.purchaseAutoTickets(purchasePrice);
		assertThat(purchasedTickets.totalPrice()).isEqualTo(ticketPrice.multiply(14L));
		assertThat(purchasedTickets.lottoTickets().size()).isEqualTo(14);
	}

	@Test
	@DisplayName("티켓 최소 구매 금액 미만인 경우 자동 티켓 발행 확인")
	void validateMinimumPurchasePrice() {
		Money purchasePrice = ticketPrice.subtract(new Money(1L));

		PurchasedTickets purchasedTickets = lottoMachine.purchaseAutoTickets(purchasePrice);
		assertThat(purchasedTickets.totalPrice()).isEqualTo(new Money(0));
		assertThat(purchasedTickets.lottoTickets().size()).isEqualTo(0);
	}

	@Test
	@DisplayName("수동 티켓 구매 검증 확인")
	void validatePurchaseManualTicket() {
		Money purchasePrice = ticketPrice;
		List<LottoNumber> targetLottoNumbers = new ArrayList<>();
		for (int i = 1; i <= LottoTicket.LOTTO_LENGTH; i++) {
			targetLottoNumbers.add(LottoNumber.of(i));
		}

		PurchasedTickets purchasedTickets = lottoMachine.purchaseManualTickets(purchasePrice, List.of(targetLottoNumbers));
		assertThat(purchasedTickets.totalPrice()).isEqualTo(ticketPrice);
		assertThat(purchasedTickets.lottoTickets().size()).isEqualTo(1);
		// LottoNumber가 모두 일치해야 함
		List<LottoNumber> lottoNumbers = purchasedTickets.lottoTickets().getFirst().getSortedLottoNumbers();
		for (int i = 0; i < LottoTicket.LOTTO_LENGTH; i++) {
			assertThat(lottoNumbers.get(i)).isEqualTo(targetLottoNumbers.get(i));
		}
	}

	@Test
	@DisplayName("잔액 부족한 경우 수동 티켓 구매 예외 발생")
	void validatePurchaseManualTicketWithInsufficientBalance() {
		Money purchasePrice = ticketPrice.subtract(new Money(1L));
		List<LottoNumber> targetLottoNumbers = new ArrayList<>();
		for (int i = 1; i <= LottoTicket.LOTTO_LENGTH; i++) {
			targetLottoNumbers.add(LottoNumber.of(i));
		}

		assertThatIllegalArgumentException().isThrownBy(() -> {
			PurchasedTickets purchasedTickets = lottoMachine.purchaseManualTickets(purchasePrice, List.of(targetLottoNumbers));
		});
	}
}
