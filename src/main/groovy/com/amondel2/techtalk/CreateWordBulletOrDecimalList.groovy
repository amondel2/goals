package com.amondel2.techtalk

import groovy.transform.CompileStatic
import org.docx4j.XmlUtils
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.wml.Br
import org.docx4j.wml.ObjectFactory
import org.docx4j.wml.P
import org.docx4j.wml.R
import org.docx4j.wml.STBrType


public class CreateWordBulletOrDecimalList {
    ReportsService reportsService
    CreateWordBulletOrDecimalList() {
        reportsService = new ReportsService()
    }

    @CompileStatic
    String createBulletsFromList(List str) {
        StringBuilder strb = new StringBuilder()
        strb.append("<ul>")
        str?.each{
            if(it instanceof List) {
                strb.append(createBulletsFromList(it))
            } else {
                strb.append("<li>${reportsService.charEncode(it.toString())}</li>")
            }
        }
        strb.append("</ul>")
        strb.toString()
    }

    P addPageBreak() {
        ObjectFactory wmlObjectFactory = new ObjectFactory()
        P p = wmlObjectFactory.createP();
        // Create object for r
        R r = wmlObjectFactory.createR();
        p.getContent().add(r);
        // Create object for br
        Br br = wmlObjectFactory.createBr();
        r.getContent().add(br);
        br.setType(org.docx4j.wml.STBrType.PAGE);
        p
    }

    void createDoc() {

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();

        mdp.addStyledParagraphOfText("Title", "Hello World!");

        mdp.addParagraphOfText("Paragraph 1");

        mdp.getContent().add(addPageBreak())

        // Add the XHTML altChunk
        String xhtml = this.createBulletsFromList(["1","2",["3","4"],"5"])
        mdp.addAltChunk(AltChunkType.Xhtml, xhtml.getBytes());

        mdp.getContent().add(addPageBreak())

        mdp.addParagraphOfText("Paragraph 3");

        // Round trip
        WordprocessingMLPackage pkgOut = mdp.convertAltChunks();

        // Display result
        System.out.println(
                XmlUtils.marshaltoString(pkgOut.getMainDocumentPart().getJaxbElement(), true, true));

        pkgOut.save(new java.io.File("AltChunkXHTMLRoundTrip.docx"));
//        FileOutputStream out = new FileOutputStream("CreateWordBulletOrDecimalList.docx");
//        wordPackage.save(out)

    }




}