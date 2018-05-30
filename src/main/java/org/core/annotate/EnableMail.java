package org.core.annotate;

import org.core.mail.boot.config.EmailConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EmailConfig.class)
@Documented
public @interface EnableMail {
}
