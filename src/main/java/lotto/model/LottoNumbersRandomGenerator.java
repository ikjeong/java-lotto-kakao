package lotto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lotto.LottoConfig;

public class LottoNumbersRandomGenerator {

	private final Random random;

	public LottoNumbersRandomGenerator(Random random) {
		this.random = random;
	}

	public LottoNumbersRandomGenerator() {
		this.random = new Random();
	}

	public LottoNumbers generate() {
		List<LottoNumber> allNumbers = new ArrayList<>(IntStream.rangeClosed(1, 45)
				.boxed()
				.map(LottoNumber::new)
				.toList());
		Collections.shuffle(allNumbers, random);
		List<LottoNumber> numbers = allNumbers.subList(0, LottoConfig.LOTTO_LENGTH);
		return new LottoNumbers(numbers);
	}

	public List<LottoNumbers> generate(Integer count) {
		return Stream.generate(this::generate)
				.limit(count)
				.collect(Collectors.toList());
	}
}
