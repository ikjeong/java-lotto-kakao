package lotto.model;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoMachineTest {

	int ticketPrice = 1_000;
	LottoMachine lottoMachine;

	@BeforeEach
	void setup() {
		LottoTicketRandomGenerator lottoTicketRandomGenerator = new LottoTicketRandomGenerator();
		lottoMachine = new LottoMachine(new Money(ticketPrice), lottoTicketRandomGenerator);
	}

	@Test
	@DisplayName("잔액 증가 테스트")
	void validateDeposit() {
		int purchasePrice = 1_500;

		lottoMachine.deposit(new Money(purchasePrice));
		assertThat(lottoMachine.getBalance()).isEqualTo(new Money(purchasePrice));
		lottoMachine.deposit(new Money(purchasePrice));
		assertThat(lottoMachine.getBalance()).isEqualTo(new Money(purchasePrice * 2));
	}

	@Test
	@DisplayName("구매 가격에 따른 티켓 발행 개수 및 잔액 확인")
	void validateLottoTicketCountByPurchasePrice() {
		Money purchasePrice = new Money(ticketPrice * 14 + (ticketPrice-1));
		lottoMachine.deposit(purchasePrice);

		PurchasedTickets purchasedTickets = lottoMachine.purchaseAutoTickets();
		Money totalPrice = purchasedTickets.totalPrice();
		int buyLottoTicketNumber = purchasedTickets.lottoTickets().size();
		Money balance = lottoMachine.getBalance();
		assertThat(totalPrice).isEqualTo(new Money(ticketPrice * 14));
		assertThat(buyLottoTicketNumber).isEqualTo(14);
		assertThat(balance).isEqualTo(new Money(purchasePrice.amount() - totalPrice.amount()));
	}

	@Test
	@DisplayName("티켓 최소 구매 금액 미만인 경우 티켓 발행 개수 및 잔액 확인")
	void validateMinimumPurchasePrice() {
		Money purchasePrice = new Money(ticketPrice-1);
		lottoMachine.deposit(purchasePrice);

		PurchasedTickets purchasedTickets = lottoMachine.purchaseAutoTickets();
		Money totalPrice = purchasedTickets.totalPrice();
		int buyLottoTicketNumber = purchasedTickets.lottoTickets().size();
		Money balance = lottoMachine.getBalance();
		assertThat(totalPrice).isEqualTo(new Money(0));
		assertThat(buyLottoTicketNumber).isEqualTo(0);
		assertThat(balance).isEqualTo(new Money(purchasePrice.amount()));
	}

	@Test
	@DisplayName("수동 티켓 구매 검증 및 잔액 확인")
	void validatePurchaseManualTicket() {
		Money purchasePrice = new Money(ticketPrice);
		lottoMachine.deposit(purchasePrice);
		List<LottoNumber> targetLottoNumbers = List.of(
				LottoNumber.of(1),
				LottoNumber.of(2),
				LottoNumber.of(3),
				LottoNumber.of(4),
				LottoNumber.of(5),
				LottoNumber.of(6)
		);

		PurchasedTickets purchasedTickets = lottoMachine.purchaseManualTicket(targetLottoNumbers);
		Money totalPrice = purchasedTickets.totalPrice();
		List<LottoTicket> lottoTickets = purchasedTickets.lottoTickets();
		Money balance = lottoMachine.getBalance();
		assertThat(totalPrice).isEqualTo(new Money(ticketPrice));
		assertThat(lottoTickets.size()).isEqualTo(1);
		assertThat(balance).isEqualTo(new Money(0));
		List<LottoNumber> lottoNumbers = lottoTickets.getFirst().getSortedLottoNumbers();
		for (int i = 0; i < 6; i++) {
			assertThat(lottoNumbers.get(i)).isEqualTo(targetLottoNumbers.get(i));
		}
	}

	@Test
	@DisplayName("잔액 부족한 경우 수동 티켓 구매 예외 발생")
	void validatePurchaseManualTicketWithInsufficientBalance() {
		Money purchasePrice = new Money(ticketPrice-1);
		lottoMachine.deposit(purchasePrice);
		List<LottoNumber> targetLottoNumbers = List.of(
				LottoNumber.of(1),
				LottoNumber.of(2),
				LottoNumber.of(3),
				LottoNumber.of(4),
				LottoNumber.of(5),
				LottoNumber.of(6)
		);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			PurchasedTickets purchasedTickets = lottoMachine.purchaseManualTicket(targetLottoNumbers);
		});
	}
}
