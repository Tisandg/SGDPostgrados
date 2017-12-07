/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.entidades;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
 
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
    import java.io.IOException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
import java.util.Random;

/**
 * Clase que contiene diferentes metodos para manipular un archivo PDF
 * link: http://developers.itextpdf.com/examples/itext-action-second-edition/chapter-12#476-metadatapdf.java
 * @author Juan
 */
public class MetodosPDF {
    
    /**
     * metodo para crear un archivo PDF y anexar algunos metadatos basicos
     * @param filename: nombre el archivo PDF.
     * @throws IOException
     * @throws DocumentException
     */
    public void createPdf(String filename) throws IOException, DocumentException {
        // step 1: Crear el objeto
        Document document = new Document();
        // step 2: instanciar para escritura con el objeto creado 
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3: agregar los metadatos
        document.addTitle("Hello World example");
        document.addAuthor("Bruno Lowagie");
        document.addSubject("This example shows how to add metadata");
        document.addKeywords("Metadata, iText, PDF");
        document.addCreator("My program using iText");
        document.open();
        // step 4: agregar el conteido, en este caso "Parrafo 1"
        document.add(new Paragraph("Parrafo 1"));
        // step 5: cerrar el archivo que se creo
        document.close();
    }
    
    /**
     * manipular el archivo PDF original (src) con el archivo de destino (dest) 
     * como resultado de la manipulacion
     * @param src: archivo PDF de origen
     * @param dest: archivo de destino
     * @throws IOException
     * @throws DocumentException
     */
    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        HashMap<String, String> info = reader.getInfo();
        info.put("Title", "Hello World stamped");
        info.put("Subject", "Hello World with changed metadata");
        info.put("Keywords", "iText in Action, PdfStamper");
        info.put("Creator", "Silly standalone example");
        info.put("Author", "Also Bruno Lowagie");
        info.put("Cod", codigoFirma("104611024139"));
        stamper.setMoreInfo(info);
        stamper.close();
        reader.close();
    }
    
    public void mostrarHash() {
    }
    
    public void firmaDigital() {
//        try { 
//            KeyStore ks = KeyStore.getInstance("pkcs12");
//            ks.load(new FileInputStream("RUTA_CERTIFICADO_PFX"), "CLAVE_PRIVADA_CERTIFICADO".toCharArray());
//            String alias = (String)ks.aliases().nextElement();
//            PrivateKey key = (PrivateKey)ks.getKey(alias, "CLAVE_PRIVADA_CERTIFICADO".toCharArray());
//            Certificate[] chain = ks.getCertificateChain(alias);
//            // Recibimos como parámetro de entrada el nombre del archivo PDF a firmar
//            PdfReader reader = new PdfReader(args[0]); 
//            FileOutputStream fout = new FileOutputStream("RUTA_ARCHIVO_PDF_FIRMADO");
// 
//            // Añadimos firma al documento PDF
//            PdfStamper stp = PdfStamper.createSignature(reader, fout, '?');
//            PdfSignatureAppearance sap = stp.getSignatureAppearance();
//            sap.setCrypto(key, chain, null, PdfSignatureAppearance.WINCER_SIGNED);
//            sap.setReason("Firma PKCS12");
//            sap.setLocation("Imaginanet");
//            // Añade la firma visible. Podemos comentarla para que no sea visible.
//            sap.setVisibleSignature(new Rectangle(100,100,200,200),1,null);
//            stp.close();
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
    }
    
    
    /**
     * 
     * @param file: nombre del archivo
     * @return hashed
     * @throws Exception 
     */
    private static byte[] hashFile(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());

        byte[] bytesBuffer = new byte[1024];
        int bytesRead = 0;

        while ((bytesRead = fis.read(bytesBuffer)) != -1) {
            digest.update(bytesBuffer, 0, bytesRead);
        }

        byte[] hashed = digest.digest();
        return hashed;
    }
    
    /**
     * 
     * @param ruta
     * @return 
     */
    public String obtenerHash(String ruta) {
        try {
            File fichero = new File(ruta); 
            FileInputStream ficheroStream = new FileInputStream(fichero); 
            byte contenido[] = new byte[(int)fichero.length()]; 
            ficheroStream.read(contenido);
            return getHash(contenido);
        } catch(Exception e){ return null; }
    }

    /**
     * 
     * @param digest
     * @return 
     */
    private String toHexadecimal(byte[] digest){ 
//        String hash = ""; 
//        for(byte aux : digest) { 
//            int b = aux & 0xff; 
//            if (Integer.toHexString(b).length() == 1) hash += "0"; 
//            hash += Integer.toHexString(b); 
//        } 
//        return hash; 

        String hash = ""; 
        for(byte aux : digest) { 
            int b = aux & 0xff; 
            if (Integer.toHexString(b).length() == 1) hash += "0"; 
            hash += Integer.toHexString(b); 
        } 
        return hash; 
    } 

    /**
     * 
     * @param contenido
     * @return 
     */
    public String getHash(byte[] contenido){ 

        byte[] digest = null; 
        try { 
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.reset(); 
            messageDigest.update(contenido); 
            digest = messageDigest.digest(); 
        } catch (NoSuchAlgorithmException ex) { 
            System.out.println("Error creando Hash"); 
        } 
        return toHexadecimal(digest); 
    }
    
    /***
     * Funcion para crea una firma que sera utilizada para guardar los 
     * archivos en openkm. Toma el codigo del estudiante y agrega una cadena
     * de caracteres aleatoria. Cada publicacion creada se guarda en una carpeta
     * en el openkm que tiene como nombre esta firma.
     * @param codigoEstudiante
     * @return 
     */
    public String codigoFirma(String codigoEstudiante) {
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while ( i < 6){
            char c = (char)r.nextInt(255);
            if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
                cadenaAleatoria += c;
                i ++;
            }
        }
        return codigoEstudiante + "_" + cadenaAleatoria;
    }
    //El codigo comentado es solo para probar la funcionalidad. 
     /* public static void main(String[] args){ 
          System.out.println("hola"+ " MD2 "+ getHash("hola",0)); 
          System.out.println("hola"+ " MD5 "+ getHash("hola",1)); 
          System.out.println("hola"+ " SHA-1 "+ getHash("hola",2)); 
          System.out.println("hola"+ " SHA-256 "+ getHash("hola",3)); 
          System.out.println("hola"+ " SHA-384 "+ getHash("hola",4)); 
          System.out.println("hola"+ " SHA-512 "+ getHash("hola",5)); 
      }*/ 
}
    
    
//    public void addLtv(String src, String dest, OcspClient ocsp, CrlClient crl, TSAClient tsa)
//        throws IOException, DocumentException, GeneralSecurityException {
//        PdfReader r = new PdfReader(src);
//        FileOutputStream fos = new FileOutputStream(dest);
//        PdfStamper stp = PdfStamper.createSignature(r, fos, '\0', null, true);
//        LtvVerification v = stp.getLtvVerification();
//        AcroFields fields = stp.getAcroFields();
//        List<String> names = fields.getSignatureNames();
//        String sigName = names.get(names.size() - 1);
//        PdfPKCS7 pkcs7 = fields.verifySignature(sigName);
//        if (pkcs7.isTsp()) {
//            v.addVerification(sigName, ocsp, crl,
//                LtvVerification.CertificateOption.SIGNING_CERTIFICATE,
//                LtvVerification.Level.OCSP_CRL,
//                LtvVerification.CertificateInclusion.NO);
//        }
//        else {
//            for (String name : names) {
//                v.addVerification(name, ocsp, crl,
//                    LtvVerification.CertificateOption.WHOLE_CHAIN,
//                    LtvVerification.Level.OCSP_CRL,
//                    LtvVerification.CertificateInclusion.NO);
//            }
//        }
//        PdfSignatureAppearance sap = stp.getSignatureAppearance();
//        LtvTimestamp.timestamp(sap, tsa, null);
//    }
    

