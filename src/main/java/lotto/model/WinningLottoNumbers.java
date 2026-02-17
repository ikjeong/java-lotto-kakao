package lotto.model;

import java.util.List;
import java.util.Set;

public class WinningLottoNumbers {

	private final Set<LottoNumber> normalLottoNumbers;
	private final LottoNumber bonusLottoNumber;

	public WinningLottoNumbers(List<LottoNumber> normalLottoNumbers, LottoNumber bonusLottoNumber) {
		validateNormalLottoNumbersLength(normalLottoNumbers);
		Set<LottoNumber> normalLottoNumbersSet = Set.copyOf(normalLottoNumbers);
		validateDuplicateNormalLottoNumbers(normalLottoNumbersSet);
		validateBonusInNormal(normalLottoNumbersSet, bonusLottoNumber);
		this.normalLottoNumbers = normalLottoNumbersSet;
		this.bonusLottoNumber = bonusLottoNumber;
	}

	public Rank match(LottoTicket myLottoTicket) {
		int normalCount = Math.toIntExact(normalLottoNumbers.stream().filter(myLottoTicket::isMatch).count());
		boolean hasBonus = myLottoTicket.isMatch(bonusLottoNumber);

		return Rank.from(normalCount, hasBonus);
	}

	private void validateNormalLottoNumbersLength(List<LottoNumber> normalLottoNumbers) {
		if (normalLottoNumbers.size() != LottoTicket.LOTTO_LENGTH) {
			throw new IllegalArgumentException("당첨 번호 개수는 로또 티켓의 번호 개수와 같아야 합니다.");
		}
	}

	private void validateDuplicateNormalLottoNumbers(Set<LottoNumber> normalLottoNumbers) {
		if (normalLottoNumbers.size() != LottoTicket.LOTTO_LENGTH) {
			throw new IllegalArgumentException("당첨 번호는 중복되지 않아야 합니다.");
		}
	}

	private void validateBonusInNormal(Set<LottoNumber> normalLottoNumbers, LottoNumber bonusLottoNumber) {
		boolean isBonusInNormal = normalLottoNumbers.contains(bonusLottoNumber);

		if (isBonusInNormal) {
			throw new IllegalArgumentException("보너스 번호는 일반 번호에 포함되지 않아야 합니다.");
		}
	}
}
