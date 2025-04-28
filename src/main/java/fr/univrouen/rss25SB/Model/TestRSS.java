package fr.univrouen.rss25SB.Model;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestRSS {
    public String loadFileXML() throws IOException {
        Resource resource = new DefaultResourceLoader()
                .getResource("item.xml");
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Lancer l'exception en cas d'erreur
        }
        return content.toString();
    }
}
