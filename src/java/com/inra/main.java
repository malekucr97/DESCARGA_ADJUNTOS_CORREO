
package com.inra;

import clases.inra.InformacionDescargaAdjuntos;
import java.security.GeneralSecurityException;

/**
 *
 * @author marco
 */
public class main {

    public static void main(String[] args) throws GeneralSecurityException {
        DescargaArchivosAdjuntos arc = new DescargaArchivosAdjuntos();
        
        InformacionDescargaAdjuntos oReq = new InformacionDescargaAdjuntos();
        
        oReq.setHost("imap.gmail.com");
        oReq.setPort("993");
        oReq.setUserName("email@gmail.com");
        oReq.setPassword("pass");
        oReq.setDir("D:\\Clientes\\DescargaDocsProveedores");
        
        //oReq.setHost("imap.gmail.com");
        //oReq.setPort("993");
        //oReq.setUserName("facturaelectronica@solucionesinra.com");
        //oReq.setPassword("Inra123456*");
        //oReq.setDir("D:\\Clientes\\INRA\\DescargaDocsProveedores");
        
        arc.DescargaArchivosAdjuntos(oReq);
    }
}