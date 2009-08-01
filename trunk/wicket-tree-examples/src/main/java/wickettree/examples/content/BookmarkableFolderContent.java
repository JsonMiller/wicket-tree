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

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;

import wickettree.AbstractTree;
import wickettree.content.Folder;
import wickettree.examples.Foo;
import wickettree.examples.FooProvider;

/**
 * @author Sven Meier
 */
public class BookmarkableFolderContent extends Content
{

	private static final long serialVersionUID = 1L;

	public BookmarkableFolderContent(final AbstractTree<Foo> tree)
	{
		String id = tree.getRequest().getParameter("foo");
		if (id != null)
		{
			Foo foo = FooProvider.get(id);
			while (foo != null)
			{
				tree.getModel().getObject().add(foo);
				foo = foo.getParent();
			}
		}
	}

	@Override
	public Component newContentComponent(String id, final AbstractTree<Foo> tree, IModel<Foo> model)
	{
		return new Folder<Foo>(id, tree, model)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected MarkupContainer newLinkComponent(String id, IModel<Foo> model)
			{
				Foo foo = model.getObject();

				if (!tree.getProvider().hasChildren(foo))
				{
					return new BookmarkablePageLink<Void>(id, tree.getPage().getClass(),
							new PageParameters("foo=" + foo.getId()));
				}
				else
				{
					return super.newLinkComponent(id, model);
				}
			}
		};
	}
}