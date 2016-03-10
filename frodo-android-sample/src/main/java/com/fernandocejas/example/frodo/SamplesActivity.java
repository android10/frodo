package com.fernandocejas.example.frodo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.fernandocejas.example.frodo.sample.MySubscriber;
import com.fernandocejas.example.frodo.sample.MySubscriberBackpressure;
import com.fernandocejas.example.frodo.sample.MySubscriberVoid;
import com.fernandocejas.example.frodo.sample.ObservableSample;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SamplesActivity extends Activity {

  private Button btnRxLogObservable;
  private Button btnRxLogSubscriber;

  private View.OnClickListener rxLogObservableListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      ObservableSample observableSample = new ObservableSample();

      observableSample.stringItemWithDefer()
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe();

      observableSample.numbers()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
              toastMessage("onNext() Integer--> " + String.valueOf(integer));
            }
          });

      observableSample.moreNumbers().toList().toBlocking().single();

      observableSample.names()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Action1<String>() {
            @Override
            public void call(String string) {
              toastMessage("onNext() String--> " + string);
            }
          });

      observableSample.error()
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
              //nothing here
            }

            @Override
            public void onError(Throwable e) {
              toastMessage("onError() --> " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
              //nothing here
            }
          });

      observableSample.list()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Action1<List<ObservableSample.MyDummyClass>>() {
            @Override
            public void call(List<ObservableSample.MyDummyClass> myDummyClasses) {
              toastMessage("onNext() List--> " + myDummyClasses.toString());
            }
          });

      observableSample.doNothing()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe();

      observableSample.doSomething(v)
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe();

      observableSample.sendNull()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe();
    }
  };

  private View.OnClickListener rxLogSubscriberListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      final ObservableSample observableSample = new ObservableSample();
      toastMessage("Subscribing to observables...Check logcat output...");

      observableSample.strings()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MySubscriber());

      observableSample.stringsWithError()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MySubscriber());

      observableSample.numbersBackpressure()
          .onBackpressureDrop()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MySubscriberBackpressure());

      observableSample.doNothing()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MySubscriberVoid());

      observableSample.doSomething(v)
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MySubscriberVoid());

      observableSample.sendNull()
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new MySubscriber());
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_samples);
    this.mapGUI();
  }

  private void mapGUI() {
    this.btnRxLogObservable = (Button) findViewById(R.id.btnRxLogObservable);
    this.btnRxLogSubscriber = (Button) findViewById(R.id.btnRxLogSubscriber);

    this.btnRxLogObservable.setOnClickListener(rxLogObservableListener);
    this.btnRxLogSubscriber.setOnClickListener(rxLogSubscriberListener);
  }

  private void toastMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}
