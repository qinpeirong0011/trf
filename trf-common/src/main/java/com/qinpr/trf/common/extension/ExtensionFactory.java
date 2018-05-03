package com.qinpr.trf.common.extension;

/**
 * Created by qinpr on 18/4/27.
 */
@SPI
public interface ExtensionFactory {

    /**
     * Get extension
     *
     * @param type
     * @param name
     * @param <T>
     * @return
     */
    <T> T getExtension(Class<T> type, String name);
}
