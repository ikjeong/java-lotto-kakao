package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatRuntimeException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoNumberTest {

	@Test
	@DisplayName("LottoNumber 일치 테스트")
	void compareSameLottoNumber() {
		LottoNumber number = new LottoNumber(1);
		LottoNumber sameNumber = new LottoNumber(1);

		Boolean isSame = number.isEqual(sameNumber);
		assertThat(isSame).isTrue();
	}

	@Test
	@DisplayName("LottoNumber 불일치 테스트")
	void compareDifferentLottoNumber() {
		LottoNumber number = new LottoNumber(1);
		LottoNumber differentNumber = new LottoNumber(2);

		Boolean isSame = number.isEqual(differentNumber);
		assertThat(isSame).isFalse();
	}

	@Test
	@DisplayName("LottoNumber 범위 검증 테스트(1~45)")
	void validateNumberRange() {
		assertThatRuntimeException().isThrownBy(() -> {
			LottoNumber lowNumber = new LottoNumber(0);
		});

		assertThatRuntimeException().isThrownBy(() -> {
			LottoNumber highNumber = new LottoNumber(46);
		});
	}
}
