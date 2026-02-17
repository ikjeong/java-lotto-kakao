package lotto.model;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoMachineTest {

	@Test
	@DisplayName("잔액 증가 테스트")
	void validateDeposit() {
		int purchasePrice = 1_500;
		LottoTicketRandomGenerator lottoTicketRandomGenerator = new LottoTicketRandomGenerator();
		LottoMachine lottoMachine = new LottoMachine(new Money(1_000), lottoTicketRandomGenerator);

		lottoMachine.deposit(new Money(purchasePrice));
		assertThat(lottoMachine.getBalance()).isEqualTo(new Money(purchasePrice));
		lottoMachine.deposit(new Money(purchasePrice));
		assertThat(lottoMachine.getBalance()).isEqualTo(new Money(purchasePrice * 2));
	}

	@Test
	@DisplayName("구매 가격에 따른 티켓 발행 개수 및 잔액 확인")
	void validateLottoTicketCountByPurchasePrice() {
		int ticketPrice = 1_000;
		Money purchasePrice = new Money(ticketPrice * 14 + (ticketPrice-1));
		LottoTicketRandomGenerator lottoTicketRandomGenerator = new LottoTicketRandomGenerator();
		LottoMachine lottoMachine = new LottoMachine(new Money(ticketPrice), lottoTicketRandomGenerator);

		lottoMachine.deposit(purchasePrice);
		LottoMachineGeneratedResult machineGeneratedResult = lottoMachine.generate();
		assertThat(machineGeneratedResult.totalPrice()).isEqualTo(new Money(ticketPrice * 14));
		assertThat(machineGeneratedResult.lottoTickets().size()).isEqualTo(14);
		Money balance = lottoMachine.getBalance();
		assertThat(balance).isEqualTo(new Money(purchasePrice.amount() - machineGeneratedResult.totalPrice().amount()));
	}

	@Test
	@DisplayName("티켓 최소 구매 금액 미만 예외 처리")
	void validateMinimumPurchasePrice() {
		int ticketPrice = 1_000;
		Money purchasePrice = new Money(ticketPrice-1);
		LottoTicketRandomGenerator lottoTicketRandomGenerator = new LottoTicketRandomGenerator();
		LottoMachine lottoMachine = new LottoMachine(new Money(ticketPrice), lottoTicketRandomGenerator);

		lottoMachine.deposit(purchasePrice);
		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoMachineGeneratedResult machineGeneratedResult = lottoMachine.generate();
		});
	}
}
