/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inra;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

@WebService(serviceName = "envioCorreo")
public class envioCorreo {

    /*
    public static void main(String args[]) {
        try {
            envioCorreo e = new envioCorreo();
            e.envio("mail.gpgca.co.cr", "465", "facturacion@corbel.co.cr", "corf4ctur@c10n!", "jvega@solucionesinra.com", "", "", "Prueba", "<html><h3>Prueba</h3></html>", "");
        } catch (MessagingException ex) {
            Logger.getLogger(envioCorreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    public static void main(String[] args) throws MessagingException {

        /* String host = "pop.gmail.com";//"mail.gpgca.co.cr";
        String port = "995";
        String userName = "corbelcr@gmail.com";//facturacion@corbel.co.cr"; //username for the mail you want to read
        String password = "Passwordcbl1";//"corf4ctur@c10n!"; //password
         */
        /*
        String host = "mail.gpgca.co.cr";
        String port = "110";
        String userName = "facturacion@corbel.co.cr"; //username for the mail you want to read
        String password = "corf4ctur@c10n!"; //password

        String saveDirectory = "D:\\Pruebaslecturacorreo\\correos";
        */
        
        
        //leer gmail
        //String host = "pop.gmail.com";
        //String port = "995";
        
        //enviar gmail  
        
        //String host = "smtp.gmail.com";
        //String port = "587";
        
        //imap.
        //143 imap 110 pop  
        
        //tls 143 imap 110 pop 587
        
        //ssl 993 imap 995 pop 465 smpt      
                
        //leer corbel
        //String host = "mail.gpgca.co.cr";
        //String port = "143";
        
        //enviar corbel
        //String host = "mail.gpgca.co.cr";
        //String port = "25";
        
        
        //String userName = "cblhacienda@corbel.co.cr"; //username for the mail you want to read
        //String password = "C0r@C8lh@c13n@!%@"; //password
                           
            
        //String destino = "efallas@solucionesinra.com";
        //String asunto = "Prueba Envió desde corbel #1";
        //String mensaje = "Mensaje de test #1";
        
        
        //String userName ="efallas@solucionesinra.com";
        //String password = "Vashthe2010#stampede"; //password
        
        //String saveDirectory = "D:\\Pruebaslecturacorreo\\correos";
        
        
        
        // MACOPA
        String host = "outlook.office365.com";
        String port = "993";
        
        String userName = "email@dom.com"; //username for the mail you want to read
        String password = "pass"; //password
        
        String saveDirectory = "D:\\correos";
        
        envioCorreo e = new envioCorreo();
        e.descargar(host, port, userName, password, saveDirectory);
        //e.envio(host, port, userName, password, destino, destino, destino, asunto, mensaje, "");
        
        
        //e.generarRandom();
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

