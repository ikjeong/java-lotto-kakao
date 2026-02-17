package lotto.view;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import lotto.model.LottoNumber;
import lotto.model.LottoResult;
import lotto.model.LottoTicket;
import lotto.model.Rank;

public class OutputView {

	public void printPurchasedTicketCount(int manualCount, int autoCount) {
		System.out.println();
		System.out.println("수동으로 " + manualCount + "장, 자동으로 " + autoCount + "개를 구매했습니다.");
	}

	public void printLottoTickets(List<LottoTicket> lottoTickets) {
		for (LottoTicket lottoTicket : lottoTickets) {
			List<LottoNumber> sortedLottoNumbers = lottoTicket.getSortedLottoNumbers();
			String lottoTicketOutput = sortedLottoNumbers.stream()
					.map(LottoNumber::getNumber)
					.map(String::valueOf)
					.collect(Collectors.joining(", ", "[", "]"));
			System.out.println(lottoTicketOutput);
		}
	}

	public void printLottoResult(LottoResult lottoResult) {
		double returnRate = lottoResult.calculateReturnRate();
		System.out.println();
		System.out.println("당첨 통계");
		System.out.println("---------");
		System.out.println("3개 일치 (" + Rank.FIFTH.prize().amount() + "원)- " + lottoResult.countRank(Rank.FIFTH) + "개");
		System.out.println("4개 일치 (" + Rank.FOURTH.prize().amount() + "원)- " + lottoResult.countRank(Rank.FOURTH) + "개");
		System.out.println("5개 일치 (" + Rank.THIRD.prize().amount() + "원)- " + lottoResult.countRank(Rank.THIRD) + "개");
		System.out.println("5개 일치, 보너스 볼 일치(" + Rank.SECOND.prize().amount() + "원) - " + lottoResult.countRank(Rank.SECOND) + "개");
		System.out.println("6개 일치 (" + Rank.FIRST.prize().amount() + "원)- " + lottoResult.countRank(Rank.FIRST) + "개");
		System.out.println("총 수익률은 " + formatReturnRate(returnRate) + "입니다.(" + getProfitLossMessage(returnRate) + ")");
	}

	public void printError(String errorMessage) {
		System.out.println();
		System.out.println("[ERROR] " + errorMessage);
	}

	private String formatReturnRate(double returnRate) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.DOWN);
		return decimalFormat.format(returnRate);
	}

	private String getProfitLossMessage(double returnRate) {
		final double baseRate = 1.0;
		final double epsilon = 1e-12;

		if (Math.abs(returnRate - baseRate) < epsilon) {
			return "기준이 1이기 때문에 결과적으로 본전이라는 의미임";
		}
		if (returnRate > baseRate) {
			return "기준이 1보다 크기 때문에 결과적으로 이익이라는 의미임";
		}
		return "기준이 1보다 작기 때문에 결과적으로 손해라는 의미임";
	}
}
