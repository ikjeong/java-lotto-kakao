package lotto.model;

public enum Rank {
	MISS(0, false, 0),
	FIFTH(3, false, 5_000),
	FOURTH(4, false, 50_000),
	THIRD(5, false, 1_500_000),
	SECOND(5, true, 30_000_000),
	FIRST(6, false, 2_000_000_000);

	private final Integer normalCount;
	private final Boolean bonus;
	private final Integer prize;

	Rank(Integer normalCount, Boolean bonus, Integer prize) {
		this.normalCount = normalCount;
		this.bonus = bonus;
		this.prize = prize;
	}

	public static Rank from(Integer normalCount, Boolean hasBonus) {
		if (normalCount == 6) return FIRST;
		if (normalCount == 5 && hasBonus) return SECOND;
		if (normalCount == 5) return THIRD;
		if (normalCount == 4) return FOURTH;
		if (normalCount == 3) return FIFTH;
		return MISS;
	}

	public Integer normalCount() {
		return normalCount;
	}

	public Boolean bonus() {
		return bonus;
	}

	public Integer prize() {
		return prize;
	}
}
