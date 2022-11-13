package lotto;

import java.util.List;

// 당첨 번호와 발행된 로또에 대한 확인 담당
public class Lotto {
    /**
     * 주의 사항
     * 1. 제공된 Lotto 클래스를 활용해 구현해야 한다.
     * 2. Lotto에 매개 변수가 없는 생성자를 추가할 수 없다.
     * 3. numbers의 접근 제어자인 private을 변경할 수 없다.
     * 4. Lotto에 필드(인스턴스 변수)를 추가할 수 없다.
     * 5. Lotto의 패키지 변경은 가능하다.
     */

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException();
        }
    }

    // TODO: 추가 기능 구현

}
