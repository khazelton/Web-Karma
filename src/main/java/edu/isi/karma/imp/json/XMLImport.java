/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isi.karma.imp.json;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.isi.karma.imp.Import;
import edu.isi.karma.rep.Worksheet;
import edu.isi.karma.rep.Workspace;
import edu.isi.karma.util.FileUtil;
import edu.isi.karma.webserver.KarmaException;

/**
 *
 * @author mielvandersande
 */
public class XMLImport extends Import {

    Import jsonImport;
    private static Logger logger = LoggerFactory.getLogger(XMLImport.class);

    public XMLImport(File xmlFile, String worksheetName, Workspace workspace) {
        super(worksheetName, workspace);

        try {
            String fileContents = FileUtil.readFileContentsToString(xmlFile);

            // Converting the XML to JSON
            JSONObject json = XML.toJSONObject(fileContents);
            jsonImport = new JsonImport(json, this.getFactory(), this.getWorksheet());
        } catch (JSONException ex) {
            logger.error("Error in populating the worksheet with XML");
        } catch (IOException ex) {
            logger.error("Error in reading the XML file");
        }
    }


    @Override
    public Worksheet generateWorksheet() throws JSONException, IOException, KarmaException, ClassNotFoundException {
        return jsonImport.generateWorksheet();
    }

}
