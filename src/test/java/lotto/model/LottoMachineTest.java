package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoMachineTest {

	@Test
	@DisplayName("구매 가격에 따른 티켓 발행 개수 확인")
	void validateLottoTicketCountByPurchasePrice() {
		int ticketPrice = 1_000;
		Money purchasePrice = new Money(ticketPrice * 14 + (ticketPrice-1));
		LottoTicketRandomGenerator lottoTicketRandomGenerator = new LottoTicketRandomGenerator();
		LottoMachine lottoMachine = new LottoMachine(new Money(ticketPrice), lottoTicketRandomGenerator);

		LottoMachineGeneratedResult machineGeneratedResult = lottoMachine.generate(purchasePrice);
		assertThat(machineGeneratedResult.lottoTickets().size()).isEqualTo(14);
	}

	@Test
	@DisplayName("티켓 최소 구매 금액 미만 예외 처리")
	void validateMinimumPurchasePrice() {
		int ticketPrice = 1_000;
		Money purchasePrice = new Money(ticketPrice-1);
		LottoTicketRandomGenerator lottoTicketRandomGenerator = new LottoTicketRandomGenerator();
		LottoMachine lottoMachine = new LottoMachine(new Money(ticketPrice), lottoTicketRandomGenerator);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoMachineGeneratedResult machineGeneratedResult = lottoMachine.generate(purchasePrice);
		});
	}
}
