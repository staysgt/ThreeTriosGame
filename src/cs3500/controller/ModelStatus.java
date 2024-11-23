package cs3500.controller;


import java.util.EventListener;

public interface ModelStatus {

  void addEventListener(EventListener listener);

  void removeEventListener(EventListener listener);

}
