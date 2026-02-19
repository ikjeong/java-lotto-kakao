package lotto.model.generator;

import java.util.List;

import lotto.model.LottoNumber;

public record ManualGenerateType(List<List<LottoNumber>> lottoNumbersList) implements GenerateType {

	@Override
	public int getCount() {
		return lottoNumbersList.size();
	}

	public ManualGenerateType(List<List<LottoNumber>> lottoNumbersList) {
		this.lottoNumbersList = lottoNumbersList.stream()
				.map(List::copyOf)
				.toList();
	}
}
