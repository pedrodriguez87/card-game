package tech.bts.cardgame.util;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class HandlebarsUtil {

    private static Handlebars handlebars;

    static {
        // Prepare handlebars
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".hbs");
        handlebars = new Handlebars(loader);
    }

    public static Template compile(String templateName) throws IOException {

        // Compile template
        return handlebars.compile(templateName);

    }

}
