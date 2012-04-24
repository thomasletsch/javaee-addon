package org.vaadin.addons.javaee.i18n;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.enterprise.inject.Instance;

import org.junit.Before;
import org.junit.Test;

public class TranslationServiceImplTest {

    private static final String KEY1 = "key";

    private static final String VALUE1 = "Value";

    private static final String KEY3 = "key3";

    private static final String VALUE3 = "Value3";

    private static final String KEY_KEY3 = "key.key3";

    private static final String VALUE_KEY_KEY3 = "ValueKK3";

    private static final String UNKNOWN_KEY2 = "key2";

    private static final String KEY1_KEY = "key1.key";

    private static final String KEY2_KEY3 = UNKNOWN_KEY2 + "." + KEY3;

    TranslationSPI spi;

    private Locale locale = Locale.GERMAN;

    List<String> unknownValues = Arrays.asList(UNKNOWN_KEY2, KEY1_KEY, KEY2_KEY3);

    private TranslationServiceImpl translation;

    @Before
    @SuppressWarnings("unchecked")
    public void initMock() {
        spi = mock(TranslationSPI.class);
        for (String value : unknownValues) {
            when(spi.get(value, locale)).thenReturn(value);
        }
        when(spi.get(KEY1, locale)).thenReturn(VALUE1);
        when(spi.get(KEY_KEY3, locale)).thenReturn(VALUE_KEY_KEY3);
        when(spi.get(KEY3, locale)).thenReturn(VALUE3);
        translation = new TranslationServiceImpl();
        Instance<TranslationSPI> providers = mock(Instance.class);
        Iterator<TranslationSPI> iterator = mock(Iterator.class);
        when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        when(iterator.next()).thenReturn(spi);
        when(providers.iterator()).thenReturn(iterator);
        translation.providers = providers;
    }

    @Test
    public void testGetSimple() {
        assertEquals(VALUE1, translation.get(KEY1));
    }

    @Test
    public void testGetSimpleNotFound() {
        assertEquals(UNKNOWN_KEY2, translation.get(UNKNOWN_KEY2));
    }

    @Test
    public void testGetTwoElements() {
        assertEquals(VALUE_KEY_KEY3, translation.get(KEY_KEY3));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetLastOfTwoElements() {
        Instance<TranslationSPI> providers = mock(Instance.class);
        Iterator<TranslationSPI> iterator = mock(Iterator.class);
        when(iterator.hasNext()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
        when(iterator.next()).thenReturn(spi);
        when(providers.iterator()).thenReturn(iterator);
        translation.providers = providers;
        assertEquals(VALUE1, translation.get(KEY1_KEY));
    }

    @Test
    public void testGetPossibleKeyVariations() {
        TranslationServiceImpl translation = new TranslationServiceImpl();
        List<String> list = translation.getPossibleKeyVariations(KEY1_KEY);
        assertEquals("List[0]", KEY1_KEY, list.get(0));
        assertEquals("List[1]", KEY1, list.get(1));
    }
}
