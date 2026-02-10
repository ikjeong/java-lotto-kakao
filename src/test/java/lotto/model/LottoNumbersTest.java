package lotto.model;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.LottoConfig;

public class LottoNumbersTest {

	private List<LottoNumber> numbers;

	@BeforeEach
	void setup() {
		numbers = generateLottoNumbers(1, LottoConfig.LOTTO_LENGTH);
	}

	List<LottoNumber> generateLottoNumbers(Integer startNumber, Integer lottoLength) {
		List<LottoNumber> lottoNumbers = new ArrayList<>();
		for (Integer number = startNumber; number < startNumber+lottoLength; number++) {
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
		List<LottoNumber> targetNumbers = generateLottoNumbers(4, LottoConfig.LOTTO_LENGTH);
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
		List<LottoNumber> shortNumbers = generateLottoNumbers(1, LottoConfig.LOTTO_LENGTH-1);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoNumbers shortLottoNumbers = new LottoNumbers(shortNumbers);
		});
	}

	@Test
	@DisplayName("LottoNumbers 생성 시 길이 검증 테스트(긴 길이)")
	void validateLongLottoNumbersLength(){
		List<LottoNumber> longNumbers = generateLottoNumbers(1, LottoConfig.LOTTO_LENGTH+1);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoNumbers longtLottoNumbers = new LottoNumbers(longNumbers);
		});
	}

	@Test
	@DisplayName("LottoNumbers 중복된 숫자로 생성시 예외 발생")
	void validateDuplicateLottoNumber() {
		List<LottoNumber> duplicateNumbers = generateLottoNumbers(1, LottoConfig.LOTTO_LENGTH-1);
		duplicateNumbers.add(new LottoNumber(1));

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoNumbers duplicateLottoNumbers = new LottoNumbers(duplicateNumbers);
		});
	}
}
