package com.invengo.xcrf.core.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;


public class EnvUtil
{
	private static Properties env = null;
    /**
	 * Get System.getenv() in a reflection kind of way. The problem is
	 * that System.getenv() was deprecated in Java 1.4 while reinstated in 1.5
	 * This method will get at getenv() using reflection and will return
	 * empty properties when used in 1.4
	 * 
	 * @return Properties containing the environment. You're not meant
	 *         to change any value in the returned Properties!
	 * 
	 */
    @SuppressWarnings({"unchecked"})
    private static final Properties getEnv()
	{		
		 Class<?> system = System.class;
		 if ( env == null )
		 {
			 Map<String,String> returnMap = null;
			 try  {
			     Method method = system.getMethod("getenv");
			     
			     returnMap = (Map<String,String>) method.invoke(system);
			 }
   	         catch ( Exception ex )  {
   	        	 returnMap = null;
   	         }
   	         
   	         env = new Properties();
   	         if ( returnMap != null )
   	         {
   	             // We're on a VM with getenv() defined.
   	             ArrayList<String> list = new ArrayList<String>(returnMap.keySet());
   	             for (int i=0;i<list.size();i++)
   	             {
   	                 String var = list.get(i);
   	                 String val = returnMap.get(var);
   	        	 
   	        	     env.setProperty(var, val);   	          
   	             }
   	         }
		 }
		 return env;
	}
	

    /**
     * This method is written especially for weird JVM's like IBM's on AIX and OS/400.
     * On these platforms, we notice that environment variables have an extra double quote around it...
     * This is messing up the ability to specify things.
     *  
     * @param key The key, the name of the environment variable to return
     * @param def The default value to return in case the key can't be found
     * @return The value of a System environment variable in the java virtual machine.  
     *         If the key is not present, the variable is not defined and the default value is returned.
     */
    public static final String getSystemPropertyStripQuotes(String key, String def)
    {
        String value = System.getProperty(key, def);
        if (value.startsWith("\"") && value.endsWith("\"") && value.length()>1)
        {
            return value.substring(1,value.length()-2);
        }
        return value;
    }

    /**
     * This method is written especially for weird JVM's like 
     * @param key The key, the name of the environment variable to return
     * @param def The default value to return in case the key can't be found
     * @return The value of a System environment variable in the java virtual machine.  
     *         If the key is not present, the variable is not defined and the default value is returned.
     */
    public static final String getSystemProperty(String key, String def)
    {
        String value = System.getProperty(key, def);
        return value;
    }
    
    /**
     * @param key The key, the name of the environment variable to return
     * @return The value of a System environment variable in the java virtual machine.  
     *         If the key is not present, the variable is not defined and null returned.
     */
    public static final String getSystemProperty(String key)
    {
        return getSystemProperty(key, null);
    }
    

    /**
     * Returns an available java.util.Locale object for the given localeCode.
     * 
     * The localeCode code can be case insensitive, if it is available
     * the method will find it and return it.
     * 
     * @param localeCode
     * @return java.util.Locale.
     */
    public static Locale createLocale(String localeCode) {
      Locale resultLocale = null;
      if (localeCode != null) {
        StringTokenizer parser = new StringTokenizer(localeCode, "_"); //$NON-NLS-1$
        if (parser.countTokens() == 2) {
          resultLocale = new Locale(parser.nextToken(), parser.nextToken());
        } else {
          resultLocale = new Locale(localeCode);
        }
      }
      return resultLocale;
    }
    

}
