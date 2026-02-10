package lotto.model;

public record MatchingCountInfo(Integer normalCount, Boolean hasBonus) {

	@Override
	public String toString() {
		if (hasBonus) {
			return "일반 번호: " + normalCount + "개 당첨, 보너스 번호 당첨";
		}
		return "일반 번호: " + normalCount + "개 당첨, 보너스 번호 미당첨";
	}
}
