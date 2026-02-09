package lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoNumberTest {

	@Test
	@DisplayName("LottoNumber 일치 테스트")
	void compareSameLottoNumber() {
		LottoNumber target = new LottoNumber(1);

		LottoNumber sameTestTarget = new LottoNumber(1);
		boolean isSame = target.isEqual(sameTestTarget);
		assertThat(isSame).isTrue();
	}

	@Test
	@DisplayName("LottoNumber 불일치 테스트")
	void compareDifferentLottoNumber() {
		LottoNumber target = new LottoNumber(1);

		LottoNumber differentTestTarget = new LottoNumber(2);
		boolean isSame = target.isEqual(differentTestTarget);
		assertThat(isSame).isFalse();
	}

	@Test
	@DisplayName("LottoNumber 범위 검증 테스트(1~45)")
	void validateNumberRange() {
		assertThatRuntimeException().isThrownBy(() -> {
			LottoNumber lowNumer = new LottoNumber(0);
		});

		assertThatRuntimeException().isThrownBy(() -> {
			LottoNumber highNumer = new LottoNumber(46);
		});
	}
}
