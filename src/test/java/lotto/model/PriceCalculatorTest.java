package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import lotto.LottoConfig;

public class PriceCalculatorTest {

	@ParameterizedTest(name = "[{index}] 매칭 정보: {0}")
	@MethodSource("allCases")
	@DisplayName("당첨금 반환 테스트")
	void validatePriceByMatchCount(MatchingCountInfo matchingCountInfo, Price targetPrice){
		PriceCalculator priceCalculator = new PriceCalculator();
		Price price = priceCalculator.calculate(matchingCountInfo);
		assertThat(price).isEqualTo(targetPrice);
	}

	static Stream<Arguments> allCases() {
		return IntStream.rangeClosed(0, LottoConfig.LOTTO_LENGTH)
				.boxed()
				.flatMap(match -> Stream.of(false, true)
						.filter(bonus -> !(match.equals(LottoConfig.LOTTO_LENGTH) && bonus)) // 불가능 케이스
						.map(bonus -> Arguments.of(
								new MatchingCountInfo(match, bonus),
								toExpectedPrice(match, bonus)
						)));
	}

	static Price toExpectedPrice(Integer match, Boolean hasBonus) {
		if (match.equals(6)) return Price.MATCH_6;
		if (match.equals(5) && hasBonus) return Price.MATCH_5_BONUS;
		if (match.equals(5)) return Price.MATCH_5;
		if (match.equals(4)) return Price.MATCH_4;
		if (match.equals(3)) return Price.MATCH_3;
		return Price.NO_MATCH;
	}
}
