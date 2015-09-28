package org.lecture.compiler.testframework;

/*
 * Copyright (c) 2015 Rene Richter.
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.lecture.compiler.api.ExerciseContext;


/**
 * Base class for unit tests used by java-evaluation-service.
 * @author Rene Richter
 */
public abstract class AbstractTest {

  @Rule
  public Timeout globalTimeout = new Timeout(10000);
  private ExerciseContext exerciseContext;

  /**
   * Injects the exerciseContext. This method gets called by the spring-impl.
   * Do <strong>NOT</strong> call this method unless for unit-test purposes.
   *
   * @param exerciseContext the injected exerciseContext.
   */
  public void setExerciseContainer(ExerciseContext exerciseContext) {
    this.exerciseContext = exerciseContext;
  }

  /**
   * Creates an object of a submitted exercise-class.
   * You can use this method to create an instance of an exercise-class.
   * By the time the concrete test is written, the implementation is not yet present.
   * Therefore, you can't instantiate or otherwise access any exercise-class
   * directly (in a traditional manner) within your testcase. This method uses
   * reflection and ensures, that you operate on the correct exercise context
   * with the correct classloader. Do not use this method for classes outside
   * the context of the given exercise.
   *
   * <p><strong> example </strong></p>
   * <pre>
   *  <code>
   * {@literal @}Test
   * public void testFooBar()
   * {
   * Object target = createObject("Foo");
   * Object result = executeMethod(foo,"bar");
   * assertEquals("baz",(String)result);
   * }
   * </code>
   * </pre>
   *
   * @param className The name of the class you want to instantiate.
   * @param params    The constructor arguments.
   * @return An instance of the given class.
   */

  protected Object createObject(String className, Object... params) {
    return exerciseContext.createObject(className, params);
  }

  /**
   * Executes the method {@code  method} on the given Object {@code target}.
   * This method allows your concrete test classesto execute a method on the given object.
   * The object has to be an instance of the submitted classes of the specified exercise.
   * By the time the concrete test is written, the implementation is not yet present.
   * Therefore, you can't instantiate or otherwise access any exercise-class directly
   * (in a traditional manner) within your testcase.
   * This method uses reflection and ensures, that you operate on the
   * correct exercise context with the correct classloader.
   * <p> <strong>example</strong> </p>
   * <pre>
   * <code>
   * {@literal @}Test
   * public void testFoo()
   * {
   * Object target = createObject("Foo");
   * Object result = executeMethod(foo,"bar");
   * assertEquals("baz",(String)result);
   * }
   * </code>
   * </pre>
   *
   * @param target The object you want to execute the method on.
   *               See {@link #createObject(String, Object...) }
   * @param method The name of the method you want to execute. Case sensitive
   * @param params The arguments the method requires.
   * @return The result of the method call.
   */
  protected Object executeMethod(Object target, String method, Object... params) {
    return exerciseContext.executeMethod(target, method, params);
  }

}
