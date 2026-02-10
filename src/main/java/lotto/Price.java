package lotto;

public enum Price {
	NO_MATCH(0),
	MATCH_3(5_000),
	MATCH_4(50_000),
	MATCH_5(1_500_000),
	MATCH_5_BONUS(30_000_000),
	MATCH_6(2_000_000_000);

	private final Integer amount;

	Price(Integer amount) {
		this.amount = amount;
	}

	public Integer amount() {
		return amount;
	}
}
