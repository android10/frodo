package com.fernandocejas.example.frodo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataPresenter {

  public void getData() {
    sleep(5);
  }

  public void getData(String name) {
    sleep(5);
  }

  public void getData(String name, int quantity) {
    sleep(5);
  }

  public void getData(String name, int quantity, int count) {
    sleep(5);
  }

  public void printMessage(String tag, String message) {
    sleep(10);
  }

  public void anotherMethodOne() {
    sleep(25);
  }

  public void anotherMethodTwo() {
    sleep(18);
  }

  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void executeDiskIOTaskOnUiThread() {
    try {
      File file = File.createTempFile("test", ".txt");
      FileWriter writer = new FileWriter(file);
      writer.write("This is Fernando Cejas testing something");
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
    }
  }
}
