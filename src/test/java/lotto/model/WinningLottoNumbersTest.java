package lotto.model;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class WinningLottoNumbersTest {

	private List<Integer> winningIntegerNormalNumbers;
	List<LottoNumber> winningNormalNumbers;
	LottoNumber winningBonusNumber;

	@BeforeEach
	void setup() {
		winningIntegerNormalNumbers = IntStream.rangeClosed(1, LottoTicket.LOTTO_LENGTH).boxed().toList();
		winningNormalNumbers = winningIntegerNormalNumbers.stream().map(LottoNumber::of).toList();
		winningBonusNumber = LottoNumber.of(LottoTicket.LOTTO_LENGTH+1);
	}

	@ParameterizedTest(name = "[{index}] 일반 {0}개, 보너스 {1}")
	@MethodSource("allCases")
	@DisplayName("당첨 등수 반환 테스트")
	void countMatchedNumber(int match, boolean bonus, Rank targetRank){
		WinningLottoNumbers winningLottoNumbers = new WinningLottoNumbers(winningNormalNumbers, winningBonusNumber);
		LottoTicket myLottoTicket = makeCustomLottoTicket(match, bonus);

		Rank rank = winningLottoNumbers.match(myLottoTicket);
		assertThat(rank).isEqualTo(targetRank);
	}

	static Stream<Arguments> allCases() {
		return IntStream.rangeClosed(0, LottoTicket.LOTTO_LENGTH)
				.boxed()
				.flatMap(match -> Stream.of(false, true)
						.filter(bonus -> !(match.equals(LottoTicket.LOTTO_LENGTH) && bonus)) // 불가능 케이스
						.map(bonus -> Arguments.of(match, bonus, Rank.from(match, bonus))));
	}

	LottoTicket makeCustomLottoTicket(int matchCount, boolean bonusMatch) {
		List<Integer> missPool = IntStream.rangeClosed(winningBonusNumber.getNumber()+1, 45).boxed().toList();
		List<Integer> picked = new ArrayList<>(winningIntegerNormalNumbers.subList(0, matchCount));
		if (bonusMatch) {
			picked.add(winningBonusNumber.getNumber());
		}

		int need = LottoTicket.LOTTO_LENGTH - picked.size();
		picked.addAll(missPool.subList(0, need));

		return new LottoTicket(picked.stream().map(LottoNumber::of).toList());
	}

	@Test
	@DisplayName("일반 번호와 보너스 번호 중복시 예외")
	void validateBonusInNormalNumbers() {
		LottoNumber bonus = LottoNumber.of(1);
		assertThatIllegalArgumentException().isThrownBy(() -> {
			WinningLottoNumbers bonusInNormalNumbers =
					new WinningLottoNumbers(winningNormalNumbers, bonus);
		});
	}

	@Test
	@DisplayName("일반 번호와 보너스 번호가 중복되지 않음")
	void validateBonusNotInNormalNumbers() {
		assertThatNoException().isThrownBy(() -> {
			WinningLottoNumbers bonusNotInNormalNumbers =
					new WinningLottoNumbers(winningNormalNumbers, winningBonusNumber);
		});
	}
}
