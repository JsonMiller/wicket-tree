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
package wickettree.table;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * @author Sven Meier
 */
public class TreeColumn<T> extends AbstractTreeColumn<T>
{

	private static final long serialVersionUID = 1L;

	public TreeColumn(IModel<String> displayModel)
	{
		super(displayModel);
	}

	public TreeColumn(IModel<String> displayModel, String sortProperty)
	{
		super(displayModel, sortProperty);
	}

	@Override
	public String getCssClass()
	{
		return "tree";
	}

	public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId,
			IModel<T> rowModel)
	{

		NodeModel<T> nodeModel = (NodeModel<T>)rowModel;

		Component nodeComponent = getTree().newNodeComponent(componentId,
				nodeModel.getWrappedModel());

		nodeComponent.add(new NodeBorder(nodeModel.getBranches()));

		cellItem.add(nodeComponent);
	}
}
