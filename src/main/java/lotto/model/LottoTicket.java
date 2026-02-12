package lotto.model;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class LottoTicket {

	public static final int LOTTO_LENGTH = 6;

	private final Set<LottoNumber> lottoNumbers;

	public LottoTicket(List<LottoNumber> numbers) {
		validateLength(numbers);
		Set<LottoNumber> lottoNumbers = Set.copyOf(numbers);
		validateDuplicate(lottoNumbers);
		this.lottoNumbers = lottoNumbers;
	}

	public boolean isMatch(LottoNumber targetNumber) {
		return lottoNumbers.contains(targetNumber);
	}

	public List<LottoNumber> getSortedLottoNumbers() {
		return lottoNumbers.stream().sorted(Comparator.comparing(LottoNumber::getNumber)).toList();
	}

	private void validateLength(List<LottoNumber> lottoNumbers) {
		int numbersLength =  lottoNumbers.size();
		if (numbersLength != LOTTO_LENGTH) {
			throw new IllegalArgumentException("로또 번호는 " + LOTTO_LENGTH + "개로 이루어져야 합니다.");
		}
	}

	private void validateDuplicate(Set<LottoNumber> lottoNumbers) {
		int numbersLength = lottoNumbers.size();
		if (numbersLength != LOTTO_LENGTH) {
			throw new IllegalArgumentException("로또 번호는 중복되지 않아야 합니다.");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LottoTicket targetLottoTicket)) return false;
		long matchCount = lottoNumbers.stream().filter(targetLottoTicket::isMatch).count();
		return matchCount == lottoNumbers.size();
	}

	@Override
	public int hashCode() {
		return Objects.hash(lottoNumbers.stream().map(LottoNumber::getNumber).sorted().toArray());
	}
}
