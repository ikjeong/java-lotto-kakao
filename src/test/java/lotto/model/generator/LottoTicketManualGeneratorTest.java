package lotto.model.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.model.LottoNumber;
import lotto.model.LottoTicket;

public class LottoTicketManualGeneratorTest {

	private LottoTicketManualGenerator generator;

	@BeforeEach
	void setup() {
		generator = new LottoTicketManualGenerator();
	}

	@Test
	@DisplayName("ManualGenerateType 지원 여부 확인")
	void supportsManualGenerateType() {
		ManualGenerateType generateType = new ManualGenerateType(List.of());

		assertThat(generator.supports(generateType)).isTrue();
	}

	@Test
	@DisplayName("RandomGenerateType 미지원 여부 확인")
	void doesNotSupportRandomGenerateType() {
		RandomGenerateType generateType = new RandomGenerateType(1);

		assertThat(generator.supports(generateType)).isFalse();
	}

	@Test
	@DisplayName("복수 수동 번호 목록으로 티켓 여러 장 생성")
	void generateMultipleTicketsWithManualNumbers() {
		List<LottoNumber> firstNumbers = LottoNumber.getLottoNumberCandidates().subList(0, LottoTicket.LOTTO_LENGTH);
		List<LottoNumber> secondNumbers = LottoNumber.getLottoNumberCandidates()
				.subList(LottoTicket.LOTTO_LENGTH, LottoTicket.LOTTO_LENGTH * 2);
		ManualGenerateType generateType = new ManualGenerateType(List.of(firstNumbers, secondNumbers));

		List<LottoTicket> tickets = generator.generate(generateType);

		assertThat(tickets).hasSize(2);
		assertThat(tickets.get(0).getSortedLottoNumbers()).isEqualTo(firstNumbers);
		assertThat(tickets.get(1).getSortedLottoNumbers()).isEqualTo(secondNumbers);
	}

	@Test
	@DisplayName("지원하지 않는 타입으로 생성 시 예외 발생")
	void generateWithUnsupportedTypeThrowsException() {
		RandomGenerateType generateType = new RandomGenerateType(1);

		assertThatIllegalArgumentException().isThrownBy(() ->
				generator.generate(generateType)
		);
	}
}
