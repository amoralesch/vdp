package com.amoralesch.vdp.spec;

import java.lang.reflect.Method;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.test.annotation.ProfileValueUtils;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks;
import org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks;
import org.springframework.util.ReflectionUtils;

public class SpecRunner extends ConcordionRunner {
  private static final Method withRulesMethod;

  static {
    withRulesMethod = ReflectionUtils.findMethod(SpecRunner.class,
      "withRules", FrameworkMethod.class, Object.class, Statement.class);

    if (withRulesMethod == null)
      throw new IllegalStateException("Failed to find withRules() method:" +
        "SpecRunner requires JUnit 4.9 or higher.");

    ReflectionUtils.makeAccessible(withRulesMethod);
  }

  private final TestContextManager testContextManager;

  public SpecRunner(Class<?> clazz) throws InitializationError {
    super(clazz);
    testContextManager = createTestContextManager(clazz);
  }

  protected TestContextManager createTestContextManager(Class<?> clazz) {
    return new TestContextManager(clazz);
  }

  protected final TestContextManager getTestContextManager() {
    return testContextManager;
  }

  @Override
  public Description getDescription() {
    if (!ProfileValueUtils.isTestEnabledInThisEnvironment(
      getTestClass().getJavaClass())) {
      return Description.createSuiteDescription(
        getTestClass().getJavaClass());
    }

    return super.getDescription();
  }

  @Override
  public void run(RunNotifier notifier) {
    if (!ProfileValueUtils.isTestEnabledInThisEnvironment(
      getTestClass().getJavaClass())) {
      notifier.fireTestIgnored(getDescription());

      return;
    }

    super.run(notifier);
  }

  @Override
  protected Statement withBeforeClasses(Statement statement) {
    Statement junitBeforeClasses = super.withBeforeClasses(statement);

    return new RunBeforeTestClassCallbacks(junitBeforeClasses,
      getTestContextManager());
  }

  @Override
  protected Statement withAfterClasses(Statement statement) {
    Statement junitAfterClasses = super.withAfterClasses(statement);

    return new RunAfterTestClassCallbacks(junitAfterClasses,
      getTestContextManager());
  }

  @Override
  protected Object createTest() throws Exception {
    Object testInstance = super.createTest();
    getTestContextManager().prepareTestInstance(testInstance);

    return testInstance;
  }
}
