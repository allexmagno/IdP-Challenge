package ldap;

import javax.naming.*;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;
import javax.naming.directory.*;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import com.sun.jndi.ldap.*;

public class Ldap{

    private static Hashtable environment;
    //private Hashtable environment;

    public Ldap(String url, String security, String bind, String password) {
        this.environment = new Hashtable<String, String>();
        this.environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        this.environment.put(Context.PROVIDER_URL, url); // "ldap://localhost:389"
        this.environment.put(Context.SECURITY_AUTHENTICATION, security); // simple
        this.environment.put(Context.SECURITY_PRINCIPAL, bind); // "CN=admin,DC=teste,DC=magno"
        this.environment.put(Context.SECURITY_CREDENTIALS, password); // ldap@magno
        this.environment.put("java.naming.ldap.attributes.binary","userCertificate");

    }



    public static X509Certificate getCertificate(String identifier, String search_base, String uid){



        X509Certificate x509Certificate = null;
        try {
            LdapContext ctx = new InitialLdapContext(environment, null);


            // connect to LDAP
        /*DirContext ctx = null;
        try {
            ctx = new InitialDirContext(environment);
        } catch (NamingException e) {
            e.printStackTrace();
        }*/

            // Specify the search filter
            String FILTER = identifier+"="+uid;

            // limit returned attributes to those we care about
            String[] attrIDs = {"*"};

            SearchControls ctls = new SearchControls();
            ctls.setReturningAttributes(attrIDs);
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchBase = search_base; //"dc=teste,dc=magno"

            // Search for objects using filter and controls
            NamingEnumeration answer = null;

            answer = ctx.search(searchBase, FILTER, ctls);

            SearchResult sr = null;

            while (answer.hasMore())
                sr = (SearchResult) answer.next();
            Attributes attrs = sr.getAttributes();
            x509Certificate = X509Certificate.getInstance((byte[]) attrs.get("usercertificate;binary").get());
            ctx.close();
        } catch (AuthenticationNotSupportedException exception) {
            System.out.println("The authentication is not supported by the server");
        } catch (AuthenticationException exception) {
            System.out.println("Incorrect password or username");
        } catch (NamingException exception) {
            System.out.println("Error when trying to create the context");
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        return x509Certificate;

    }
}
