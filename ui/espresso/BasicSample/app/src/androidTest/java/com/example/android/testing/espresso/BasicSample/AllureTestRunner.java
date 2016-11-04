package com.example.android.testing.espresso.BasicSample;

import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.internal.util.AndroidRunnerParams;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import ru.yandex.qatools.allure.junit.AllureRunListener;

/**
 * Created by Kirill Zhukov on 11/4/16.
 * <p/>
 * Copyright 2016 Evernote Corporation. All rights reserved.
 */

public class AllureTestRunner extends AndroidJUnit4ClassRunner {
  public AllureTestRunner(Class<?> klass) throws InitializationError {
    super(klass, new AndroidRunnerParams(InstrumentationRegistry.getInstrumentation(), new Bundle(), false, 0, false));
  }

  public AllureTestRunner(Class<?> klass, AndroidRunnerParams runnerParams) throws InitializationError {
    super(klass, runnerParams);
  }

  @Override
  public void run(RunNotifier notifier) {
    // JUnit don't support Java SPI for adding test listeners.
    // To add global Listener we should manually create JUnitCore.
    // With gradle we don't have (?) such option.
    // Solution - add listener via runner.
    // And manually call "runStarted" and "runFinished" events.
    notifier.addListener(new AllureRunListener());

    // Allure do nothing on testRunStarted
    // Just for consistency.
    notifier.fireTestRunStarted(Description.createSuiteDescription("Tests"));

    super.run(notifier);

    // Allure don't use Result from JUnit.
    // It gathers results on its own.
    // So, basically any param is OK here.
    notifier.fireTestRunFinished(new Result());
  }
}
