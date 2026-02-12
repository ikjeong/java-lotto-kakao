package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoNumberTest {

	@Test
	@DisplayName("LottoNumber 일치 테스트")
	void compareSameLottoNumber() {
		LottoNumber number = new LottoNumber(1);
		LottoNumber sameNumber = new LottoNumber(1);

		boolean isSame = number.equals(sameNumber);
		assertThat(isSame).isTrue();
	}

	@Test
	@DisplayName("LottoNumber 불일치 테스트")
	void compareDifferentLottoNumber() {
		LottoNumber number = new LottoNumber(1);
		LottoNumber differentNumber = new LottoNumber(2);

		boolean isSame = number.equals(differentNumber);
		assertThat(isSame).isFalse();
	}

	@Test
	@DisplayName("LottoNumber 범위 검증 테스트(1~45)")
	void validateNumberRange() {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoNumber lowNumber = new LottoNumber(0);
		});

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoNumber highNumber = new LottoNumber(46);
		});
	}
}
