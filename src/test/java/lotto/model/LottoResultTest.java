package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoResultTest {

	@Test
	@DisplayName("수익률 계산")
	void validateReturnRate() {
		List<Rank> ranks = new ArrayList<>();
		ranks.add(Rank.FIFTH);
		for (int i = 0; i < 13; i++) {
			ranks.add(Rank.MISS);
		}

		LottoResult lottoResult = new LottoResult(ranks);
		Integer returnRate = (int) (lottoResult.calculateReturnRate() * 100);
		Integer targetReturnRate = 35;
		assertThat(returnRate).isEqualTo(targetReturnRate);
	}


	@Test
	@DisplayName("랭크별 당첨 개수 반환")
	void checkRankCount() {
		List<Rank> ranks = new ArrayList<>();
		ranks.add(Rank.FIRST);
		ranks.add(Rank.FOURTH);
		ranks.add(Rank.FOURTH);
		ranks.add(Rank.FOURTH);

		LottoResult lottoResult = new LottoResult(ranks);
		Integer firstCount = lottoResult.countRank(Rank.FIRST);
		assertThat(firstCount).isEqualTo(1);

		Integer fourthCount = lottoResult.countRank(Rank.FOURTH);
		assertThat(fourthCount).isEqualTo(3);

		Integer missCount = lottoResult.countRank(Rank.MISS);
		assertThat(missCount).isEqualTo(0);
	}
}
