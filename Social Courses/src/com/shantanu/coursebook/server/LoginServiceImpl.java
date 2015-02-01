/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.shantanu.coursebook.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.shantanu.coursebook.client.LoginService;
import com.shantanu.coursebook.client.MyUser;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	@Override
	public String login(String username, String password) {
		// TODO Auto-generated method stub
		UserPass u = ofy().load().type(UserPass.class).id(username).get();
		
		if(u != null && u.password.equals(password))
			return "OK";
		else
			return "NO";
	}

	@Override
	public String signUp(String name, String uniAddr, String password, int year) {
		// TODO Auto-generated method stub
		MyUser u = new MyUser();
		UserPass u1 = new UserPass();
		
		u.name = name;
		String email[] = uniAddr.split("@");
		u.userId = email[0] + "@" + email[1];
		u1.userId = u.userId;
		u.year = year;
		u.uniAddr = email[1];
		u1.password = password;
		ofy().save().entities(u, u1).now();
		
		
		return null;
	}
}
