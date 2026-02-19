package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoNumberTest {

	@Test
	@DisplayName("LottoNumber 일치 테스트")
	void compareSameLottoNumber() {
		LottoNumber number = LottoNumber.getLottoNumberCandidates().getFirst();
		LottoNumber sameNumber = LottoNumber.getLottoNumberCandidates().getFirst();

		boolean isSame = number.equals(sameNumber);
		assertThat(isSame).isTrue();
	}

	@Test
	@DisplayName("LottoNumber 불일치 테스트")
	void compareDifferentLottoNumber() {
		LottoNumber number = LottoNumber.getLottoNumberCandidates().getFirst();
		LottoNumber differentNumber = LottoNumber.getLottoNumberCandidates().get(1);

		boolean isSame = number.equals(differentNumber);
		assertThat(isSame).isFalse();
	}

	@Test
	@DisplayName("LottoNumber 잘못된 번호 검증 테스트")
	void validateNumberRange() {
		int minimum = LottoNumber.getLottoNumberCandidates().getFirst().getNumber();
		int maximum = LottoNumber.getLottoNumberCandidates().getLast().getNumber();

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoNumber lowNumber = LottoNumber.of(minimum - 1);
		});

		assertThatIllegalArgumentException().isThrownBy(() -> {
			LottoNumber highNumber = LottoNumber.of(maximum + 1);
		});
	}
}
