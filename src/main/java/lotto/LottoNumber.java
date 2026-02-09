package lotto;

public class LottoNumber {

	private final Integer number;

	public LottoNumber(Integer number) {
		this.number = number;
	}

	public Integer getNumber() {
		return number;
	}

	public boolean isEqual(LottoNumber lottoNumber) {
		return number.equals(lottoNumber.getNumber());
	}
}
