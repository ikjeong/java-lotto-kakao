package lotto;

import java.util.List;

public class LottoNumbers {

	private final List<LottoNumber> numbers;

	public LottoNumbers(List<LottoNumber> numbers) {
		Integer numbersLength =  Math.toIntExact(numbers.size());
		if (!numbersLength.equals(LottoConfig.LOTTO_LENGTH)) {
			throw new IllegalArgumentException("로또 길이는 " + LottoConfig.LOTTO_LENGTH + " 이어야합니다");
		}

		Integer distinctCount = Math.toIntExact(numbers.stream()
				.map(LottoNumber::getNumber)
				.distinct()
				.count());
		if (!distinctCount.equals(LottoConfig.LOTTO_LENGTH)) {
			throw new IllegalArgumentException("로또 번호는 중복되지 않아야 합니다.");
		}

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
