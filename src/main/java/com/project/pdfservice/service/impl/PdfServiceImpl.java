package com.project.pdfservice.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.project.pdfservice.Item;
import com.project.pdfservice.Person;
import com.project.pdfservice.service.DbServiceClient;
import com.project.pdfservice.service.PdfService;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Collections;


@Service
public class PdfServiceImpl implements PdfService {
    @Autowired
    private DbServiceClient dbServiceClient;
    @Override
      public  HttpEntity<byte[]> createPdf(
              Long id)  {
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            velocityEngine.init();
            Person person = dbServiceClient.getPerson(id);
            Template template = velocityEngine.getTemplate("templates/pdftemplate.vm");
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("person",person);
            velocityContext.put("genDateTime", LocalDateTime.now().toString());

            StringWriter stringWriter = new StringWriter();
            template.merge(velocityContext, stringWriter);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byteArrayOutputStream = generatePdf(stringWriter.toString());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + person.getName());
            headers.setContentLength(byteArrayOutputStream.toByteArray().length);

            return new HttpEntity<byte[]>(byteArrayOutputStream.toByteArray(),headers);
        }
   /* public Person createPerson(){
        Person person = new Person();
        person.setName("Gabe");
        person.setAddress("rue 92");
        Item item = new Item();
        item.setId(4L);
        item.setName("item1");
        item.setDescription("fragile item");
        person.setItems(Collections.singleton(item));
        return person;
    }*/
        public ByteArrayOutputStream generatePdf(String html){
            String pdfFilePath ="";
            PdfWriter pdfWriter = null;

            Document document = new Document();
            try{

                document = new Document();
                document.addAuthor("Gabriel");
                document.addCreationDate();
                document.addProducer();
                document.addTitle("Create PDF using Velocity");
                document.setPageSize(PageSize.LETTER);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PdfWriter.getInstance(document,byteArrayOutputStream);

                document.open();

                XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
                xmlWorkerHelper.getDefaultCssResolver(true);
                xmlWorkerHelper.parseXHtml(pdfWriter,document,new StringReader(html));

                document.close();
                System.out.println("PDF generated successfully");

                return byteArrayOutputStream;
            } catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

