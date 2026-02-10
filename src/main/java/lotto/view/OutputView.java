package lotto.view;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import lotto.model.LottoNumbers;
import lotto.model.LottoResult;
import lotto.model.Rank;

public class OutputView {

	public void printPurchasedTicketCount(Integer count) {
		System.out.println(count + "개를 구매했습니다.");
	}

	public void printLottoTickets(List<LottoNumbers> lottoTickets) {
		lottoTickets.stream()
				.forEach(System.out::println);
	}

	public void printLottoResult(LottoResult lottoResult) {
		Double returnRate = lottoResult.calculateReturnRate();

		System.out.println("당첨 통계");
		System.out.println("---------");
		System.out.println("3개 일치 (5000원)- " + lottoResult.countRank(Rank.FIFTH) + "개");
		System.out.println("4개 일치 (50000원)- " + lottoResult.countRank(Rank.FOURTH) + "개");
		System.out.println("5개 일치 (1500000원)- " + lottoResult.countRank(Rank.THIRD) + "개");
		System.out.println("5개 일치, 보너스 볼 일치(30000000원) - " + lottoResult.countRank(Rank.SECOND) + "개");
		System.out.println("6개 일치 (2000000000원)- " + lottoResult.countRank(Rank.FIRST) + "개");
		System.out.println("총 수익률은 " + formatReturnRate(returnRate)
				+ "입니다.(" + getProfitLossMessage(returnRate) + ")");
	}

	private String formatReturnRate(Double returnRate) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.DOWN);
		return decimalFormat.format(returnRate);
	}

	private String getProfitLossMessage(Double returnRate) {
		final Double baseRate = 1.0;
		final Double epsilon = 1e-12;

		if (Math.abs(returnRate - baseRate) < epsilon) {
			return "기준이 1이기 때문에 결과적으로 본전이라는 의미임";
		}
		if (returnRate > baseRate) {
			return "기준이 1보다 크기 때문에 결과적으로 이익이라는 의미임";
		}
		return "기준이 1보다 작기 때문에 결과적으로 손해라는 의미임";
	}
}
