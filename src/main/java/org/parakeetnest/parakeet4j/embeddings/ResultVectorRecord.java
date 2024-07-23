package org.parakeetnest.parakeet4j.embeddings;

import org.parakeetnest.parakeet4j.llm.Answer;
import org.parakeetnest.parakeet4j.llm.VectorRecord;

import java.util.Optional;

public class ResultVectorRecord {
    private VectorRecord vectorRecord;
    private Exception exception;

    public ResultVectorRecord() {}

    public ResultVectorRecord(VectorRecord vectorRecord, Exception exception) {
        this.vectorRecord = vectorRecord;
        this.exception = exception;
    }

    public VectorRecord getVectorRecord() {
        return vectorRecord;
    }

    public void setVectorRecord(VectorRecord vectorRecord) {
        this.vectorRecord = vectorRecord;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Optional<Exception> exception() {
        if(exception != null) {
            return Optional.of(exception);
        } else {
            return Optional.empty();
        }
    }

    public Optional<VectorRecord> vectorRecord() {
        if(vectorRecord != null) {
            return Optional.of(vectorRecord);
        } else {
            return Optional.empty();
        }
    }
}
