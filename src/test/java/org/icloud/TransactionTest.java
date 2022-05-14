package org.icloud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TransactionTest {

    List<Transaction> transactions;

    @BeforeEach
    void setup() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        this.transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    @DisplayName("2011년에 일어난 모든 트랜잭션을  찾아서 값을 오름차순으로 정렬하시오.")
    @Test
    void test1() {
        List<Transaction> tr2011 = transactions.stream()
                .filter(transaction -> transaction.year() == 2011)
                .sorted(Comparator.comparing(Transaction::value))
                .toList();
        System.out.println("tr2011 = " + tr2011);
    }

}
