package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.model.generator.LottoTicketManualGenerator;
import lotto.model.generator.ManualGenerateType;
import lotto.model.generator.RandomGenerateType;
import lotto.model.generator.LottoTicketRandomGenerator;

public class PurchaseSessionTest {

	Money ticketPrice = new Money(1_000L);
	LottoMachine lottoMachine;

	@BeforeEach
	void setup() {
		lottoMachine = new LottoMachine(List.of(
				new LottoTicketManualGenerator(),
				new LottoTicketRandomGenerator()
		));
	}

	@Test
	@DisplayName("구매 금액이 티켓 가격 미만이면 예외 발생")
	void throwsExceptionWhenDepositLessThanTicketPrice() {
		Money depositPrice = ticketPrice.subtract(new Money(1L));

		assertThatIllegalArgumentException().isThrownBy(() ->
				new PurchaseSession(ticketPrice, depositPrice, lottoMachine)
		);
	}

	@Test
	@DisplayName("구매 금액이 티켓 가격보다 같거나 크면 세션 생성 가능")
	void createsSessionWhenDepositEqualsTicketPrice() {
		PurchaseSession session = new PurchaseSession(ticketPrice, ticketPrice, lottoMachine);

		assertThat(session.getPurchasableCount()).isEqualTo(1);
	}

	@Test
	@DisplayName("구매 가능한 티켓 수")
	void getPurchasableCountReturnsDepositDividedByTicketPrice() {
		Money depositPrice = ticketPrice.multiply(5L);
		PurchaseSession session = new PurchaseSession(ticketPrice, depositPrice, lottoMachine);

		assertThat(session.getPurchasableCount()).isEqualTo(5);
	}

	@Test
	@DisplayName("구매 후 티켓 목록에 반영")
	void purchasedTicketsAreAddedToList() {
		Money depositPrice = ticketPrice.multiply(3L);
		PurchaseSession session = new PurchaseSession(ticketPrice, depositPrice, lottoMachine);
		List<LottoNumber> numbers = LottoNumber.getLottoNumberCandidates().subList(0, LottoTicket.LOTTO_LENGTH);
		ManualGenerateType generateType = new ManualGenerateType(List.of(numbers));

		session.purchaseTickets(generateType);

		assertThat(session.getLottoTickets()).hasSize(1);
		assertThat(session.getLottoTickets().getFirst().getSortedLottoNumbers()).isEqualTo(numbers);
	}

	@Test
	@DisplayName("구매 후 총 가격에 반영")
	void totalPriceIsUpdatedAfterPurchase() {
		Money depositPrice = ticketPrice.multiply(3L);
		PurchaseSession session = new PurchaseSession(ticketPrice, depositPrice, lottoMachine);
		List<LottoNumber> numbers = LottoNumber.getLottoNumberCandidates().subList(0, LottoTicket.LOTTO_LENGTH);
		ManualGenerateType generateType = new ManualGenerateType(List.of(numbers, numbers.subList(0, LottoTicket.LOTTO_LENGTH)));

		session.purchaseTickets(new ManualGenerateType(List.of(numbers)));
		session.purchaseTickets(new ManualGenerateType(List.of(
				LottoNumber.getLottoNumberCandidates().subList(LottoTicket.LOTTO_LENGTH, LottoTicket.LOTTO_LENGTH * 2)
		)));

		assertThat(session.getTotalPrice()).isEqualTo(ticketPrice.multiply(2L));
	}

	@Test
	@DisplayName("구매 후 구매 가능 수 감소")
	void purchasableCountDecreasesAfterPurchase() {
		Money depositPrice = ticketPrice.multiply(3L);
		PurchaseSession session = new PurchaseSession(ticketPrice, depositPrice, lottoMachine);
		List<LottoNumber> numbers = LottoNumber.getLottoNumberCandidates().subList(0, LottoTicket.LOTTO_LENGTH);

		session.purchaseTickets(new ManualGenerateType(List.of(numbers)));

		assertThat(session.getPurchasableCount()).isEqualTo(2);
	}

	@Test
	@DisplayName("잔액 부족 시 구매 시 예외 발생")
	void throwsExceptionWhenInsufficientBalance() {
		Money depositPrice = ticketPrice;
		PurchaseSession session = new PurchaseSession(ticketPrice, depositPrice, lottoMachine);
		RandomGenerateType generateType = new RandomGenerateType(2);

		assertThatIllegalArgumentException().isThrownBy(() ->
				session.purchaseTickets(generateType)
		);
	}

	@Test
	@DisplayName("복수 구매 후 누적 티켓 수 확인")
	void accumulatesTicketsAcrossMultiplePurchases() {
		Money depositPrice = ticketPrice.multiply(5L);
		PurchaseSession session = new PurchaseSession(ticketPrice, depositPrice, lottoMachine);
		List<LottoNumber> numbers = LottoNumber.getLottoNumberCandidates().subList(0, LottoTicket.LOTTO_LENGTH);

		session.purchaseTickets(new ManualGenerateType(List.of(numbers)));
		session.purchaseTickets(new RandomGenerateType(2));

		assertThat(session.getLottoTickets()).hasSize(3);
		assertThat(session.getTotalPrice()).isEqualTo(ticketPrice.multiply(3L));
	}
}
