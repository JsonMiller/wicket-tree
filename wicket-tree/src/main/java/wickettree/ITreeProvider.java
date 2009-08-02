/*
 * Copyright 2009 Sven Meier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wickettree;

import java.util.Iterator;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

/**
 * Provider of a tree.
 * 
 * You can use the {@link IDetachable#detach()} method for cleaning up your
 * ITreeProvider instance.
 * 
 * @see IDetachable
 * @see AbstractTree
 * 
 * @author Sven Meier
 */
public interface ITreeProvider<T> extends IDetachable
{

	/**
	 * Get the roots of the tree.
	 * 
	 * @return roots
	 */
	Iterator<T> getRoots();

	/**
	 * Does the given object have children - note that this method may return
	 * <code>true</code> even if {@link #getChildren(Object)} returns an empty
	 * iterator.
	 */
	boolean hasChildren(T object);

	/**
	 * Get the children of the given object.
	 * 
	 * @return roots
	 */
	Iterator<T> getChildren(T object);

	/**
	 * Callback used by the consumer of this tree provider to wrap objects
	 * retrieved from {@link #getRoots()} or {@link #getChildren(Object)} with a
	 * model (usually a detachable one).
	 * 
	 * @param object
	 *            the object that needs to be wrapped
	 * 
	 * @return the model representation of the object
	 */
	IModel<T> model(T object);
}
