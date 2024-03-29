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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wickettree.AbstractTree;
import wickettree.ITreeProvider;
import wickettree.content.CheckedFolder;
import wickettree.examples.Foo;

/**
 * @author Sven Meier
 */
public class CheckedSelectableFolderContent extends SelectableFolderContent
{

	private static final long serialVersionUID = 1L;

	public CheckedSelectableFolderContent(ITreeProvider<Foo> provider)
	{
		super(provider);
	}

	@Override
	public Component newContentComponent(String id, final AbstractTree<Foo> tree, IModel<Foo> model)
	{
		return new CheckedFolder<Foo>(id, tree, model)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected IModel<Boolean> newCheckBoxModel(final IModel<Foo> model)
			{
				return new PropertyModel<Boolean>(model, "quux");
			}

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				Foo foo = getModelObject();
				
				// search first ancestor with quux not set
				while (!foo.getQuux() && foo.getParent() != null) {
					foo = foo.getParent();
				}
				
				tree.updateBranch(foo, target);
			}
			
			/**
			 * Always clickable.
			 */
			@Override
			protected boolean isClickable()
			{
				return true;
			}

			@Override
			protected void onClick(AjaxRequestTarget target)
			{
				CheckedSelectableFolderContent.this.select(getModelObject(), tree, target);
			}

			@Override
			protected boolean isSelected()
			{
				return CheckedSelectableFolderContent.this.isSelected(getModelObject());
			}
		};
	}
}