    @WebMethod(operationName = "envio")
    public void envio(@WebParam(name = "vvHost") String vvHost, @WebParam(name = "vvPuerto") String vvPuerto, @WebParam(name = "vvUsuario") String vvUsuario, @WebParam(name = "vvPass") String vvPass, @WebParam(name = "vvDestinos") String vvDestinos, @WebParam(name = "vvCopia") String vvCopia, @WebParam(name = "vvOculto") String vvOculto, @WebParam(name = "vvAsunto") String vvAsunto, @WebParam(name = "vvMensaje") String vvMensaje, @WebParam(name = "vvAdjuntos") String vvAdjuntos) throws MessagingException {

        Properties props = new Properties();
        System.setProperty("jsse.enableSNIExtension", "false");
        System.setProperty("http.proxyUser", vvUsuario);
        System.setProperty("http.proxyPassword", vvPass);
        System.setProperty("http.proxyHost", vvHost);
        System.setProperty("http.proxyPort", vvPuerto);
        props.put("mail.smtp.host", vvHost);
        props.put("mail.smtp.socketFactory.port", vvPuerto);
        props.put("mail.imap.ssl.checkserveridentity", "false");
        props.put("mail.smtp.ssl.trust", vvHost);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        if (vvPuerto.equals("465")) {
            props.put("mail.smtp.ssl.enable", "true");
        } else if (vvPuerto.equals("587")) {
            props.put("mail.smtp.starttls.enable", "true");
        }
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", vvPuerto);
        props.put("mail.smtp.socketFactory.fallback", "true");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(vvUsuario, vvPass);
            }
        });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(vvUsuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(vvDestinos));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(vvCopia));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(vvOculto));
            message.addHeader("content-type", "html/text");
            message.setSubject(vvAsunto);
            message.setSentDate(new Date());
            //message.setText(vvMensaje);

            Multipart multipart = new MimeMultipart();

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(vvMensaje, "text/html; charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            if (!vvAdjuntos.equals("")) {
                String ListaAdjuntos[] = vvAdjuntos.split(",");
                for (String adj : ListaAdjuntos) {
                    messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setHeader("content-type", "html/text");
                    DataSource source = new FileDataSource(adj);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(source.getName());
                    multipart.addBodyPart(messageBodyPart);
                }
            }

            message.setContent(multipart);
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @WebMethod(operationName = "descargar")
    public void descargar(@WebParam(name = "host") String host, @WebParam(name = "port") String port, @WebParam(name = "userName") String userName, @WebParam(name = "password") String password, @WebParam(name = "dir") String dir) {
        Properties props = new Properties();

        if (host.equals("pop.gmail.com")) {
            props.put("mail.pop3.host", host);
            props.put("mail.pop3.port", port);

            // SSL setting
            props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.pop3.socketFactory.fallback", "false");
            props.setProperty("mail.pop3.socketFactory.port", String.valueOf(port));

        } else { 
            System.setProperty("jsse.enableSNIExtension", "false");
            System.setProperty("http.proxyUser", userName);
            System.setProperty("http.proxyPassword", password);
            System.setProperty("http.proxyHost", host);
            System.setProperty("http.proxyPort", port);
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.socketFactory.port", port);
            props.put("mail.imap.ssl.checkserveridentity", "false");
            props.put("mail.smtp.ssl.trust", host);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.socketFactory.fallback", "true");

        }

        Session session = Session.getDefaultInstance(props);

        try {

            String vvTipoConexion;
            if (host.equals("pop.gmail.com")) {
                vvTipoConexion = "imaps";
            } else {
                vvTipoConexion = "imap";
            }
            Store store = session.getStore(vvTipoConexion);
            if (host.equals("pop.gmail.com")) {

                store.connect("imap.googlemail.com", userName, password);
            } else {
                store.connect(host, userName, password);
            } 
           //Store store = session.getStore("imap");
           //store.connect(host, userName, password);
               
            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_WRITE);

            Message[] arrayMessages = folderInbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (int i = 0; i < arrayMessages.length; i++) {
                Message message = arrayMessages[i];
                Address[] fromAddress = message.getFrom();
                /*String from = fromAddress[0].toString();
				String subject = message.getSubject();
				String sentDate = message.getSentDate().toString();*/

                String contentType = message.getContentType();
                String messageContent = "";

                // store attachment file name, separated by comma
                String attachFiles = "";
                String idCorreo = generarRandom();

                if (contentType.contains("multipart")) {
                    // content may contain attachments
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            // this part is attachment
                            String fileName = part.getFileName();
                            attachFiles += fileName + ", ";
                            part.saveFile(dir + File.separator + idCorreo + "-" + fileName);
                        } else {
                            // this part may be the message content
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

                /*print out details of each message
				System.out.println("Message #" + (i + 1) + ":");
				System.out.println("\t From: " + from);
				System.out.println("\t Subject: " + subject);
				System.out.println("\t Sent Date: " + sentDate);
				System.out.println("\t Message: " + messageContent);
				System.out.println("\t Attachments: " + attachFiles);*/
            }

            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for pop3.");
            ex.printStackTrace();
            System.out.println(ex);
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
            System.out.println(ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
    }
}
