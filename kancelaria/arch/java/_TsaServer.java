package pl.kalisz.szpital.kancelaria.utils.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.log4j.BasicConfigurator;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cmp.PKIStatus;
import org.bouncycastle.asn1.cmp.PKIStatusInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.tsp.TimeStampResp;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.tsp.TSPAlgorithms;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampResponseGenerator;
import org.bouncycastle.tsp.TimeStampTokenGenerator;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

// XXX RFC3161 duzo szumu
// http://wiki.cacert.org/TimeStamping
public class _TsaServer {

  private static final String OID = "1.3.6.1.4.1.13762.3";
  private static Logger logger = Logger.getAnonymousLogger();
  private static final String X_509 = "X.509";
  private static final String RSA = "RSA";
  private static final String KEY_PEM = "/home/andrzej/Pulpit/key/tsa.key";
  // private static final String KEY_PEM =
  // "/home/andrzej/Pulpit/key/privateKey.pem";
  // git/kancelaria/kancelaria/src/main/resources/key/ia.key";
  private static final String KEY_CRT = "/home/andrzej/Pulpit/key/tsa.crt";
  // "/home/andrzej/git/kancelaria/kancelaria/src/main/resources/key/ia.crt";

  /** The Constant SIGN_EVERRYTHING_OID. */
  private static final String SIGN_EVERRYTHING_OID = "1.3.6.1.4.1.3029.54.11940.54";
  // DefaultSignatureAlgorithmIdentifierFinder
  private static final String ALGORITHM = "RSA";// "SHA1WithRSA";
  private static X509Certificate signerCertificate = null;
  private static PrivateKey signingKey = null;
  private static final String TSA_SERVER = "http://tsa.safelayer.com:8093/";

