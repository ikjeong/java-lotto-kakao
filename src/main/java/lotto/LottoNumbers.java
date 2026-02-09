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


}
