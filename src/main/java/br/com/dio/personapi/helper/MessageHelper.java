package br.com.dio.personapi.helper;

import br.com.dio.personapi.locale.CustomLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageHelper {

    private MessageSource messageSource;

    public MessageHelper(@Autowired MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    public String getMessage( String messageKey )
    {
        return messageSource.getMessage(messageKey, null, CustomLocaleResolver.resolveLocale(LocaleContextHolder.getLocale()));
    }

}
