package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoNumbersTest {

	private List<LottoNumber> numbers;

	@BeforeEach
	void setup() {
		numbers = List.of(
				new LottoNumber(1),
				new LottoNumber(2),
				new LottoNumber(3),
				new LottoNumber(4),
				new LottoNumber(5),
				new LottoNumber(6)
		);
	}

	@Test
	@DisplayName("LottoNumber 포함 테스트")
	void matchLottoNumbers() {
		LottoNumbers lottoNumbers = new LottoNumbers(numbers);
		LottoNumber matchNumber = new LottoNumber(1);

		Boolean isMatch = lottoNumbers.isMatch(matchNumber);
		assertThat(isMatch).isTrue();
	}

	@Test
	@DisplayName("LottoNumber 미포함 테스트")
	void nonMatchLottoNumbers() {
		LottoNumbers lottoNumbers = new LottoNumbers(numbers);
		LottoNumber nonMatchNumber =  new LottoNumber(7);

		Boolean isMatch = lottoNumbers.isMatch(nonMatchNumber);
		assertThat(isMatch).isFalse();
	}

	@Test
	@DisplayName("LottoNumbers 간 일치하는 숫자 개수 테스트")
	void countMatchLottoNumber() {
		List<LottoNumber> targetNumbers;
		targetNumbers = List.of(
				new LottoNumber(4),
				new LottoNumber(5),
				new LottoNumber(6),
				new LottoNumber(7),
				new LottoNumber(8),
				new LottoNumber(9)
		);
		LottoNumbers lottoNumbers = new LottoNumbers(numbers);
		LottoNumbers targetLottoNumbers = new LottoNumbers(targetNumbers);

		Integer count = lottoNumbers.countMatchNumber(targetLottoNumbers);
		assertThat(count).isEqualTo(3);
	}
}
