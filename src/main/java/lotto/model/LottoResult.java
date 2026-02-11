package lotto.model;

import java.util.List;

import lotto.LottoConfig;

public class LottoResult {

	private final List<Rank> ranks;

	public LottoResult(List<Rank> ranks) {
		this.ranks = ranks;
	}

	public Double calculateReturnRate() {
		Integer sumPrize = ranks.stream().map(Rank::prize).reduce(0, Integer::sum);
		Integer pay = LottoMachine.LOTTO_TICKET_PRICE * ranks.size();
		return sumPrize.doubleValue() / pay;
	}

	public Integer countRank(Rank targetRank) {
		return Math.toIntExact(ranks.stream().filter(targetRank::equals).count());
	}
}
