package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.LottoConfig;

public class LottoTicketRandomGeneratorTest {

	@Test
	@DisplayName("같은 시드에서 같은 결과값을 반환")
	void validateSameRandom() {
		Random random = new Random(20260105);
		Random targetRandom = new Random(20260105);

		LottoTicketRandomGenerator generator = new LottoTicketRandomGenerator(random);
		LottoTicketRandomGenerator targetGenerator = new LottoTicketRandomGenerator(targetRandom);

		LottoTicket lottoTicket = generator.generate();
		LottoTicket targetLottoTicket = targetGenerator.generate();

		assertThat(lottoTicket.equals(targetLottoTicket)).isTrue();
	}

	@Test
	@DisplayName("다른 시드에서 다른 결과값을 반환")
	void validateDifferentRandom() {
		Random random = new Random(20260105);
		Random targetRandom = new Random(20260210);

		LottoTicketRandomGenerator generator = new LottoTicketRandomGenerator(random);
		LottoTicketRandomGenerator targetGenerator = new LottoTicketRandomGenerator(targetRandom);

		LottoTicket lottoTicket = generator.generate();
		LottoTicket targetLottoTicket = targetGenerator.generate();

		assertThat(lottoTicket.equals(targetLottoTicket)).isFalse();
	}
}



