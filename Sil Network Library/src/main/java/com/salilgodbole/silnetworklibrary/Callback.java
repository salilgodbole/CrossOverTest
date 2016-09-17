package com.salilgodbole.silnetworklibrary;

/**
 * Created by salil.
 */
public interface Callback<T> {
    void success(T t);

    void error(AppError appError);
}
