package lotto.model;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoNumber {

	private final int number;

	private static final Map<Integer, LottoNumber> lottoNumberPool = IntStream.rangeClosed(1, 45)
			.mapToObj(LottoNumber::new)
			.collect(Collectors.toUnmodifiableMap(
					LottoNumber::getNumber,
					Function.identity()
			));

	private LottoNumber(int number) {
		validateNumberRange(number);
		this.number = number;
	}

	public static LottoNumber of(int number) {
		validateNumberRange(number);
		return lottoNumberPool.get(number);
	}

	public int getNumber() {
		return number;
	}

	private static void validateNumberRange(int targetNumber) {
		if (1 > targetNumber || targetNumber > 45) {
			throw new IllegalArgumentException("로또는 1부터 45 이내의 숫자이어야 합니다.");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LottoNumber targetLottoNumber)) return false;
		return number == targetLottoNumber.getNumber();
	}

	@Override
	public int hashCode() {
		return number;
	}
}
