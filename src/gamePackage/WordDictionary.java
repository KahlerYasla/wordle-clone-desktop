package gamePackage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordDictionary
{
    public List<String> wordsList = new ArrayList<String>();

    public String getWord()
    {
        File wordFile = new File("valid-wordle-words.txt");

        try
        {
            wordsList = Files.readAllLines(wordFile.toPath());
        }
        catch(IOException ex)
        {

        }
        Random random = new Random();
        String wordOfTheDay = wordsList.get(random.nextInt(wordsList.size()));
        wordOfTheDay.toUpperCase();
        System.out.println(wordOfTheDay);

        return wordOfTheDay;
    }


    public WordDictionary() {

    }

}
