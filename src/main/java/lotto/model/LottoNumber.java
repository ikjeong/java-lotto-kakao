package lotto.model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoNumber {

	private static final int MINIMUM = 1;
	private static final int MAXIMUM = 45;

	private final int number;

	private static final Map<Integer, LottoNumber> lottoNumberPool = IntStream.rangeClosed(MINIMUM, MAXIMUM)
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

	public static List<LottoNumber> getLottoNumberCandidates() {
		return lottoNumberPool.values().stream()
				.sorted(Comparator.comparingInt(LottoNumber::getNumber))
				.toList();
	}

	public int getNumber() {
		return number;
	}

	private static void validateNumberRange(int targetNumber) {
		if (MINIMUM > targetNumber || targetNumber > MAXIMUM) {
			throw new IllegalArgumentException("로또는 " + MINIMUM + "부터 " + MAXIMUM + " 이내의 숫자이어야 합니다.");
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
