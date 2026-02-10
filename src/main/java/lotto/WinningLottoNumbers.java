package lotto;

public class WinningLottoNumbers {

	private final LottoNumbers lottoNumbers;
	private final LottoNumber bonusNumber;

	public WinningLottoNumbers(LottoNumbers lottoNumbers, LottoNumber bonusNumber) {
		this.lottoNumbers = lottoNumbers;
		this.bonusNumber = bonusNumber;
	}

	public MatchingCountInfo countMatchingNumber(LottoNumbers myLottoNumbers) {
		Integer normalCount = lottoNumbers.countMatchNumber(myLottoNumbers);
		Boolean hasBonus = myLottoNumbers.isMatch(bonusNumber);

		return new MatchingCountInfo(normalCount,hasBonus);
	}
}
