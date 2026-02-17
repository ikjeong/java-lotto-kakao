package lotto.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoneyTest {

	@Test
	@DisplayName("금액은 음수가 될 수 없음")
	void validateNegativeNumber() {
		long negativeNumber = -1L;
		assertThatIllegalArgumentException().isThrownBy(() -> {
			Money money	= new Money(negativeNumber);
		});
	}

	@Test
	@DisplayName("금액은 음이 아닌 정수")
	void validateNonNegativeNumber() {
		long zero = 0L;
		assertThatNoException().isThrownBy(() -> {
			Money money	= new Money(zero);
		});

		long positiveNumber = 1L;
		assertThatNoException().isThrownBy(() -> {
			Money money	= new Money(positiveNumber);
		});
	}

	@Test
	@DisplayName("금액 덧셈")
	void addMoney() {
		Money result = new Money(1_000L).add(new Money(500L));
		assertThat(result).isEqualTo(new Money(1_500L));
	}

	@Test
	@DisplayName("금액 뺄셈")
	void subtractMoney() {
		Money result = new Money(1_000L).subtract(new Money(500L));
		assertThat(result).isEqualTo(new Money(500L));
	}

	@Test
	@DisplayName("금액 뺄셈 결과가 음수면 예외")
	void subtractMoneyToNegative() {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			Money subtract = new Money(500L).subtract(new Money(1_000L));
		});
	}

	@Test
	@DisplayName("0원 정적 팩토리")
	void createZeroMoney() {
		assertThat(Money.zero()).isEqualTo(new Money(0L));
	}

	@Test
	@DisplayName("금액 곱셈")
	void multiplyMoney() {
		Money result = new Money(1_000L).multiply(3L);
		assertThat(result).isEqualTo(new Money(3_000L));
	}

	@Test
	@DisplayName("음수 곱셈 계수 예외")
	void multiplyMoneyWithNegativeFactor() {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			Money multiply = new Money(1_000L).multiply(-1L);
		});
	}

	@Test
	@DisplayName("금액 나눗셈")
	void divideMoney() {
		long result = new Money(3_500L).divideBy(new Money(1_000L));
		assertThat(result).isEqualTo(3L);
	}

	@Test
	@DisplayName("0원으로 나눌 때 예외")
	void divideMoneyByZero() {
		assertThatIllegalArgumentException().isThrownBy(() -> {
			long result = new Money(1_000L).divideBy(Money.zero());
		});
	}

	@Test
	@DisplayName("금액 대소 비교")
	void compareMoney() {
		assertThat(new Money(999L).isLessThan(new Money(1_000L))).isTrue();
		assertThat(new Money(1_000L).isLessThan(new Money(1_000L))).isFalse();
	}

	@Test
	@DisplayName("0원 여부 확인")
	void checkZeroMoney() {
		assertThat(Money.zero().isZero()).isTrue();
		assertThat(new Money(1L).isZero()).isFalse();
	}
}
