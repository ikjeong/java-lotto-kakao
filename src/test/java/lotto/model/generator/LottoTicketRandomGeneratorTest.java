package lotto.model.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.model.LottoTicket;

public class LottoTicketRandomGeneratorTest {

	@Test
	@DisplayName("같은 시드에서 같은 결과값을 반환")
	void validateSameRandom() {
		Random random = new Random(20260105);
		Random targetRandom = new Random(20260105);

		LottoTicketRandomGenerator generator = new LottoTicketRandomGenerator(random);
		LottoTicketRandomGenerator targetGenerator = new LottoTicketRandomGenerator(targetRandom);
		RandomGenerateType randomPurchaseType = new RandomGenerateType(1);

		LottoTicket lottoTicket = generator.generate(randomPurchaseType).getFirst();
		LottoTicket targetLottoTicket = targetGenerator.generate(randomPurchaseType).getFirst();

		assertThat(lottoTicket.equals(targetLottoTicket)).isTrue();
	}

	@Test
	@DisplayName("다른 시드에서 다른 결과값을 반환")
	void validateDifferentRandom() {
		Random random = new Random(20260105);
		Random targetRandom = new Random(20260210);

		LottoTicketRandomGenerator generator = new LottoTicketRandomGenerator(random);
		LottoTicketRandomGenerator targetGenerator = new LottoTicketRandomGenerator(targetRandom);
		RandomGenerateType randomPurchaseType = new RandomGenerateType(1);

		LottoTicket lottoTicket = generator.generate(randomPurchaseType).getFirst();
		LottoTicket targetLottoTicket = targetGenerator.generate(randomPurchaseType).getFirst();

		assertThat(lottoTicket.equals(targetLottoTicket)).isFalse();
	}

	@Test
	@DisplayName("RandomGenerateType 지원 여부 확인")
	void supportsRandomGenerateType() {
		LottoTicketRandomGenerator generator = new LottoTicketRandomGenerator();
		RandomGenerateType generateType = new RandomGenerateType(1);

		assertThat(generator.supports(generateType)).isTrue();
	}

	@Test
	@DisplayName("ManualGenerateType 미지원 여부 확인")
	void doesNotSupportManualGenerateType() {
		LottoTicketRandomGenerator generator = new LottoTicketRandomGenerator();
		ManualGenerateType generateType = new ManualGenerateType(List.of());

		assertThat(generator.supports(generateType)).isFalse();
	}

	@Test
	@DisplayName("count만큼 티켓 생성")
	void generateTicketCountMatchesRequestedCount() {
		LottoTicketRandomGenerator generator = new LottoTicketRandomGenerator();
		RandomGenerateType generateType = new RandomGenerateType(5);

		List<LottoTicket> tickets = generator.generate(generateType);

		assertThat(tickets).hasSize(5);
	}

	@Test
	@DisplayName("지원하지 않는 타입으로 생성 시 예외 발생")
	void generateWithUnsupportedTypeThrowsException() {
		LottoTicketRandomGenerator generator = new LottoTicketRandomGenerator();
		ManualGenerateType generateType = new ManualGenerateType(List.of());

		assertThatIllegalArgumentException().isThrownBy(() ->
				generator.generate(generateType)
		);
	}
}



