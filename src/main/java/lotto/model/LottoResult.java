package lotto.model;

import java.util.List;

public class LottoResult {

	private final List<Rank> ranks;

	public LottoResult(List<Rank> ranks) {
		this.ranks = ranks;
	}

	public double calculateReturnRate() {
		Money sumPrize = new Money(ranks.stream().map(Rank::prize).reduce(0, Integer::sum));
		Money pay = new Money(LottoMachine.LOTTO_TICKET_PRICE.amount() * ranks.size());
		return ((double) sumPrize.amount()) / pay.amount();
	}

	public int countRank(Rank targetRank) {
		return Math.toIntExact(ranks.stream().filter(targetRank::equals).count());
	}
}
