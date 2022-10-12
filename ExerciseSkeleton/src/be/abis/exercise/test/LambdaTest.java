package be.abis.exercise.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LambdaTest {

    @Test
    void testLambdaReduce() {
        int[] array = new int[]{4, 17, 3, -5, 6};
        assertEquals(25, Arrays.stream(array).reduce(Integer::sum).getAsInt());
    }

}
