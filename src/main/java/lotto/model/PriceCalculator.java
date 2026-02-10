package lotto.model;

public class PriceCalculator {

	public Price calculate(MatchingCountInfo matchingCountInfo) {
		Integer normalCount = matchingCountInfo.normalCount();
		Boolean hasBonus = matchingCountInfo.hasBonus();

		if (normalCount.equals(6)) return Price.MATCH_6;
		if (normalCount.equals(5) && hasBonus) return Price.MATCH_5_BONUS;
		if (normalCount.equals(5)) return Price.MATCH_5;
		if (normalCount.equals(4)) return Price.MATCH_4;
		if (normalCount.equals(3)) return Price.MATCH_3;
		return Price.NO_MATCH;
	}
}
