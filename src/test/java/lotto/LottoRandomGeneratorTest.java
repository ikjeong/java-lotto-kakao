package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoRandomGeneratorTest {

	@Test
	@DisplayName("같은 시드에서 같은 결과값을 반환")
	void validateSameRandom() {
		Random random = new Random(20260105);
		Random targetRandom = new Random(20260105);

		LottoRandomGenerator generator = new LottoRandomGenerator(random);
		LottoRandomGenerator targetGenerator = new LottoRandomGenerator(targetRandom);

		LottoNumbers lottoNumbers = generator.generate();
		LottoNumbers targetLottoNumbers = targetGenerator.generate();

		Integer matchCount = lottoNumbers.countMatchNumber(targetLottoNumbers);
		assertThat(matchCount).isEqualTo(LottoConfig.LOTTO_LENGTH);
	}

	@Test
	@DisplayName("다른 시드에서 다른 결과값을 반환")
	void validateDifferentRandom() {
		Random random = new Random(20260105);
		Random targetRandom = new Random(20260210);

		LottoRandomGenerator generator = new LottoRandomGenerator(random);
		LottoRandomGenerator targetGenerator = new LottoRandomGenerator(targetRandom);

		LottoNumbers lottoNumbers = generator.generate();
		LottoNumbers targetLottoNumbers = targetGenerator.generate();

		Integer matchCount = lottoNumbers.countMatchNumber(targetLottoNumbers);
		assertThat(matchCount).isNotEqualTo(LottoConfig.LOTTO_LENGTH);
	}
}



