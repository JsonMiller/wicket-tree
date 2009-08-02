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

import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.apache.wicket.session.ISessionStore;

/**
 * @author Sven Meier
 */
public class WicketApplication extends WebApplication
{
	public WicketApplication()
	{
	}

	@Override
	protected void init()
	{
		try
		{
			new Thread().start();
		}
		catch (Exception threadsNotAllowed)
		{
			getResourceSettings().setResourcePollFrequency(null);
		}

		mount(new QueryStringUrlCodingStrategy("nested", NestedTreePage.class));
		mount(new QueryStringUrlCodingStrategy("table", TableTreePage.class));
	}

	public Class<NestedTreePage> getHomePage()
	{
		return NestedTreePage.class;
	}

	@Override
	protected ISessionStore newSessionStore()
	{
		// assume no disk present
		return new HttpSessionStore(this);
	}
}
