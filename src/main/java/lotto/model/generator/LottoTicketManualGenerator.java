package lotto.model.generator;

import java.util.List;

import lotto.model.LottoTicket;

public class LottoTicketManualGenerator implements LottoTicketGenerator {

	public LottoTicketManualGenerator() {
	}

	@Override
	public boolean supports(GenerateType generateType) {
		return generateType instanceof ManualGenerateType;
	}

	@Override
	public List<LottoTicket> generate(GenerateType generateType) {
		if (!supports(generateType)) {
			throw new IllegalArgumentException("수동 구매 타입만 지원합니다.");
		}
		ManualGenerateType manualPurchaseType = (ManualGenerateType) generateType;
		return manualPurchaseType.lottoNumbersList().stream()
				.map(LottoTicket::new)
				.toList();
	}
}
