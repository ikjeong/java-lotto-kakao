package lotto;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoNumbersTest {

	private List<LottoNumber> numbers;

	@BeforeEach
	void setup() {
		numbers = generateLottoNumbers(LottoConfig.LOTTO_LENGTH);
	}

	List<LottoNumber> generateLottoNumbers(Integer lottoLength) {
		List<LottoNumber> lottoNumbers = new ArrayList<>();
		for (Integer number =1; number <= lottoLength; number++) {
			lottoNumbers.add(new LottoNumber(number));
		}
		return lottoNumbers;
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

	@Test
	@DisplayName("LottoNumbers 생성 시 길이 검증 테스트(정상 길이)")
	void validateCorrectLottoNumbersLength(){
		assertThatNoException().isThrownBy(()->{
			LottoNumbers lottoNumbers = new LottoNumbers(numbers);
		});
	}

	@Test
	@DisplayName("LottoNumbers 생성 시 길이 검증 테스트(짧은 길이)")
	void validateShortLottoNumbersLength(){
		List<LottoNumber> shortNumbers = generateLottoNumbers(LottoConfig.LOTTO_LENGTH-1);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoNumbers shortLottoNumbers = new LottoNumbers(shortNumbers);
		});
	}

	@Test
	@DisplayName("LottoNumbers 생성 시 길이 검증 테스트(긴 길이)")
	void validateLongLottoNumbersLength(){
		List<LottoNumber> longNumbers = generateLottoNumbers(LottoConfig.LOTTO_LENGTH+1);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoNumbers longtLottoNumbers = new LottoNumbers(longNumbers);
		});
	}
}
