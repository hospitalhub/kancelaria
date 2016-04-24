package pl.kalisz.szpital.kancelaria.utils.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.ByteArray;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.CertificateInfo;
import com.itextpdf.text.pdf.security.CertificateVerification;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import com.itextpdf.text.pdf.security.TSAClient;
import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;
import com.itextpdf.text.pdf.security.VerificationException;

/**
 * The Class Signatures.
 */
public class Sign {

  /** The Constant CRT. */
  private static final String CRT = "/home/andrzej/Pulpit/key/tsa.crt";

  /** The Constant KEY_ALIAS. */
  private static final String KEY_ALIAS = "Siaty klucz";

  /** The Constant KLUCZ_P12. */
  private static final String KLUCZ_P12_PATH =
      "/home/andrzej/git/kancelaria/kancelaria/src/main/resources/key/id.p12";

  /** The Constant RESOURCE. */
  // XXX
  public static final String RESOURCE =
      "/home/andrzej/git/kancelaria/kancelaria/src/main/resources/key/signature.gif";

  /** The Constant MD5. */
  private static final String MD5 = "MD5";

  /** The Constant BC. */
  private static final String BC = "BC";

  /** The Constant PKCS12. */
  private static final String PKCS12 = "PKCS12";

  /** The Constant PASS. */
  private static final String PASS = "hello world";

  /** The Constant TSA_SERVER_LOCAL. */
  private static final String TSA_SERVER = "http://tsa.safelayer.com:8093/";
  // private static final String TSA_SERVER = "http://127.0.0.1:3333/";
  // private static final String TSA_SERVER = "http://timestamp.verisign.com/";
  BouncyCastleProvider bcProvider = null;

  /** The original. */
  public static final String ORIGINAL =
      "/volume2/Kancelaria/Przychodzące wewnętrzne/02_01_2014/0001.pdf";

  /** The SIGNE d1. */
  public static final String SIGNED1 =
      "/volume2/Kancelaria/Przychodzące wewnętrzne/02_01_2014/0001_1.pdf";

  /** The SIGNE d2. */
  public static final String SIGNED2 =
      "/volume2/Kancelaria/Przychodzące wewnętrzne/02_01_2014/0001_2.pdf";

  /** The verification. */
  public static final String VERIFICATION =
      "/volume2/Kancelaria/Przychodzące wewnętrzne/verify.txt";

  /** The revision. */
  public static final String REVISION =
      "/volume2/Kancelaria/Przychodzące wewnętrzne/02_01_2014/0001_rev.pdf";


