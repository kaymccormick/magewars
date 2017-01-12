package us.heptet.magewars.jsinterop;

import jsinterop.annotations.JsType;

/**
 * Created by jade on 20/08/2016.
 */
@JsType(isNative = true)
public class Authentication {
    public String principal;
    public native String getPrincipal();
}

