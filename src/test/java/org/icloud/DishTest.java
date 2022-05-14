package org.icloud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DishTest {

    List<Dish> menu;
    Map<String, List<String>> dishTags;

    @BeforeEach
    void setup() {
        this.menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );

        this.dishTags = new HashMap<>();
        dishTags.put("pork", List.of("greasy", "salty"));
        dishTags.put("beef", List.of("salty", "roasted"));
        dishTags.put("chicken", List.of("fried", "crisp"));
        dishTags.put("french fries", List.of("greasy", "fried"));
        dishTags.put("rice", List.of("light", "natural"));
        dishTags.put("season fruit", List.of("fresh", "natural"));
        dishTags.put("pizza", List.of("tasty", "salty"));
        dishTags.put("prawns", List.of("tasty", "roasted"));
        dishTags.put("salmon", List.of("delicious", "fresh"));
    }

    @Test
    void test1() {
        var dishesByCaloricLevel = menu.stream()
                .collect(Collectors.groupingBy(dish -> {
                    if (dish.calories() <= 400) return CaloricLevel.DIET;
                    else if (dish.calories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));
    }

    @Test
    void groupingTest1() {
        var dishesByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::type));
    }

    @Test
    void groupingTest2() {
        var dishesByCaloricLevel = menu.stream()
                .collect(
                        Collectors.groupingBy(dish -> {
                            if (dish.calories() <= 400) return CaloricLevel.DIET;
                            else if (dish.calories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        })
                );
    }

    @Test
    void groupingTest3() {
        var caloricDishesByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::type,
                        Collectors.filtering(dish -> dish.calories() > 500,
                                Collectors.toList()))
                );
    }

    @Test
    void groupingTest4() {
        var dishNamesByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::type,
                        Collectors.mapping(Dish::name, Collectors.toList())));
    }

    @Test
    void groupingTest5() {
        var dishNamesByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::type,
                        Collectors.flatMapping(dish -> dishTags.get(dish.name()).stream(), Collectors.toList())));
    }

    @Test
    void multiGroupingTest1() {
        var dishesByTypeCaloricLevel = menu.stream()
                .collect(Collectors.groupingBy(Dish::type,
                        Collectors.groupingBy(dish -> {
                            if (dish.calories() <= 400) {
                                return CaloricLevel.DIET;
                            } else if (dish.calories() <= 700) {
                                return CaloricLevel.NORMAL;
                            } else {
                                return CaloricLevel.FAT;
                            }
                        })));

    }
}
