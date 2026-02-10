package lotto.model;

public class WinningLottoNumbers {

	private final LottoNumbers lottoNumbers;
	private final LottoNumber bonusNumber;

	public WinningLottoNumbers(LottoNumbers lottoNumbers, LottoNumber bonusNumber) {
		if (lottoNumbers.isMatch(bonusNumber)) {
			throw new IllegalArgumentException("보너스 번호는 일반 번호에 포함되지 않아야 합니다.");
		}

		this.lottoNumbers = lottoNumbers;
		this.bonusNumber = bonusNumber;
	}

	public Rank match(LottoNumbers myLottoNumbers) {
		Integer normalCount = lottoNumbers.countMatchNumber(myLottoNumbers);
		Boolean hasBonus = myLottoNumbers.isMatch(bonusNumber);

		return Rank.from(normalCount, hasBonus);
	}
}
