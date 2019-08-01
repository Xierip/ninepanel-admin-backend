package dev.nine.ninepanel.infrastructure.converter;

@FunctionalInterface
interface Converter<R, T> {

  R convert(T value);

}
