/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wickettree.nested;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * A branch contains a single node and its children.
 * 
 * @author Sven Meier
 */
public final class Branch<T> extends Item<T>
{

	private static final long serialVersionUID = 1L;

	public Branch(String id, int index, IModel<T> model)
	{
		super(id, index, model);

		setOutputMarkupId(true);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);

		if (isLast())
		{
			tag.put("class", "branch-last");
		}
		else
		{
			tag.put("class", "branch");
		}
	}

	private boolean isLast()
	{
		return getIndex() == getParent().size() - 1;
	}
}