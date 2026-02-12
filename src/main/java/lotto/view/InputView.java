package lotto.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

	private final Scanner scanner;

	public InputView() {
		scanner = new Scanner(System.in);
	}

	public Integer readPurchasePrice() {
		System.out.println("구입금액을 입력해 주세요.");
		return Integer.parseInt(scanner.nextLine());
	}

	public List<Integer> readWinningNormalNumbers() {
		System.out.println("지난 주 당첨 번호를 입력해 주세요.");
		return Arrays.stream(scanner.nextLine().split(","))
				.map(String::trim)
				.map(Integer::parseInt)
				.toList();
	}

	public Integer readBonusNumber() {
		System.out.println("보너스 번호를 입력해 주세요.");
		return Integer.parseInt(scanner.nextLine());
	}
}
