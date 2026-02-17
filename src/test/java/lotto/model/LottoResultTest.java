package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoResultTest {

	@Test
	@DisplayName("수익률 계산")
	void validateReturnRate() {
		Money totalPrice = new Money(1_000L * 14);
		List<Rank> ranks = new ArrayList<>();
		ranks.add(Rank.FIFTH);
		for (int i = 0; i < 13; i++) {
			ranks.add(Rank.MISS);
		}

		LottoResult lottoResult = new LottoResult(totalPrice, ranks);
		int returnRate = (int) (lottoResult.calculateReturnRate() * 100);
		int targetReturnRate = Math.toIntExact(Rank.FIFTH.prize().amount() * 100L / totalPrice.amount());
		assertThat(returnRate).isEqualTo(targetReturnRate);
	}

	@Test
	@DisplayName("랭크별 당첨 개수 반환")
	void checkRankCount() {
		Money totalPrice = new Money(1_000L);
		List<Rank> ranks = new ArrayList<>();
		ranks.add(Rank.FIRST);
		ranks.add(Rank.FOURTH);
		ranks.add(Rank.FOURTH);
		ranks.add(Rank.FOURTH);

		LottoResult lottoResult = new LottoResult(totalPrice, ranks);
		int firstCount = lottoResult.countRank(Rank.FIRST);
		assertThat(firstCount).isEqualTo(1);

		int fourthCount = lottoResult.countRank(Rank.FOURTH);
		assertThat(fourthCount).isEqualTo(3);

		int missCount = lottoResult.countRank(Rank.MISS);
		assertThat(missCount).isEqualTo(0);
	}

	@Test
	@DisplayName("총 구매 금액이 0원인 경우 예외")
	void validateTotalPriceIsZero() {
		Money totalPrice = new Money(0L);
		List<Rank> ranks = new ArrayList<>();
		ranks.add(Rank.FIRST);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoResult lottoResult = new LottoResult(totalPrice, ranks);
		});
	}

	@Test
	@DisplayName("등수 개수가 0개인 경우 예외")
	void validateRanksSizeIsZero(){
		Money totalPrice = new Money(1_000L);
		List<Rank> ranks = new ArrayList<>();

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoResult lottoResult = new LottoResult(totalPrice, ranks);
		});
	}
}
