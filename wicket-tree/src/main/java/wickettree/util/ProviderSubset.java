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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

import wickettree.ITreeProvider;

/**
 * A subset of a {@link ITreeProvider}'s tree offering automatic detachment.
 * 
 * Make sure that the containing model calls {@link IDetachable#detach()} on its
 * model object.
 * 
 * @see ITreeProvider#model(Object)
 * 
 * @author Sven Meier
 */
public class ProviderSubset<T> implements Set<T>, IDetachable
{

	private static final long serialVersionUID = 1L;

	private ITreeProvider<T> provider;

	private Set<IModel<T>> models = new HashSet<IModel<T>>();

	/**
	 * Create an empty subset.
	 * 
	 * @param provider
	 *            the provider of the complete set
	 */
	public ProviderSubset(ITreeProvider<T> provider)
	{
		this(provider, false);
	}

	/**
	 * Create a subset optionally containing all roots of the provider.
	 * 
	 * @param provider
	 *            the provider of the complete set
	 * @param addRoots
	 *            should all roots be added to this subset
	 */
	public ProviderSubset(ITreeProvider<T> provider, boolean addRoots)
	{
		this.provider = provider;

		if (addRoots)
		{
			Iterator<? extends T> roots = provider.getRoots();
			while (roots.hasNext())
			{
				add(roots.next());
			}
		}
	}

	public void detach()
	{
		for (IModel<T> model : models)
		{
			model.detach();
		}
	}

	public int size()
	{
		return models.size();
	}

	public boolean isEmpty()
	{
		return models.size() == 0;
	}

	public void clear()
	{
		detach();
		
		models.clear();
	}

	public boolean contains(Object o)
	{
		IModel<T> model = model(o);

		boolean contains = models.contains(model);

		model.detach();

		return contains;
	}

	public boolean add(T t)
	{
		return models.add(model(t));
	}

	public boolean remove(Object o)
	{
		IModel<T> model = model(o);

		boolean removed = models.remove(model);

		model.detach();

		return removed;
	}

	public Iterator<T> iterator()
	{
		return new Iterator<T>()
		{

			private Iterator<IModel<T>> iterator = models.iterator();

			private IModel<T> current;

			public boolean hasNext()
			{
				return iterator.hasNext();
			}

			public T next()
			{
				current = iterator.next();

				return current.getObject();
			}

			public void remove()
			{
				iterator.remove();

				current.detach();
				current = null;
			}
		};
	}

	public boolean addAll(Collection<? extends T> ts)
	{
		boolean changed = false;

		for (T t : ts)
		{
			changed |= add(t);
		}

		return changed;
	}

	public boolean containsAll(Collection<?> cs)
	{
		for (Object c : cs)
		{
			if (!contains(c))
			{
				return false;
			}
		}
		return true;
	}

	public boolean removeAll(Collection<?> cs)
	{
		boolean changed = false;

		for (Object c : cs)
		{
			changed |= remove(c);
		}

		return changed;
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

	@SuppressWarnings("unchecked")
	private IModel<T> model(Object o)
	{
		return provider.model((T)o);
	}
	
	/**
	 * Create a model holding this set.
	 * 
	 * @return model
	 */
	public IModel<Set<T>> createModel()
	{
		return new AbstractReadOnlyModel<Set<T>>()
		{
			@Override
			public Set<T> getObject()
			{
				return ProviderSubset.this;
			}
			
			@Override
			public void detach()
			{
				ProviderSubset.this.detach();
			}
		};
	}
}