package lotto.model;

public class LottoNumber {

	private final Integer number;

	public LottoNumber(Integer number) {
		if (1 > number || number > 45) {
			throw new IllegalArgumentException("로또는 1부터 45 이내의 숫자이어야 합니다.");
		}
		this.number = number;
	}

	public Integer getNumber() {
		return number;
	}

	public Boolean isEqual(LottoNumber targetNumber) {
		return number.equals(targetNumber.getNumber());
	}
}
