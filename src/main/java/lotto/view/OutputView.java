package lotto.view;

import java.util.List;

import lotto.model.LottoNumbers;

public class OutputView {

	public void printPurchasedTicketCount(Integer count) {
		System.out.println(count + "개를 구매했습니다.");
	}

	public void printLottoTickets(List<LottoNumbers> lottoTickets) {
		lottoTickets.stream()
				.forEach(System.out::println);
	}
}
