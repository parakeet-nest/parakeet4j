package org.parakeetnest.parakeet4j.content;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Content {

    /**
     * A function that splits a given text into chunks of a specified size with overlap.
     *
     * @param  text        the text to be chunked
     * @param  chunkSize   the size of each chunk
     * @param  overlap     the overlap between chunks
     * @return             an array of chunked text
     */
    public static String[] ChunkText(String text, int chunkSize, int overlap) {
        List<String> chunks = new ArrayList<>();
        for (int start = 0; start < text.length(); start += chunkSize - overlap) {
            int end = Math.min(start + chunkSize, text.length());
            chunks.add(text.substring(start, end));
        }
        return chunks.toArray(new String[0]);
    }

    /**
     * Splits a given text into an array of substrings based on a specified delimiter.
     *
     * @param  text        the text to be split
     * @param  delimiter   the delimiter used to split the text
     * @return             an array of substrings split from the original text
     */
    public static String[] SplitTextWithDelimiter(String text, String delimiter) {
        return text.split(delimiter);
    }

    /**
     * Splits a given text into an array of substrings based on a specified regular expression delimiter.
     *
     * @param  text            the text to be split
     * @param  regexDelimiter  the regular expression delimiter used to split the text
     * @return                 an array of substrings split from the original text
     */
    /*
    public static String[] _SplitTextWithRegex(String text, String regexDelimiter) {
        String[] result = text.split(regexDelimiter);
        return result;
    }
    */

    public static String[] SplitTextWithRegex(String text, String regexDelimiter) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(regexDelimiter);
        Matcher matcher = pattern.matcher(text);
        int start = 0;
        while (matcher.find()) {
            result.add(text.substring(start, matcher.start()));
            start = matcher.end();
        }
        result.add(text.substring(start));
        return result.toArray(new String[0]);
    }

    /**
     * Generates a context string from an array of documents.
     *
     * @param  docs   the array of documents
     * @return         the context string
     */
    public static String GenerateContextFromDocs(String[] docs) {
        StringBuilder documentsContent = new StringBuilder("<context>\n");
        for (String doc : docs) {
            documentsContent.append("<doc>").append(doc).append("</doc>\n");
        }
        documentsContent.append("</context>");
        return documentsContent.toString();
    }
}
