package lotto.model.generator;

import java.util.List;

import lotto.model.LottoTicket;

public interface LottoTicketGenerator {

	boolean supports(GenerateType generateType);

	List<LottoTicket> generate(GenerateType generateType);
}
