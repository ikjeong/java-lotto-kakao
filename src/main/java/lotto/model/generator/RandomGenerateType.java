package lotto.model.generator;

public record RandomGenerateType(int count) implements GenerateType {

	@Override
	public int getCount() {
		return count;
	}

	public RandomGenerateType {
		if (count < 0) {
			throw new IllegalArgumentException("구매 개수가 음수가 될 수 없습니다.");
		}
	}
}
