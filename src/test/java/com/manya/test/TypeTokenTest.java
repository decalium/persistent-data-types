package com.manya.test;



import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TypeTokenTest {
    public static void main(String[] args) {

        System.out.println(getCollectionClass(Collectors.toUnmodifiableList()));






    }


    @SuppressWarnings("unchecked")
    public static <T, A, R> Class<R> getCollectionClass(Collector<T, A, R> collector) {
        return (Class<R>) collector.finisher().apply(collector.supplier().get()).getClass();
    }
}
