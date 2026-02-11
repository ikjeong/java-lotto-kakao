package lotto.model;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lotto.LottoConfig;

public class LottoTicket {

	private final List<LottoNumber> numbers;

	public LottoTicket(List<LottoNumber> numbers) {
		validateLength(numbers);
		validateDuplication(numbers);

		this.numbers = numbers.stream()
				.map(number -> new LottoNumber(number.getNumber()))
				.sorted(Comparator.comparing(LottoNumber::getNumber))
				.toList();
	}

	public Boolean isMatch(LottoNumber targetNumber) {
		return numbers.stream()
				.anyMatch(number -> number.isEqual(targetNumber));
	}

	private void validateLength(List<LottoNumber> numbers) {
		Integer numbersLength =  Math.toIntExact(numbers.size());
		if (!numbersLength.equals(LottoConfig.LOTTO_LENGTH)) {
			throw new IllegalArgumentException("로또 길이는 " + LottoConfig.LOTTO_LENGTH + " 이어야합니다");
		}
	}

	private void validateDuplication(List<LottoNumber> numbers) {
		Integer distinctCount = Math.toIntExact(numbers.stream()
				.map(LottoNumber::getNumber)
				.distinct()
				.count());
		if (!distinctCount.equals(LottoConfig.LOTTO_LENGTH)) {
			throw new IllegalArgumentException("로또 번호는 중복되지 않아야 합니다.");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LottoTicket)) return false;
		LottoTicket targetLottoTicket = (LottoTicket) o;
		Long matchCount = numbers.stream().filter(targetLottoTicket::isMatch).count();
		return matchCount == numbers.size();
	}

	@Override
	public int hashCode() {
		return Objects.hash(numbers.stream().map(LottoNumber::getNumber).sorted().toArray());
	}

	@Override
	public String toString() {
		return numbers.stream()
				.map(LottoNumber::getNumber)
				.map(String::valueOf)
				.collect(Collectors.joining(", ", "[", "]"));
	}
}
