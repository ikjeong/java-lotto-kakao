package lotto.model;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoTicketTest {

	private List<LottoNumber> lottoNumberCandidates;
	private List<LottoNumber> numbers;

	@BeforeEach
	void setup() {
		lottoNumberCandidates = LottoNumber.getLottoNumberCandidates();
		numbers = pickFirstNumbers(LottoTicket.LOTTO_LENGTH);
	}

	List<LottoNumber> pickFirstNumbers(int count) {
		return new ArrayList<>(lottoNumberCandidates.subList(0, count));
	}

	@Test
	@DisplayName("LottoNumber 포함 테스트")
	void matchLottoNumber() {
		LottoTicket lottoTicket = new LottoTicket(numbers);
		LottoNumber matchNumber = numbers.getFirst();

		boolean isMatch = lottoTicket.isMatch(matchNumber);
		assertThat(isMatch).isTrue();
	}

	@Test
	@DisplayName("LottoNumber 미포함 테스트")
	void nonMatchLottoNumber() {
		LottoTicket lottoTicket = new LottoTicket(numbers);
		LottoNumber nonMatchNumber = pickAnyNumberNotIn(numbers);

		boolean isMatch = lottoTicket.isMatch(nonMatchNumber);
		assertThat(isMatch).isFalse();
	}

	LottoNumber pickAnyNumberNotIn(List<LottoNumber> targetNumbers) {
		return lottoNumberCandidates.stream()
				.filter(candidate -> !targetNumbers.contains(candidate))
				.findFirst()
				.orElseThrow();
	}

	@Test
	@DisplayName("LottoTicket 생성 시 길이 검증 테스트(정상 길이)")
	void validateCorrectLottoNumbersLength(){
		assertThatNoException().isThrownBy(()->{
			LottoTicket lottoTicket = new LottoTicket(numbers);
		});
	}

	@Test
	@DisplayName("LottoTicket 생성 시 길이 검증 테스트(짧은 길이)")
	void validateShortLottoNumbersLength(){
		List<LottoNumber> shortNumbers = pickFirstNumbers(LottoTicket.LOTTO_LENGTH - 1);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoTicket shortLottoTicket = new LottoTicket(shortNumbers);
		});
	}

	@Test
	@DisplayName("LottoTicket 생성 시 길이 검증 테스트(긴 길이)")
	void validateLongLottoNumbersLength(){
		List<LottoNumber> longNumbers = pickFirstNumbers(LottoTicket.LOTTO_LENGTH + 1);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoTicket longtLottoTicket = new LottoTicket(longNumbers);
		});
	}

	@Test
	@DisplayName("LottoTicket 중복된 숫자로 생성시 예외 발생")
	void validateDuplicateLottoNumber() {
		List<LottoNumber> duplicateNumbers = pickFirstNumbers(LottoTicket.LOTTO_LENGTH - 1);
		duplicateNumbers.add(duplicateNumbers.getFirst());

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoTicket duplicateLottoTicket = new LottoTicket(duplicateNumbers);
		});
	}
}
