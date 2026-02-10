package lotto.view;

import java.util.Scanner;

public class InputView {

	private final Scanner scanner;

	public InputView() {
		scanner = new Scanner(System.in);
	}

	public Integer readPurchasePrice() {
		System.out.println("구입금액을 입력해 주세요.");
		return scanner.nextInt();
	}
}
