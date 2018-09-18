package io.untaek.animal;

public class NativeAdapter {
  static {
    System.loadLibrary("media");
  }

  public native int hello();
  public native String helloWorld();
  public static native int getThumbnails(String path);
  public static native String cut();
}
