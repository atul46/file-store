package com.nokia.test.util;


import org.apache.tika.Tika;
import org.kamranzafar.jtar.TarEntry;
import org.kamranzafar.jtar.TarInputStream;
import org.kamranzafar.jtar.TarOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.util.StringJoiner;
import java.util.zip.GZIPInputStream;

@Component
public class TarManager {

    @Value("${file.upload-dir}")
    private String path;

    public  boolean validateTarFile(InputStream tarFile,String fileName) {
        TarInputStream tis = null;
        TarOutputStream tos=null;
        try {
            tis = new TarInputStream(tarFile);
            // Output file stream

            FileOutputStream dest = new FileOutputStream(path.concat(fileName));
            tos= new TarOutputStream(new BufferedOutputStream(
                    dest));
            TarEntry entry;
            while ((entry = tis.getNextEntry()) != null) {
                int count;
                byte data[] = new byte[2048];
                while ((count = tis.read(data)) != -1) {
                    tos.write(data,0,count);
                }

                tos.flush();
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("File not found");
        } catch (IOException e) {
            throw new RuntimeException("IO exception");
        } finally {

            closeConnection(tis);
        }

        return true;
    }

    public boolean getMIMEType(byte[] mimeType){
        Tika tika = new Tika();
        return tika.detect(mimeType).equals("application/gzip");


    }

      private void closeConnection(TarInputStream tis){
            try {

                tis.close();
            }catch(IOException e){
                throw new RuntimeException("IO exception");
            }
        }

}



