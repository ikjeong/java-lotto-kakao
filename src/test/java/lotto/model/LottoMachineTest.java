package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

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
	@DisplayName("자동 및 수동 티켓 구매 검증")
	void validatePurchaseTicket() {
		Money purchasePrice = ticketPrice.multiply(5L);
		List<LottoNumber> firstManualNumbers = pickLottoNumbersFrom(0);
		List<LottoNumber> secondManualNumbers = pickLottoNumbersFrom(LottoTicket.LOTTO_LENGTH);

		PurchasedTicketsGroup purchasedTicketsGroup = lottoMachine.purchaseTickets(
				purchasePrice,
				List.of(firstManualNumbers, secondManualNumbers)
		);
		PurchasedTickets manualPurchasedTickets = purchasedTicketsGroup.manualPurchasedTickets();
		PurchasedTickets autoPurchasedTickets = purchasedTicketsGroup.autoPurchasedTickets();
		PurchasedTickets mergedPurchasedTickets = purchasedTicketsGroup.getMergedPurchasedTickets();

		assertThat(manualPurchasedTickets.totalPrice()).isEqualTo(ticketPrice.multiply(2L));
		assertThat(manualPurchasedTickets.lottoTickets().size()).isEqualTo(2);
		assertThat(autoPurchasedTickets.totalPrice()).isEqualTo(ticketPrice.multiply(3L));
		assertThat(autoPurchasedTickets.lottoTickets().size()).isEqualTo(3);
		assertThat(mergedPurchasedTickets.totalPrice()).isEqualTo(purchasePrice);
		assertThat(mergedPurchasedTickets.lottoTickets().size()).isEqualTo(5);

		List<LottoNumber> firstManualTicketNumbers = manualPurchasedTickets.lottoTickets().getFirst().getSortedLottoNumbers();
		List<LottoNumber> secondManualTicketNumbers = manualPurchasedTickets.lottoTickets().get(1).getSortedLottoNumbers();
		assertThat(firstManualTicketNumbers).isEqualTo(firstManualNumbers);
		assertThat(secondManualTicketNumbers).isEqualTo(secondManualNumbers);
	}

	@Test
	@DisplayName("최소 비용 미만시 예외")
	void validatePurchaseTicketWithInsufficientPrice() {
		Money purchasePrice = ticketPrice.subtract(new Money(1L));

		assertThatIllegalArgumentException().isThrownBy(() ->
				lottoMachine.purchaseTickets(purchasePrice, List.of())
		);
	}

	@Test
	@DisplayName("수동 티켓 구매 검증 확인")
	void validatePurchaseManualTicket() {
		Money purchasePrice = ticketPrice;
		List<LottoNumber> targetLottoNumbers = pickLottoNumbersFrom(0);

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
	@DisplayName("금액 부족한 경우 수동 티켓 구매 예외 발생")
	void validatePurchaseManualTicketWithInsufficientPrice() {
		Money purchasePrice = ticketPrice.subtract(new Money(1L));
		List<LottoNumber> targetLottoNumbers = pickLottoNumbersFrom(0);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			PurchasedTickets purchasedTickets = lottoMachine.purchaseManualTickets(purchasePrice, List.of(targetLottoNumbers));
		});
	}

	private List<LottoNumber> pickLottoNumbersFrom(int startIndex) {
		return LottoNumber.getLottoNumberCandidates().subList(startIndex, startIndex + LottoTicket.LOTTO_LENGTH);
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
}
