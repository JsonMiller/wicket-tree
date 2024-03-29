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
package wickettree.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.wicket.model.IDetachable;

/**
 * An inverse set.
 * 
 * @author Sven Meier
 */
public class InverseSet<T> implements Set<T>, IDetachable
{

	private static final long serialVersionUID = 1L;

	private Set<T> set;

	/**
	 * Create a full set.
	 * 
	 * @param set
	 *            the contained set
	 */
	public InverseSet(Set<T> set)
	{
		this.set = set;
	}

	public void detach()
	{
		if (set instanceof IDetachable)
		{
			((IDetachable)set).detach();
		}
	}

	public boolean isEmpty()
	{
		return !set.isEmpty();
	}

	public boolean contains(Object o)
	{
		return !set.contains(o);
	}

	public boolean add(T t)
	{
		return set.remove(t);
	}

	@SuppressWarnings("unchecked")
	public boolean remove(Object o)
	{
		return set.add((T)o);
	}

	public boolean addAll(Collection<? extends T> ts)
	{
		boolean changed = false;

		for (T t : ts)
		{
			changed |= set.remove(t);
		}

		return changed;
	}

	public boolean containsAll(Collection<?> cs)
	{
		for (Object c : cs)
		{
			if (set.contains(c))
			{
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean removeAll(Collection<?> cs)
	{
		boolean changed = false;

		for (Object c : cs)
		{
			changed |= set.add((T)c);
		}

		return changed;
	}

	public int size()
	{
		throw new UnsupportedOperationException();
	}

	public void clear()
	{
		throw new UnsupportedOperationException();
	}

	public Iterator<T> iterator()
	{
		throw new UnsupportedOperationException();
	}

	public boolean retainAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	public Object[] toArray()
	{
		throw new UnsupportedOperationException();
	}

	public <S> S[] toArray(S[] a)
	{
		throw new UnsupportedOperationException();
	}
}
