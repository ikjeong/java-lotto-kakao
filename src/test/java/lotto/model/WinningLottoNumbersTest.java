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

	private List<Integer> winningIntegerNormalLottoNumbers;
	List<LottoNumber> winningNormalLottoNumbers;
	LottoNumber winningBonusLottoNumber;

	@BeforeEach
	void setup() {
		winningIntegerNormalLottoNumbers = IntStream.rangeClosed(LottoNumber.MINIMUM, LottoTicket.LOTTO_LENGTH).boxed().toList();
		winningNormalLottoNumbers = winningIntegerNormalLottoNumbers.stream().map(LottoNumber::of).toList();
		winningBonusLottoNumber = LottoNumber.of(LottoTicket.LOTTO_LENGTH + 1);
	}

	@ParameterizedTest(name = "[{index}] 일반 {0}개, 보너스 {1}")
	@MethodSource("allCases")
	@DisplayName("당첨 등수 반환 테스트")
	void countMatchedNumber(int match, boolean bonus, Rank targetRank){
		WinningLottoNumbers winningLottoNumbers = new WinningLottoNumbers(winningNormalLottoNumbers, winningBonusLottoNumber);
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
		List<Integer> missPool = IntStream.rangeClosed(winningBonusLottoNumber.getNumber() + 1, LottoNumber.MAXIMUM).boxed().toList();
		List<Integer> picked = new ArrayList<>(winningIntegerNormalLottoNumbers.subList(0, matchCount));
		if (bonusMatch) {
			picked.add(winningBonusLottoNumber.getNumber());
		}

		int need = LottoTicket.LOTTO_LENGTH - picked.size();
		picked.addAll(missPool.subList(0, need));

		return new LottoTicket(picked.stream().map(LottoNumber::of).toList());
	}

	@Test
	@DisplayName("일반 번호 개수가 로또 티켓의 번호 개수보다 적으면 예외")
	void validateNormalLottoNumbersLengthLessThanLottoLength() {
		List<LottoNumber> lottoNumbers = new ArrayList<>();
		lottoNumbers.add(LottoNumber.of(LottoNumber.MINIMUM));
		LottoNumber bonus = LottoNumber.of(LottoNumber.MINIMUM + 1);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			WinningLottoNumbers winningLottoNumbers = new WinningLottoNumbers(lottoNumbers, bonus);
		});
	}

	@Test
	@DisplayName("일반 번호 개수가 로또 티켓의 번호 개수보다 많으면 예외")
	void validateNormalLottoNumbersLengthMoreThanLottoLength() {
		List<LottoNumber> lottoNumbers = new ArrayList<>();
		for (int i = LottoNumber.MINIMUM; i <= LottoNumber.MINIMUM + LottoTicket.LOTTO_LENGTH; i++) {
			lottoNumbers.add(LottoNumber.of(i));
		}
		LottoNumber bonus = LottoNumber.of(LottoNumber.MINIMUM + LottoTicket.LOTTO_LENGTH + 1);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			WinningLottoNumbers winningLottoNumbers = new WinningLottoNumbers(lottoNumbers, bonus);
		});
	}

	@Test
	@DisplayName("일반 번호 중복시 예외")
	void validateDuplicateNormalLottoNumbers() {
		List<LottoNumber> lottoNumbers = new ArrayList<>();
		for (int i = LottoNumber.MINIMUM; i < LottoNumber.MINIMUM + LottoTicket.LOTTO_LENGTH - 1; i++) {
			lottoNumbers.add(LottoNumber.of(i));
		}
		lottoNumbers.add(LottoNumber.of(LottoNumber.MINIMUM));
		LottoNumber bonus = LottoNumber.of(LottoNumber.MINIMUM + LottoTicket.LOTTO_LENGTH - 1);

		assertThatIllegalArgumentException().isThrownBy(() -> {
			WinningLottoNumbers duplicateNormalLottoNumbers = new WinningLottoNumbers(lottoNumbers, bonus);
		});
	}

	@Test
	@DisplayName("일반 번호와 보너스 번호 중복시 예외")
	void validateBonusInNormalLottoNumbers() {
		LottoNumber bonus = LottoNumber.of(LottoNumber.MINIMUM);
		assertThatIllegalArgumentException().isThrownBy(() -> {
			WinningLottoNumbers bonusInNormalLottoNumbers = new WinningLottoNumbers(winningNormalLottoNumbers, bonus);
		});
	}

	@Test
	@DisplayName("일반 번호 개수 만족 및 일반 번호와 보너스 번호가 중복되지 않음")
	void validateBonusNotInNormalLottoNumbers() {
		assertThatNoException().isThrownBy(() -> {
			WinningLottoNumbers bonusNotInNormalLottoNumbers = new WinningLottoNumbers(winningNormalLottoNumbers, winningBonusLottoNumber);
		});
	}
}