  /**
   * Creates the pdf.
   * 
   * @param filename the filename
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws DocumentException the document exception
   */
  @Deprecated
  private void createPdf(String filename) throws IOException, DocumentException {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(filename));
    document.open();
    document.add(new Paragraph("to jest tresc!"));
    document.close();
  }


  public Sign() {
    if (bcProvider == null) {
      bcProvider = new BouncyCastleProvider();
      // Provider[] providers = Security.getProviders();

      String name = bcProvider.getName();
      Security.removeProvider(name); // remove old instance
      Security.addProvider(bcProvider);
    }

  }

  /**
   * Sign pdf first time.
   * 
   * @param src the src
   * @param dest the dest
   * @param withDate the with date
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws DocumentException the document exception
   * @throws GeneralSecurityException the general security exception
   */
  public void signPdfFirstTime(String src, String dest, boolean withDate) throws IOException,
      DocumentException, GeneralSecurityException {

    TSAClient tsaClient = new TSAClientBouncyCastle(TSA_SERVER, "", "");
    String path = KLUCZ_P12_PATH;
    String keystorePassword = PASS;
    String keyPassword = PASS;
    KeyStore ks = KeyStore.getInstance(PKCS12, BC);
    ks.load(new FileInputStream(path), keystorePassword.toCharArray());
    System.out.println(ks.size());
    String alias = KEY_ALIAS;
    PrivateKey pk = (PrivateKey) ks.getKey(alias, keyPassword.toCharArray());
    System.out.println(new ByteArray(pk.getEncoded()).toString());
    System.out.println(pk.getAlgorithm());
    Certificate[] chain = ks.getCertificateChain(alias);
    // reader and stamper
    PdfReader reader = new PdfReader(src);
    FileOutputStream os = new FileOutputStream(dest);
    System.out.println(src + new File(src).exists());
    System.out.println(dest);
    PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
    // appearance
    Map<String, String> info = reader.getInfo();
    info.put("Title", "tytuł tego dokumentu");
    info.put("Subject", "temat dokumentu");
    info.put("Keywords", "WSZ");
    info.put("Creator", "WSZ Kancelaria");
    info.put("Author", "Andrzej Marcinkowski");
    stamper.setMoreInfo(info);

    PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
    appearance.setImage(Image.getInstance(RESOURCE));
    appearance.setReason("Podpisano cyfrowo");
    appearance.setLocation("WSZ im L. Perzyny w Kaliszu");
    appearance.setVisibleSignature(new Rectangle(70, 730, 160, 760), 1, "podpis pierwszy");
    // digital signature
    ExternalSignature es = null;
    try {
      es = new PrivateKeySignature(pk, MD5, BC);
    } catch (Exception e) {
      e.printStackTrace();
    }
    ExternalDigest digest = new BouncyCastleDigest();
    try {
      if (withDate) {
        MakeSignature.signDetached(appearance, digest, es, chain, null, null, tsaClient, 0,
            CryptoStandard.CMS);
      } else {
        MakeSignature.signDetached(appearance, digest, es, chain, null, null, null, 0,
            CryptoStandard.CMS);
      }
      System.out.println("signed " + (withDate ? "with date" : ""));
    } catch (java.net.UnknownHostException e) {
      System.out.println("unknown host");
      signPdfFirstTime(src, dest, false);
    } catch (ExceptionConverter e) {
      System.out.println("unknown host");
      signPdfFirstTime(src, dest, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Sign pdf second time.
   * 
   * @param src the src
   * @param dest the dest
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws DocumentException the document exception
   * @throws GeneralSecurityException the general security exception
   */

  @Deprecated
  public void signPdfSecondTime(String src, String dest) throws IOException, DocumentException,
      GeneralSecurityException {
    String path = KLUCZ_P12_PATH;
    String keystorepassword = PASS;
    String keypassword = PASS;
    String alias = KEY_ALIAS;
    KeyStore ks = KeyStore.getInstance(PKCS12, BC);
    ks.load(new FileInputStream(path), keystorepassword.toCharArray());
    PrivateKey pk = (PrivateKey) ks.getKey(alias, keypassword.toCharArray());
    Certificate[] chain = ks.getCertificateChain(alias);
    PdfReader reader = new PdfReader(src);
    FileOutputStream os = new FileOutputStream(dest);
    PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);
    PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
    appearance.setReason("Zatwierdzone.");
    appearance.setLocation("Foobar");
    appearance.setVisibleSignature(new Rectangle(160, 732, 232, 780), 1, "second");
    ExternalSignature es = new PrivateKeySignature(pk, MD5, BC);
    ExternalDigest digest = new BouncyCastleDigest();
    MakeSignature.signDetached(appearance, digest, es, chain, null, null, null, 0,
        CryptoStandard.CMS);

  }

  /**
   * Verify signatures.
   * 
   * @throws GeneralSecurityException the general security exception
   * @throws IOException Signals that an I/O exception has occurred.
   */

  private void verifySignatures() throws GeneralSecurityException, IOException {
    KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
    ks.load(null, null);
    CertificateFactory cf = CertificateFactory.getInstance("X509");
    FileInputStream is1 = new FileInputStream(CRT);
    X509Certificate cert1 = (X509Certificate) cf.generateCertificate(is1);
    ks.setCertificateEntry(KEY_ALIAS, cert1);
    FileInputStream is2 = new FileInputStream(CRT);
    X509Certificate cert2 = (X509Certificate) cf.generateCertificate(is2);
    ks.setCertificateEntry(KEY_ALIAS, cert2);
    System.out.println("VERIFI ... CATION");
    PdfReader reader = new PdfReader(SIGNED2);
    AcroFields af = reader.getAcroFields();
    for (String f : af.getFields().keySet()) {
      Item i = af.getFieldItem(f);
      for (int j = 0; j < i.size(); j++) {
        System.out.println(f + " " + i.getValue(j));
      }
    }
    ArrayList<String> names = af.getSignatureNames();
    System.out.println("signames: " + names);
    for (String name : names) {
      System.out.println("Signature name: " + name);
      System.out.println("Signature covers whole document: "
          + af.signatureCoversWholeDocument(name));
      System.out.println("Document revision: " + af.getRevision(name) + " of "
          + af.getTotalRevisions());
      PdfPKCS7 pk = af.verifySignature(name);
      Calendar cal = pk.getSignDate();
      System.out.println("cal" + cal.getTime());
      Certificate[] pkc = pk.getCertificates();
      System.out.println("Subject: "
          + CertificateInfo.getSubjectFields(pk.getSigningCertificate()).getField("CN"));
      System.out.println("Revision modified: " + !pk.verify());
      try {
        List<VerificationException> errors =
            CertificateVerification.verifyCertificates(pkc, ks, null);
        if (errors.size() == 0) {
          System.out.println("Certificates verified against the KeyStore");
        } else {
          System.out.println(errors);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * Extract first revision.
   * 
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @SuppressWarnings("unused")
  @Deprecated
  private void extractFirstRevision() throws IOException {
    PdfReader reader = new PdfReader(SIGNED2);
    AcroFields af = reader.getAcroFields();
    FileOutputStream os = new FileOutputStream(REVISION);
    byte bb[] = new byte[1028];
    InputStream ip = af.extractRevision("first");
    int n = 0;
    while ((n = ip.read(bb)) > 0) {
      os.write(bb, 0, n);
    }
    os.close();
    ip.close();
  }


  /**
   * The main method.
   * 
   * @param args the arguments
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws DocumentException the document exception
   * @throws GeneralSecurityException the general security exception
   */


  public static void main(String[] args) throws IOException, DocumentException,
      GeneralSecurityException {
    Sign signatures = new Sign();
    signatures.createPdf(ORIGINAL);
    signatures.signPdfFirstTime(ORIGINAL, SIGNED1, true);
    signatures.signPdfSecondTime(SIGNED1, SIGNED2);
    signatures.verifySignatures();
    // signatures.extractFirstRevision();
  }
}
