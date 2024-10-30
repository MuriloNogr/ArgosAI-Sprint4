package br.com.argos.argosaisprint3.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(WebConfig.class)
public class WebConfigTest {

    @Autowired
    private LocaleResolver localeResolver;

    @Test
    public void testLocaleResolverBean() {
        assertNotNull(localeResolver, "LocaleResolver bean should not be null");
    }
}
