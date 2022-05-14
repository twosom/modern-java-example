package org.icloud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

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

    @Test
    void subGroupingTest1() {
        var typesCount = menu.stream()
                .collect(Collectors.groupingBy(Dish::type, Collectors.counting()));
    }

    @Test
    void subGroupingTest2() {
        var mostCaloricByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::type,
                        Collectors.maxBy(comparingInt(Dish::calories))));
    }

    @Test
    void subGroupingTest3() {
        var mostCaloricByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::type,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(
                                        comparingInt(Dish::calories)),
                                Optional::get))
                );
    }

    @Test
    void anotherGroupingTest1() {
        Map<Dish.Type, Integer> totalCaloriesByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::type,
                        Collectors.summingInt(Dish::calories)));
    }

    @Test
    void anotherGroupingTest2() {
        var caloricLevelsByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::type,
                        Collectors.mapping(dish -> {
                            if (dish.calories() <= 400) {
                                return CaloricLevel.DIET;
                            } else if (dish.calories() <= 700) {
                                return CaloricLevel.NORMAL;
                            } else {
                                return CaloricLevel.FAT;
                            }
                        }, Collectors.toSet())));
    }

    @Test
    void anotherGroupingTest3() {
        var caloricLevelsByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::type,
                        Collectors.mapping(dish -> {
                            if (dish.calories() <= 400) {
                                return CaloricLevel.DIET;
                            } else if (dish.calories() <= 700) {
                                return CaloricLevel.NORMAL;
                            } else {
                                return CaloricLevel.FAT;
                            }
                        }, Collectors.toCollection(HashSet::new))));
    }

    @Test
    void partitioningTest1() {
        var partitionedMenu = menu.stream()
                .collect(Collectors.partitioningBy(Dish::vegetarian));

        List<Dish> vegetarianDishes = menu.stream()
                .filter(Dish::vegetarian)
                .toList();
    }

    @Test
    void partitioningTest2() {
        var vegetarianDishesByType = menu.stream()
                .collect(Collectors.partitioningBy(Dish::vegetarian,
                        Collectors.groupingBy(Dish::type)));
    }

    @DisplayName("채식 요리와 채식이 아닌 요리 각각의 그룹에서 가장 칼로리가 높은 요리의 이름 찾기")
    @Test
    void partitioningTest3() {
        var mostCaloricPartitionedByVegetarian = menu.stream()
                .collect(Collectors.partitioningBy(Dish::vegetarian,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(comparingInt(Dish::calories)),
                                Optional::get
                        )));

    }

    @Test
    void partitioningTest4() {
        var collect = menu.stream()
                .collect(Collectors.partitioningBy(Dish::vegetarian,
                        Collectors.partitioningBy(d -> d.calories() > 500)
                ));
    }

    @Test
    void partitioningTest5() {
        Map<Boolean, Long> collect = menu.stream()
                .collect(Collectors.partitioningBy(Dish::vegetarian,
                        Collectors.counting()));
    }

    @Test
    void customCollectorTest() {
        List<Dish> dishes = menu.stream()
                .collect(new ToListCollector<>());
    }


}
