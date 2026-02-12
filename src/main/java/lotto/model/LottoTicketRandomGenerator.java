package lotto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LottoTicketRandomGenerator {

	private final Random random;

	public LottoTicketRandomGenerator(Random random) {
		this.random = random;
	}

	public LottoTicketRandomGenerator() {
		this.random = new Random();
	}

	public LottoTicket generate() {
		List<LottoNumber> allNumbers = new ArrayList<>(IntStream.rangeClosed(1, 45)
				.boxed()
				.map(LottoNumber::new)
				.toList());
		Collections.shuffle(allNumbers, random);
		List<LottoNumber> numbers = allNumbers.subList(0, LottoTicket.LOTTO_LENGTH);
		return new LottoTicket(numbers);
	}

	public List<LottoTicket> generate(int count) {
		return Stream.generate(this::generate)
				.limit(count)
				.collect(Collectors.toList());
	}
}
