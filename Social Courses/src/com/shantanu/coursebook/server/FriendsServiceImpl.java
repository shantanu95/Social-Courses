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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.shantanu.coursebook.client.FriendsService;
import com.shantanu.coursebook.client.MyUser;
import com.shantanu.coursebook.client.SearchedUser;

@SuppressWarnings("serial")
public class FriendsServiceImpl extends RemoteServiceServlet implements FriendsService {

	@Override
	public MyUser userFetch(String userId) {
		// TODO Auto-generated method stub
		
		MyUser u = ofy().load().type(MyUser.class).id(userId).get();
		return u;
	}

	@Override
	public void accept(String first, String second) {
		// TODO Auto-generated method stub
		MyUser u1 = ofy().load().type(MyUser.class).id(first).get();
		MyUser u2 = ofy().load().type(MyUser.class).id(second).get();
		
		u1.friends.add(second);
		u1.frndReqReceived.remove(second);
		u2.friends.add(first);
		u2.frndReqSent.remove(first);
		
		ofy().save().entities(u1, u2).now();
	}

	@Override
	public void reject(String first, String second) {
		// TODO Auto-generated method stub
		MyUser u1 = ofy().load().type(MyUser.class).id(first).get();
		MyUser u2 = ofy().load().type(MyUser.class).id(second).get();
		
		u1.frndReqReceived.remove(second);
		u2.frndReqSent.remove(first);
		
		ofy().save().entities(u1, u2).now();
		
	}

	@Override
	public void cancel(String first, String second) {
		// TODO Auto-generated method stub
		MyUser u1 = ofy().load().type(MyUser.class).id(first).get();
		MyUser u2 = ofy().load().type(MyUser.class).id(second).get();
		
		u1.frndReqSent.remove(second);
		u2.frndReqReceived.remove(first);
		
		ofy().save().entities(u1, u2).now();
		
	}

	@Override
	public void unfriend(String first, String second) {
		// TODO Auto-generated method stub
		MyUser u1 = ofy().load().type(MyUser.class).id(first).get();
		MyUser u2 = ofy().load().type(MyUser.class).id(second).get();
		
		u1.friends.remove(second);
		u2.friends.remove(first);
		
		ofy().save().entities(u1, u2).now();
		
	}

	@Override
	public ArrayList<SearchedUser> getSearch(String userId, String in) {
		// TODO Auto-generated method stub
		MyUser u = ofy().load().type(MyUser.class).id(userId).get();
		
		List<MyUser> ls = ofy().load().type(MyUser.class).filter("uniAddr", u.uniAddr).list();
		
		ArrayList<SearchedUser> ls1 = new ArrayList<SearchedUser>();
		for(MyUser k : ls){
			if(k.name.contains(in) && !k.userId.equals(userId)){
				SearchedUser su = new SearchedUser();
				su.userId = k.userId;
				su.name = k.name;
				su.type = u.friends.contains(k.userId)? 1 : u.frndReqReceived.contains(k.userId) ? 2 :
					u.frndReqSent.contains(k.userId) ? 3 : 4;
				ls1.add(su);
			}
		}
		
		return ls1;
	}

	@Override
	public void sendRequest(String first, String second) {
		// TODO Auto-generated method stub
		MyUser u1 = ofy().load().type(MyUser.class).id(first).get();
		MyUser u2 = ofy().load().type(MyUser.class).id(second).get();
		
		u1.frndReqSent.add(second);
		u2.frndReqReceived.add(first);
		
		ofy().save().entities(u1, u2).now();
		
	}


}
