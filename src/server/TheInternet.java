package server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import message.Status;

/**
 * Platzhalter Klasse für das Internet. Eine Art DNS.
 * Ist ein Singleton mit zweifach geprüfter Sperre.
 *
 */
public class TheInternet {
    private ConcurrentHashMap<String, ServerProxy> domains;
    private volatile static TheInternet instanz;
    
    private TheInternet() {
        super();
        this.domains = new ConcurrentHashMap<String, ServerProxy>();
    }
    
    public static TheInternet goOnline() {
        if (instanz == null) {
            synchronized (TheInternet.class) {
                if(instanz == null) {
                    instanz = new TheInternet();
                }
            }
        }
        return instanz;
    }
    
    /**
     * Wir lassen die Authentifizierung hier einfachheitshalber mal weg.
     * Dafür kann man bestehende Domains nicht überschreiben.
     * @param domain String, z.B. google.com
     * @param proxy Der Server
     * @return Status, ob erfolgreich
     */
    public Status registerDomain(String domain, ServerProxy proxy) {
        if(domains.containsKey(domain)) {
            throw new RuntimeException("Domain " + domain + " is already registered.");
        } else {
            domains.put(domain, proxy);
            return new Status(200, "Domain " + domain + "registered");
        }
    }
    
    /**
     * Gibt den Server für eine Domain zurück.
     * @param domain
     * @return ServerSocket instanz oder null, wenn nicht bekannt.
     */
    public ServerProxy lookup(String domain) {
        if(domains.containsKey(domain)) {
            return domains.get(domain);
        } else {
            return null;
        }
    }   
    
    /**
     * Empty out registry. Used for tests.
     */
    public void startOver() {
        this.domains = new ConcurrentHashMap<String, ServerProxy>();
    }
    
}
