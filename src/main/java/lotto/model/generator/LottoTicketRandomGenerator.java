package lotto.model.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import lotto.model.LottoNumber;
import lotto.model.LottoTicket;

public class LottoTicketRandomGenerator implements LottoTicketGenerator {

	private final Random random;

	public LottoTicketRandomGenerator(Random random) {
		this.random = random;
	}

	public LottoTicketRandomGenerator() {
		this.random = new Random();
	}

	@Override
	public boolean supports(GenerateType generateType) {
		return generateType instanceof RandomGenerateType;
	}

	@Override
	public List<LottoTicket> generate(GenerateType generateType) {
		if (!supports(generateType)) {
			throw new IllegalArgumentException("랜덤 구매 타입만 지원합니다.");
		}
		RandomGenerateType randomPurchaseType = (RandomGenerateType) generateType;
		return Stream.generate(this::generateSingleTicket)
				.limit(randomPurchaseType.count())
				.toList();
	}

	private LottoTicket generateSingleTicket() {
		List<LottoNumber> allNumbers = new ArrayList<>(LottoNumber.getLottoNumberCandidates());
		Collections.shuffle(allNumbers, random);
		List<LottoNumber> numbers = allNumbers.subList(0, LottoTicket.LOTTO_LENGTH);
		return new LottoTicket(numbers);
	}
}
