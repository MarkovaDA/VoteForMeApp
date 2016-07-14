package su.vistar.gvpromoweb.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import java.util.Properties;


public class JndiPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    
    private static final Logger log = LoggerFactory.getLogger(JndiPropertyPlaceholderConfigurer.class);
    
    private String jndiPrefix = "java:comp/env/";
    private JndiTemplate jndiTemplate = new JndiTemplate();

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
        String value = null;
        value = resolveJndiPlaceholder(placeholder);
        if (value == null) {
            value = super.resolvePlaceholder(placeholder, props);
            log.debug("Resolved property {} : {}", placeholder, value);
        }
        return value;
    }

    private String resolveJndiPlaceholder(String placeholder) {
        try {
            String value = (String)jndiTemplate.lookup(jndiPrefix + placeholder, String.class);
            log.debug("Resolved JNDI property {} : {}", placeholder, value);
            return value;
        } catch (NamingException e) {
            // ignore
        } 
        return null;
    }

    public void setJndiPrefix(String jndiPrefix) {
        this.jndiPrefix = jndiPrefix;
    }

    public void setJndiTemplate(JndiTemplate jndiTemplate) {
        this.jndiTemplate = jndiTemplate;
    }
}
