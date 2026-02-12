package lotto.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
		return new Money(purchasePrice);
	}

	public List<Integer> readWinningNormalNumbers() {
		System.out.println("지난 주 당첨 번호를 입력해 주세요.");
		String[] numbers = splitNumbersByDelimiter(scanner.nextLine(), ",");
		return Arrays.stream(numbers)
				.map(String::trim)
				.map(this::praseInt)
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
