package es.ucm.fdi.iw.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Table;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name="WordList")
public class WordList {

    private List<String> wordList;

    public void readData() {
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader("../../../../../../resources/static/csv/spanish-word-list-total.csv");

            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
    
            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                                    .withSkipLines(4)
                                    .withCSVParser(parser)
                                    .build();
            List<String[]> allData = csvReader.readAll();

            wordList = new ArrayList<String>();
    
            for (String[] row : allData) {
                // Only save second col (words)
                wordList.add(row[1]);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }  

    public boolean isWord(String word) {
        return wordList.contains(word);
    }

    public String rndIfx(int ifx_length) {
        Random rand = new Random(0);
        int rnd;
        String rndWord;
        // Get random word of at least length ifx_length
        do {
            rnd = rand.nextInt(wordList.size());
            rndWord = wordList.get(rnd);
        } while(rndWord.length() < ifx_length);

        // Generate all possible intervals of length ifx_length
        List<String> possible_ifx = new ArrayList<String>();

        for (int i = 0; i < ifx_length; i++) 
            for (int len = 1; len <= ifx_length - i; len++)
                possible_ifx.add(rndWord.substring(i, len));

        rnd = rand.nextInt(possible_ifx.size());

        return possible_ifx.get(rnd);
    }
}
