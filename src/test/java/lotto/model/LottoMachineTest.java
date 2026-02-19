package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.model.generator.LottoTicketManualGenerator;
import lotto.model.generator.LottoTicketRandomGenerator;
import lotto.model.generator.ManualGenerateType;
import lotto.model.generator.RandomGenerateType;

public class LottoMachineTest {

	@Test
	@DisplayName("RandomGenerateType으로 지정한 수만큼 티켓 발행")
	void issueTicketsWithRandomType() {
		LottoMachine lottoMachine = new LottoMachine(List.of(new LottoTicketRandomGenerator()));
		RandomGenerateType generateType = new RandomGenerateType(3);

		List<LottoTicket> tickets = lottoMachine.issueTickets(generateType);

		assertThat(tickets).hasSize(3);
	}

	@Test
	@DisplayName("ManualGenerateType으로 지정한 번호의 티켓 발행")
	void issueTicketsWithManualType() {
		LottoMachine lottoMachine = new LottoMachine(List.of(new LottoTicketManualGenerator()));
		List<LottoNumber> numbers = LottoNumber.getLottoNumberCandidates().subList(0, LottoTicket.LOTTO_LENGTH);
		ManualGenerateType generateType = new ManualGenerateType(List.of(numbers));

		List<LottoTicket> tickets = lottoMachine.issueTickets(generateType);

		assertThat(tickets).hasSize(1);
		assertThat(tickets.getFirst().getSortedLottoNumbers()).isEqualTo(numbers);
	}

	@Test
	@DisplayName("지원하지 않는 GenerateType으로 발행 시 예외 발생")
	void issueTicketsWithUnsupportedTypeThrowsException() {
		LottoMachine lottoMachine = new LottoMachine(List.of(new LottoTicketRandomGenerator()));
		ManualGenerateType generateType = new ManualGenerateType(List.of());

		assertThatIllegalArgumentException().isThrownBy(() ->
				lottoMachine.issueTickets(generateType)
		);
	}
}
