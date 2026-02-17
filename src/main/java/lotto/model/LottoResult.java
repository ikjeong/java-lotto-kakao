package lotto.model;

import java.util.List;

public class LottoResult {

	private final Money totalPrice;
	private final List<Rank> ranks;

	public LottoResult(Money totalPrice, List<Rank> ranks) {
		validateTotalPrice(totalPrice);
		validateRanks(ranks);

		this.totalPrice = totalPrice;
		this.ranks = ranks;
	}

	public double calculateReturnRate() {
		Money sumPrize = ranks.stream()
				.map(Rank::prize)
				.reduce(Money.zero(), Money::add);
		return ((double) sumPrize.amount()) / totalPrice.amount();
	}

	public int countRank(Rank targetRank) {
		return Math.toIntExact(ranks.stream().filter(targetRank::equals).count());
	}

	private void validateTotalPrice(Money totalPrice) {
		if (totalPrice.isZero()) {
			throw new IllegalArgumentException("총 구매 금액이 0원보다 높아야 합니다.");
		}
	}

	private void validateRanks(List<Rank> ranks){
		if (ranks.isEmpty()){
			throw new IllegalArgumentException("등수 정보는 1개 이상이어야합니다.");
		}
	}
}
