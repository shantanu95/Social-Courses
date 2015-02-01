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
package com.shantanu.coursebook.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("CourseService")
public interface CourseService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static CourseServiceAsync instance;
		public static CourseServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(CourseService.class);
			}
			return instance;
		}
	}
	
	void createCourse(String userId, CourseData cd);
	ArrayList<CourseData> getCourseList(String userId, Date d);
	void deleteCourse(Long id, String userId);
	
	ArrayList<String> getFriendList(String userId);
	ArrayList<String> computeFreeTime(String u1, String u2, Date d);
	
}
