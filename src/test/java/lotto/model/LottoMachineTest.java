package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;

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
		LottoMachineGeneratedResult machineGeneratedResult = lottoMachine.generate();
		Money totalPrice = machineGeneratedResult.totalPrice();
		int buyLottoTicketNumber = machineGeneratedResult.lottoTickets().size();
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
		LottoMachineGeneratedResult machineGeneratedResult = lottoMachine.generate();
		Money totalPrice = machineGeneratedResult.totalPrice();
		int buyLottoTicketNumber = machineGeneratedResult.lottoTickets().size();
		Money balance = lottoMachine.getBalance();
		assertThat(totalPrice).isEqualTo(new Money(0));
		assertThat(buyLottoTicketNumber).isEqualTo(0);
		assertThat(balance).isEqualTo(new Money(purchasePrice.amount()));
	}
}
