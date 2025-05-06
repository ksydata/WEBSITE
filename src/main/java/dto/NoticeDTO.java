package dto;

import java.sql.Timestamp;

public class NoticeDTO {
	// 게시글 구성요소: 인덱스, 게시자 아이디, 제목, 생성일시, 수정일시, 제목, 권한
	int noticeID;
	String userID;
	String title;
	String contents;
	Timestamp createDate;
	Timestamp updateDate;
	Timestamp endDate;
	String permissionRole;
	public int getNoticeID() {
		return noticeID;
	}
	public void setNoticeID(int noticeID) {
		this.noticeID = noticeID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getPermissionRole() {
		return permissionRole;
	}
	public void setPermissionRole(String permissionRole) {
		this.permissionRole = permissionRole;
	}
	
}
