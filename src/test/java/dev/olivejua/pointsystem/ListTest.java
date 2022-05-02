package dev.olivejua.pointsystem;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ListTest {

    @Test
    void emptyTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("abc");
        list.add(null);
        list.add("def");

        assertThat(list).containsExactly("abc", null, "def");
        assertThat(list).hasSize(3);
    }

    @Test
    void removeTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("abc");
        list.add(null);
        list.add("def");
        list.add(null);

        list.removeAll(Collections.singleton(null));
        assertThat(list).containsExactly("abc", "def");
        assertThat(list).hasSize(2);
    }

    @Test
    void removeTest2() {
        ArrayList<String> list = Lists.newArrayList("abc", null, "def", null);
        while(list.remove(null));

        assertThat(list).hasSize(2);
    }
}
