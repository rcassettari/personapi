package br.com.dio.personapi.locale;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CustomLocaleResolver {

    private static List<Locale> LOCALES = Arrays.asList(new Locale("en"), new Locale("es"), new Locale("pt"), new Locale("fr"));

    public static Locale resolveLocale( Locale takenLocale  )
    {
        List<Locale.LanguageRange> list = null;

        if (takenLocale == null || takenLocale.toString().isEmpty() ) {
            return Locale.getDefault();
        }

        list = Locale.LanguageRange.parse(takenLocale.toLanguageTag());

        Locale locale = Locale.lookup(list, LOCALES);

        if( locale == null ) {
            return Locale.getDefault();
        }

        return locale;
    }

}