  @SuppressWarnings("unused")
  private static void stamp() {

    String ocspUrl = TSA_SERVER;
    OutputStream out = null;
    HttpURLConnection con = null;

    try {

      TimeStampRequestGenerator timeStampRequestGenerator = new TimeStampRequestGenerator();
      timeStampRequestGenerator.setReqPolicy(new ASN1ObjectIdentifier(OID));
      TimeStampRequest timeStampRequest =
          timeStampRequestGenerator.generate(TSPAlgorithms.SHA1, new byte[20],
              BigInteger.valueOf(100));
      byte request[] = timeStampRequest.getEncoded();

      URL url = new URL(ocspUrl);
      con = (HttpURLConnection) url.openConnection();
      con.setDoOutput(true);
      con.setDoInput(true);
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-type", "application/timestamp-query");
      con.setRequestProperty("Content-length", String.valueOf(request.length));
      out = con.getOutputStream();
      out.write(request);
      out.flush();

      if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
        throw new IOException("Received HTTP error: " + con.getResponseCode() + " - "
            + con.getResponseMessage());
      } else {
        logger.log(Level.INFO, "Response Code: ".concat(Integer.toString(con.getResponseCode())));
      }
      InputStream in = con.getInputStream();
      int size = in.available();
      byte[] b = new byte[size];

      in.read(b);
      System.out.println(new String(b));
      @SuppressWarnings("resource")
      TimeStampResp resp = TimeStampResp.getInstance(new ASN1InputStream(b).readObject());
      TimeStampResponse response = new TimeStampResponse(resp);
      response.validate(timeStampRequest);
      byte[] ba = response.getEncoded();
      System.out.println(Arrays.toString(ba));
      logger.log(Level.INFO, "Status = {0}", response.getStatusString());

      if (response.getFailInfo() != null) {

        switch (response.getFailInfo().intValue()) {
          case 0: {
            logger.log(Level.INFO, "unrecognized or unsupported Algorithm Identifier");
            return;
          }

          case 2: {
            logger.log(Level.INFO, "transaction not permitted or supported");
            return;
          }

          case 5: {
            logger.log(Level.INFO, "the data submitted has the wrong format");
            return;
          }

          case 14: {
            logger.log(Level.INFO, "the TSA’s time source is not available");
            return;
          }

          case 15: {
            logger.log(Level.INFO, "the requested TSA policy is not supported by the TSA");
            return;
          }
          case 16: {
            logger.log(Level.INFO, "the requested extension is not supported by the TSA");
            return;
          }

          case 17: {
            logger.log(Level.INFO,
                "the additional information requested could not be understood or is not available");
            return;
          }

          case 25: {
            logger.log(Level.INFO, "the request cannot be handled due to system failure");
            return;
          }
        }
      }
      System.out.println(response.getTimeStampToken());
      logger.log(Level.INFO, "Timestamp: {0}", response.getTimeStampToken().getTimeStampInfo()
          .getGenTime());
      logger.log(Level.INFO, "TSA: {0}", response.getTimeStampToken().getTimeStampInfo().getTsa());
      logger.log(Level.INFO, "Serial number: {0}", response.getTimeStampToken().getTimeStampInfo()
          .getSerialNumber());
      logger.log(Level.INFO, "Policy: {0}", response.getTimeStampToken().getTimeStampInfo()
          .getPolicy());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception {
    Logger logger = Logger.getLogger("org.bouncycastle");
    logger.setLevel(Level.ALL);
    BasicConfigurator.configure();
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    // stamp();

    FileInputStream cin = new FileInputStream(KEY_CRT);
    CertificateFactory certificateFactory = CertificateFactory.getInstance(X_509);
    signerCertificate = (X509Certificate) certificateFactory.generateCertificate(cin);

    // Load TSA signing key
    // http://stackoverflow.com/questions/9497719/how-to-extract-a-public-private-key-from-a-pkcs12-file-with-openssl-for-later-us
    // openssl pkcs12 -in id.p12 -nocerts -out privateKey.pem

    // String key = File2StringUtil.readFromFile(KEY_PEM);
    // key = key.substring(key.indexOf(BEGIN_PRIVATE_KEY));
    // key = key.substring(key.indexOf("eAHi0j"));
    // key = key.replace(BEGIN_PRIVATE_KEY, "");
    // key = key.replace(END_PRIVATE_KEY, "");
    // System.out.println(key);
    // byte[] encoded = Base64.decode(key);
    // System.out.println(ArrayUtils.toString(encoded));
    // PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
    KeyFactory keyFactory = KeyFactory.getInstance(RSA);
    PemReader pemReader = new PemReader(new FileReader(new File(KEY_PEM)));
    PemObject obj = pemReader.readPemObject();
    pemReader.close();
    System.out.println(obj.getType());
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(obj.getContent());
    signingKey = keyFactory.generatePrivate(keySpec);
    System.out.println(signingKey.getFormat());
    System.out.println(signingKey.getAlgorithm());
    System.out.println(signingKey.getEncoded().length);
    // Start server
    System.out.println(" * Starting server...");
    ServerSocket serverSocket = new ServerSocket(3333);

    while (true) { // Never do it in production!
      Socket clientSocket = serverSocket.accept();

      // Receive timestamp request
      System.out.println(" * Request from " + clientSocket.getInetAddress());
      InputStream in = clientSocket.getInputStream();

      int i;
      String s = "";
      while ((i = in.read()) > 0) {
        s += (char) i;
      }
      System.out.println("Req:" + s);
      // in.close();
      // System.out.println(s);
      // redirect(s);

      /*
       * TimeStampRequest request = null; try { ASN1InputStream asn1 = new
       * ASN1InputStream(s.getBytes()); ASN1Object asn1o = asn1.readObject(); // ASN1Primitive asn1p
       * = asn1o.toASN1Primitive(); // System.out.println(asn1o); // System.out.println(asn1p);
       * ASN1Sequence asn1s = ASN1Sequence.getInstance(asn1o); TimeStampReq req =
       * TimeStampReq.getInstance(asn1o); request = new TimeStampRequest(req); asn1.close(); } catch
       * (Exception e) { e.printStackTrace(); System.exit(0); }
       */

      // byte[] ba = {48,9,48,7,2,1,2,3,2,2,4};
      // ASN1InputStream asn1 = new ASN1InputStream(ba);
      // TimeStampResp resp =
      // TimeStampResp.getInstance(asn1.readObject());
      // asn1.close();
      // TimeStampResponse response = new TimeStampResponse(resp);
      // Create timestamp response
      System.out.println(" * Sending timestamp response...");
      // TimeStampResponse response = buildResponse(request);
      OutputStream out = clientSocket.getOutputStream();
      // out.write(response.getEncoded());
      byte[] barr = {48, 10, 48, 8, 2, 1, 2, 3, 3, 0, 0, 1};
      out.write(barr);
      out.flush();
      System.out.println("sent");
      // End connection
      in.close();
      out.close();
      System.out.println("closed");
      clientSocket.close();
      System.out.println("client done");
      if (Math.random() > 0.99999) {
        break;
      }
    }

    serverSocket.close();
  }

  @SuppressWarnings("unused")
  private static TimeStampResponse buildResponse(TimeStampRequest request) {
    try {
      // Policy ID: timestamping
      ASN1ObjectIdentifier policyId = new ASN1ObjectIdentifier(SIGN_EVERRYTHING_OID);

      // Initialize signer
      ContentSigner contentSigner = new JcaContentSignerBuilder(ALGORITHM).build(signingKey);

      // Initialize digest calculator
      DigestCalculatorProvider digestCalculator = new JcaDigestCalculatorProviderBuilder().build();

      // Initialize generators
      SignerInfoGenerator signerInfoGenerator =
          new JcaSignerInfoGeneratorBuilder(digestCalculator).build(contentSigner,
              signerCertificate);
      // DefaultSignatureAlgorithmIdentifierFinder
      // MessageDigest
      TimeStampTokenGenerator timestampGenerator =
          new TimeStampTokenGenerator(signerInfoGenerator, digestCalculator.get(AlgorithmIdentifier
              .getInstance(PKCSObjectIdentifiers.sha1WithRSAEncryption)), policyId);
      TimeStampResponseGenerator responseGenerator =
          new TimeStampResponseGenerator(timestampGenerator, TSPAlgorithms.ALLOWED);

      // Generate and return timestamp response
      BigInteger serial = new BigInteger(128, new SecureRandom());
      return responseGenerator.generate(request, serial, new Date());
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed to create timestamp: " + e.getMessage());
    }

    try {
      // Return response with error code
      PKIStatusInfo statusInfo = new PKIStatusInfo(PKIStatus.getInstance(PKIStatus.REJECTION)); // Rejected
      return new TimeStampResponse(new TimeStampResp(statusInfo, null));
    } catch (Exception e) {
      System.out.println("Failed to create response: " + e.getMessage());
    }

    return null;
  }

}
