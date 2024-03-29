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
package wickettree.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wickettree.AbstractTree;
import wickettree.TableTree;
import wickettree.table.HeadersToolbar;
import wickettree.table.NoRecordsToolbar;
import wickettree.table.NodeModel;
import wickettree.table.TreeColumn;

/**
 * @author Sven Meier
 */
public class TableTreePage extends ContentPage
{

	private static final long serialVersionUID = 1L;

	private TableTree<Foo> tree;

	@Override
	protected AbstractTree<Foo> createTree(FooProvider provider, IModel<Set<Foo>> state)
	{
		List<IColumn<Foo>> columns = createColumns();

		tree = new TableTree<Foo>("tree", columns, provider, Integer.MAX_VALUE, state)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected Component newContentComponent(String id, IModel<Foo> model)
			{
				return TableTreePage.this.newContentComponent(id, model);
			}

			@Override
			protected Item<Foo> newRowItem(String id, int index, IModel<Foo> model)
			{
				return new OddEvenItem<Foo>(id, index, model);
			}
		};
		tree.addTopToolbar(new HeadersToolbar(tree));
		tree.addBottomToolbar(new NoRecordsToolbar(tree));
		return tree;
	}

	private List<IColumn<Foo>> createColumns()
	{
		List<IColumn<Foo>> columns = new ArrayList<IColumn<Foo>>();

		columns.add(new PropertyColumn<Foo>(Model.of("ID"), "id"));

		columns.add(new TreeColumn<Foo>(Model.of("Tree")));

		columns.add(new AbstractColumn<Foo>(Model.of("Depth"))
		{
			private static final long serialVersionUID = 1L;

			public void populateItem(Item<ICellPopulator<Foo>> cellItem, String componentId,
					IModel<Foo> rowModel)
			{
				NodeModel<Foo> nodeModel = (NodeModel<Foo>)rowModel;

				cellItem.add(new Label(componentId, "" + nodeModel.getDepth()));
			}

			@Override
			public String getCssClass()
			{
				return "number";
			}
		});

		columns.add(new PropertyColumn<Foo>(Model.of("Bar"), "bar"));
		columns.add(new PropertyColumn<Foo>(Model.of("Baz"), "baz"));

		return columns;
	}
}
