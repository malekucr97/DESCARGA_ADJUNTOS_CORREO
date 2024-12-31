package com.inra;

import clases.inra.InformacionDescargaAdjuntos;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;


import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;

/**
 *
 * @author marco
 */
@WebService(serviceName = "DescargaArchivosAdjuntos", targetNamespace = "http://inra.com/")
public class DescargaArchivosAdjuntos {

    @WebMethod(operationName = "DescargaArchivosAdjuntos")
    @WebResult(name = "respuesta")
    public String DescargaArchivosAdjuntos(@WebParam(name = "oReq", targetNamespace = "")
                                    InformacionDescargaAdjuntos oReq) {
        
        InformacionDescargaAdjuntos informacionParametro = new InformacionDescargaAdjuntos();
        String respuesta = "Respuesta Oracle WebService";
        
        informacionParametro = oReq;
        String extension = "";
        
        Properties props = new Properties();
        
        try {
            
            props.setProperty("mail.store.protocol", "imaps");

            props.put("mail.imaps.auth", "true");
            props.put("mail.imaps.host", informacionParametro.getHost());
            props.put("mail.imaps.port", informacionParametro.getPort());

            props.put("mail.imaps.starttls.enable", "true");
            props.put("mail.imaps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            props.put("mail.imaps.ssl.enable", "true");
            props.put("mail.imaps.ssl.checkserveridentity", "false");
            props.put("mail.imaps.ssl.trust", "*");

            props.put("mail.imaps.ssl.checkserveridentity", "false");

            props.setProperty("mail.imaps.socketFactory.fallback", "false");
            props.setProperty("mail.imaps.socketFactory.port", informacionParametro.getPort());

            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            
            store.connect(informacionParametro.getHost(), informacionParametro.getUserName(), informacionParametro.getPassword());

            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_WRITE);

            Message[] arrayMessages = folderInbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (int i = 0; i < arrayMessages.length; i++) {
                Message message = arrayMessages[i];

                String contentType = message.getContentType();
                String messageContent = "";

                String attachFiles = "";
                String idCorreo = generarRandom();

                if (contentType.contains("multipart")) {

                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {

                            String fileName = part.getFileName();
                            
                            if (fileName != null){
                            
                                fileName = MimeUtility.decodeText(fileName);

                                fileName = fileName.replace(" ", "");
                                fileName = fileName.replace("=", "");
                                fileName = fileName.replace("?", "");
                                fileName = fileName.replace(".", "");
                                fileName = fileName.replace("-", "");
                                fileName = fileName.replace("_", "");
                                fileName = fileName.replace("°", "");
                                fileName = fileName.replace("/", "");
                                fileName = fileName.replace("#", "");
                                fileName = fileName.replace(";", "");
                                fileName = fileName.replace("&", "");

                                extension = "." + fileName.substring(fileName.length() - 3, fileName.length());

                                if (extension.equals(".pdf") || extension.equals(".xml") || extension.equals(".PDF") || extension.equals(".XML")){

                                    fileName = fileName.replace("pdf", "");
                                    fileName = fileName.replace("xml", "");
                                    fileName = fileName.replace("PDF", "");
                                    fileName = fileName.replace("XML", "");

                                } else {

                                    if(fileName.indexOf("xmiso") > 0){
                                        extension = ".xml";
                                    }
                                }

                                if (extension.equals(".pdf") || extension.equals(".xml") || extension.equals(".PDF") || extension.equals(".XML")){

                                    fileName = fileName + extension;

                                    attachFiles += fileName + ", ";
                                    part.saveFile(informacionParametro.getDir() + File.separator + idCorreo + "-" + fileName);
                                }
                            
                            }
 
                        } else {
                            messageContent = part.getContent().toString();
                        }
                    }

                    if (attachFiles.length() > 1) {
                        attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                    }
                } else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                    Object content = message.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                }
            }

            folderInbox.close(false);
            store.close();
            
        } catch (NoSuchProviderException ex) { respuesta = "Error 1.0: " +  ex.getMessage();
            
        } catch (MessagingException ex) { respuesta = "Error 2.0: " + ex.getMessage();
            
        } catch (IOException ex) { respuesta = "Error 3.0: " + ex.getMessage(); }
    
        return respuesta;
    }
    
    public static String generarRandom() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }
}