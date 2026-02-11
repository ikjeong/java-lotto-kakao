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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LottoNumber targetLottoNumber)) return false;
		return number.equals(targetLottoNumber.getNumber());
	}

	@Override
	public int hashCode() {
		return number;
	}
}
