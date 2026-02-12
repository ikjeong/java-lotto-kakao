package lotto.model;

import java.util.List;

public class LottoResult {

	private final List<Rank> ranks;

	public LottoResult(List<Rank> ranks) {
		this.ranks = ranks;
	}

	public double calculateReturnRate() {
		int sumPrize = ranks.stream().map(Rank::prize).reduce(0, Integer::sum);
		int pay = LottoMachine.LOTTO_TICKET_PRICE * ranks.size();
		return ((double) sumPrize) / pay;
	}

	public int countRank(Rank targetRank) {
		return Math.toIntExact(ranks.stream().filter(targetRank::equals).count());
	}
}
