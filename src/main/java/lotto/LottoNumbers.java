package lotto;

import java.util.List;

public class LottoNumbers {

	private final List<LottoNumber> numbers;

	public LottoNumbers(List<LottoNumber> numbers) {
		this.numbers = numbers.stream()
				.map(number -> new LottoNumber(number.getNumber()))
				.toList();
	}

	public Boolean isMatch(LottoNumber targetNumber) {
		return numbers.stream()
				.anyMatch(number -> number.isEqual(targetNumber));
	}

	public Integer countMatchNumber(LottoNumbers targetNumbers) {
		return Math.toIntExact(
				numbers.stream()
						.filter(targetNumbers::isMatch)
						.count()
		);
	}
}
