package lotto.model;

public enum Rank {
	MISS(0, false, new Money(0L)),
	FIFTH(3, false, new Money(5_000L)),
	FOURTH(4, false, new Money(50_000L)),
	THIRD(5, false, new Money(1_500_000L)),
	SECOND(5, true, new Money(30_000_000L)),
	FIRST(6, false, new Money(2_000_000_000L));

	private final int normalCount;
	private final boolean bonus;
	private final Money prize;

	Rank(int normalCount, boolean bonus, Money prize) {
		this.normalCount = normalCount;
		this.bonus = bonus;
		this.prize = prize;
	}

	public static Rank from(int normalCount, boolean hasBonus) {
		if (normalCount == 6) return FIRST;
		if (normalCount == 5 && hasBonus) return SECOND;
		if (normalCount == 5) return THIRD;
		if (normalCount == 4) return FOURTH;
		if (normalCount == 3) return FIFTH;
		return MISS;
	}

	public int normalCount() {
		return normalCount;
	}

	public boolean bonus() {
		return bonus;
	}

	public Money prize() {
		return prize;
	}
}
