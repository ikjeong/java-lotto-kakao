package lotto.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import lotto.config.LottoPolicy;
import lotto.model.LottoNumber;
import lotto.model.Money;

public class InputView {

	private final Scanner scanner;

	public InputView() {
		scanner = new Scanner(System.in);
	}

	public Money readPurchasePrice() {
		System.out.println("구입금액을 입력해 주세요.");
		int purchasePrice = praseInt(scanner.nextLine());
		if (purchasePrice < LottoPolicy.LOTTO_TICKET_PRICE) {
			throw new IllegalArgumentException("최소 " + LottoPolicy.LOTTO_TICKET_PRICE + "원 이상을 입력해주세요.");
		}
		return new Money(purchasePrice);
	}

	public int readManualLottoTicketNumber() {
		System.out.println("수동으로 구매할 로또 수를 입력해 주세요.");
		int manualLottoTicketNumber = praseInt(scanner.nextLine());
		if (manualLottoTicketNumber < 0) {
			throw new IllegalArgumentException("음이 아닌 정수를 입력해주세요.");
		}
		return manualLottoTicketNumber;
	}

	public List<List<LottoNumber>> readManualLottoNumbersList(int manualLottoTicketNumber) {
		System.out.println("수동으로 구매할 번호를 입력해 주세요.");
		List<List<LottoNumber>> manualLottoNumbersList = new ArrayList<>();
		for (int i = 0; i < manualLottoTicketNumber; i++) {
			String[] numbers = splitNumbersByDelimiter(scanner.nextLine(), ",");
			manualLottoNumbersList.add(Arrays.stream(numbers)
					.map(String::trim)
					.map(this::praseInt)
					.map(LottoNumber::of)
					.toList());
		}
		return manualLottoNumbersList;
	}

	public List<LottoNumber> readWinningNormalNumbers() {
		System.out.println("지난 주 당첨 번호를 입력해 주세요.");
		String[] numbers = splitNumbersByDelimiter(scanner.nextLine(), ",");
		return Arrays.stream(numbers)
				.map(String::trim)
				.map(this::praseInt)
				.map(LottoNumber::of)
				.toList();
	}

	public LottoNumber readBonusNumber() {
		System.out.println("보너스 번호를 입력해 주세요.");
		int bonusNumber = praseInt(scanner.nextLine());
		return LottoNumber.of(bonusNumber);
	}

	private int praseInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (RuntimeException runtimeException) {
			throw new IllegalArgumentException("정확한 숫자를 입력해주세요.", runtimeException);
		}
	}

	private String[] splitNumbersByDelimiter(String input, String delimiter) {
		try {
			return input.split(delimiter);
		} catch (RuntimeException runtimeException) {
			throw new IllegalArgumentException("입력 형식을 지켜주세요. 예시) 1,2,3", runtimeException);
		}
	}
}
