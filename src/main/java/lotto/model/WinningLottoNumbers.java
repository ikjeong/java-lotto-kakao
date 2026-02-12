package lotto.model;

import java.util.List;

public class WinningLottoNumbers {

	private final List<LottoNumber> normalLottoNumbers;
	private final LottoNumber bonusLottoNumber;

	public WinningLottoNumbers(List<LottoNumber> normalLottoNumbers, LottoNumber bonusLottoNumber) {
		validateBonusInNormal(normalLottoNumbers, bonusLottoNumber);
		this.normalLottoNumbers = normalLottoNumbers;
		this.bonusLottoNumber = bonusLottoNumber;
	}

	public Rank match(LottoTicket myLottoTicket) {
		Integer normalCount = Math.toIntExact(normalLottoNumbers.stream().filter(myLottoTicket::isMatch).count());
		Boolean hasBonus = myLottoTicket.isMatch(bonusLottoNumber);

		return Rank.from(normalCount, hasBonus);
	}

	private void validateBonusInNormal(List<LottoNumber> normalLottoNumbers, LottoNumber bonusLottoNumber) {
		Boolean isBonusInNormal = normalLottoNumbers.stream()
				.anyMatch(bonusLottoNumber::equals);

		if (isBonusInNormal) {
			throw new IllegalArgumentException("보너스 번호는 일반 번호에 포함되지 않아야 합니다.");
		}
	}
}
