package lotto.model;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoneyTest {

	@Test
	@DisplayName("금액은 음수가 될 수 없음")
	void validateNegativeNumber() {
		int negativeNumber = -1;
		assertThatIllegalArgumentException().isThrownBy(() -> {
			Money money	= new Money(negativeNumber);
		});
	}

	@Test
	@DisplayName("금액은 음이 아닌 정수")
	void validateNonNegativeNumber() {
		int zero = 0;
		assertThatNoException().isThrownBy(() -> {
			Money money	= new Money(zero);
		});

		int positiveNumber = 1;
		assertThatNoException().isThrownBy(() -> {
			Money money	= new Money(positiveNumber);
		});
	}
}
