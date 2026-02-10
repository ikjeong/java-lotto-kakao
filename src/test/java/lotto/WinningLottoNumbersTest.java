package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


public class WinningLottoNumbersTest {

	private List<Integer> winningIntegerNormalNumbers;
	LottoNumbers winningNormalNumbers;
	LottoNumber winningBonusNumber;

	@BeforeEach
	void setup() {
		winningIntegerNormalNumbers = IntStream.rangeClosed(1, LottoConfig.LOTTO_LENGTH).boxed().toList();
		winningNormalNumbers = new LottoNumbers(winningIntegerNormalNumbers.stream().map(LottoNumber::new).toList());
		winningBonusNumber = new LottoNumber(LottoConfig.LOTTO_LENGTH+1);
	}

	@ParameterizedTest(name = "[{index}] 일반 {0}개, 보너스 {1}")
	@MethodSource("allCases")
	@DisplayName("일반,보너스 당첨 개수 반환 테스트")
	void countMatchedNumber(Integer match, Boolean bonus){
		WinningLottoNumbers winningLottoNumbers = new WinningLottoNumbers(winningNormalNumbers, winningBonusNumber);
		LottoNumbers myLottoNumbers = makeCustomLottoNumbers(match, bonus);

		MatchingCountInfo matchingResult = winningLottoNumbers.countMatchingNumber(myLottoNumbers);
		assertThat(matchingResult.normalCount()).isEqualTo(match);
		assertThat(matchingResult.hasBonus()).isEqualTo(bonus);
	}

	static Stream<Arguments> allCases() {
		return IntStream.rangeClosed(0, LottoConfig.LOTTO_LENGTH)
				.boxed()
				.flatMap(match -> Stream.of(false, true)
						.filter(bonus -> !(match.equals(LottoConfig.LOTTO_LENGTH) && bonus)) // 불가능 케이스
						.map(bonus -> Arguments.of(match, bonus)));
	}

	private LottoNumbers makeCustomLottoNumbers(int matchCount, boolean bonusMatch) {
		List<Integer> missPool = IntStream.rangeClosed(winningBonusNumber.getNumber()+1, 45).boxed().toList();
		List<Integer> picked = new ArrayList<>(winningIntegerNormalNumbers.subList(0, matchCount));
		if (bonusMatch) {
			picked.add(winningBonusNumber.getNumber());
		}

		int need = LottoConfig.LOTTO_LENGTH - picked.size();
		picked.addAll(missPool.subList(0, need));

		return new LottoNumbers(picked.stream().map(LottoNumber::new).toList());
	}

}
