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

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IObjectClassAwareModel;

/**
 * A column utilizing other {@link IColumn}s depending on a row's model object
 * type.
 * 
 * @see #wrap(Class, IColumn)
 */
public class MultiColumn<T> implements IStyledColumn<T>
{

	private IColumn<T> mainColumn;

	private IModel<String> displayModel;

	private String sortProperty;

	private Map<Class<?>, IColumn<?>> columns = new HashMap<Class<?>, IColumn<?>>();

	/**
	 * Create an initially empty column.
	 * 
	 * @param displayModel
	 *            model for header
	 */
	public MultiColumn(IModel<String> displayModel)
	{
		this(displayModel, null);
	}

	/**
	 * Create an initially empty column.
	 * 
	 * @param displayModel
	 *            model for header
	 * @param sortProperty
	 *            the sort property
	 */
	public MultiColumn(IModel<String> displayModel, String sortProperty)
	{
		this.displayModel = displayModel;
		this.sortProperty = sortProperty;
	}

	/**
	 * Wrap the given column specifying header model and sort property
	 * explicitely.
	 * 
	 * @param displayModel
	 *            model for header
	 * @param sortProperty
	 *            sort property
	 * @param type
	 *            row type
	 * @param column
	 *            wrapped column
	 */
	public <S> MultiColumn(IModel<String> displayModel, String sortProperty, Class<S> type,
			IColumn<S> column)
	{
		this(displayModel, sortProperty);

		wrap(type, column);
	}

	/**
	 * Wrap the given column, delegating header creation and sorting.
	 * 
	 * @param clazz
	 *            type of column
	 * @param column
	 *            wrapped column
	 * 
	 * @see IColumn#getHeader(String)
	 * @see IColumn#getSortProperty()
	 */
	@SuppressWarnings("unchecked")
	public <S> MultiColumn(Class<S> clazz, IColumn<S> column)
	{
		this.mainColumn = (IColumn<T>)column;

		wrap(clazz, column);
	}

	/**
	 * Wrap another {@link IColumn} for the given type.
	 * 
	 * @param type
	 *            row type
	 * @param column
	 *            wrapped column
	 * @return this
	 */
	public <S> MultiColumn<T> wrap(Class<S> type, IColumn<S> column)
	{
		columns.put(type, column);

		return this;
	}

	public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId,
			IModel<T> rowModel)
	{
		IColumn<T> column = getColumn(getObjectClass(rowModel));

		if (column == null)
		{
			cellItem.add(new WebMarkupContainer(componentId)
			{
				public boolean isVisible()
				{
					return false;
				};
			});
		}
		else
		{
			column.populateItem(cellItem, componentId, rowModel);
		}
	}

	/**
	 * Get a suitable {@link IColumn} for the given type.
	 * 
	 * @param type
	 *            type to get column for
	 * @return column or <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	private IColumn<T> getColumn(Class<?> type)
	{
		while (type != Object.class)
		{
			IColumn<T> column = (IColumn<T>)columns.get(type);
			if (column != null)
			{
				return column;
			}
			type = type.getSuperclass();
		}

		return null;
	}

	/**
	 * Get the object class for the given model.
	 * 
	 * @param model
	 *            model
	 * @return object class
	 */
	private Class<? extends Object> getObjectClass(IModel<T> model)
	{
		if (model instanceof IObjectClassAwareModel<?>)
		{
			return ((IObjectClassAwareModel<T>)model).getObjectClass();
		}
		else
		{
			return model.getObject().getClass();
		}
	}

	public Component getHeader(String componentId)
	{
		if (this.mainColumn != null)
		{
			return this.mainColumn.getHeader(componentId);
		}
		else
		{
			return new Label(componentId, displayModel);
		}
	}

	public String getSortProperty()
	{
		if (this.mainColumn != null)
		{
			return this.mainColumn.getSortProperty();
		}
		else
		{
			return this.sortProperty;
		}
	}

	public boolean isSortable()
	{
		if (this.mainColumn != null)
		{
			return this.mainColumn.isSortable();
		}
		else
		{
			return this.sortProperty != null;
		}
	}

	public String getCssClass()
	{
		if (this.mainColumn instanceof IStyledColumn<?>) {
			return ((IStyledColumn<?>)this.mainColumn).getCssClass();
		} else {
			return null;
		}
	}
	
	public void detach()
	{
		if (this.displayModel != null)
		{
			this.displayModel.detach();
		}
	}
}
