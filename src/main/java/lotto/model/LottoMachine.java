package lotto.model;

import java.util.List;

import lotto.model.generator.LottoTicketGenerator;
import lotto.model.generator.GenerateType;

public class LottoMachine {

	private final List<LottoTicketGenerator> lottoTicketGenerators;

	public LottoMachine(List<LottoTicketGenerator> lottoTicketGenerators) {
		this.lottoTicketGenerators = List.copyOf(lottoTicketGenerators);
	}

	public List<LottoTicket> issueTickets(GenerateType generateType) {
		LottoTicketGenerator lottoTicketGenerator = findLottoTicketGenerator(generateType);
		return lottoTicketGenerator.generate(generateType);
	}

	private LottoTicketGenerator findLottoTicketGenerator(GenerateType generateType) {
		return lottoTicketGenerators.stream()
				.filter(generator -> generator.supports(generateType))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("지원하지 않는 구매 타입입니다."));
	}
}
