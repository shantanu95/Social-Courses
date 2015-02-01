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
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.shantanu.coursebook.client.CourseData;
import com.shantanu.coursebook.client.CourseService;
import com.shantanu.coursebook.client.MyUser;

@SuppressWarnings("serial")
public class CourseServiceImpl extends RemoteServiceServlet implements CourseService {

	@Override
	public void createCourse(String userId, CourseData cd) {
		// TODO Auto-generated method stub
		cd.parent = ofy().load().type(MyUser.class).id(userId).key();
		ofy().save().entity(cd).now();
	}

	@SuppressWarnings("deprecation")
	@Override
	public ArrayList<CourseData> getCourseList(String userId, Date d) {
		// TODO Auto-generated method stub
		Key<MyUser> p = ofy().load().type(MyUser.class).id(userId).key();
		List<CourseData> ls = ofy().load().type(CourseData.class).ancestor(p).list();
		
		ArrayList<CourseData> arr = new ArrayList<>();
		
		for(CourseData cd : ls){
			d.setHours(cd.startdate.getHours());
			d.setMinutes(cd.startdate.getMinutes());
			d.setSeconds(cd.startdate.getSeconds());
			DateTime dt1 = new DateTime(d);
			DateTime dt2 = new DateTime(cd.startdate);
			int days = Days.daysBetween(dt1, dt2).getDays();
			
			if(days%(7*cd.frequency) == 0)
				arr.add(cd);
		}
		
		return arr;
	}

	@Override
	public void deleteCourse(Long id, String userId) {
		// TODO Auto-generated method stub
		Key<MyUser> p = ofy().load().type(MyUser.class).id(userId).key();
		ofy().delete().type(CourseData.class).parent(p).id(id).now();
	}

	@Override
	public ArrayList<String> getFriendList(String userId) {
		// TODO Auto-generated method stub
		return (ArrayList<String>) ofy().load().type(MyUser.class).id(userId).get().friends;
	}

	@Override
	public ArrayList<String> computeFreeTime(String u1, String u2, Date d) {
		// TODO Auto-generated method stub
		ArrayList<CourseData> l1 = getCourseList(u1, d);
		ArrayList<CourseData> l2 = getCourseList(u2, d);
		
		ArrayList<Integer> freeTime = new ArrayList<Integer>();
		ArrayList<Integer> freeTime1 = new ArrayList<Integer>();
		
		for(int i=1; i<=48; i++){
			int stHH = (i-1)/2 % 24, stMM = i%2==0 ? 30 : 0;
			if(freeSlot(stHH, stMM, l1) && freeSlot(stHH, stMM, l2))
				freeTime.add(i);
		}
		
		for(int x:freeTime){
			int timeS = ((x-1)/2 % 24)*100 + (x%2==0 ? 30 : 0);
			int timeE = timeS%100==30 ? timeS+70 : timeS+30;
			if(freeTime1.contains(timeS))
				freeTime1.remove(new Integer(timeS));
			else
				freeTime1.add(new Integer(timeS));
			freeTime1.add(timeE);
		}
		ArrayList<String> finalAns = new ArrayList<String>();
		for(int i=0; i<freeTime1.size()-1; i=i+2){
			finalAns.add("Free from " + padNum(freeTime1.get(i)/100) + ":" +  padNum(freeTime1.get(i)%100)
					+ " to " + padNum(freeTime1.get(i+1)/100) + ":" +  padNum(freeTime1.get(i+1)%100));
		}
		
		return finalAns;
		
		
	}
	
	private String padNum(int i){
		if(i<10)
			return "0" + i;
		else
			return ""+i;
	}
	
	private boolean freeSlot(int stHH, int stMM, ArrayList<CourseData> ls){
		int stHM = stHH*100 + stMM;
		for(CourseData cd:ls){
			if(stHM >= cd.stTime_HH*100 + cd.stTime_MM && stHM < cd.enTime_HH*100 + cd.enTime_MM)
				return false;
		}
		return true;
	}
}
