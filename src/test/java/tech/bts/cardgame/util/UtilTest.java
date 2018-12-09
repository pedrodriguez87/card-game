package tech.bts.cardgame.util;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import tech.bts.cardgame.model.Game;
import tech.bts.cardgame.service.GameService;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void joinOnce() {

        String result = Util.join(Arrays.asList("one")," vs ");

        assertThat(result, is("one"));
    }

}