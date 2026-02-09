package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoNumberTest {
	@Test
	@DisplayName("LottoNumber 일치 여부 테스트")
	void compareLottoNumber() {
		LottoNumber target = new LottoNumber(1);

		// Same
		LottoNumber sameTestTarget = new LottoNumber(1);
		boolean isSame = target.isEqual(sameTestTarget);
		assertThat(isSame).isTrue();

		// Different
		LottoNumber differentTestTarget = new LottoNumber(2);
		isSame = target.isEqual(differentTestTarget);
		assertThat(isSame).isFalse();
	}
}
