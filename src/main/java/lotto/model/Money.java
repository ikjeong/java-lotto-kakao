package lotto.model;

public record Money(long amount) {

	public Money {
		if (amount < 0) {
			throw new IllegalArgumentException("금액은 음수가 될 수 없습니다.");
		}
	}

	public static Money zero() {
		return new Money(0L);
	}

	public Money add(Money target) {
		return new Money(Math.addExact(amount, target.amount));
	}

	public Money subtract(Money target) {
		long result = amount - target.amount;
		if (result < 0) {
			throw new IllegalArgumentException("금액은 음수가 될 수 없습니다.");
		}
		return new Money(result);
	}

	public Money multiply(long factor) {
		if (factor < 0) {
			throw new IllegalArgumentException("곱셈 계수는 음수가 될 수 없습니다.");
		}
		return new Money(Math.multiplyExact(amount, factor));
	}

	public long calculateQuotientDivideBy(Money target) {
		if (target.amount == 0) {
			throw new IllegalArgumentException("0원으로 나눌 수 없습니다.");
		}
		return amount / target.amount;
	}

	public double divideBy(Money target) {
		if (target.amount == 0) {
			throw new IllegalArgumentException("0원으로 나눌 수 없습니다.");
		}
		return ((double) amount) / target.amount();
	}

	public boolean isLessThan(Money target) {
		return amount < target.amount;
	}

	public boolean isZero() {
		return amount == 0;
	}
}
