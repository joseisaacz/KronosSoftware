package com.kronos.model;

public class NotificationDTO {

		private String accNumber;
		private String user;
		private Department dep;
		
		public NotificationDTO() {
			super();
		}
		public NotificationDTO(String accNumber, String user, Department dep) {
			super();
			this.accNumber = accNumber;
			this.user = user;
			this.dep = dep;
		}
		public String getAccNumber() {
			return accNumber;
		}
		public void setAccNumber(String accNumber) {
			this.accNumber = accNumber;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public Department getDep() {
			return dep;
		}
		public void setDep(Department dep) {
			this.dep = dep;
		}
		
		
}
