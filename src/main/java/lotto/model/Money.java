package lotto.model;

public record Money(int amount) {

	public Money {
		if (amount < 0) {
			throw new IllegalArgumentException("금액은 음수가 될 수 없습니다.");
		}
	}
}
