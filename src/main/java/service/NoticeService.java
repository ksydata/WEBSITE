package service;


import dao.NoticeDAO;
import dao.PagingDAO;
import dto.NoticeDTO;
import dto.PagingDTO;

public class NoticeService {
//	private final NoticeDAO noticeDAO = new NoticeDAO();
	
	/*
	 * 지금 Notice 조회는 PagingService를 BoardServlet에 바로 불러와서 처리하고 있고,
	edit / delete / write / 개별 post 조회는 NoticeListDAO 등 DAO를 Servlet에 바로 불러와서 처리하고 있음. 즉 서비스가 없는 구조.
	이걸 전부 가져와서 한데 모으는 NoticeService가 필요함
	
	NoticeService에 리스트 단위 조회 (페이징 포함) / 개별 포스트 조회 / 포스트 등록 / 수정 / 삭제 전부 심어놓고 서블릿에서 로딩하는 것으로.
	 */
	
	// 공지 리스트 조회 : BoardServlet의 PagingService 적용 로직 활용
	public PagingDTO<NoticeDTO> getNoticeList(int page) {
		
		// PagingService 불러오기
		PagingService pagingService = new PagingService();

        // RowMapper 정의
        // RowMapper 안에 NoticeDTO를 채운다는 아이디어를 살리되, 형태를 약간 바꾸어 resultSet 내부에 NoticeDTO를 정의하고 내용을 채움
        PagingDAO.RowMapper<NoticeDTO> mapper = resultSet -> {
            NoticeDTO dto = new NoticeDTO();
            // 공지사항 게시글 번호
            dto.setNoticeID(resultSet.getInt("noticeID"));
            // 글 제목
            dto.setTitle(resultSet.getString("title"));
            // 글 내용
            dto.setContents(resultSet.getString("contents"));
            // 작성자 아이디(학번/사번)
            dto.setUserID(resultSet.getString("userID"));
            // 작성일자
            dto.setCreateDate(resultSet.getTimestamp("createDate"));
            return dto;
        };
        
        
        // 페이징 및 글 목록 조회
        PagingDTO<NoticeDTO> pagingObject = pagingService.getPage(
        		"NOTICE",
        		null,
        		"noticeID DESC",
        		page,
        		mapper
        		// The method getPage(String, String, String, int, PagingDAO.RowMapper<T>) in the type PagingService is not applicable for the arguments (String, null, String, int, int)
        );
        
        return pagingObject;
	}	
	
	// 개별 공지 조회
	public NoticeDTO getNotice(int id) {
		NoticeDAO noticeDAO = new NoticeDAO();
		NoticeDTO notice = noticeDAO.getNoticeByID(id);
		return notice;
	}
	
	
	// 공지 편집
	public void updatePost(int id, String title, String contents) {
		NoticeDAO noticeDAO = new NoticeDAO();
		noticeDAO.updateNotice(id, title, contents);
	}
	
	
	// 공지 작성
	public int uploadPost(String userID, String title, String contents, String endDate, String permissionRole) {
		NoticeDAO noticeDAO = new NoticeDAO();
		int noticeID = noticeDAO.uploadNotice(userID, title, contents, endDate, permissionRole);
		return noticeID;
	}
	
	
	// 공지 삭제
	public boolean deletePost(int id) {
		NoticeDAO noticeDAO = new NoticeDAO();
		if (noticeDAO.deleteNotice(id)) {
			return true;
		}
		return false;
	}
	
	
}
