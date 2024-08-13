package org.parakeetnest.parakeet4j.completion;


@FunctionalInterface
public interface ChunkHandler<T, R> {
    R handle(T input);
}
