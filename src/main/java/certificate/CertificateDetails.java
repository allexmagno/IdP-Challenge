package certificate;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class CertificateDetails {

    private PrivateKey privateKey;

    private X509Certificate x509Certificate;

    private Certificate[] certificateChain;

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public X509Certificate getX509Certificate() {
        return x509Certificate;
    }

    public void setX509Certificate(X509Certificate x509Certificate) {
        this.x509Certificate = x509Certificate;
    }

    public void setCertificateChain(Certificate[] certificateChain){ this.certificateChain = certificateChain; }

    public PublicKey getPublicKey(){return x509Certificate.getPublicKey(); }

    public Certificate[] getCertificateChain() {return certificateChain; }
}
