package me.espryth.trollgui.util;

import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FieldReflect<T> {

  private final Field field;

  public FieldReflect(final @NotNull Field field) {
    this.field = field;
  }

  @SuppressWarnings("unchecked")
  public @Nullable T get(final @NotNull Object object) {
    try {
      return (T) field.get(object);
    } catch (final IllegalAccessException e) {
      throw new IllegalStateException(e);
    }
  }

  public void set(final @NotNull Object object, final @Nullable T value) {
    try {
      field.set(object, value);
    } catch (final IllegalAccessException e) {
      throw new IllegalStateException(e);
    }
  }


  public static <T> FieldReflect<T> findFieldByType(
      final @NotNull Class<?> clazz,
      final @NotNull Class<? super T> type
  ) {
    Field value = null;
    for (final var field : clazz.getDeclaredFields()) {
      if (field.getType() == type) {
        value = field;
        field.setAccessible(true);
      }
    }
    if (value == null) {
      throw new IllegalStateException("Field with type " + type + " not found in " + clazz);
    }
    return new FieldReflect<>(value);
  }
}