package org.parakeetnest.parakeet4j.completion;

import org.parakeetnest.parakeet4j.llm.Answer;

import java.util.Optional;

public class ResultAnswer {
    private Answer answer;
    private Exception exception;

    public ResultAnswer() {}
    public ResultAnswer(Answer answer, Exception exception) {
        this.answer = answer;
        this.exception = exception;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
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

    public Optional<Answer> answer() {
        if(answer != null) {
            return Optional.of(answer);
        } else {
            return Optional.empty();
        }
    }
}
