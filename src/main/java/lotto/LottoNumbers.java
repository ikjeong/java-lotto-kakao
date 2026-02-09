package lotto;

import java.util.List;

public class LottoNumbers {

	private final List<LottoNumber> numbers;

	public LottoNumbers(List<LottoNumber> numbers) {
		this.numbers = numbers.stream()
				.map(number -> new LottoNumber(number.getNumber()))
				.toList();
	}

	public Boolean isMatch(LottoNumber compareNumber) {
		return numbers.stream()
				.anyMatch(number -> number.isEqual(compareNumber));
	}

	public Integer countMatchNumber(LottoNumbers compareLottoNumbers) {
		return Math.toIntExact(
				numbers.stream()
						.filter(compareLottoNumbers::isMatch)
						.count()
		);
	}
}
