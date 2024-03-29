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
package wickettree.examples.content;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import wickettree.AbstractTree;
import wickettree.examples.Foo;

/**
 * @author Sven Meier
 */
public class MixedContent extends Content
{

	private static final long serialVersionUID = 1L;

	private List<Content> contents;

	public MixedContent(List<Content> contents)
	{
		this.contents =  new ArrayList<Content>(contents);
	}

	@Override
	public Component newContentComponent(String id, final AbstractTree<Foo> tree, IModel<Foo> model)
	{
		int index = (int)(Math.random() * contents.size()) % contents.size();

		return contents.get(index).newContentComponent(id, tree, model);
	}
}